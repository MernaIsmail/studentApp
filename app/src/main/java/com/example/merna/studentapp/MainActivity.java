package com.example.merna.studentapp;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;
import com.estimote.sdk.Beacon;
import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.Region;
import com.example.merna.studentapp.model.Student;
import com.example.merna.studentapp.utils.Constants;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ServerValue;
import com.firebase.client.ValueEventListener;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class MainActivity extends BaseActivity {
    private Firebase mUserRef;
    private ValueEventListener mUserRefListener;

    private BeaconManager beaconManager;
    private StudentAdapter mStudentListAdapter;
    ListView list;
    SharedPreferences sharedPref ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        beaconManager = new BeaconManager(getApplicationContext());
        beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
            @Override
            public void onServiceReady() {
                beaconManager.startMonitoring(new Region
                        (
                                "monitored region",
                                UUID.fromString("77b3670e-454b-434d-8445-787cc0e1ffb8"),
                                19755, 3891));
            }
        });

        beaconManager.setMonitoringListener(new BeaconManager.MonitoringListener() {

            @Override
            public void onEnteredRegion(Region region, List<Beacon> list) {
                 sharedPref = getPreferences(Context.MODE_PRIVATE);
                   int f=  sharedPref.getInt("flag",0);
                if(f==0){
                    WriteData();
                }else {
                    Toast.makeText(getApplicationContext(), "you enter before ", Toast.LENGTH_LONG).show();
                }
            }


            @Override
            public void onExitedRegion(Region region) {
                Toast.makeText(getApplicationContext(), "Exit", Toast.LENGTH_LONG).show();
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putInt("flag", 0);
                editor.commit();
            }
        });




        list=(ListView)findViewById(R.id.list_view_student_lists);
        Firebase refListName = new Firebase(Constants.FIREBASE_URL_ACTIVE_LIST);
        Log.e("StudentID",mUserID);
        Query query = refListName.orderByChild("studentID").equalTo(mUserID);
        mStudentListAdapter = new StudentAdapter(this, Student.class,R.layout.list_item,query);
        list.setAdapter(mStudentListAdapter);








    }




    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void showNotification(String title, String message) {
        Intent notifyIntent = new Intent(this, MainActivity.class);
        notifyIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivities(this, 0,
                new Intent[] { notifyIntent }, PendingIntent.FLAG_UPDATE_CURRENT);
        Notification notification = new Notification.Builder(this)
                .setSmallIcon(R.drawable.happy39)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .build();
        notification.defaults |= Notification.DEFAULT_SOUND;
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, notification);
    }

    public void  WriteData(){
        sharedPref = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("flag", 1);
        editor.commit();
            /***************write info in DB*******************/
            Firebase listsRef = new Firebase(Constants.FIREBASE_URL).child("StudentList");
            Firebase newListRef = listsRef.push();

            /* Save listsRef.push() to maintain same random Id */
            final String listId = newListRef.getKey();

            HashMap<String, Object> timestampCreated = new HashMap<>();
            timestampCreated.put(Constants.FIREBASE_PROPERTY_TIMESTAMP, ServerValue.TIMESTAMP);


            Log.e("write", mUserName+ mUserID);
            Student NewStudent = new Student(mUserID, mUserName, "ture", "false", timestampCreated);
            newListRef.setValue(NewStudent);

            showNotification("attendance", "You've proved attendance now..");

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
    public void onDestroy() {
        super.onDestroy();
       //    mUserRef.removeEventListener(mUserRefListener);
    }
}
