package test.SACAPP;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
/**
 * Created by 장태림 on 2016-11-07.
 */

public class AdptMain extends BaseExpandableListAdapter {
    private Context context;
    private ArrayList<Weeks> arrayGroup;
    private HashMap<Weeks, ArrayList<Attendance>> arrayChild;
    public AdptMain(Context context, ArrayList<Weeks> arrayGroup, HashMap<Weeks, ArrayList<Attendance>> arrayChild){
        super();
        this.context = context;
        this.arrayGroup = arrayGroup;
        this.arrayChild = arrayChild;;
    }
    public int getGroupCount(){
        return arrayGroup.size();
    }
    public int getChildrenCount(int groupPosition){
        return arrayChild.get(arrayGroup.get(groupPosition)).size();
    }
    public Object getGroup(int groupPosition){
        return arrayGroup.get(groupPosition);
    }
    public Object getChild(int groupPosition, int childPosition){
        return arrayChild.get(arrayGroup.get(groupPosition)).get(childPosition);
    }
    public long getGroupId(int groupPosition){
        return groupPosition;
    }
    public long getChildId(int groupPosition, int childPosition){
        return childPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String groupWeek = arrayGroup.get(groupPosition).week;
        String groupDate = arrayGroup.get(groupPosition).date;
        View v = convertView;

        if(v==null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = (LinearLayout) inflater.inflate(R.layout.group, null);
        }
        TextView textGroupWeek = (TextView) v.findViewById(R.id.textGroupWeek);
        textGroupWeek.setText(groupWeek);
        TextView textGroupDate = (TextView) v.findViewById(R.id.textGroupDate);
        textGroupDate.setText(groupDate);
        return v;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        String childNo = arrayChild.get(arrayGroup.get(groupPosition)).get(childPosition).no;
        String childDepartment = arrayChild.get(arrayGroup.get(groupPosition)).get(childPosition).department;
        String childMyNum = arrayChild.get(arrayGroup.get(groupPosition)).get(childPosition).myNum;
        String childName = arrayChild.get(arrayGroup.get(groupPosition)).get(childPosition).name;
        String childGrade = arrayChild.get(arrayGroup.get(groupPosition)).get(childPosition).grade;
        String childIsChecked = arrayChild.get(arrayGroup.get(groupPosition)).get(childPosition).isChecked;
        View v = convertView;

        if(v==null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = (LinearLayout) inflater.inflate(R.layout.attendance, null);
        }
        TextView textGroupNo = (TextView) v.findViewById(R.id.tvNo);
        textGroupNo.setText(childNo);
        TextView textGroupDepartment = (TextView) v.findViewById(R.id.tvDepartment);
        textGroupDepartment.setText(childDepartment);
        TextView textGroupMyNum = (TextView) v.findViewById(R.id.tvMyNum);
        textGroupMyNum.setText(childMyNum);
        TextView textGroupName = (TextView) v.findViewById(R.id.tvName);
        textGroupName.setText(childName);
        TextView textGroupGrade = (TextView) v.findViewById(R.id.tvGrade);
        textGroupGrade.setText(childGrade);
        TextView textGroupIsChecked = (TextView) v.findViewById(R.id.tvIsChecked);
        textGroupIsChecked.setText(childIsChecked);

        return v;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
