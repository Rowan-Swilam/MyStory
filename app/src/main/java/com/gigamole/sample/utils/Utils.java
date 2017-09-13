package com.gigamole.sample.utils;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gigamole.sample.R;
import com.gigamole.sample.data.Entry;
import com.gigamole.sample.screens.MainActivity;

/**
 * Created by GIGAMOLE on 8/18/16.
 */
public class Utils {
    public static void setupItem(final View view, final Entry entry) {
        final TextView txt = (TextView) view.findViewById(R.id.txt_item);
        txt.setText(entry.getEntryTitle());

        final ImageView img = (ImageView) view.findViewById(R.id.img_item);
        try {
            int num = Integer.parseInt(entry.getEntryImage());
            Glide.with(MainActivity.mContext).load(num).into(img);
        } catch(Exception e) {
            Glide.with(MainActivity.mContext).load(entry.getEntryImage()).into(img);
        }

        final TextView text = (TextView) view.findViewById(R.id.txt_date);
        text.setText(entry.getDate());

    }

}
