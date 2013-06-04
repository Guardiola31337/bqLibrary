package com.bqlibrary.guardiola.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.bqlibrary.guardiola.dropbox.DownloadEpubFile;
import com.bqlibrary.guardiola.dropbox.SearchEpubFiles;
import com.bqlibrary.guardiola.general.Constants;
import com.bqlibrary.guardiola.general.Functions;
import com.bqlibrary.guardiola.gui.adapters.EpubAdapter;
import com.bqlibrary.guardiola.model.Epub;
import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.android.AndroidAuthSession;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created with IntelliJ IDEA.
 * User: Administrador
 * Date: 1/06/13
 * Time: 20:12
 * To change this template use File | Settings | File Templates.
 */
public class LibraryActivity extends ActBase {

    public final static String TAG = "[com.bqlibrary.guardiola.activities.LibraryActivity]";

    private GridView mLibraryGridView;
    private OnItemLongClickListener gridComponentListener;

    private EpubAdapter mEpubAdapter;
    private ArrayList<Epub> mEpubList;

    DropboxAPI<AndroidAuthSession> mApi;

    private int mPosEpubSelected;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.options, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // This is called when the Home (Up) button is pressed
                // in the Action Bar.
                onBackPressed();
                break;
            case R.id.menuSortName:
                Collections.sort(mEpubList, new Comparator<Epub>() {
                    @Override
                    public int compare(final Epub object1, final Epub object2) {
                        return object1.getmNameEpub().compareTo(object2.getmNameEpub());
                    }
                });
                // Create the adapter with the new epubs list order
                mEpubAdapter = new EpubAdapter(mEpubList, this);
                // and set it with the grid view to refresh
                mLibraryGridView.setAdapter(mEpubAdapter);
                break;
            case R.id.menuSortDate:
                Collections.sort(mEpubList, new Comparator<Epub>() {
                    @Override
                    public int compare(final Epub object1, final Epub object2) {
                        return object1.getmDateCreated().compareTo(object2.getmDateCreated());
                    }
                });
                // Create the adapter with the new epubs list order
                mEpubAdapter = new EpubAdapter(mEpubList, this);
                // and set it with the grid view to refresh
                mLibraryGridView.setAdapter(mEpubAdapter);
                break;
        }
        return true;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // We create a new AuthSession so that we can use the Dropbox API.
        AndroidAuthSession session = Functions.buildSession(this);
        mApi = new DropboxAPI<AndroidAuthSession>(session);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getXMLToInflate() {
        return R.layout.epubs;
    }

    @Override
    protected void dataReceived(Bundle extras) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    protected void dataToSave(Bundle extras) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    protected void initViews() {
        mLibraryGridView = (GridView) findViewById(R.id.epub_grid_view);
    }

    @Override
    protected void initValues() {
        // Implementing up navigation
        getActionBar().setDisplayHomeAsUpEnabled(true);
        // Make a list of the epub files
        mEpubList = new ArrayList<Epub>();
        mEpubAdapter = new EpubAdapter(mEpubList, getApplicationContext());

    }

    @Override
    protected void initListeners() {
        // Long Press is a recommeded interaction in the UI Guidelines, double touch is not
        gridComponentListener = new OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                mPosEpubSelected = position;
                if(mEpubList.get(position).getmSselected()) {
                    mEpubList.get(position).setmSselected(false);
                }
                else {
                    mEpubList.get(position).setmSselected(true);
                    if(!mEpubList.get(position).getmDownloaded()) {
                        // Starts the task for downloading the epub file
                        DownloadEpubFile downloadEpubFile = new DownloadEpubFile(LibraryActivity.this, mApi,
                                mEpubList.get(position), handler);
                        downloadEpubFile.execute();
                    }
                    else {
                        //Create a Dialog to display the thumbnail
                        AlertDialog.Builder thumbDialog = new AlertDialog.Builder(LibraryActivity.this);
                        ImageView thumbView = new ImageView(LibraryActivity.this);
                        thumbView.setImageBitmap(mEpubList.get(position).getmCover());
                        LinearLayout layout = new LinearLayout(LibraryActivity.this);
                        layout.setOrientation(LinearLayout.VERTICAL);
                        layout.addView(thumbView);
                        thumbDialog.setView(layout);
                        thumbDialog.setNegativeButton(Constants.CLOSE_DIALOG, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // if this button is clicked, just close
                                // the dialog box and do nothing
                                dialog.cancel();
                            }
                        });
                        thumbDialog.show();
                    }
                }
                // Create the adapter with the new epubs list order
                mEpubAdapter = new EpubAdapter(mEpubList, getApplicationContext());
                // and set it with the grid view to refresh
                mLibraryGridView.setAdapter(mEpubAdapter);
                return false;
            }
        };
        mLibraryGridView.setOnItemLongClickListener(gridComponentListener);
    }

    @Override
    protected void init() {
        // Starts the task for searching the epub files in Dropbox
        SearchEpubFiles searchEpubFiles = new SearchEpubFiles(LibraryActivity.this, mApi, "/", mEpubList, mEpubAdapter,
                mLibraryGridView);
        searchEpubFiles.execute();

    }

    @Override
    public void click(View view) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    protected void handleMessageReceived(int what, int arg1, int arg2, Object obj) {
        // Download book response OK
        if (what == Constants.WHAT_DOWNLOAD_EPUB_OK) {
            mEpubList.get(mPosEpubSelected).setmDownloaded(true);
            //Create a Dialog to display the thumbnail
            AlertDialog.Builder thumbDialog = new AlertDialog.Builder(LibraryActivity.this);
            ImageView thumbView = new ImageView(LibraryActivity.this);
            thumbView.setImageBitmap(mEpubList.get(mPosEpubSelected).getmCover());
            LinearLayout layout = new LinearLayout(LibraryActivity.this);
            layout.setOrientation(LinearLayout.VERTICAL);
            layout.addView(thumbView);
            thumbDialog.setView(layout);
            thumbDialog.setNegativeButton(Constants.CLOSE_DIALOG, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // if this button is clicked, just close
                    // the dialog box and do nothing
                    dialog.cancel();
                }
            });
            thumbDialog.show();

        }
        // Download book ERROR
        else if(what == Constants.WHAT_ERROR_DOWNLOAD_EPUB) {

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    protected boolean keyPressed(int keyCode, KeyEvent event) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void showLoading() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void hideLoading() {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
