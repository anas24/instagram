package com.parse.starter;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

public class usersfeed extends AppCompatActivity {

    LinearLayout linearLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usersfeed);
        linearLayout=(LinearLayout)findViewById(R.id.linearly);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent in=getIntent();
        String active=in.getStringExtra("username");
        Log.i("app",active);
        setTitle(active+"'s Feed");
        ParseQuery<ParseObject> query=new ParseQuery<ParseObject>("images");
        query.whereEqualTo("username",active);
        query.addDescendingOrder("createdAt");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if(e==null)
                {
                    if(objects.size()>0)
                    {
                        for(ParseObject object:objects)
                        {
                            ParseFile file =(ParseFile)object.get("image");
                            file.getDataInBackground(new GetDataCallback() {
                                @Override
                                public void done(byte[] data, ParseException e) {
                                    if(e==null) {
                                        Bitmap bmp = BitmapFactory.decodeByteArray(data, 0, data.length);
                                        ImageView iv = new ImageView(getApplicationContext());
                                        iv.setImageBitmap(bmp);
                                        iv.setLayoutParams(new ViewGroup.LayoutParams(
                                                ViewGroup.LayoutParams.MATCH_PARENT,
                                                ViewGroup.LayoutParams.WRAP_CONTENT

                                        ));
                                        linearLayout.addView(iv);
                                    }
                                    else
                                    {
                                        e.printStackTrace();
                                    }
                                }

                            });
                        }
                    }
                }
                else
                {
                    e.printStackTrace();
                }
            }
        });

    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                switch (item.getItemId())
                {
                    case android.R.id.home:
                        this.finish();
                        return true;
                    default:

                }
        }
        return super.onOptionsItemSelected(item);
    }


}
