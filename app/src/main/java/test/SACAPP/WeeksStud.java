package test.SACAPP;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by 장태림 on 2016-11-07.
 */
public class WeeksStud extends Activity{
    ArrayList<StudentAtt> StudAtt = new ArrayList<StudentAtt>();
    TextView tvMyClass;
    private DbOpenHelper mDbOpenHelper;
    private Cursor mCursor;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weeks_stud);

        //데이터베이스 생성(파라메터 Context) 및 오픈
        mDbOpenHelper = new DbOpenHelper(this);
        try {
            mDbOpenHelper.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // 강의명 출력
        tvMyClass = (TextView) findViewById(R.id.tvClassNameWeeksStud);
        Intent intent = getIntent();
        tvMyClass.setText(intent.getStringExtra("classname"));

        // 출석 정보 저장
        StudAtt.add(new StudentAtt("1주차", "11/1", "출석"));
        StudAtt.add(new StudentAtt("1주차", "11/3", "결석"));
        StudAtt.add(new StudentAtt("2주차", "11/8", "지각"));
        StudAtt.add(new StudentAtt("2주차", "11/10", "결석"));

        MyAdapter1 adapter = new MyAdapter1(
                getApplicationContext(),
                R.layout.child,
                StudAtt);
        ListView listview = (ListView) findViewById(R.id.listview3);
        listview.setAdapter(adapter);
    }
    class MyAdapter1 extends BaseAdapter { // 리스트 뷰의 아답타
        Context context;
        int layout;
        ArrayList<StudentAtt> studAtt;
        LayoutInflater inf;
        public MyAdapter1(Context context, int layout, ArrayList<StudentAtt> studAtt) {
            this.context = context;
            this.layout = layout;
            this.studAtt = StudAtt;
            inf = (LayoutInflater)context.getSystemService
                    (Context.LAYOUT_INFLATER_SERVICE);
        }
        @Override
        public int getCount() {
            return studAtt.size();
        }
        @Override
        public Object getItem(int position) {
            return studAtt.get(position);
        }
        @Override
        public long getItemId(int position) {
            return position;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView==null) {
                convertView = inf.inflate(layout, null);
            }
            TextView tvStudWeek = (TextView)convertView.findViewById(R.id.tvStudWeek);
            TextView tvStudDate = (TextView)convertView.findViewById(R.id.tvStudDate);
            TextView tvStudIsChecked = (TextView)convertView.findViewById(R.id.tvStudIsChecked);

            StudentAtt m = studAtt.get(position);
            tvStudWeek.setText(m.week);
            tvStudDate.setText(m.date);
            tvStudIsChecked.setText(m.isChecked);

            return convertView;
        }
    }
    class StudentAtt{
        String week = "";
        String date = "";
        String isChecked = "";
        public StudentAtt(String week, String date, String isChecked){
            super();
            this.week = week;
            this.date = date;
            this.isChecked = isChecked;
        }
        public StudentAtt(){}
    }
}
