package com.example.ssuroid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class BookTab extends AppCompatActivity implements View.OnClickListener{

    ListView listview = null;
    private AdapterView.OnItemClickListener listener;
    final int NEW_REST = 22;
    ArrayList<ListViewItem> list = new ArrayList<ListViewItem>();
    ListViewAdapter adapter;

    Button home;
    Button place;

    String[] title = new String[10];
    String[] desc = new String[10];

    int cnt=0;
    int cntHome=0;
    int titleI=0;
    String value ="false";

    private FirebaseAuth mAuth;
    Map data;
    private FirebaseFirestore db;

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_tab);

        home = (Button)findViewById(R.id.home);
        place = (Button)findViewById(R.id.place);

        home.setOnClickListener(this);
        place.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if(bundle !=null){
            value = bundle.getString("value");
            cntHome = bundle.getInt("cnt");
        }

        // Adapter 생성
        adapter = new ListViewAdapter();

        // 리스트뷰 참조 및 Adapter달기
        listview = (ListView) findViewById(R.id.listview);
        listview.setAdapter(adapter);

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);



        //Intent intent1 = getIntent();
        //String title1 = intent1.getStringExtra("title");
        //String desc1 = intent1.getStringExtra("desc");

        //adapter.addItem(ContextCompat.getDrawable(this, R.drawable.white), title1, desc1);
        //adapter.notifyDataSetChanged();


        Button plus_btn = (Button) findViewById(R.id.plus_btn);
        plus_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BookTab.this, SearchCourse.class);
                startActivityForResult(intent, NEW_REST);
            }
        });

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {
                ListViewItem dto = (ListViewItem) adapter.getItem(position);
                final Toast toast = Toast.makeText(BookTab.this, dto.getTitle() + "\n과목이 추가되었습니다.", Toast.LENGTH_SHORT);
                Log.d("pos",position+"");
                final String title = dto.getTitle();
                final String desc = dto.getDesc();
                //Toast.makeText(MainActivity.this, "선택 : " + position + "\n이름 : " + dto.getTitle()
                //                        + "\n세부사항 : " + dto.getDesc(), Toast.LENGTH_SHORT);


                DialogInterface.OnClickListener dialogListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        toast.show();


                    }
                };

                builder.setTitle("교재 구매\n");
                builder.setMessage("\n" + dto.getTitle() + "\n교재를 구매하시겠습니까?\n");
                builder.setPositiveButton("네", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Intent mIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/"));
                        //startActivity(mIntent);

                        String srchString = "쉽게 배우는 알고리즘";
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com/search?q="+srchString));
                        startActivity(browserIntent);

                        //Intent intent = new Intent(Intent.ACTION_VIEW);
                        //Uri uri = Uri.parse("http://www.naver.com");
                        //intent.setData(uri);
                        //startActivity(intent);

                        //Intent intent = new Intent(Intent.ACTION_VIEW);
                        //Uri uri = Uri.parse("http://www.naver.com");
                        //intent.setData(uri);
                        //startActivity(intent);

                        //Intent intent = new Intent();
                        //intent.setAction(Intent.ACTION_VIEW);
                        //intent.setData(Uri.parse("http://m.naver.com"));
                        //startActivity(intent);

                    }
                });

                builder.setNegativeButton("아니요", null);
                builder.create().show();

            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();

        if(value.equals("true")) {

            db = FirebaseFirestore.getInstance();

            while (titleI < cntHome) {

                    db.collection("course").document("title" + titleI).get()
                            .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()) {
                                        DocumentSnapshot document = task.getResult();

                                        if (document.exists()) {

                                            data = document.getData();
                                            Log.d("TAG", data.get("title").toString());

                                            AddItems(Integer.parseInt(data.get("cnt").toString()), data.get("title").toString(), data.get("desc").toString());


                                            Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                                        } else {

                                            Log.d(TAG, "No such document");
                                        }
                                    } else {

                                        Log.d(TAG, "get failed with ", task.getException());
                                    }
                                }
                            });
                    titleI++;

            }
        }


    }

    public void AddItems(int cntNum, String titleValue, String descValue){
        Log.d("TAG", cntNum+"");
        title[cntNum] = titleValue;
        desc[cntNum] = descValue;

        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.white), title[cntNum], desc[cntNum]);
        adapter.notifyDataSetChanged();

        Log.d("TAG", title[cntNum]+desc[cntNum]);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if(requestCode == NEW_REST)
        {
            if(resultCode == RESULT_OK)
            {
                /*
                String title1 = data.getStringExtra("title");
                String desc1 = data.getStringExtra("desc");

                adapter.addItem(ContextCompat.getDrawable(this, R.drawable.white), title1, desc1);
                adapter.notifyDataSetChanged();
                 */

                String title1 = data.getStringExtra("title");
                String desc1 = data.getStringExtra("desc");

                title[cnt] = title1;
                desc[cnt] = desc1;

                adapter.addItem(ContextCompat.getDrawable(this, R.drawable.white), title[cnt], desc[cnt]);
                adapter.notifyDataSetChanged();

                //firestore에 수강과목을 넣어주는 작업
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                FirebaseFirestore db = FirebaseFirestore.getInstance();

                CourseInfo courseInfo = new CourseInfo(title[cnt], desc[cnt], cnt);

                if (user != null) {
                    db.collection("course").document("title"+cnt)
                            .set(courseInfo)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(BookTab.this, "수강 과목 불러오기에 성공하였습니다.", Toast.LENGTH_LONG).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(BookTab.this, "수강 과목 불러오기에 실패하였습니다.", Toast.LENGTH_LONG).show();
                                }
                            });

                }

                cnt++;
                Log.d("TAG", cnt+" cnt");


            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public void onClick(View v) {
        if(v == home){
            Intent intent = new Intent();
            intent.setClass(this, MainActivity.class);
            intent.putExtra("cnt", cnt);
            startActivityForResult(intent, 0);
        }
        else if(v == place){
            Intent intent = new Intent(this, PlaceTab.class);
            startActivity(intent);
        }
    }
}