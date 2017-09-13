package com.gigamole.sample.screens;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.gigamole.sample.R;
import com.gigamole.sample.adapters.HorizontalPagerAdapter;
import com.gigamole.sample.data.Entry;

import java.util.Date;


public class card extends AppCompatActivity {

    private Entry entry;
    private String textContent;
    private String image;
    private String dateContent;
    private String description;
    private CollapsingToolbarLayout collapsingToolbar;
    private AppBarLayout appBar;
    private EditText text;
    private EditText desc;
    private int day;
    private int month;
    private int year;
    private ImageView img;
    private TextView dateText;
    private int entryPosition;
    private boolean editMode = false;
    private Bundle extras;
    private int id;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);

        img = (ImageView) findViewById(R.id.img);
        text = (EditText) findViewById(R.id.textTitle);
        dateText = (TextView) findViewById(R.id.dateText);
        desc = (EditText) findViewById(R.id.description);
        java.text.DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(getApplicationContext());
        dateContent = dateFormat.format(new Date());
        extras = getIntent().getExtras();

        if (extras != null) {
            text.setText(extras.getString("title"));
            try {
                int num = Integer.parseInt(extras.getString("image"));
                Glide.with(this).load(num).into(img);
            } catch (Exception e) {
                Glide.with(this).load(extras.getString("image")).into(img);
            }

            dateContent = extras.getString("date");
            dateText.setText(dateContent);
            description = extras.getString("description");
            desc.setText(description);
            entryPosition = extras.getInt("position");
            image = extras.getString("image");
            editMode = true;
            id = extras.getInt("id");

        }

        collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        appBar = (AppBarLayout) findViewById(R.id.app_bar);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textContent = ((EditText) findViewById(R.id.textTitle)).getText().toString();
                if(((EditText) findViewById(R.id.description)).getText() != null)
                    description = ((EditText) findViewById(R.id.description)).getText().toString();
                else
                    description = "";
                finish();

                if (image == null) {
                    image = Integer.toString(view.getContext().getResources().getIdentifier
                            ("bak", "drawable", view.getContext().getPackageName()));
                }

                if (!editMode && !textContent.matches("")) {
                    entry = new Entry(textContent, description, dateContent, image);
                    id = (int) HorizontalPagerAdapter.getInstance(getApplicationContext()).mDataSource.addEntry(entry);
                    entry.setId(id);
                    HorizontalPagerAdapter.getInstance(getApplicationContext()).entryArrayList.add(entry);
                    HorizontalPagerAdapter.getInstance(getApplicationContext()).notifyDataSetChanged();

                } else if (editMode && !textContent.matches("")) {
                    entry = new Entry(textContent, description, dateContent, image);
                    id = extras.getInt("id");
                    HorizontalPagerAdapter.getInstance(getApplicationContext()).mDataSource.updateEntry(id, entry);
                    Entry entry = HorizontalPagerAdapter.getInstance(getApplicationContext()).entryArrayList.get(entryPosition);
                    entry.setDate(dateContent);
                    entry.setEntryDescription(description);
                    entry.setEntryImage(image);
                    entry.setEntryTitle(textContent);
                    entry.setId(id);
                    HorizontalPagerAdapter.getInstance(getApplicationContext()).entryArrayList.remove(entryPosition);
                    HorizontalPagerAdapter.getInstance(getApplicationContext()).entryArrayList.add(entry);
                    HorizontalPagerAdapter.getInstance(getApplicationContext()).notifyDataSetChanged();
                }

            }
        });

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                final int ACTIVITY_SELECT_IMAGE = 1234;
                startActivityForResult(i, ACTIVITY_SELECT_IMAGE);
            }
        });

        AppBarLayout.OnOffsetChangedListener mListener = new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (collapsingToolbar.getHeight() + verticalOffset < 2 * ViewCompat.getMinimumHeight(collapsingToolbar)) {
                    text.setVisibility(View.INVISIBLE);
                    collapsingToolbar.setTitle(text.getText().toString());
                } else {
                    text.setVisibility(View.VISIBLE);
                    collapsingToolbar.setTitle("");
                }
            }
        };

        appBar.addOnOffsetChangedListener(mListener);

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        switch (requestCode) {
            case 1234:
                if (resultCode == RESULT_OK) {
                    Uri selectedImage = data.getData();
                    image = getRealPathFromURI(getApplicationContext(), selectedImage);
                    Glide.with(this).load(image).into(img);

                }
        }

    };

    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == R.id.Date) {
            return new DatePickerDialog(this, myDateListener, 2017, 7, 1);
        }
        return null;
    }

    // when set button is clicked
    private DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            year = selectedYear;
            month = selectedMonth;
            day = selectedDay;


            // set selected date into textview
            dateText.setText(new StringBuilder().append(day)
                    .append("/").append(month + 1).append("/").append(year)
                    .append(" "));
            dateContent = dateText.getText().toString();
        }
    };

    public void setDate(View view) {
        showDialog(R.id.Date);
    }


    public String getRealPathFromURI(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }




}

