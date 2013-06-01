package com.bqlibrary.guardiola.general;

import com.dropbox.client2.session.Session.AccessType;

/**
 * Created with IntelliJ IDEA.
 * User: Administrador
 * Date: 31/05/13
 * Time: 14:03
 * To change this template use File | Settings | File Templates.
 */
public class Constants {
    private static final Constants INSTANCE = new Constants();

    private Constants() {}

    public static final Constants getInstance() {
        return INSTANCE;
    }

    ///////////////////////////////////////////////////////////////////////////
    //                      App-specific settings.                           //
    ///////////////////////////////////////////////////////////////////////////

    public static final String APP_KEY = "8s51f0igq39z282";
    public static final String APP_SECRET = "uuhmpo6uqfjlssw";

    public static final AccessType ACCESS_TYPE = AccessType.DROPBOX;

    ///////////////////////////////////////////////////////////////////////////
    //                      End app-specific settings.                       //
    ///////////////////////////////////////////////////////////////////////////

    /** Show logs or not **/
    public static final boolean SHOW_LOGS = false;

    /** Show remote logs or not **/
    @SuppressWarnings("unused")
    public static final boolean ACTIVE_REMOTE_LOGS = SHOW_LOGS && false;

    /** URL of connection **/
    public static final String URL_CONNECTION = "";

    /** SharedPreferences keys **/
    public static final String ACCOUNT_PREFS_NAME = "prefs";
    public static final String ACCESS_KEY_NAME = "ACCESS_KEY";
    public static final String ACCESS_SECRET_NAME = "ACCESS_SECRET";

    public static final String EPUB = ".epub";
}
