package test.SACAPP;

import android.app.ActionBar;
import android.app.Activity;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothProfile;
import android.content.Context;
import android.util.Log;

/**
 * Created by 2011112311 on 2016-11-08.
 */

public class BLEScanHelper {

    BLEScanEvent mBleScanEvent;

    //    private ScanResultAdapter mScanResultAdapter;
    private static final String TAG = BLEScanHelper.class.getSimpleName();

    private Context mContext;
    private static BLEScanHelper instance = new BLEScanHelper();

    private BLEScanHelper(){

    }

    public static BLEScanHelper getInstance(){
        if(instance == null){
            synchronized (BLEScanHelper.class){
                if(instance == null){
                    instance = new BLEScanHelper();
                }
            }
        }
        return instance;
    }

    public void setmContext(Context context, BLEScanEvent bleScanEvent){
        mBleScanEvent = bleScanEvent;
        mContext=context;

    }

    public BluetoothGattCallback mGattCallback = new BluetoothGattCallback(){
        @Override
        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
            super.onConnectionStateChange(gatt, status, newState);
            Log.d(TAG, "onConnectionStateChange:"
                    +BLEChatProfile.getStatusDescription(status)+" "
                    +BLEChatProfile.getStateDescription(newState));

            if(status == BluetoothGatt.GATT_SUCCESS) {
                if (newState == BluetoothProfile.STATE_CONNECTED) {
                    gatt.discoverServices();
                } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {

                }
            }else{
                final int finalStatus = status;

            }
        }

        @Override
        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
            super.onServicesDiscovered(gatt, status);
            Log.d(TAG, "onServicesDiscovered!!:");
            //test! 여기까지 순차적으로 온다
            for (BluetoothGattService service : gatt.getServices()) {
                Log.d(TAG, "Service: "+service.getUuid());
                if (BLEChatProfile.SERVICE_UUID.equals(service.getUuid())) {
                    gatt.readCharacteristic(service.getCharacteristic(BLEChatProfile.CHARACTERISTIC_USERINFO_UUID));
                }
            }

        }

        @Override
        public void onCharacteristicRead(BluetoothGatt gatt,
                                         final BluetoothGattCharacteristic characteristic,
                                         int status) {
            Log.d(TAG, "oncharread!!");
            super.onCharacteristicRead(gatt, characteristic, status);
            if (BLEChatProfile.CHARACTERISTIC_USERINFO_UUID.equals(characteristic.getUuid())) {
                String stringfortoast = new String(characteristic.getValue());

//                activity.addAttendanceList(stringfortoast);


                mBleScanEvent.getData(stringfortoast);


                Log.d(TAG, "stuid::"+stringfortoast);
                //Register for further updates as notifications
                gatt.setCharacteristicNotification(characteristic, true);
            }
        }

        @Override
        public void onCharacteristicChanged(BluetoothGatt gatt,
                                            final BluetoothGattCharacteristic characteristic) {
            super.onCharacteristicChanged(gatt, characteristic);
            Log.i(TAG, "Notification of message characteristic changed on server!!");
            if (BLEChatProfile.CHARACTERISTIC_RFCOMM_TRANSFER_UUID.equals(characteristic.getUuid())) {

            } else if (BLEChatProfile.CHARACTERISTIC_BLE_TRANSFER_UUID.equals(characteristic.getUuid())) {

            }
        }
    }; //End BluetoothGattCallback





    //test!
    public void connectBLE(Context context, BluetoothDevice device){
        Log.d(TAG, "connecting gatt!!");
        device.connectGatt(context, false, mGattCallback);
    }

}
