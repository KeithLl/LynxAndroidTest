package com.android.lynx.modules;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.android.lynx.callback.LynxCallback;
import com.lynx.jsbridge.LynxMethod;
import com.lynx.jsbridge.LynxModule;
import com.lynx.react.bridge.ReadableMap;
import com.lynx.tasm.behavior.LynxContext;

import java.util.HashMap;


/**
 * Created by KeithLee on 2025/10/14.
 * Introduction:LynxView和原生交互Module定义
 */

public class NativeLynxModule extends LynxModule {

    private Handler mHandler = new Handler(Looper.getMainLooper());

    public NativeLynxModule(Context context) {
        this(context, null);
    }

    public NativeLynxModule(Context context, Object param) {
        super(context, param);
    }

    public Context getContext() {
        LynxContext lynxContext = (LynxContext) mContext;
        return lynxContext.getContext();
    }

    @LynxMethod
    public void callMethod(String moduleName, String method, ReadableMap params) {
        HashMap<String, String> paramsMap = new HashMap<>();
        if (params != null) {
            HashMap<String, Object> hashMap = params.asHashMap();
            for (String key : hashMap.keySet()) {
                paramsMap.put(key, hashMap.get(key).toString());
            }
        }

        if (mParam instanceof LynxCallback) {
            mHandler.post(() -> {
                ((LynxCallback) mParam).onModuleMethodInvoked(moduleName, method, paramsMap);
            });
        }
    }
}
