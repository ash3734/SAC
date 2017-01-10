package test.SACAPP;

/**
 * Created by 장태림 on 2016-11-08.
 */
public class ClassInfo {
    String className="";
    String classCode = "";
    String lectureTime="";
    String profName = "";
    public ClassInfo(String classCode, String className, String lectureTime, String profName){
        super();
        this.className = className;
        this.classCode = classCode;
        this.lectureTime = lectureTime;
        this.profName = profName;
    }
    public ClassInfo(){}
}
