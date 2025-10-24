package com.android.lynx.util

import android.content.Context
import com.android.lynx.provider.LocalTemplateProvider
import com.android.lynx.provider.RemoteTemplateProvider
import com.example.lynxdevtool.BuildConfig
import com.lynx.tasm.LynxViewBuilder

/**
 * Created by KeithLee on 2025/5/20.
 * Introduction:
 */
object LynxUtils {
    private const val LOAD_REMOTE = true
    private val RemoteUrl =  "http://192.168.32.89:3000"

    @JvmStatic
    fun getLynxParams(): HashMap<String, Any> {
        val map = HashMap<String, Any>()
//        map["commonParams"] = AIClassOnlineServices.getUrlCommonParams()
        return map
    }

    @JvmStatic
    fun getLoadUrl(bundleName: String = "main.lynx.bundle", loadLocal: Boolean): String {
        // 全局控制是否加载远程资源
        return if (LOAD_REMOTE || !loadLocal) {
            "$RemoteUrl/${bundleName}"
        } else {
            bundleName
        }
    }

    @JvmStatic
    fun getLynxBuilder(context: Context, loadLocal: Boolean): LynxViewBuilder {
        val lynxViewBuilder = LynxViewBuilder()
        lynxViewBuilder.setTemplateProvider(
            if (LOAD_REMOTE || !loadLocal) {
                RemoteTemplateProvider(
                    context
                )
            } else {
                LocalTemplateProvider(context)
            }
        )
        return lynxViewBuilder
    }
}
