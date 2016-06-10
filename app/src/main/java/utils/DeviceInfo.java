package utils;

import android.content.Context;
import android.provider.Settings;
import cart.ItemCart;
import models.MenusItem;

import java.util.ArrayList;

/**
 * Created by attribe on 6/9/16.
 */
public class DeviceInfo {
    private static Context context;
    private static  DeviceInfo deviceInfo;

    public static synchronized DeviceInfo getInstance() {

        if (deviceInfo == null) {


            deviceInfo = new DeviceInfo();

        }

        return deviceInfo;

    }

    public String getDeviceID(){

        String deviceId = Settings.Secure.getString(this.context.getContentResolver(),
                Settings.Secure.ANDROID_ID);

        return deviceId;

    }
}
