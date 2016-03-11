package com.example.merna.studentapp;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.example.merna.studentapp.utils.Constants;

/**
 * Created by Merna on 3/11/2016.
 */
public class BaseActivity extends AppCompatActivity {

    protected String mUserID,mUserName,mProvider,mEncodedEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /**
                  * Getting mProvider and mEncodedEmail from SharedPreferences
                 */
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(BaseActivity.this);
               /* Get mEncodedEmail and mProvider from SharedPreferences, use null as default value */
        mUserID = sp.getString(Constants.KEY_STUDENT_ID, null);
        mUserName = sp.getString(Constants.KEY_STUDENT_NAME, null);
        mProvider=sp.getString(Constants.KEY_PROVIDER,null);
        mEncodedEmail=sp.getString(Constants.KEY_ENCODED_EMAIL,null);
        Log.e("base",mUserName + mUserID);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            super.onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
