package com.android.lynx

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.android.lynx.provider.DemoTemplateProvider
import com.lynx.react.bridge.JavaOnlyArray
import com.lynx.tasm.LynxView
import com.lynx.tasm.LynxViewBuilder
import com.lynx.tasm.LynxViewClient
import com.lynx.tasm.LynxViewClientV2
import kotlin.math.log

class MainActivity : AppCompatActivity() {

    var lynxView: LynxView? = null

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
//        initMain()
        initView()

        val uri = "main1.lynx.bundle";
        lynxView!!.renderTemplateUrl(uri, "")

        // 延迟发送一个事件
        initEvent()
    }

    // 测试代码-事件
    private fun initEvent() {
        Handler().postDelayed({
            Log.e("keith", "sendGlobalEvent")
            lynxView!!.sendGlobalEvent("testEvent", JavaOnlyArray.of("myEventParam"))
        }, 3000)
//        lynxView!!.sendGlobalEvent("testEvent", JavaOnlyArray.of("myEventParam"))
    }

    private fun initMain() {
        setContentView(R.layout.activity_main)

        // 设置LynxView
        lynxView = findViewById<LynxView>(R.id.lynxView)
        val viewBuilder: LynxViewBuilder = LynxViewBuilder()
        viewBuilder.setTemplateProvider(DemoTemplateProvider(this))
        lynxView!!.initWithLynxViewBuilder(viewBuilder)
    }

    private fun initView() {
        val viewBuilder: LynxViewBuilder = LynxViewBuilder()
        viewBuilder.setTemplateProvider(DemoTemplateProvider(this))
        lynxView = viewBuilder.build(this)
        // 生命周期监听
        lynxView!!.addLynxViewClient(object : LynxViewClient() {
            override fun onPageStart(url: String?) {
                Log.e("Keith", "onPageStart: + $url")
                super.onPageStart(url)
            }

            override fun onLoadSuccess() {
                super.onLoadSuccess()
                Log.e("Keith", "onLoadSuccess")
            }

            override fun onLoadFailed(errorMessage: String?) {
                super.onLoadFailed(errorMessage)
                Log.e("Keith", "onLoadFailed: + $errorMessage")
            }

            override fun onFirstScreen() {
                super.onFirstScreen()
                Log.e("Keith", "onFirstScreen")
            }

            override fun onPageUpdate() {
                super.onPageUpdate()
                Log.e("Keith", "onPageUpdate")
            }

            override fun onDataUpdated() {
                super.onDataUpdated()
                Log.e("Keith", "onDataUpdated")
            }

            override fun onRuntimeReady() {
                super.onRuntimeReady()
                Log.e("Keith", "onRuntimeReady")
            }

            override fun onModuleMethodInvoked(module: String?, method: String?, error_code: Int) {
                Log.e("Keith", "onModuleMethodInvoked: $module")
                super.onModuleMethodInvoked(module, method, error_code)
            }

            override fun onDestroy() {
                Log.e("Keith", "onDestroy")
                super.onDestroy()
            }
        })
        setContentView(lynxView)
    }
}
