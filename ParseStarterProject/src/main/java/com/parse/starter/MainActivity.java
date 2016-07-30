/*
 * Copyright (c) 2015-present, Parse, LLC.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */
package com.parse.starter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.LogInCallback;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import java.util.List;


public class MainActivity extends AppCompatActivity implements View.OnKeyListener,View.OnClickListener {

  EditText username,passsword;
  Button LoginButton;
  int flag=1;
  TextView sign;


  public void Login(View view)
  {
    String usernameText=username.getText().toString();
    String passwordText=passsword.getText().toString();

    ParseUser user= new ParseUser();
    String operation=String.valueOf(LoginButton.getText());
    if(operation.equalsIgnoreCase("SignUp")) {
      if (usernameText.isEmpty()) {
        Toast.makeText(getApplicationContext(), "Username can't be empty!!", Toast.LENGTH_LONG).show();
      } else if (passwordText.isEmpty()) {
        Toast.makeText(getApplicationContext(), "Password can't be empty!!", Toast.LENGTH_LONG).show();
      } else {
        user.setUsername(usernameText);
        user.setPassword(passwordText);

        user.signUpInBackground(new SignUpCallback() {
          @Override
          public void done(ParseException e) {
            if (e == null) {
              Log.i("SignUp", "Successfull");
              Intent in=new Intent(getApplicationContext(),userslist.class);
              startActivity(in);
              finish();

              Toast.makeText(getApplicationContext(),"Signup Successfull!!",Toast.LENGTH_LONG).show();
            }
            else
            {
              Log.i("signup","unsuccessfull");
              e.printStackTrace();
              Toast.makeText(getApplicationContext(),"you already have an account!!",Toast.LENGTH_LONG).show();
            }
          }
        });
      }
    }
    if(operation.equalsIgnoreCase("Login"))
    {
      if (usernameText.isEmpty()) {
        Toast.makeText(getApplicationContext(), "Username can't be empty!!", Toast.LENGTH_LONG).show();
      } else if (passwordText.isEmpty())
        Toast.makeText(getApplicationContext(), "Password can't be empty!!", Toast.LENGTH_LONG).show();
        else
        {
          ParseUser.logInInBackground(usernameText, passwordText, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
              if (user != null) {
                Toast.makeText(getApplicationContext(),"Login Successfull",Toast.LENGTH_LONG).show();
                Intent in=new Intent(getApplicationContext(),userslist.class);
                startActivity(in);
                finish();
              }
              else
              {
                Toast.makeText(getApplicationContext(),"Invalid Username or passsword",Toast.LENGTH_LONG).show();

              }
            }
          });
        }
      }
  }
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if(ParseUser.getCurrentUser()!=null)
    {
      Intent in=new Intent(getApplicationContext(),userslist.class);
      startActivity(in);
      finish();
    }
    setContentView(R.layout.activity_main);
    username=(EditText)findViewById(R.id.username);
    passsword=(EditText)findViewById(R.id.Password);
    LoginButton= (Button)findViewById(R.id.Login);
    sign=(TextView)findViewById(R.id.textView);
    username.setOnKeyListener(this);
    passsword.setOnKeyListener(this);
    //ImageView background=(ImageView)findViewById(R.id.imageView);
    sign.setOnClickListener(this);
    ImageView bg=(ImageView)findViewById(R.id.imageView);

    bg.setAlpha(0.5f);
    bg.setOnClickListener(this);
    LoginButton.setAlpha(0.5f);
    sign.setAlpha(0.5f);









//    ParseUser user=new ParseUser();
//    user.setUsername("anas24");
//    user.setPassword("qwaszx12");
//    user.signUpInBackground(new SignUpCallback() {
//      @Override
//      public void done(ParseException e) {
//       if(e==null)
//       {
//         Log.i("Singup","Successfull");
//       }
//        else
//       {
//         Log.i("Signup","Unsuccessfull");
//         e.printStackTrace();
//       }
//      }
//    });
//
//ParseUser.logInInBackground("anas24", "qwaszx12", new LogInCallback() {
//  @Override
//  public void done(ParseUser user, ParseException e) {
//   if(user!=null)
//   {
//     Log.i("Login","successfull");
//   }
//    else
//   {
//     Log.i("Login","failed");
//     e.printStackTrace();
//   }
//  }
//}
//      ParseQuery<ParseObject> query =ParseQuery.getQuery("Score");
//      query.findInBackground(new FindCallback<ParseObject>() {
//        @Override
//        public void done(List<ParseObject> objects, ParseException e) {
//          for(ParseObject obj : objects)
//          {
//            if(obj.get("username").equals("fatty"))
//            {
//              obj.put("username","amir");
//              obj.saveInBackground();
//            }
//          }
//        }
//      });



//    ParseQuery<ParseObject> query = ParseQuery.getQuery("Score");
//    query.findInBackground(new FindCallback<ParseObject>() {
//      @Override
//      public void done(List<ParseObject> objects, ParseException e) {
//        Log.i("Total no of users",String.valueOf(objects.size()));
//        for(ParseObject Objs:objects)
//        {
//              Log.i("Retrieved:",String.valueOf(Objs.get("username")+":"+Objs.get("marks")));
//        }
//      }
//    });
//    ParseObject score =new ParseObject("Score");
//    score.put("username","amir");
//    score.put("marks",250);
//    score.saveInBackground();


//    ParseQuery<ParseObject> query=ParseQuery.getQuery("Score");
//    query.getInBackground("YSxGJq2lbI", new GetCallback<ParseObject>() {
//      @Override
//      public void done(ParseObject object, ParseException e) {
//        if(e==null)
//        {
//          object.put("username","fatty");
//          object.saveInBackground();
//          Log.i("ho gaya","save");
//        }
//        else
//        {
//          e.printStackTrace();
//        }
//
//      }
//    });


    ParseAnalytics.trackAppOpenedInBackground(getIntent());
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    //noinspection SimplifiableIfStatement
    if (id == R.id.action_settings) {
      return true;
    }

    return super.onOptionsItemSelected(item);
  }

  @Override
  public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {

    if(keyCode==keyEvent.KEYCODE_ENTER) {
      if (view.getId() == R.id.username) {
        if (passsword.requestFocus())
        {
          getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
      }
    }
    if(keyCode==keyEvent.KEYCODE_ENTER && keyEvent.getAction()==KeyEvent.ACTION_DOWN && view.getId()==R.id.Password)
      {
        Login(view);
      }

    return false;
  }

  @Override
  public void onClick(View view) {
    if(view.getId()==R.id.textView)
    {
      Log.i("Button","Tapped");
      if(flag==1) {
        LoginButton.setText("SignUp");
        sign.setText("Login");
        flag=0;
      }
      else
      {
        LoginButton.setText("Login");
        sign.setText("SignUp");
        flag=1;
      }
    }
    else if(view.getId()==R.id.imageView)
    {
      Log.i("image view","tapped");
      InputMethodManager imm =(InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
      imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);

    }

  }
}
