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
    public static final String MIMETYPE_EPUB = "application/epub+zip";

    // Alpha constants
    public static final int ALPHA_TRANSPARENCY_60 = 153;

    public static final int WHAT_DOWNLOAD_EPUB_OK = 0;
    public static final int WHAT_ERROR_DOWNLOAD_EPUB = 1;

    public static final String CLOSE_DIALOG = "Close";
    public static final String COULDNT_AUTHENTICATE_DROPBOX = "Couldn't authenticate with Dropbox:";
    public static final String ERROR_AUTHENTICATING_DROPBOX = "Error authenticating";
    public static final String UNLINK_DROPBOX = "Unlink from Dropbox";
    public static final String LINK_DROPBOX = "Link with Dropbox";
    public static final String DOWNLOADING_EPUB = "Downloading Epub";
    public static final String CANCEL = "Cancel";
    public static final String CANCELED = "Canceled";
    public static final String COULDNT_GET_EPUB_METADATA = "Couldn't get epub metadata";
    public static final String COULDNT_CREATE_EPUB_BOOK = "Couldn't create epub book";
    public static final String COULDNT_GET_EPUB_FILE = "Couldn't get epub file";
    public static final String DOWNLOADED = "Downloaded!";
    public static final String COULDNT_SEARCH_EPUB_FILES = "Couldn't search epub files";
    public static final String NO_EPUB_FILES = "There aren't epub files";
    public static final String EPUB_FILES_GOT = "Epub files successfully got";

}
