package test.SACAPP;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by 장태림 on 2016-11-06.
 */
public class Main extends Activity {
    ArrayList<ClassInfo> classInfo = new ArrayList<ClassInfo>();
    String ID ="";
    private BackPressCloseHandler backPressCloseHandler;
    private DbOpenHelper mDbOpenHelper;
    private Cursor mCursor;
    int flag = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //데이터베이스 생성(파라메터 Context) 및 오픈
        mDbOpenHelper = new DbOpenHelper(this);
        try {
            mDbOpenHelper.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        backPressCloseHandler = new BackPressCloseHandler(this);

        Intent getIntent = getIntent();
        ID = getIntent.getExtras().getString("ID");

        // 로그아웃버튼
        ImageView btnLogout = (ImageView) findViewById(R.id.btnBackInMain);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("onClick", "CallLogout");
                Intent intent = new Intent(Main.this, LogIn.class);
                startActivity(intent);
                finish();
            }
        });
        // 홈버튼
        ImageView btnHome = (ImageView) findViewById(R.id.btnHomeInMain);
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("onClick", "CallHome");

            }
        });

        MyAdapter3 adapter = new MyAdapter3(
                getApplicationContext(),
                R.layout.classinfo,
                classInfo);
        // 리스트뷰 생성 및 어댑터 지정
        final ListView listview = (ListView) findViewById(R.id.listview1) ;
        listview.setAdapter(adapter) ;

        if(ID.equals("안종석") || ID.equals("이용규")) {
            mCursor = mDbOpenHelper.mdb.rawQuery("select * from class where profname = '" + ID + "'", null);
            while (mCursor.moveToNext()) {
                classInfo.add(new ClassInfo(mCursor.getString(1), mCursor.getString(2), mCursor.getString(3), ""));
            }
            mCursor.close();
            flag=1;
        }
        else if(ID.equals("2011112370") || ID.equals("2011112323")) {
            mCursor = mDbOpenHelper.mdb.rawQuery("select class.classcode, class.classname, class.lecturetime from class, sugang where class.classcode=sugang.classcode and " +
                    "sugang.s_num = '" + ID + "'", null);
            while (mCursor.moveToNext()) {
                classInfo.add(new ClassInfo(mCursor.getString(0), mCursor.getString(1), mCursor.getString(2), ""));
            }
            mCursor.close();
            flag=2;
        }


        //갱신
        adapter.notifyDataSetChanged();

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (flag){
                    case 1 :
                        Intent intent = new Intent(
                            getApplicationContext(),
                            MyClassProf.class);
                        intent.putExtra("classname", classInfo.get(position).className.toString());
                        startActivity(intent);
                        break;
                    case 2:
                        Intent intent1 = new Intent(
                                getApplicationContext(),
                                MyClassStud.class);
                        intent1.putExtra("classname", classInfo.get(position).className.toString());
                        intent1.putExtra("_id", ID);
                        startActivity(intent1);
                        break;
                }
            }
        });
    }
    class MyAdapter3 extends BaseAdapter { // 리스트 뷰의 아답타
        Context context;
        int layout;
        ArrayList<ClassInfo> classInfo;
        LayoutInflater inf;
        public MyAdapter3(Context context, int layout, ArrayList<ClassInfo> classInfo) {
            this.context = context;
            this.layout = layout;
            this.classInfo = classInfo;
            inf = (LayoutInflater)context.getSystemService
                    (Context.LAYOUT_INFLATER_SERVICE);
        }
        @Override
        public int getCount() {
            return classInfo.size();
        }
        @Override
        public Object getItem(int position) {
            return classInfo.get(position);
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
            TextView tvClassName = (TextView)convertView.findViewById(R.id.tvClassName);
            TextView tvClassCode = (TextView)convertView.findViewById(R.id.tvClassCode);
            TextView tvLectureTime = (TextView)convertView.findViewById(R.id.tvLectureTime);
            TextView tvProfName = (TextView)convertView.findViewById(R.id.tvProfName);

            ClassInfo m = classInfo.get(position);
            tvClassName.setText(m.className);
            tvClassCode.setText(m.classCode);
            tvLectureTime.setText(m.lectureTime);
            tvProfName.setText(m.profName);

            return convertView;
        }
    }
    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        backPressCloseHandler.onBackPressed();
    }
    public class BackPressCloseHandler {
        private long backKeyPressedTime = 0;
        private Toast toast;
        private Activity activity;

        public BackPressCloseHandler(Activity context) {
            this.activity = context;
        }
        public void onBackPressed() {
            if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
                backKeyPressedTime = System.currentTimeMillis();
                showGuide();
                return;
            }
            if (System.currentTimeMillis() <= backKeyPressedTime + 2000) {
                activity.finish();
                toast.cancel();
            }
        }
        public void showGuide() {
            toast = Toast.makeText(activity,
                    "\'뒤로\'버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}