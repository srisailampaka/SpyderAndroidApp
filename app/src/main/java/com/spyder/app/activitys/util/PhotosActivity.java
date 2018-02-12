package com.spyder.app.activitys.util;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.GridView;

import com.spyder.app.activitys.R;

/**
 * Created by RaviKiran Yadav on 2/12/2018.
 */

public class PhotosActivity extends AppCompatActivity {
    int int_position;
    private GridView gridView;
    GridViewAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gridview);
        gridView = (GridView)findViewById(R.id.gv_folder);
        int_position = getIntent().getIntExtra("value", 0);
        adapter = new GridViewAdapter(this, GalleryImagesActivity.al_images,int_position);
        gridView.setAdapter(adapter);
    }
}
