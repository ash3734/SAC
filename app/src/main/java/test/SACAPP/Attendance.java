package test.SACAPP;

/**
 * Created by 장태림 on 2016-11-07.
 */
public class Attendance{
    String no="";
    String department = "";
    String myNum="";
    String name = "";
    String grade="";
    String isChecked = "";
    public Attendance(String no, String department,  String grade, String myNum, String name, String isChecked){
        super();
        this.no = no;
        this.department = department;
        this.myNum = myNum;
        this.name = name;
        this.grade = grade;
        this.isChecked = isChecked;
    }
    public Attendance(){}
}
