package test.SACAPP;

import android.provider.BaseColumns;

/**
 * Created by 장태림 on 2016-11-08.
 */
public class DataBase {
    //데이터베이스 호출 시 사용될 생성자
    public static final class CreateDB implements BaseColumns {
        public static final String S_NUM = "s_num";//학번
        public static final String NAME = "name";//이름
        public static final String GRADE = "grade";//학년
        public static final String MAJOR = "major";//학과

        public static final String CLASSCODE = "classcode";//과목코드
        public static final String CLASSNAME = "classname";//과목명
        public static final String LECTURETIME = "lecturetime";//강의시간
        public static final String PROFNAME = "profname";//강의시간

        public static final String STUDENT = "SANDP";
        public static final String CLASS = "class";
        public static final String SUGANG = "sugang";
        public static final String STATE = "state"; //출결상태(출석,지각,결석)
        public static final String ATTENDANCE = "attendance";
        public static final String WEEK = "week";
        public static final String DATE = "date";

        public static final String _CREATE1 =
                "create table " + STUDENT + "("
                        + _ID + " integer, "
                        + S_NUM + " text primary key not null, "
                        + NAME + " text not null, "
                        + MAJOR + " text not null, "
                        + GRADE + " text);";
        public static final String _CREATE2 =
                "create table " + CLASS + "("
                        + _ID + " integer, "
                        + CLASSCODE + " text primary key not null, "
                        + CLASSNAME + " text not null, "
                        + LECTURETIME + " text not null,"
                        + PROFNAME + " text not null);";
        public static final String _CREATE3 =
                "create table " + SUGANG + "("
                        + _ID + " integer primary key autoincrement, "
                        + S_NUM + " text not null, "
                        + CLASSCODE + " text not null);";
        public static final String _CREATE4 =
                "create table " + ATTENDANCE + "("
                        + _ID + " integer primary key, "
                        + WEEK + " text not null, "
                        + DATE + " text not null, "
                        + MAJOR + " text not null, "
                        + GRADE + " text not null, "
                        + S_NUM + " text not null, "
                        + NAME + " text not null, "
                        + STATE + " text not null);";

        public static final String _INSERT1 =
                "insert into " + STUDENT + " values (1, '2011112323', '유재환', '컴공', '4학년');";
        public static final String _INSERT2 =
                "insert into " + STUDENT + " values (2, '2011112370', '장태림', '컴공', '4학년');";
        public static final String _INSERT3 =
                "insert into " + STUDENT + " values (3, '2011112311', '정승현', '컴공', '4학년');";
        public static final String _INSERT4 =
                "insert into " + STUDENT + " values (4, '2013112015', '박지현', '컴공', '4학년');";
        public static final String _INSERT5 =
                "insert into " + STUDENT + " values (5, '2012112001', '변연규', '컴공', '3학년');";
        public static final String _INSERT6 =
                "insert into " + STUDENT + " values (6, '2012111939', '김용현', '컴공', '3학년');";
        public static final String _INSERT7 =
                "insert into " + STUDENT + " values (7, '2011112296', '박광순', '컴공', '3학년');";
        public static final String _INSERT8 =
                "insert into " + STUDENT + " values (8, '2011112301', '임동우', '컴공', '3학년');";
        public static final String _INSERT9 =
                "insert into " + STUDENT + " values (9, '2011112302', '금영환', '컴공', '3학년');";
        public static final String _INSERT10 =
                "insert into " + STUDENT + " values (10, '2011112303', '임승욱', '컴공', '3학년');";

        public static final String _INSERT11 =
                "insert into " + CLASS + " values (1, 'cse2016-01', '종합설계2', '월15:00~17:00/금16:30~18:30', '안종석');";
        public static final String _INSERT12 =
                "insert into " + CLASS + " values (2, 'cse2016-02', '데이터통신입문', '화13:00~14:30/목13:00~14:30', '안종석');";
        public static final String _INSERT13 =
                "insert into " + CLASS + " values (3, 'cse2016-03', '네트워킹', '월13:30~15:00/금13:30~15:00', '안종석');";
        public static final String _INSERT14 =
                "insert into " + CLASS + " values (4, 'cse2016-04', '데이터베이스프로그래밍', '화10:00~12:00/목14:00~16:00', '이용규');";
        public static final String _INSERT15 =
                "insert into " + CLASS + " values (5, 'cse2016-05', '컴퓨터보안', '월10:00~12:00/금14:00~16:00', '문봉교');";
        public static final String _INSERT16 =
                "insert into " + CLASS + " values (6, 'cse2016-06', '컴퓨터구조', '화10:00~12:00/목14:00~16:00', '장태무');";
        public static final String _INSERT17 =
                "insert into " + CLASS + " values (7, 'cse2016-07', '알고리즘', '화10:00~12:00/수14:00~16:00', '정진우');";
        public static final String _INSERT18 =
                "insert into " + CLASS + " values (8, 'cse2016-08', '인공지능', '월10:00~12:00/목14:00~16:00', '김준태');";
        public static final String _INSERT19 =
                "insert into " + CLASS + " values (9, 'cse2016-09', '시스템소프트웨어', '수10:00~12:00/목14:00~16:00', '이금석');";
        public static final String _INSERT20 =
                "insert into " + CLASS + " values (10, 'cse2016-10', '임베디드', '화10:00~12:00/금14:00~16:00', '이강우');";

        public static final String _INSERT21 =
                "insert into " + SUGANG + " values (1, '2011112323', 'cse2016-01');";
        public static final String _INSERT22 =
                "insert into " + SUGANG + " values (2, '2011112323', 'cse2016-04');";
        public static final String _INSERT23 =
                "insert into " + SUGANG + " values (3, '2011112323', 'cse2016-05');";
        public static final String _INSERT24 =
                "insert into " + SUGANG + " values (4, '2011112370', 'cse2016-01');";
        public static final String _INSERT25 =
                "insert into " + SUGANG + " values (5, '2011112370', 'cse2016-02');";
        public static final String _INSERT26 =
                "insert into " + SUGANG + " values (6, '2011112370', 'cse2016-03');";

        public static final String _INSERT40 =
                "insert into " + ATTENDANCE + " values (1, '1주차', '11/3', '컴퓨터공학과', '4학년', '2011112323', '유재환', '출석');";
        public static final String _INSERT41 =
                "insert into " + ATTENDANCE + " values (2, '1주차', '11/3', '컴퓨터공학과', '4학년', '2011112323', '장태림', '출석');";
        public static final String _INSERT42 =
                "insert into " + ATTENDANCE + " values (3, '1주차', '11/3', '컴퓨터공학과', '4학년', '2011112323', '정승현', '출석');";
        public static final String _INSERT43 =
                "insert into " + ATTENDANCE + " values (4, '1주차', '11/8', '컴퓨터공학과', '4학년', '2011112323', '유재환', '출석');";
        public static final String _INSERT44 =
                "insert into " + ATTENDANCE + " values (5, '1주차', '11/8', '컴퓨터공학과', '4학년', '2011112323', '장태림', '출석');";
        public static final String _INSERT45 =
                "insert into " + ATTENDANCE + " values (6, '1주차', '11/8', '컴퓨터공학과', '4학년', '2011112323', '정승현', '출석');";
    }
}
