package test.SACAPP;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.ScanSettings;
import android.content.Context;
import android.os.Bundle;
import android.os.ParcelUuid;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 2011112311 on 2016-11-08.
 */

public class ScannerFragment extends ListFragment {




    /**
     * test!
     * 0 = none, 2 = central, 4 = peripheral
     */
    private int mBleMode;
    private BluetoothManager mBluetoothManager;
    private BluetoothAdapter mBluetoothAdapter;
    private ScanCallback mScanCallback;
    private ScanResultAdapter mScanResultAdapter;
    private static final String TAG = ScannerFragment.class.getSimpleName();

    BLEScanHelper mBleScan = BLEScanHelper.getInstance();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setHasOptionsMenu(true);
        setRetainInstance(true);

        mScanResultAdapter = new ScanResultAdapter(getActivity().getApplicationContext(),
                LayoutInflater.from(getActivity()));

        //adapter를 받아오기 위해
        //먼 여행을 다녀왔음
        mBluetoothManager = (BluetoothManager) getActivity().getApplicationContext().getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = mBluetoothManager.getAdapter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = super.onCreateView(inflater, container, savedInstanceState);

        // Use getActivity().getApplicationContext() instead of just getActivity() because this
        // object lives in a fragment and needs to be kept separate from the Activity lifecycle.
        //
        // We could get a LayoutInflater from the ApplicationContext but it messes with the
        // default theme, so generate it from getActivity() and pass it in separately.
//        mNewDevicesArrayAdapter = new ArrayAdapter<BLEDevice>(getActivity().getApplicationContext(), R.layout.device_name);

        setListAdapter(mScanResultAdapter);

        mBleScan.setmContext(getActivity().getApplicationContext(), mBleScanEvent);


        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Trigger refresh on app's 1st load
//        startScanning();
//        textView_scan.setText("view created");

        getListView().setDivider(null);
        getListView().setDividerHeight(0);

        setEmptyText("No Attendance");

//        mBleCent.init(getActivity().getApplicationContext(), mBleDiscoverCallback);
//        startScanning();    //스캔 시작


    }


    public void fragScanStart(){
        Log.d("scannFrag","Start");
        startScanning();
    }

    public void fragScanStop(){
        stopScanning();
    }


    private BLEScanEvent mBleScanEvent = new BLEScanEvent() {
        @Override
        public void getData(String string) {
            MyClassProf myClassProf = (MyClassProf) getActivity();
            myClassProf.setMainName(string);
        }
    };



    /**
     * Start scanning for BLE Advertisements (& set it up to stop after a set period of time).
     */
    public void startScanning() {
        if (mScanCallback == null) {
            Log.d(TAG, "Starting Scanning");

            // Will stop the scanning after a set time.
            /*mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    stopScanning();
                }
            }, SCAN_PERIOD);*/

            ScanFilter scanFilter = new ScanFilter.Builder()
                    .setServiceUuid(new ParcelUuid(BLEChatProfile.SERVICE_UUID))
                    .build();
            ArrayList<ScanFilter> filters = new ArrayList<ScanFilter>();
            filters.add(scanFilter);

            ScanSettings settings = new ScanSettings.Builder()
//                    .setScanMode(ScanSettings.SCAN_MODE_BALANCED)
                    .setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY)
                    .build();

            // Kick off a new scan.
            mScanCallback = new SampleScanCallback();
            mBluetoothAdapter.getBluetoothLeScanner().startScan(filters, settings, mScanCallback);

            /*String toastText = "Scanning for "
                    + TimeUnit.SECONDS.convert(SCAN_PERIOD, TimeUnit.MILLISECONDS)
                    + " seconds.";*/
//            Toast.makeText(getActivity(), "Scanning...", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getActivity(), "Scanning already started.", Toast.LENGTH_SHORT);
        }
    }

    /**
     * Stop scanning for BLE Advertisements.
     */
    public void stopScanning() {
        Log.d(TAG, "Stopping Scanning");

        // Stop the scan, wipe the callback.
        mBluetoothAdapter.getBluetoothLeScanner().stopScan(mScanCallback);
        mScanCallback = null;

        // Even if no new results, update 'last seen' times.
        mScanResultAdapter.notifyDataSetChanged();
    }

    /**
     * test!
     * discover call back을 버리자
     */
    private class SampleScanCallback extends ScanCallback {

        @Override
        public void onBatchScanResults(List<ScanResult> results) {
            super.onBatchScanResults(results);
            for (ScanResult result : results) {
                //test!
                connectBleDevice(result);
                mScanResultAdapter.add(result);
            }
            mScanResultAdapter.notifyDataSetChanged();
        }

        @Override
        public void onScanResult(int callbackType, ScanResult result) {
            super.onScanResult(callbackType, result);
            connectBleDevice(result);
            mScanResultAdapter.add(result);
            mScanResultAdapter.notifyDataSetChanged();
        }

        @Override
        public void onScanFailed(int errorCode) {
            super.onScanFailed(errorCode);
            Toast.makeText(getActivity(), "Scan failed with error: " + errorCode, Toast.LENGTH_LONG)
                    .show();
        }
    }

    /**
     * test!
     * BluetoothChatFragment의 connectBleDevice만 가져옴
     * 이걸 통해 여러 개의 디바이스를 연결해볼 계획
     *
     */
    private void connectBleDevice(ScanResult result){
        mBleMode = 2;
        if(mScanResultAdapter.isExist(result)>=0){
            Log.d(TAG, "already connected");
        }
        else {
            Log.d(TAG, "try connecting");
            BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(result.getDevice().getAddress());
            mBleScan.connectBLE(getActivity().getApplicationContext(), device);
        }
    }
}