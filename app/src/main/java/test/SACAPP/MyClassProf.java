package test.SACAPP;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * Created by 장태림 on 2016-11-06.
 */
public class MyClassProf extends AppCompatActivity {
    ArrayList<Attendance> att = new ArrayList<Attendance>();
    TextView tvMyClass; TextView tv;
    private DbOpenHelper mDbOpenHelper;
    private Cursor mCursor;

    MyAdapter adapter;


    //시작, 중지 버튼
    private boolean btnStartPauseFlag = false;  //true = show pause, false = show start

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myclass_prof);

        //데이터베이스 생성(파라메터 Context) 및 오픈
        mDbOpenHelper = new DbOpenHelper(this);
        try {
            mDbOpenHelper.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // 값 저장 하는 곳 여기다가 !!!!!


        // 리스트버튼
        ImageView btnList = (ImageView) findViewById(R.id.btnListMyClassProf);
        btnList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("onClick", "CallList");
                Intent intent = new Intent(MyClassProf.this, WeeksProf.class);
                intent.putExtra("classname", tvMyClass.getText().toString());
                startActivity(intent);
            }
        });
        // 홈버튼
        ImageView btnHome = (ImageView) findViewById(R.id.btnHomeMyClassProf);
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("onClick", "CallHome");

            }
        });
        // 홈버튼
        final ImageView btnStart = (ImageView) findViewById(R.id.btnStartMyClassProf);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("onClick", "CallStart");
                if(btnStartPauseFlag == false){
                    btnStartPauseFlag = true;

                    ScannerFragment tmpFrag = (ScannerFragment) getSupportFragmentManager().findFragmentById(R.id.professor_scanner_fragment_container);
                    tmpFrag.fragScanStart();
                    Log.d("prof frag","scan start");
                    /*AdvertiserFragment tmpFragTwo = (AdvertiserFragment) getSupportFragmentManager().findFragmentById(R.id.professor_advertiser_fragment_container);
                    tmpFragTwo.fragAdvertStart();
                    Log.d("prof fragtwo","advert start");*/

                    btnStart.setImageResource(R.drawable.pause);

                }else {
                    btnStartPauseFlag = false;

                    ScannerFragment tmpFrag = (ScannerFragment) getSupportFragmentManager().findFragmentById(R.id.professor_scanner_fragment_container);
                    tmpFrag.fragScanStop();
                    Log.d("prof frag","scan stop");
                    /*AdvertiserFragment tmpFragTwo = (AdvertiserFragment) getSupportFragmentManager().findFragmentById(R.id.professor_advertiser_fragment_container);
                    tmpFragTwo.fragAdvertStop();
                    Log.d("prof fragtwo","advert stop");*/

                    btnStart.setImageResource(R.drawable.start);
                }
            }
        });

        // 강의명 출력
        tvMyClass = (TextView) findViewById(R.id.tvClassNameProf);
        Intent intent = getIntent();
        tvMyClass.setText(intent.getStringExtra("classname"));
        // 날짜 출력
        tv = (TextView) findViewById(R.id.tvDateProf);
        String format = new String("yyyy년 MM월 dd일");
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.KOREA);
        tv.setText(sdf.format(new Date()));

        adapter = new MyAdapter(
                getApplicationContext(),
                R.layout.attendance,
                att);
        ListView listview = (ListView) findViewById(R.id.listview2);
        listview.setAdapter(adapter);

        setupFragments();
    }

    class MyAdapter extends BaseAdapter { // 리스트 뷰의 아답타
        Context context;
        int layout;
        ArrayList<Attendance> att;
        LayoutInflater inf;
        public MyAdapter(Context context, int layout, ArrayList<Attendance> att) {
            this.context = context;
            this.layout = layout;
            this.att = att;
            inf = (LayoutInflater)context.getSystemService
                    (Context.LAYOUT_INFLATER_SERVICE);
        }
        @Override
        public int getCount() {
            return att.size();
        }
        @Override
        public Object getItem(int position) {
            return att.get(position);
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
            TextView tvNo = (TextView)convertView.findViewById(R.id.tvNo);
            TextView tvDepartment = (TextView)convertView.findViewById(R.id.tvDepartment);
            TextView tvMyNum = (TextView)convertView.findViewById(R.id.tvMyNum);
            TextView tvName = (TextView)convertView.findViewById(R.id.tvName);
            TextView tvGrade = (TextView)convertView.findViewById(R.id.tvGrade);
            TextView tvIsChecked = (TextView)convertView.findViewById(R.id.tvIsChecked);

            Attendance m = att.get(position);
            tvNo.setText(m.no);
            tvDepartment.setText(m.department);Log.i(m.department, " ");
            tvMyNum.setText(m.myNum);Log.i(m.myNum, " ");
            tvName.setText(m.name);Log.i(m.name, " ");
            tvGrade.setText(m.grade);Log.i(m.grade, " ");
            tvIsChecked.setText("");

            return convertView;
        }
    }

    public void addAttendanceList(String string){
        Log.d("ProfAct", "call addAtt" + string);
        /*att.add(attendance);
        adapter.notifyDataSetChanged();*/
    }


    public void setMainName(String string){
        tvMyClass.setText(string);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if ((keyCode == KeyEvent.KEYCODE_BACK))
        {
//            mBleChat.stopScan();
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    private void setupFragments() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        ScannerFragment scannerFragment = new ScannerFragment();
        transaction.replace(R.id.professor_scanner_fragment_container, scannerFragment);

//        transaction.hide(scannerFragment);
        /*AdvertiserFragment advertiserFragment = new AdvertiserFragment();
        transaction.replace(R.id.professor_advertiser_fragment_container, advertiserFragment);*/

        transaction.commit();
    }
}