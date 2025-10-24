package com.android.lynx.callback

/**
 * Created by KeithLee on 2025/10/14.
 * Introduction:
 */
interface LynxCallback {
    fun onModuleMethodInvoked(moduleName: String, method: String, params: HashMap<String, String?>?)
}
