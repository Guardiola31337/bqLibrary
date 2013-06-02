package com.bqlibrary.guardiola.dropbox;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Context;
import android.os.AsyncTask;
import com.bqlibrary.guardiola.general.Utils;
import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.DropboxAPI.Entry;
import com.dropbox.client2.DropboxAPI.DropboxFileInfo;
import com.dropbox.client2.exception.*;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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

    private FileOutputStream mFos;

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

                // This will cancel the getEpub operation by closing
                // its stream
                if (mFos != null) {
                    try {
                        mFos.close();
                    } catch (IOException e) {
                    }
                }
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

        String cachePath = mContext.getCacheDir().getAbsolutePath() + "/" + dirent.fileName();
        try {
            mFos = new FileOutputStream(cachePath);
        } catch (FileNotFoundException e) {
            mErrorMsg = "Couldn't create a local file to store the epub";
            return false;
        }

        // This downloads the actual epub file
        DropboxFileInfo info = null;
        try {
            info = mApi.getFile(mPath, null, mFos, null);
            System.out.println("epub downloaded ok!");
        } catch (DropboxException e) {
            mErrorMsg = "Couldn't get epub file";
            return false;
        } finally {
            if (mFos != null) {
                try {
                    mFos.close();
                } catch (IOException e) {}
            }
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
