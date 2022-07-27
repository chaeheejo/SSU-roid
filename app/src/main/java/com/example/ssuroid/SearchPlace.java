package com.example.ssuroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class SearchPlace extends AppCompatActivity {

    ListView listview = null ;
    private AdapterView.OnItemClickListener listener;
    final int NEW_REST = 22;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_place);


        ArrayList<ListViewItem> list;

        final ListViewAdapter2 adapter;

        Point size;

        LayoutInflater inflater;
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //final AlertDialog dialog = builder.create();

        // Adapter 생성
        adapter = new ListViewAdapter2();

        // 리스트뷰 참조 및 Adapter달기
        listview = (ListView) findViewById(R.id.listview1);
        listview.setAdapter(adapter);
        //listview.setOnItemClickListener(listener);


        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.ssu),
                "IT학부 테라스 [스터디/팀플 장소]", "정보과학관 2~5층 중앙복도\n오전7시~오후11시") ;
        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.ssu),
                "도담식당 [교내 식당]", "신양관 2층\n오전 11시반~ 오후2시/ 오후 5시~6시반") ;
        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.ssu),
                "학생회관 생협 [교내 식당]", "학생회관 4층\n오전 9시~오후 5시") ;
        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.ssu),
                "융합특성화자유전공학부 과방\n[과별 공간]", "문화관 303호\n오전 9시~오후 11시") ;
        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.ssu),
                "숭실마루 [카페]", "중앙도서관 6층\n오전11시~오후 5시") ;
        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.ssu),
                "IT학부 사무실 [행정 업무처]", "정보과학관 1층\n오전 10시~오후 5시반") ;
        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.ssu),
                "기숙사 식당 [교내 식당]", "레지던스홀 지하 1층\n오전 8시~오후 6시") ;
        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.ssu),
                "학생회관 식당 [교내 식당]", "학생회관 3층\n오전 10시~오후 3시") ;
        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.ssu),
                "동아리방 [스터디/팀플 장소]", "학생회관 1~3층\n오전 12시~오후 11시") ;
        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.ssu),
                "푸드코트 [교내 식당]", "학생회관 2층\n오전 11시~오후 3시") ;
        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.ssu),
                "스낵코너 [교내 식당]", "학생회관 3층\n오전 11시~오후 3시") ;
        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.ssu),
                "The Kitchen [교내 식당]", "전산관 1층\n오전 11시~오후 5시") ;
        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.ssu),
                "카페331 [카페]", "웨스트민스트홀 3층\n오전 10시~오후 4시") ;
        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.ssu),
                "서점 [행정 업무처]", "신양관 1층\n오전 11시~오후 5시") ;
        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.ssu),
                "스페이스엔 [스터디/팀플 장소]", "정보과학관 지하 1층\n오전 9시~오후 11시") ;
        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.ssu),
                "우체국 [행정 업무처]", "신양관 지하 1층\n오전 10시~오후 5시") ;
        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.ssu),
                "우리은행 [행정 업무처]", "신양관 지하 1층\n오전 10시~오후 5시") ;
        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.ssu),
                "중앙 도서관 [스터디/팀플 장소]", "중앙도서관\n오전 9시~오후 9시") ;

        /*Button plus_btn = (Button)findViewById(R.id.favorite_btn);
        plus_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SearchPlace.this, Favorite.class);
                startActivity(intent);
            }
        });*/


        EditText editTextFilter = (EditText)findViewById(R.id.editTextFilter) ;
        editTextFilter.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable edit) {
                String filterText = edit.toString();
                ((ListViewAdapter2) listview.getAdapter()).getFilter().filter(filterText);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            private void showToast(String message) {
            }
        });



        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ListViewItem dto = (ListViewItem) adapter.getItem(position);
                final Toast toast = Toast.makeText(SearchPlace.this,  dto.getTitle() + "\n장소가 추가되었습니다.", Toast.LENGTH_SHORT);

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

                builder.setTitle("즐겨찾기 추가\n");
                builder.setMessage("\n" + dto.getTitle()+"\n장소를 추가하시겠습니까?\n");
                builder.setPositiveButton("네", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(SearchPlace.this, Favorite.class);
                        intent.putExtra("title", title);
                        intent.putExtra("desc", desc);
                        int count = 1;

                        //if(count==1){
                        //    startActivity(intent);
                        //   count++;
                        //}
                        //else{
                        //startActivityForResult(intent,NEW_REST);
                        setResult(RESULT_OK,intent);
                        // }
                        finish();
                        toast.show();
                    }
                });
                builder.setNegativeButton("아니요",null);
                builder.create().show();

            }
        });


    }




}