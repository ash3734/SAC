package test.SACAPP;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by 장태림 on 2016-11-07.
 */
public class WeeksProf extends Activity {
    TextView tvMyClass;
    ExpandableListView listMain;
    private DbOpenHelper mDbOpenHelper;
    private Cursor mCursor;
    private ArrayList<Weeks> arrayGroup = new ArrayList<Weeks>();
    private HashMap<Weeks, ArrayList<Attendance>> arrayChild = new HashMap<Weeks, ArrayList<Attendance>>();
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weeks_prof);

        //데이터베이스 생성(파라메터 Context) 및 오픈
        mDbOpenHelper = new DbOpenHelper(this);
        try {
            mDbOpenHelper.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        mCursor = mDbOpenHelper.mdb.rawQuery("select * from attendance", null);
        while (mCursor.moveToNext()) {
            setArrayData(mCursor.getString(0),mCursor.getString(1),mCursor.getString(2),mCursor.getString(3),mCursor.getString(4),mCursor.getString(5),mCursor.getString(6),mCursor.getString(7));
        }
        mCursor.close();

        // 강의명 출력
        tvMyClass = (TextView) findViewById(R.id.tvClassNameWeeksProf);
        Intent intent = getIntent();
        tvMyClass.setText(intent.getStringExtra("classname"));

        // 확장 리스트뷰
        listMain = (ExpandableListView) this.findViewById(R.id.expandableListView1);
        //setArrayData();
        listMain.setAdapter(new AdptMain(this, arrayGroup, arrayChild));
    }
    private void setArrayData(String ID, String week, String date, String department, String grade, String myNum, String name, String state){
        arrayGroup.add(new Weeks(week,date));
        ArrayList<Attendance> week1 = new ArrayList<Attendance>();
        week1.add(new Attendance(ID, department, grade,  myNum, name , state));
        arrayChild.put(arrayGroup.get(arrayGroup.size()-1), week1);
    }
}
