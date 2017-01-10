package test.SACAPP;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.SQLException;

/**
 * Created by 장태림 on 2016-11-08.
 */
public class DbOpenHelper {
    private static final String DATABASE_NAME = "SAC.db";
    private static final int DATABASE_VERSION = 1;
    SQLiteDatabase mdb;
    WordDBHelper mhelper;
    Context mCtx;
    //sqlite를 사용하기 위한 클래스
    class WordDBHelper extends SQLiteOpenHelper {
        public WordDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
            super(context, name, factory, version);
            // TODO Auto-generated constructor stub
        }
        @Override
        public void onCreate(SQLiteDatabase db) {
            //테이블 생성
            db.execSQL(DataBase.CreateDB._CREATE1);
            db.execSQL(DataBase.CreateDB._CREATE2);
            db.execSQL(DataBase.CreateDB._CREATE3);
            db.execSQL(DataBase.CreateDB._CREATE4);

            db.execSQL(DataBase.CreateDB._INSERT1);
            db.execSQL(DataBase.CreateDB._INSERT2);
            db.execSQL(DataBase.CreateDB._INSERT3);
            db.execSQL(DataBase.CreateDB._INSERT4);
            db.execSQL(DataBase.CreateDB._INSERT5);
            db.execSQL(DataBase.CreateDB._INSERT6);
            db.execSQL(DataBase.CreateDB._INSERT7);
            db.execSQL(DataBase.CreateDB._INSERT8);
            db.execSQL(DataBase.CreateDB._INSERT9);
            db.execSQL(DataBase.CreateDB._INSERT10);
            db.execSQL(DataBase.CreateDB._INSERT11);
            db.execSQL(DataBase.CreateDB._INSERT12);
            db.execSQL(DataBase.CreateDB._INSERT13);
            db.execSQL(DataBase.CreateDB._INSERT14);
            db.execSQL(DataBase.CreateDB._INSERT15);
            db.execSQL(DataBase.CreateDB._INSERT16);
            db.execSQL(DataBase.CreateDB._INSERT17);
            db.execSQL(DataBase.CreateDB._INSERT18);
            db.execSQL(DataBase.CreateDB._INSERT19);
            db.execSQL(DataBase.CreateDB._INSERT20);
            db.execSQL(DataBase.CreateDB._INSERT21);
            db.execSQL(DataBase.CreateDB._INSERT22);
            db.execSQL(DataBase.CreateDB._INSERT23);
            db.execSQL(DataBase.CreateDB._INSERT24);
            db.execSQL(DataBase.CreateDB._INSERT25);
            db.execSQL(DataBase.CreateDB._INSERT26);

            db.execSQL(DataBase.CreateDB._INSERT40);
            db.execSQL(DataBase.CreateDB._INSERT41);
            db.execSQL(DataBase.CreateDB._INSERT42);
            db.execSQL(DataBase.CreateDB._INSERT43);
            db.execSQL(DataBase.CreateDB._INSERT44);
            db.execSQL(DataBase.CreateDB._INSERT45);
        }
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldViersion, int newVersion) {
            //테이블을 지우는 구문을 수행
            db.execSQL("drop table if exists class");
            //테이블 다시 생성
            onCreate(db);
        }

        /*public Attendance selectUser(String snum){
            SQLiteDatabase db = this.getReadableDatabase();

            String query = " SELECT *" +
                    " FROM SANDP" +
                    " WHERE s_num = ?";

            Cursor cursor = db.rawQuery(query.toString(),
                    new String[]{
                            snum
                    });

            dguStudent student = null;
            if(cursor.getCount()>0) {
                cursor.moveToFirst();
                student = new dguStudent(cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4));
                cursor.close();
            }
            return student;
        }*/
    }
    //DbOpenHelper 생성자
    public DbOpenHelper(Context context) {
        this.mCtx = context;
    }
    //Db를 여는 메소드
    public DbOpenHelper open() throws SQLException {
        mhelper = new WordDBHelper(mCtx, DATABASE_NAME, null, DATABASE_VERSION);
        mdb = mhelper.getWritableDatabase();
        return this;
    }
    public void close(){ mdb.close();}
}
