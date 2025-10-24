package com.android.lynx

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.android.lynx.callback.LynxCallback
import com.android.lynx.util.LynxModuleConst
import com.android.lynx.util.LynxUtils.getLynxParams
import com.android.lynx.widget.LynxWrapperView

class MainActivity : AppCompatActivity() {

    private lateinit var mLynxView: LynxWrapperView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        initMain()
    }

    private fun initMain() {
        setContentView(R.layout.activity_main)

        // 设置LynxView
        mLynxView = findViewById<LynxWrapperView>(R.id.lynxView_wrapper)

        val params = getLynxParams()
        mLynxView.setInitData("AllMedalFragment.lynx.bundle", params, mLynxCallback)
    }


    private val mLynxCallback = object : LynxCallback {
        override fun onModuleMethodInvoked(
            moduleName: String, method: String, params: HashMap<String, String?>?
        ) {
            Log.e("Keith", "methodName = ${method}")
            if (LynxModuleConst.moduleName_Medal != moduleName || params == null) {
                return
            }
        }
    }
}
