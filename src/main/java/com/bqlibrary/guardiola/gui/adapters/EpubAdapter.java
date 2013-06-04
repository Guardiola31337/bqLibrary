package com.bqlibrary.guardiola.gui.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.bqlibrary.guardiola.general.Constants;
import com.bqlibrary.guardiola.gui.components.EpubHolder;
import com.bqlibrary.guardiola.model.Epub;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Administrador
 * Date: 1/06/13
 * Time: 19:58
 * To change this template use File | Settings | File Templates.
 */
public class EpubAdapter extends BaseAdapter {
    public final static String TAG = "[com.bqlibrary.guardiola.gui.adapters.EpubAdapter]";

    private ArrayList<Epub> mEpubsList;
    private Context mContext;

    public EpubAdapter(ArrayList<Epub> epubsList, Context context) {
        this.mEpubsList = epubsList;
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return mEpubsList.size();
    }

    @Override
    public Object getItem(int arg0) {
        return mEpubsList.get(arg0);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        EpubHolder view;

        // If it's not recycled, initialize some attributes
        if(convertView == null) {
            view = new EpubHolder(mContext);
            convertView = view.getmView();
            convertView.setTag(view);
        }
        else {
            view = (EpubHolder) convertView.getTag();
        }

        Epub epub = mEpubsList.get(position);
        view.getTxtViewTitle().setText(epub.getmNameEpub());
        view.getImgViewThumbnail().setImageBitmap(epub.getmThumbnail());

        if(epub.getmSselected() == false) {
            view.getTxtViewBackgroundSelection().setVisibility(View.INVISIBLE);
        }
        else {
            view.getTxtViewBackgroundSelection().setVisibility(View.VISIBLE);
            view.getTxtViewBackgroundSelection().getBackground().setAlpha(Constants.ALPHA_TRANSPARENCY_60);
        }

        return convertView;
    }
}
