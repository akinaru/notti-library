/**
 * The MIT License (MIT)
 * <p/>
 * Copyright (c) 2015 Bertrand Martel
 * <p/>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p/>
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * <p/>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package fr.bmartel.android.notti.service.bluetooth.notti;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothGattCharacteristic;
import android.util.Log;

import java.util.ArrayList;
import java.util.UUID;

import fr.bmartel.android.notti.service.bluetooth.listener.ICharacteristicListener;
import fr.bmartel.android.notti.service.bluetooth.listener.IDeviceInitListener;
import fr.bmartel.android.notti.service.bluetooth.connection.BluetoothDeviceAbstr;
import fr.bmartel.android.notti.service.bluetooth.connection.IBluetoothDeviceConn;
import fr.bmartel.android.notti.service.bluetooth.listener.IPushListener;

/**
 * Dotti Bluetooth device management
 *
 * @author Bertrand Martel
 */
public class NottiDevice extends BluetoothDeviceAbstr implements INottiDevice {

    private String TAG = NottiDevice.this.getClass().getName();

    private String notti_service = "0000fff0-0000-1000-8000-00805f9b34fb";
    private String notti_charac = "0000fff3-0000-1000-8000-00805f9b34fb";
    private String notti_charac2 = "0000fff4-0000-1000-8000-00805f9b34fb";

    private ArrayList<IDeviceInitListener> initListenerList = new ArrayList<>();

    private boolean init = false;

    /**
     * @param conn
     */
    @SuppressLint("NewApi")
    public NottiDevice(IBluetoothDeviceConn conn) {
        super(conn);
        setCharacteristicListener(new ICharacteristicListener() {

            @Override
            public void onCharacteristicReadReceived(BluetoothGattCharacteristic charac) {

            }

            @Override
            public void onCharacteristicChangeReceived(BluetoothGattCharacteristic charac) {

            }

            @Override
            public void onCharacteristicWriteReceived(BluetoothGattCharacteristic charac) {

            }
        });
    }

    @Override
    public void init() {

        Log.i(TAG, "initializing notti");

        conn.enableDisableNotification(UUID.fromString(notti_service), UUID.fromString(notti_charac), true);
        conn.enableDisableNotification(UUID.fromString(notti_service), UUID.fromString(notti_charac2), true);

        for (int i = 0; i < initListenerList.size(); i++) {
            initListenerList.get(i).onInit();
        }
    }

    /**
     * switch led state
     *
     * @param state
     */
    @Override
    public void setOnOff(boolean state, IPushListener listener) {

        if (state)
            getConn().writeCharacteristic(notti_service, notti_charac, new byte[]{(byte) 6, (byte) 1, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF}, listener);
        else
            getConn().writeCharacteristic(notti_service, notti_charac, new byte[]{(byte) 6, (byte) 1, (byte) 0x00, (byte) 0x00, (byte) 0x00}, listener);

    }

    @Override
    public void setRGBColor(int red, int green, int blue, IPushListener listener) {

        getConn().writeCharacteristic(notti_service, notti_charac, new byte[]{(byte) 6, (byte) 1, (byte) red, (byte) green, (byte) blue}, listener);


    }

    @Override
    public void setLuminosityForColor(int value, int red, int green, int blue, IPushListener listener) {

        if (value >= 0 && value <= 100) {

            value = 100 - value;

            getConn().writeCharacteristic(notti_service, notti_charac, new byte[]{(byte) 6, (byte) 1, (byte) ((1f - value / 100f) * red), (byte) ((1f - value / 100f) * green), (byte) ((1f - value / 100f) * blue)}, listener);

        } else {
            Log.e(TAG, "Error luminosity must be set between 0 and 100");
        }
    }

    @Override
    public boolean isInit() {
        return init;
    }

    @Override
    public void addInitListener(IDeviceInitListener listener) {
        initListenerList.add(listener);
    }
}