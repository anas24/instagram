package com.parse.starter;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;


public class userslist extends AppCompatActivity {

    ArrayList<String> usernames;
    ArrayAdapter<String>adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userslist);
        //


        final ListView userslist = (ListView) findViewById(R.id.listView);
        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.whereNotEqualTo("username",ParseUser.getCurrentUser().getUsername());
        query.addAscendingOrder("username");
        query.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> objects, ParseException e) {
                if(e==null)
                {

                    if(objects.size()>0)
                    {
                        usernames=new ArrayList<String>();
                        for(ParseUser user:objects)
                        {
                            Log.i("username",user.getUsername());
                            usernames.add(user.getUsername());
                        }
                        adapter=new ArrayAdapter<String>(userslist.this,android.R.layout.simple_list_item_1,usernames);
                        userslist.setAdapter(adapter);
                    }
                }
            }
        });


        userslist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Intent in= new Intent(getApplicationContext(),usersfeed.class);
                        in.putExtra("username",usernames.get(i));
                        startActivity(in);
            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_userslist, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.share)
        {
            Intent in=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(in,1);
            return true;
        }
        else if (id == R.id.logout)
        {
            ParseUser.logOut();
            Intent in=new Intent(getApplicationContext(),MainActivity.class);
            startActivity(in);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1 && resultCode==RESULT_OK && data!=null)
        {
            Uri selectedImage =data.getData();
            try {
                Bitmap bmp=MediaStore.Images.Media.getBitmap(this.getContentResolver(),selectedImage);
                Log.i("image","rcvd");
                ByteArrayOutputStream stream=new ByteArrayOutputStream();
                bmp.compress(Bitmap.CompressFormat.PNG,60,stream);
                byte[] byteArray=stream.toByteArray();
                ParseFile file=new ParseFile("image.png",byteArray);
                ParseObject obj=new ParseObject("images");
                obj.put("username",ParseUser.getCurrentUser().getUsername());
                obj.put("image",file);
                ParseACL parseACL=new ParseACL();
                parseACL.setPublicReadAccess(true);
                obj.setACL(parseACL);


                obj.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if(e==null)
                        {
                            Toast.makeText(getApplication().getApplicationContext(),"Image  posted",Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            e.printStackTrace();
                            Toast.makeText(getApplication().getApplicationContext(),"Some error occured",Toast.LENGTH_LONG).show();
                        }
                    }
                });


            } catch (IOException e) {
                Toast.makeText(getApplication().getApplicationContext(),"Some error occured",Toast.LENGTH_LONG).show();
                e.printStackTrace();

            }
        }

    }

    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

}