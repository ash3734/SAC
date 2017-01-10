package test.SACAPP;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;

public class LogIn extends AppCompatActivity {
    InputMethodManager imm;
    EditText tvID; EditText tvPW;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        tvID = (EditText) findViewById(R.id.tvID);
        tvPW = (EditText) findViewById(R.id.tvPW);

        imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);

        ImageView btnEnter = (ImageView) findViewById(R.id.btnEnter);
        btnEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("onClick", "CallMain");
                Intent intent = new Intent(LogIn.this, Main.class);
                intent.putExtra("ID", tvID.getText().toString());
                tvID.setText("");
                tvID.setHint("아이디");
                tvPW.setText("");
                tvPW.setHint("비밀번호");
                startActivity(intent);
                finish();
            }
        });
    }
    public void linearOnClick(View v) {
        imm.hideSoftInputFromWindow(tvID.getWindowToken(), 0);
    }
}
