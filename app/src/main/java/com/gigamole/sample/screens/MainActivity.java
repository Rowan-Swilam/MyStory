package com.gigamole.sample.screens;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.gigamole.sample.R;
import com.gigamole.sample.adapters.MainPagerAdapter;


public class MainActivity extends AppCompatActivity {

    public static Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext=this;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.System.canWrite(this)) {
                requestPermissions(new String[]{Manifest.permission.MANAGE_DOCUMENTS,
                        Manifest.permission.READ_EXTERNAL_STORAGE}, 2909);
            } else {}
        } else {}

        final ViewPager viewPager = (ViewPager) findViewById(R.id.vp_main);
        viewPager.setAdapter(new MainPagerAdapter(getSupportFragmentManager()));
        viewPager.setOffscreenPageLimit(2);
    }

    public void openAddActivity(View view) {
        Intent intent = new Intent(this, card.class);
        startActivity(intent);

    }

    public void popUp(View view) {
        Button but = (Button) findViewById(R.id.popUpButton);
        PopupMenu popup = new PopupMenu(MainActivity.this, but);
        //Inflating the Popup using xml file
        popup.getMenuInflater()
                .inflate(R.menu.menu_card, popup.getMenu());

        //registering popup with OnMenuItemClickListener
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                Toast.makeText(
                        MainActivity.this,
                        "You Clicked : " + item.getTitle(),
                        Toast.LENGTH_SHORT
                ).show();
                return true;
            }
        });

        popup.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 2909: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {} else {}
                return;
            } }
    }

    @Override
    public void onBackPressed() {
        finish();
    }

}
