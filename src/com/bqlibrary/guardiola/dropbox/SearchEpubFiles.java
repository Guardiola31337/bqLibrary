package com.bqlibrary.guardiola.dropbox;

import android.content.Context;
import android.os.AsyncTask;
import com.bqlibrary.guardiola.general.Constants;
import com.bqlibrary.guardiola.general.Utils;
import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.DropboxAPI.Entry;
import com.dropbox.client2.exception.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Administrador
 * Date: 31/05/13
 * Time: 17:22
 * To change this template use File | Settings | File Templates.
 */
public class SearchEpubFiles extends AsyncTask<Void, Long, Boolean> {

    private DropboxAPI<?> mApi;
    private String mPath;

    private Context mContext;
    private boolean mCanceled;

    private String mErrorMsg;

    private ArrayList<String> epubListNames;

    public SearchEpubFiles(Context context, DropboxAPI<?> api, String dropboxPath) {
        // We set the context this way so we don't accidentally leak activities
        mContext = context.getApplicationContext();
        this.mApi = api;
        this.mPath = dropboxPath;
    }

    @Override
    protected Boolean doInBackground(Void... params) {

        if (mCanceled) {
            return false;
        }

        // Get the epub files of the path
        List<Entry> epubFiles = new ArrayList<Entry>();
        try {
            epubFiles = mApi.search(mPath, Constants.EPUB, 0, false);
        } catch (DropboxException e) {
            mErrorMsg = "Couldn't search epub files";
            return false;
        }

        if (epubFiles.size() == 0) {
            // There aren't epub files
            mErrorMsg = "There aren't epub files";
            return false;
        }

        // Make a list of the epub files names
        epubListNames = new ArrayList<String>();
        for(Entry ent : epubFiles) {
            // Add it to the list of epub files names
            epubListNames.add(ent.fileName());
        }

        if (mCanceled) {
            return false;
        }

        // We must have a legitimate list of epub files
        return true;

    }

    @Override
    protected void onPostExecute(Boolean result) {
        if (result) {
            // Mock
            for (String epubName : epubListNames) {
                System.out.println("epubName: " + epubName);
            }
            System.out.println("Epub number files: " + epubListNames.size());
            Utils.showToast("Epub files successfully got", mContext);
        } else {
            Utils.showToast(mErrorMsg, mContext);
        }
    }
}
