package com.example.ssuroid;

public class CourseInfo {

    //수강 과목의 이름, 세부 정보
    private  String title;
    private  String desc;
    private  int cnt;


    public CourseInfo(String title, String desc, int cnt){
        this.title = title;
        this.desc = desc;
        this.cnt = cnt;
    }

    public String getTitle(){
        return this.title;
    }

    public void setTitle(String title){
        this.title=title;
    }

    public String getDesc(){
        return this.desc;
    }

    public void setDesc(String desc){
        this.desc=desc;
    }

    public int getCnt(){
        return this.cnt;
    }

    public void setCnt(int cnt){
        this.cnt=cnt;
    }
}
