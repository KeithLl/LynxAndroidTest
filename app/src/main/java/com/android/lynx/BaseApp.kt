package com.android.lynx

import android.app.Application
import com.android.lynx.widget.MyLynxInput
import com.lynx.tasm.LynxEnv
import com.lynx.service.http.LynxHttpService
import com.lynx.service.image.LynxImageService
import com.lynx.service.log.LynxLogService
import com.lynx.tasm.service.LynxServiceCenter
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.imagepipeline.core.ImagePipelineConfig
import com.facebook.imagepipeline.memory.PoolConfig
import com.facebook.imagepipeline.memory.PoolFactory
import com.lynx.service.devtool.LynxDevToolService
import com.lynx.tasm.behavior.Behavior
import com.lynx.tasm.behavior.LynxContext
import com.lynx.tasm.behavior.ui.LynxUI

/**
 * Created by KeithLee on 2025/4/10.
 * Introduction:
 */
class BaseApp : Application() {
    override fun onCreate() {
        super.onCreate()
        initLynxService()
        initLynxEnv()
    }

    private fun initLynxService() {
        // init Fresco which is needed by LynxImageService
        val factory = PoolFactory(PoolConfig.newBuilder().build())
        val builder = ImagePipelineConfig.newBuilder(applicationContext).setPoolFactory(factory)
        Fresco.initialize(applicationContext, builder.build())

        LynxServiceCenter.inst().registerService(LynxImageService.getInstance())
        LynxServiceCenter.inst().registerService(LynxLogService)
        LynxServiceCenter.inst().registerService(LynxHttpService)
//        LynxServiceCenter.inst().registerService(LynxDevToolService)
    }

    private fun initLynxEnv() {
        LynxEnv.inst().init(
            this,// 上下文
            null, // 本地so加载器,null默认系统加载器
            null, // 全局AppBundle加载器
            null // 自定义组件列表
        )

        // 全局添加自定义组件
        LynxEnv.inst().addBehavior(object : Behavior("hhinput") {
            override fun createUI(context: LynxContext): MyLynxInput {
                return MyLynxInput(context)
            }
        })
//        // 打开 Lynx Debug 开关
//        LynxEnv.inst().enableLynxDebug(true)
//        // 打开 Lynx DevTool 开关
//        LynxEnv.inst().enableDevtool(true)
//        // 打开 Lynx LogBox 开关
//        LynxEnv.inst().enableLogBox(true)
    }
}
