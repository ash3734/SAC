package test.SACAPP;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by 2011112311 on 2016-11-08.
 */

public class AdvertiserFragment extends Fragment {

    private Attendance attendance = null;
    private String ident;
    private TextView tvAdvert;
    //peripheral 역할 helper
    BLEAdvertiseHelper mBleAdvert = BLEAdvertiseHelper.getInstance();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        if(getArguments() != null){
            ident = getArguments().getString("_ID");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_advertiser, container, false);

        mBleAdvert.initiateAdvertising(getActivity().getApplicationContext(), ident);

        tvAdvert = (TextView) view.findViewById(R.id.textView_advertiser);

        return view;
    }

    public void fragAdvertStart(){
        mBleAdvert.startAdvertising();
    }

    public void fragAdvertStop(){
        mBleAdvert.stopAdvertising();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    //test!
    public void tmpAtt(Attendance att){
        attendance = att;
    }

}
