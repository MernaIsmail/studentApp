package com.example.merna.studentapp;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.example.merna.studentapp.model.Student;
import com.example.merna.studentapp.utils.Utils;
import com.firebase.client.Query;
import com.firebase.ui.FirebaseListAdapter;

import java.util.Date;

/**
 * Created by Merna on 3/7/2016.
 */
public class StudentAdapter extends FirebaseListAdapter<Student> {

    public StudentAdapter(Activity activity, Class<Student> modelClass, int modelLayout, Query ref) {
        super(activity, modelClass, modelLayout, ref);
    }

    @Override
    protected void populateView(View v, Student model) {

        TextView studentId=(TextView)v.findViewById(R.id.studentId);
        TextView studentName=(TextView)v.findViewById(R.id.studentName);
        TextView time=(TextView)v.findViewById(R.id.time);

               studentId.setText(model.getStudentID());
               studentName.setText(model.getStudentName());
               if (model.getTimestampLastChanged() != null) {
                   time.setText(
                           Utils.SIMPLE_DATE_FORMAT.format(new Date(model.getTimestampLastChangedLong())));

               } else {
                   time.setText("");
               }


    }
}
