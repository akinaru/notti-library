/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Bertrand Martel
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package fr.bmartel.android.notti.service.bluetooth.listener;

import android.bluetooth.BluetoothGattCharacteristic;

/**
 * Characteritic listener template to be used in device implementation
 *
 * @author Bertrand Martel
 */
public interface ICharacteristicListener {

    /**
     * called when onCharacteristicRead() gatt callback has been received
     *
     * @param charac characteristic that has been read
     */
    public void onCharacteristicReadReceived(BluetoothGattCharacteristic charac);

    /**
     * called chane onCharacteristicChange() gatt callback has been received
     *
     * @param charac characteristic whose value has changed
     */
    public void onCharacteristicChangeReceived(BluetoothGattCharacteristic charac);

    /**
     * called when onCharacteristicWrite() gatt callback is received
     *
     * @param charac
     */
    public void onCharacteristicWriteReceived(BluetoothGattCharacteristic charac);
}
