package com.bqlibrary.guardiola.model;

import android.graphics.Bitmap;

/**
 * Created with IntelliJ IDEA.
 * User: Administrador
 * Date: 1/06/13
 * Time: 19:43
 * To change this template use File | Settings | File Templates.
 */
public class Epub {

    public final static String TAG = "[com.bqlibrary.guardiola.model.Epub]";

    private String mNameEpub;
    private String mPathEpub;
    private Bitmap mThumbnail;
    private Boolean mSselected;

    public Epub() {
    }

    public Epub(String nameEpub, String pathEpub, Bitmap thumbnail) {
        this.mNameEpub = nameEpub;
        this.mPathEpub = pathEpub;
        this.mThumbnail = thumbnail;
        this.mSselected = false;

    }

    public String getmNameEpub() {
        return mNameEpub;
    }

    public String getmPathEpub() {
        return mPathEpub;
    }

    public Bitmap getmThumbnail() {
        return mThumbnail;
    }

    public Boolean getmSselected() {
        return mSselected;
    }

    public void setmSselected(Boolean mSselected) {
        this.mSselected = mSselected;
    }

}
