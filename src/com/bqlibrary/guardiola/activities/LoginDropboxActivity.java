package com.bqlibrary.guardiola.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import com.bqlibrary.guardiola.dropbox.SearchEpubFiles;
import com.bqlibrary.guardiola.general.Functions;
import com.bqlibrary.guardiola.general.Utils;
import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.android.AndroidAuthSession;
import com.dropbox.client2.session.TokenPair;

public class LoginDropboxActivity extends ActBase {

    public final static String TAG = "[com.bqlibrary.guardiola.activities.LoginDropboxActivity]";

    DropboxAPI<AndroidAuthSession> mApi;

    private boolean mLoggedIn;

    // Android widgets
    private Button mSubmit;

    private Button mSearch;

    // Android listeners
    private OnClickListener mSubmitListener;

    private OnClickListener mSearchListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // We create a new AuthSession so that we can use the Dropbox API.
        AndroidAuthSession session = Functions.buildSession(this);
        mApi = new DropboxAPI<AndroidAuthSession>(session);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getXMLToInflate() {
        return R.layout.login;
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
        mSubmit = (Button)findViewById(R.id.auth_button);
        mSearch = (Button)findViewById(R.id.search_button);
    }

    @Override
    protected void initValues() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    protected void initListeners() {
        mSubmitListener = new OnClickListener() {
            @Override
            public void onClick(View v) {
                // This logs you out if you're logged in, or vice versa
                if (mLoggedIn) {
                    logOut();
                } else {
                    // Start the remote authentication
                    mApi.getSession().startAuthentication(LoginDropboxActivity.this);
                }
            }
        };
        mSubmit.setOnClickListener(mSubmitListener);

        mSearchListener = new OnClickListener() {
            @Override
            public void onClick(View v) {
                // This logs you out if you're logged in, or vice versa
                if (mLoggedIn) {
                    // Start library activity
                    Intent i = new Intent(LoginDropboxActivity.this, LibraryActivity.class);
                    startActivity(i);
                } else {
                    // Start the remote authentication
                    mApi.getSession().startAuthentication(LoginDropboxActivity.this);
                }
            }
        };
        mSearch.setOnClickListener(mSearchListener);
    }

    @Override
    protected void init() {
        // Display the proper UI state if logged in or not
        setLoggedIn(mApi.getSession().isLinked());
    }

    @Override
    public void click(View view) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    protected void handleMessageReceived(int what, int arg1, int arg2, Object obj) {
        //To change body of implemented methods use File | Settings | File Templates.
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

    @Override
    public void onResume() {
        super.onResume();
        AndroidAuthSession session = mApi.getSession();

        // The next part must be inserted in the onResume() method of the
        // activity from which session.startAuthentication() was called, so
        // that Dropbox authentication completes properly.
        if (session.authenticationSuccessful()) {
            try {
                // Mandatory call to complete the auth
                session.finishAuthentication();

                // Store it locally in our app for later use
                TokenPair tokens = session.getAccessTokenPair();
                Functions.storeKeys(tokens.key, tokens.secret, this);
                setLoggedIn(true);
            } catch (IllegalStateException e) {
                Utils.showToast("Couldn't authenticate with Dropbox:" + e.getLocalizedMessage(), this);
                Log.i(TAG, "Error authenticating", e);
            }
        }
    }

    private void logOut() {
        // Remove credentials from the session
        mApi.getSession().unlink();

        // Clear our stored keys
        Functions.clearKeys(this);
        // Change UI state to display logged out version
        setLoggedIn(false);
    }

    /**
     * Convenience function to change UI state based on being logged in
     */
    private void setLoggedIn(boolean loggedIn) {
        mLoggedIn = loggedIn;
        if (loggedIn) {
            mSubmit.setText("Unlink from Dropbox");
        } else {
            mSubmit.setText("Link with Dropbox");
        }
    }
}