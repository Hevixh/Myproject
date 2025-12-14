package org.example.myproject;

import java.util.Date;

public class studentData {
    private int zk;
    private String group;
    private int course;
    private  String name;
    private String  surname;
    private String gender;
    private Date birth;
    private String image;

    public studentData(int zk, String group, int course, String name, String surname, String gender, Date birth, String image){
        this.zk = zk;
        this.group = group;
        this.course = course;
        this.name = name;
        this.surname =surname;
        this.gender = gender;
        this.birth = birth;
        this.image = image;
    }

    public int getZk(){
        return zk;
    }
    public String getGroup(){
        return group;
    }
    public int getCourse(){
        return course;
    }
    public String getName(){
        return name;
    }
    public  String getSurname(){
        return surname;
    }
    public String getGender(){
        return gender;
    }
    public Date getBirth() {
        return birth;
    }
    public String getImage() {
        return image;
    }

}
