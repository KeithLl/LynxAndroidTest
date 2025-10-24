package com.android.lynx.provider

import android.content.Context
import com.lynx.tasm.provider.AbsTemplateProvider
import java.io.ByteArrayOutputStream
import java.io.IOException

class LocalTemplateProvider(context: Context) : AbsTemplateProvider() {

    private var mContext: Context = context.applicationContext

    override fun loadTemplate(uri: String, callback: Callback) {
        Thread {
            try {
//                // 读取本地文件
//                val filePath = DirContext.getRootDir().absolutePath + File.separator + uri
//                val file = File(filePath)
//                if (file.exists()) {
//                    callback.onSuccess(file.readBytes())
//                    return@Thread
//                }
                mContext.assets.open(uri).use { inputStream ->
                    ByteArrayOutputStream().use { byteArrayOutputStream ->
                        val buffer = ByteArray(1024)
                        var length: Int
                        while ((inputStream.read(buffer).also { length = it }) != -1) {
                            byteArrayOutputStream.write(buffer, 0, length)
                        }
                        callback.onSuccess(byteArrayOutputStream.toByteArray())
                    }
                }
            } catch (e: IOException) {
                callback.onFailed(e.message)
            }
        }.start()
    }
}
