package test.SACAPP;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattServer;
import android.bluetooth.BluetoothGattServerCallback;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.AdvertiseCallback;
import android.bluetooth.le.AdvertiseData;
import android.bluetooth.le.AdvertiseSettings;
import android.bluetooth.le.BluetoothLeAdvertiser;
import android.content.Context;
import android.os.ParcelUuid;
import android.util.Log;

/**
 * Created by 2011112311 on 2016-11-08.
 */

public class BLEAdvertiseHelper {
    private BluetoothManager mBluetoothManager;
    private BluetoothAdapter mBluetoothAdapter;
    private BluetoothLeAdvertiser mBluetoothLeAdvertiser;
    //    private ScanResultAdapter mScanResultAdapter;
    private BluetoothGattServer mBluetoothGattServer;
    private static final String TAG = BLEAdvertiseHelper.class.getSimpleName();

    private Attendance attendance = null;
    private Context mContext;
    private static BLEAdvertiseHelper instance = new BLEAdvertiseHelper();

    private BLEAdvertiseHelper(){

    }

    public static BLEAdvertiseHelper getInstance(){
        if(instance == null){
            synchronized (BLEAdvertiseHelper.class){
                if(instance == null){
                    instance = new BLEAdvertiseHelper();
                }
            }
        }
        return instance;
    }

    private void setBluetoothAdapter() {
        mBluetoothManager = (BluetoothManager) mContext.getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = mBluetoothManager.getAdapter();
        mBluetoothLeAdvertiser = mBluetoothAdapter.getBluetoothLeAdvertiser();
    }

    /**
     * Initializes all BLE Peripheral services so we can advertise later on.
     */
    private void initService(String str) {
        mBluetoothGattServer = mBluetoothManager.openGattServer(mContext, mGattServerCallback);

        BluetoothGattService service = new BluetoothGattService(BLEChatProfile.SERVICE_UUID,
                BluetoothGattService.SERVICE_TYPE_PRIMARY);

        //test!
        BluetoothGattCharacteristic usrInfCharacteristic =
                new BluetoothGattCharacteristic(BLEChatProfile.CHARACTERISTIC_USERINFO_UUID,
                        //Read-write characteristic, supports notifications
                        BluetoothGattCharacteristic.PROPERTY_READ,
                        BluetoothGattCharacteristic.PERMISSION_READ);
        //test!
        //char의 data 설정
        //중요!
        //학번을 넘겨주어야 함, 뒤에 contain 방법을 모르니까 !를 붙여서 split
        usrInfCharacteristic.setValue(str);

        service.addCharacteristic(usrInfCharacteristic);

        mBluetoothGattServer.addService(service);
    }

    /**
     * Advertise the services initialized before on initService() method
     */
    private void advertiseService() {
        AdvertiseSettings settings = new AdvertiseSettings.Builder()
//                .setAdvertiseMode(AdvertiseSettings.ADVERTISE_MODE_BALANCED)
                .setAdvertiseMode(AdvertiseSettings.ADVERTISE_MODE_LOW_LATENCY)
                .setConnectable(true)
                .setTimeout(0)
//                .setTxPowerLevel(AdvertiseSettings.ADVERTISE_TX_POWER_MEDIUM)
                .setTxPowerLevel(AdvertiseSettings.ADVERTISE_TX_POWER_HIGH)
                .build();

        AdvertiseData data = new AdvertiseData.Builder()
                .setIncludeDeviceName(true) //기본 scan uuid를 쓰지 않으면 이것때문에 31바이트를 초과할 수 있다.
                .addServiceUuid(new ParcelUuid(BLEChatProfile.SERVICE_UUID))
                .build();

        mBluetoothLeAdvertiser.startAdvertising(settings, data, mAdvertiseCallback);
    }

    /**
     * Initialize BLE Advertisement
     */
    public void initiateAdvertising(Context context, String str) {
        mContext = context;
        setBluetoothAdapter();
        initService(str);
    }

    /**
     * Initialize BLE Advertisement
     */
    public void startAdvertising() {
        advertiseService();
    }


    /*
     * Terminate the advertiser
     */
    public void stopAdvertising() {
        if (mBluetoothLeAdvertiser == null)
            return;

        mBluetoothLeAdvertiser.stopAdvertising(mAdvertiseCallback);
    }

    private AdvertiseCallback mAdvertiseCallback = new AdvertiseCallback() {
        @Override
        public void onStartSuccess(AdvertiseSettings settingsInEffect) {
            Log.i(TAG, "Peripheral Advertise Started.");
        }

        @Override
        public void onStartFailure(int errorCode) {
            Log.w(TAG, "Peripheral Advertise Failed: " + errorCode);
        }
    };


    //test!
    public void tmpAtt(Attendance att){
        attendance = att;
    }



