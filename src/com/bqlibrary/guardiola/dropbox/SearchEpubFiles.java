package com.bqlibrary.guardiola.dropbox;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.GridView;
import com.bqlibrary.guardiola.activities.R;
import com.bqlibrary.guardiola.general.Constants;
import com.bqlibrary.guardiola.general.Utils;
import com.bqlibrary.guardiola.gui.adapters.EpubAdapter;
import com.bqlibrary.guardiola.model.Epub;
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

    public final static String TAG = "[com.bqlibrary.guardiola.dropbox.SearchEpubFiles]";

    private DropboxAPI<?> mApi;
    private String mPath;

    private Context mContext;
    private boolean mCanceled;

    private String mErrorMsg;

    private ArrayList<Epub> mEpubList;
    private EpubAdapter mEpubAdapter;
    private GridView mLibraryGridView;

    private Bitmap mIcon;

    public SearchEpubFiles(Context context, DropboxAPI<?> api, String dropboxPath, ArrayList<Epub> epubList,
                           EpubAdapter epubAdapter, GridView libraryGridView) {
        // We set the context this way so we don't accidentally leak activities
        mContext = context.getApplicationContext();
        this.mApi = api;
        this.mPath = dropboxPath;
        this.mEpubList = epubList;
        this.mEpubAdapter = epubAdapter;
        this.mLibraryGridView = libraryGridView;
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
        Epub epub;
        // Mock - Load a generic icon
        mIcon = BitmapFactory.decodeResource(mContext.getResources(),
                R.drawable.address_book);
        for(Entry ent : epubFiles) {
            // Create a epub item
            epub = new Epub(ent.fileName(), ent.path, mIcon);
            // Add it to the list of epub files
            mEpubList.add(epub);
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
            // Once we have the result, create the adapter with the epubs list
            mEpubAdapter = new EpubAdapter(mEpubList, mContext);
            // and set it with the grid view
            mLibraryGridView.setAdapter(mEpubAdapter);
            // Mock
            for (Epub epub : mEpubList) {
                System.out.println("epubName: " + epub.getmNameEpub());
            }
            System.out.println("Epub number files: " + mEpubList.size());
            Utils.showToast("Epub files successfully got", mContext);
        } else {
            Utils.showToast(mErrorMsg, mContext);
        }
    }
}
