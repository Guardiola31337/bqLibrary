package com.bqlibrary.guardiola.activities;

import android.util.Log;
import android.view.View.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import com.bqlibrary.guardiola.general.Constants;
import com.bqlibrary.guardiola.general.Utils;
import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.android.AndroidAuthSession;
import com.dropbox.client2.session.AccessTokenPair;
import com.dropbox.client2.session.AppKeyPair;
import com.dropbox.client2.session.TokenPair;

public class LoginDropboxActivity extends ActBase {

    public final static String TAG = "[com.bqlibrary.guardiola.activities.LoginDropboxActivity]";

    DropboxAPI<AndroidAuthSession> mApi;

    private boolean mLoggedIn;

    // Android widgets
    private Button mSubmit;

    // Android listeners
    private OnClickListener mSubmitListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // We create a new AuthSession so that we can use the Dropbox API.
        AndroidAuthSession session = buildSession();
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
                storeKeys(tokens.key, tokens.secret);
                setLoggedIn(true);
            } catch (IllegalStateException e) {
                Utils.showToast("Couldn't authenticate with Dropbox:" + e.getLocalizedMessage(), this);
                Log.i(TAG, "Error authenticating", e);
            }
        }
    }

    private AndroidAuthSession buildSession() {
        AppKeyPair appKeyPair = new AppKeyPair(Constants.APP_KEY, Constants.APP_SECRET);
        AndroidAuthSession session;

        String[] stored = getKeys();
        if (stored != null) {
            AccessTokenPair accessToken = new AccessTokenPair(stored[0], stored[1]);
            session = new AndroidAuthSession(appKeyPair, Constants.ACCESS_TYPE, accessToken);
        } else {
            session = new AndroidAuthSession(appKeyPair, Constants.ACCESS_TYPE);
        }

        return session;
    }

    /**
     * Shows keeping the access keys returned from Trusted Authenticator in a local
     * store, rather than storing user name & password, and re-authenticating each
     * time (which is not to be done, ever).
     *
     * @return Array of [access_key, access_secret], or null if none stored
     */
    private String[] getKeys() {
        SharedPreferences prefs = getSharedPreferences(Constants.ACCOUNT_PREFS_NAME, 0);
        String key = prefs.getString(Constants.ACCESS_KEY_NAME, null);
        String secret = prefs.getString(Constants.ACCESS_SECRET_NAME, null);
        if (key != null && secret != null) {
            String[] ret = new String[2];
            ret[0] = key;
            ret[1] = secret;
            return ret;
        } else {
            return null;
        }
    }

    /**
     * Shows keeping the access keys returned from Trusted Authenticator in a local
     * store, rather than storing user name & password, and re-authenticating each
     * time (which is not to be done, ever).
     */
    private void storeKeys(String key, String secret) {
        // Save the access key for later
        SharedPreferences prefs = getSharedPreferences(Constants.ACCOUNT_PREFS_NAME, 0);
        Editor edit = prefs.edit();
        edit.putString(Constants.ACCESS_KEY_NAME, key);
        edit.putString(Constants.ACCESS_SECRET_NAME, secret);
        edit.commit();
    }

    private void logOut() {
        // Remove credentials from the session
        mApi.getSession().unlink();

        // Clear our stored keys
        clearKeys();
        // Change UI state to display logged out version
        setLoggedIn(false);
    }

    private void clearKeys() {
        SharedPreferences prefs = getSharedPreferences(Constants.ACCOUNT_PREFS_NAME, 0);
        SharedPreferences.Editor edit = prefs.edit();
        edit.clear();
        edit.commit();
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