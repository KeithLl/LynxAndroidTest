package com.android.lynx.callback

/**
 * Created by KeithLee on 2025/10/14.
 * Introduction:
 */
object GlobalLynxCallbackImpl : LynxCallback {
    private val mListeners: ArrayList<LynxCallback> = ArrayList()
    override fun onModuleMethodInvoked(
        moduleName: String, method: String, params: HashMap<String, String?>?
    ) {
        for (listener in mListeners) {
            listener.onModuleMethodInvoked(moduleName, method, params ?: HashMap())
        }
    }

    fun addListener(listener: LynxCallback) {
        mListeners.add(listener)
    }

    fun removeListener(listener: LynxCallback) {
        mListeners.remove(listener)
    }

    fun release() {
        mListeners.clear()
    }
}
