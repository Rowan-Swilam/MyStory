package com.gigamole.sample.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.gigamole.sample.R;
import com.gigamole.sample.data.DiaryDataSource;
import com.gigamole.sample.screens.card;
import com.gigamole.sample.data.Entry;
import java.util.ArrayList;

import static com.gigamole.sample.utils.Utils.setupItem;

public class HorizontalPagerAdapter extends PagerAdapter {

    public final DiaryDataSource mDataSource;
    public boolean sure;
    private static HorizontalPagerAdapter pagerAdapter = null;
    public final ArrayList<Entry> entryArrayList;

    private Context mContext;
    private LayoutInflater mLayoutInflater;

    private HorizontalPagerAdapter(final Context context) {
        mDataSource = new DiaryDataSource(context);
        sure = false;
        mDataSource.open();
   //     mDataSource.close();

        entryArrayList = mDataSource.getAllEntries();
        Log.d("values in adapter",String.valueOf(entryArrayList.size()));
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);

    }

    public static HorizontalPagerAdapter getInstance(final Context context) {
        if(pagerAdapter == null) {
            pagerAdapter= new HorizontalPagerAdapter(context);
        }
        return pagerAdapter;
    }

    @Override
    public int getCount() {
        return entryArrayList.size();
    }

    @Override
    public int getItemPosition(final Object object) {
       return POSITION_NONE;
    }

    @Override
    public Object instantiateItem(final ViewGroup container, final int position) {
        final View view;

        view = mLayoutInflater.inflate(R.layout.item, container, false);
        ImageButton btn = (ImageButton) view.findViewById(R.id.delete_button);
        btn.setOnClickListener(new View.OnClickListener() {
                                   @Override
                                   public void onClick(View view) {
                                       AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                                       builder.setTitle("Delete")
                                               .setMessage("Are you sure you want to delete?")
                                               .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                                   public void onClick(DialogInterface dialog, int id) {
                                                       mDataSource.deleteEntry(entryArrayList.get(position).getId());
                                                       entryArrayList.remove(position);
                                                       notifyDataSetChanged();
                                                   }
                                               })
                                               .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                                                   public void onClick(DialogInterface dialog, int id) {
                                                       dialog.cancel();
                                                   }
                                               });
                                       builder.show();
                                   }
                                       });


        view.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)  {
                Intent intent1 = new Intent(mContext, card.class);
                intent1.putExtra("title",  entryArrayList.get(position).getEntryTitle());
                intent1.putExtra("description",  entryArrayList.get(position).getEntryDescription());
                intent1.putExtra("date",  entryArrayList.get(position).getDate());
                intent1.putExtra("image",  entryArrayList.get(position).getEntryImage());
                intent1.putExtra("position",position);
                intent1.putExtra("id",entryArrayList.get(position).getId());
                mContext.startActivity(intent1);
            }
        });

        setupItem(view, entryArrayList.get(position));
        container.addView(view);
        return view;
    }

    @Override
    public boolean isViewFromObject(final View view, final Object object) {
        return view.equals(object);
    }

    @Override
    public void destroyItem(final ViewGroup container, final int position, final Object object) {
        container.removeView((View) object);
    }
}
