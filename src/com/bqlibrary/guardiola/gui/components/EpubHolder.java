package com.bqlibrary.guardiola.gui.components;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.bqlibrary.guardiola.activities.R;

/**
 * Created with IntelliJ IDEA.
 * User: Administrador
 * Date: 1/06/13
 * Time: 19:53
 * To change this template use File | Settings | File Templates.
 */
public class EpubHolder {
    public final static String TAG = "[com.bqlibrary.guardiola.gui.components.EpubHolder]";

    /** Activity base to get the context or contact with the activity window **/
    protected Context mContext;
    private View mView;
    private ImageView imgViewThumbnail;
    private TextView txtViewTitle;
    private TextView txtViewBackgroundSelection;

    public EpubHolder(Context context) {
        super();
        this.mContext = context;
        initAll();
    }

    private void initAll() {
        //assign the xml
        LayoutInflater factory = LayoutInflater.from(mContext);
        mView = factory.inflate(R.layout.epub_row, null);

        //Initialize all data
        initProperties();
        initViews();
        initValues();
        initListeners();
        init();

    }

    private void init() {
        // TODO Auto-generated method stub

    }

    private void initListeners() {
        // TODO Auto-generated method stub

    }

    private void initValues() {
        // TODO Auto-generated method stub

    }

    private void initViews() {
        txtViewTitle = (TextView) mView.findViewById(R.id.epub_name);
        imgViewThumbnail = (ImageView) mView.findViewById(R.id.epub_thumbnail);
        txtViewBackgroundSelection = (TextView) mView.findViewById(R.id.epub_background_selection);

    }

    private void initProperties() {
        // TODO Auto-generated method stub

    }

    public View getmView() {
        return mView;
    }

    public ImageView getImgViewThumbnail() {
        return imgViewThumbnail;
    }

    public TextView getTxtViewTitle() {
        return txtViewTitle;
    }

    public TextView getTxtViewBackgroundSelection() {
        return txtViewBackgroundSelection;
    }
}
