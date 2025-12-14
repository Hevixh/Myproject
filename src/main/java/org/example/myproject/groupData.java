package org.example.myproject;

public class groupData {
    private String group;
    private  String program;
    private String  faculty;

    public groupData(String group, String program, String faculty){
        this.group = group;
        this.program = program;
        this.faculty = faculty;
    }
    public String getGroup(){
        return group;
    }
    public String getProgram(){
        return program;
    }
    public String getFaculty(){
        return faculty;
    }
}
