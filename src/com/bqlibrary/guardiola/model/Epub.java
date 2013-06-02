package com.bqlibrary.guardiola.model;

import android.graphics.Bitmap;

import java.util.Date;

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
    private Date mDateCreated;
    private String mTitle;
    private Bitmap mCover;

    public Epub() {
    }

    public Epub(String nameEpub, String pathEpub, Bitmap thumbnail, Date dateCreated) {
        this.mNameEpub = nameEpub;
        this.mPathEpub = pathEpub;
        this.mThumbnail = thumbnail;
        this.mSselected = false;
        this.mDateCreated = dateCreated;

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

    public Date getmDateCreated() {
        return mDateCreated;
    }

    public String getmTitle() {
        return mTitle;
    }

    public Bitmap getmCover() {
        return mCover;
    }

    public void setmSselected(Boolean mSselected) {
        this.mSselected = mSselected;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public void setmCover(Bitmap mCover) {
        this.mCover = mCover;
    }

}
