package com.example.ssuroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class Favorite extends AppCompatActivity {

    ListView listview = null ;
    private AdapterView.OnItemClickListener listener;
    final int NEW_REST = 22;
    ArrayList<ListViewItem> list = new ArrayList<ListViewItem>();
    ListViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);


        // Adapter 생성
        adapter = new ListViewAdapter();

        // 리스트뷰 참조 및 Adapter달기
        listview = (ListView) findViewById(R.id.listview2);
        listview.setAdapter(adapter);

        Button search_btn = (Button)findViewById(R.id.search_btn);
        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Favorite.this, SearchPlace.class);
                startActivityForResult(intent,NEW_REST);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if(requestCode == NEW_REST)
        {
            if(resultCode == RESULT_OK)
            {
                //adapter = new ListViewAdapter();
                //listview = (ListView) findViewById(R.id.listview);
                //Intent intent1 = getIntent();
                String title1 = data.getStringExtra("title");
                String desc1 = data.getStringExtra("desc");
                //list.add("title",title1);

                adapter.addItem(ContextCompat.getDrawable(this, R.drawable.ssu), title1, desc1);
                adapter.notifyDataSetChanged();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}