package com.bqlibrary.guardiola.dropbox;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.AsyncTask;
import com.bqlibrary.guardiola.general.Utils;
import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.DropboxAPI.DropboxInputStream;
import com.dropbox.client2.DropboxAPI.Entry;
import com.dropbox.client2.exception.DropboxException;
import nl.siegmann.epublib.domain.Book;
import nl.siegmann.epublib.epub.EpubReader;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: Administrador
 * Date: 2/06/13
 * Time: 11:54
 * To change this template use File | Settings | File Templates.
 */
public class DownloadEpubFile extends AsyncTask<Void, Long, Boolean> {

    private Context mContext;
    private final ProgressDialog mDialog;
    private DropboxAPI<?> mApi;
    private String mPath;

    private boolean mCanceled;
    private Long mFileLen;
    private String mErrorMsg;

    public DownloadEpubFile(Context context, DropboxAPI<?> api,
                            String dropboxPath) {
        // We set the context this way so we don't accidentally leak activities
        mContext = context.getApplicationContext();

        mApi = api;
        mPath = dropboxPath;

        mDialog = new ProgressDialog(context);
        mDialog.setMessage("Downloading Epub");
        mDialog.setButton("Cancel", new OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                mCanceled = true;
                mErrorMsg = "Canceled";
            }
        });

        mDialog.show();
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        if (mCanceled) {
            return false;
        }

        // Get the metadata for the epub given
        Entry dirent = null;
        try {
            dirent = mApi.metadata(mPath, 1, null, false, null);
        } catch (DropboxException e) {
            mErrorMsg = "Couldn't get epub metadata";
            return false;
        }

        if (mCanceled) {
            return false;
        }

        mFileLen = dirent.bytes;

        // This downloads the actual epub file
        DropboxInputStream info = null;
        try {
            info = mApi.getFileStream(mPath, null);
            System.out.println("epub downloaded ok!");
            if (mCanceled) {
                return false;
            }
            Book book = null;
            try {
                book = (new EpubReader()).readEpub(info);
                // Log the book's authors
                System.out.println("epublib author(s): " + book.getMetadata().getAuthors());
                // Log the book's title
                System.out.println("epublib title: " + book.getTitle());
            } catch (IOException e) {
                mErrorMsg = "Couldn't create epub book";
                return false;
            }

        } catch (DropboxException e) {
            mErrorMsg = "Couldn't get epub file";
            return false;
        }

        if (mCanceled) {
            return false;
        }

        // We must have a legitimate epub
        return true;
    }

    @Override
    protected void onProgressUpdate(Long... progress) {
        int percent = (int)(100.0*(double)progress[0]/mFileLen + 0.5);
        mDialog.setProgress(percent);
    }

    @Override
    protected void onPostExecute(Boolean result) {
        mDialog.dismiss();
        if (result) {
            // Mock
            Utils.showToast("Downloaded!", mContext);
        } else {
            // Couldn't download it, so show an error
            Utils.showToast(mErrorMsg, mContext);
        }
    }
}
