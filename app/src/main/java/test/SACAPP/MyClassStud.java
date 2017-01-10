package test.SACAPP;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by 장태림 on 2016-11-06.
 */
public class MyClassStud extends AppCompatActivity {
    TextView tvMyClass; TextView tv;
    //시작, 중지 버튼
    private boolean btnStartPauseFlagStud = false;  //true = show pause, false = show start
    String studentID = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myclass_stud);

        // 리스트버튼
        ImageView btnList = (ImageView) findViewById(R.id.btnListMyClassStud);
        btnList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("onClick", "CallList");
                Intent intent = new Intent(MyClassStud.this, WeeksStud.class);
                intent.putExtra("classname", tvMyClass.getText().toString());
                startActivity(intent);
            }
        });
        // 홈버튼
        ImageView btnHome = (ImageView) findViewById(R.id.btnHomeMyClassStud);
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("onClick", "CallHome");

            }
        });
        // 홈버튼
        final ImageView btnStart = (ImageView) findViewById(R.id.btnStartMyClassStud);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("onClick", "CallStart");
                if(btnStartPauseFlagStud == false){
                    btnStartPauseFlagStud = true;

                    AdvertiserFragment tmpFragTwo = (AdvertiserFragment) getSupportFragmentManager().findFragmentById(R.id.student_advertiser_fragment_container);
                    tmpFragTwo.fragAdvertStart();
                    Log.d("prof fragtwo","advert start");

                    btnStart.setImageResource(R.drawable.pause);
                }else{
                    btnStartPauseFlagStud = false;

                    AdvertiserFragment tmpFragTwo = (AdvertiserFragment) getSupportFragmentManager().findFragmentById(R.id.student_advertiser_fragment_container);
                    tmpFragTwo.fragAdvertStop();
                    Log.d("prof fragtwo","advert stop");

                    btnStart.setImageResource(R.drawable.start);
                }
            }
        });

        // 강의명 가져오기
        tvMyClass = (TextView) findViewById(R.id.tvClassNameStud);
        Intent intent = getIntent();
        tvMyClass.setText(intent.getStringExtra("classname"));


        //학번
        studentID = getIntent().getExtras().getString("_id");
        Log.d("getID: ", studentID);

        // 날짜 가져오기
        tv = (TextView) findViewById(R.id.tvDateStud);
        String format = new String("yyyy년 MM월 dd일");
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.KOREA);
        tv.setText(sdf.format(new Date()));

        setupFragments(studentID);
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

    private void setupFragments(String _id) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        AdvertiserFragment advertiserFragment = new AdvertiserFragment();

        Bundle args = new Bundle();
        args.putString("_ID", _id);
        advertiserFragment.setArguments(args);

        transaction.replace(R.id.student_advertiser_fragment_container, advertiserFragment);

        transaction.hide(advertiserFragment);



        transaction.commit();
    }
}