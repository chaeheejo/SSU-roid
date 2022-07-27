package com.example.ssuroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;

import static android.content.ContentValues.TAG;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button book;
    Button place;
    String value;
    int cnt;

    Map data;
    private FirebaseFirestore db;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    TextView getName;
    TextView getStudentID;
    TextView getDepartment;
    TextView getPhoneNumber;
    TextView getAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        book = (Button)findViewById(R.id.book);
        place = (Button)findViewById(R.id.place);

        book.setOnClickListener(this);
        place.setOnClickListener(this);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if(bundle !=null){
            cnt = bundle.getInt("cnt");
            Log.d("TAG-MAIN", cnt+" cnt");
        }

        getName = (TextView) findViewById(R.id.getName);
        getStudentID = (TextView) findViewById(R.id.getStudentID);
        getDepartment = (TextView) findViewById(R.id.getDepartment);
        getPhoneNumber = (TextView) findViewById(R.id.getPhoneNumber);
        getAddress = (TextView) findViewById(R.id.getAddress);

        db = FirebaseFirestore.getInstance();
        db.collection("users").document(user.getUid()).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                data=document.getData();
                                getAddress.setText(data.get("address").toString());
                                getPhoneNumber.setText(data.get("phoneNumber").toString());
                                getStudentID.setText(data.get("studentID").toString());
                                getName.setText(data.get("name").toString());
                                getDepartment.setText(data.get("department").toString());

                                Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                            } else {
                                Log.d(TAG, "No such document");
                            }
                        }else {
                            Log.d(TAG, "get failed with ", task.getException());
                        }
                    }
                });

    }

    @Override
    public void onClick(View v) {
        if(v == book){
            Intent intent = new Intent();
            intent.setClass(this, BookTab.class);
            value = "true";
            intent.putExtra("value", value);
            intent.putExtra("cnt", cnt);
            startActivityForResult(intent, 0);
        }
        else if(v == place){
            Intent intent = new Intent(MainActivity.this, PlaceTab.class);
            startActivity(intent);
        }
    }
}