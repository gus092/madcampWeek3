package com.example.screenshotorg;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.screenshotorg.R;
import com.example.screenshotorg.ui.main.PageViewModel;

/**
 * A placeholder fragment containing a simple view.
 */
public class Fragment2 extends Fragment {

    TextView lst;
    EditText studentid;
    EditText studentname;
    Button loadbtn, addbtn, findbtn, deletebtn, updatebtn;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        pageViewModel = ViewModelProviders.of(this).get(PageViewModel.class);
//        int index = 1;
//        if (getArguments() != null) {
//            index = getArguments().getInt(ARG_SECTION_NUMBER);
//        }
//        pageViewModel.setIndex(index);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.twofragment, container, false);
        lst = (TextView) view.findViewById(R.id.list);
        studentid = (EditText) view.findViewById(R.id.studentID);
        studentname = (EditText) view.findViewById(R.id.studentName);
        loadbtn = view.findViewById(R.id.btnload);
        loadbtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                loadStudents(view);
            }
        });
        addbtn = view.findViewById(R.id.btnadd);
        addbtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                addStudent(view);
            }
        });
        findbtn = view.findViewById(R.id.btnfind);
        findbtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                findStudent(view);
            }
        });
        deletebtn = view.findViewById(R.id.btndelete);
        deletebtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                deleteStudent(view);
            }
        });
        updatebtn = view.findViewById(R.id.btnupdate);
        updatebtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                updateStudent(view);
            }
        });
//        final TextView textView = root.findViewById(R.id.section_label);
//        pageViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        return view;
    }

    public void addStudent (View view) {
        MyDBHandler dbHandler = new MyDBHandler(this.getContext(), null, null, 1);
        int id = Integer.parseInt(studentid.getText().toString());
        String name = studentname.getText().toString();
        Student student = new Student(id,name);
        dbHandler.addHandler(student);
        studentid.setText("");
        studentname.setText("");
    }

    public void findStudent (View view) {
        MyDBHandler dbHandler = new MyDBHandler(this.getContext(), null, null, 1);
        Student student = dbHandler.findHandler(Integer.parseInt(studentid.getText().toString()));
        if (student != null) {
            lst.setText(String.valueOf(student.getID()) +" "+ student.getStudentName());

        } else {
            lst.setText("No Match Found");
        }
    }

    public void loadStudents(View view) {
        MyDBHandler dbHandler = new MyDBHandler(this.getContext(), null, null, 1);
        lst.setText(dbHandler.loadHandler());
        studentid.setText("");
        studentname.setText("");
    }

    public void deleteStudent(View view) {
        MyDBHandler dbHandler = new MyDBHandler(this.getContext(), null,
                null, 1);
        boolean result = dbHandler.deleteHandler(Integer.parseInt(
                studentid.getText().toString()));
        if (result) {
            studentid.setText("");
            studentname.setText("");
            lst.setText("Record Deleted");
        } else
            studentid.setText("No Match Found");
    }

    public void updateStudent(View view) {
        MyDBHandler dbHandler = new MyDBHandler(this.getContext(), null,
                null, 1);
        boolean result = dbHandler.updateHandler(Integer.parseInt(
                studentid.getText().toString()), studentname.getText().toString());
        if (result) {
            studentid.setText("");
            studentname.setText("");
            lst.setText("Record Updated");
        } else
            studentid.setText("No Match Found");
    }
}