    private BluetoothGattServerCallback mGattServerCallback = new BluetoothGattServerCallback() {
        @Override
        public void onConnectionStateChange(BluetoothDevice device, int status, int newState) {
            super.onConnectionStateChange(device, status, newState);
            Log.i(TAG, "onConnectionStateChange "
                    + BLEChatProfile.getStatusDescription(status) + " "
                    + BLEChatProfile.getStateDescription(newState));
            if (status == BluetoothGatt.GATT_SUCCESS) {
                if (newState == BluetoothGatt.STATE_CONNECTED) {
//                    mConnectedDevices.add(device);
//                    notifyAdvListeners(BLEPeripheralHelper.NotifyAdvAction.NOTIFY_ADV_ACTION_CLIENT_CONNECT, device);
                } else if (newState == BluetoothGatt.STATE_DISCONNECTED) {
//                    mConnectedDevices.remove(device);
//                    notifyChatListeners(BLEPeripheralHelper.NotifyChatAction.NOTIFY_CHAT_ACTION_CLIENT_DISCONNECT, device);
                }
            } else {
                String error = "Error:" + status;
//                notifyChatListeners(BLEPeripheralHelper.NotifyChatAction.NOTIFY_CHAT_ACTION_CONNECTION_ERROR, error);
//                notifyAdvListeners(BLEPeripheralHelper.NotifyAdvAction.NOTIFY_ADV_ACTION_CONNECTION_ERROR, error);
            }
        }

        @Override
        public void onCharacteristicReadRequest(BluetoothDevice device,
                                                int requestId,
                                                int offset,
                                                BluetoothGattCharacteristic characteristic) {
            super.onCharacteristicReadRequest(device, requestId, offset, characteristic);
            Log.i(TAG, "onCharacteristicReadRequest " + characteristic.getUuid().toString());
            byte [] value;
            if (BLEChatProfile.CHARACTERISTIC_USERINFO_UUID.equals(characteristic.getUuid())) {
                value = characteristic.getValue();
            } else {
                value = new byte[0];
            }

            mBluetoothGattServer.sendResponse(device,
                    requestId,
                    BluetoothGatt.GATT_SUCCESS,
                    offset,
                    value);
        }

        @Override
        public void onCharacteristicWriteRequest(BluetoothDevice device,
                                                 int requestId,
                                                 BluetoothGattCharacteristic characteristic,
                                                 boolean preparedWrite,
                                                 boolean responseNeeded,
                                                 int offset,
                                                 byte[] value) {
            super.onCharacteristicWriteRequest(device, requestId, characteristic, preparedWrite, responseNeeded, offset, value);
            Log.i(TAG, "onCharacteristicWriteRequest " + characteristic.getUuid().toString());
            /*int gatResult = BluetoothGatt.GATT_SUCCESS;
            try{
                if (BLEChatProfile.CHARACTERISTIC_MESSAGE_UUID.equals(characteristic.getUuid())) {
                    String msg = new String(value, "UTF-8");
                    notifyChatListeners(BLEPeripheralHelper.NotifyChatAction.NOTIFY_CHAT_ACTION_MESSAGE, msg);
                    *//*for (BluetoothDevice connectedDevice : mConnectedDevices) {
                        BluetoothGattCharacteristic msgCharacteristic = mGattServer.getService(BLEChatProfile.SERVICE_UUID)
                                .getCharacteristic(BLEChatProfile.CHARACTERISTIC_DESC_UUID);
                        msgCharacteristic.setValue(msg.getBytes());
                        mGattServer.notifyCharacteristicChanged(connectedDevice, msgCharacteristic, false);
                    }*//*
                }else if(BLEChatProfile.CHARACTERISTIC_BLE_TRANSFER_UUID.equals(characteristic.getUuid())) {
                    notifyChatListeners(BLEPeripheralHelper.NotifyChatAction.NOTIFY_CHAT_ACTION_BLE_STREAM, value);
                }
            }catch (UnsupportedEncodingException ex) {
                notifyChatListeners(BLEPeripheralHelper.NotifyChatAction.NOTIFY_CHAT_ACTION_CONNECTION_ERROR, ex.toString());
                gatResult = BluetoothGatt.GATT_FAILURE;
            }finally{
                if (responseNeeded) {
                    mGattServer.sendResponse(device,
                            requestId,
                            gatResult,
                            offset,
                            value);
                }
            }*/
        }

        @Override
        public void onDescriptorWriteRequest(BluetoothDevice device,
                                             int requestId, BluetoothGattDescriptor descriptor,
                                             boolean preparedWrite, boolean responseNeeded,
                                             int offset, byte[] value) {
            /*if (responseNeeded) {
                mGattServer.sendResponse(device,
                        requestId,
                        BluetoothGatt.GATT_SUCCESS,
                        offset,
                        value);
            }*/
        }
    };











}
