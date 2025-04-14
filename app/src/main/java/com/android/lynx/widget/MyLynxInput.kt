package com.android.lynx.widget

import android.content.Context
import androidx.appcompat.widget.AppCompatEditText
import com.lynx.tasm.behavior.LynxContext
import com.lynx.tasm.behavior.ui.LynxUI

/**
 * Created by KeithLee on 2025/4/10.
 * Introduction:自定义组件
 */
class MyLynxInput(context: LynxContext) : LynxUI<AppCompatEditText>(context) {
    override fun createView(context: Context): AppCompatEditText {
        return AppCompatEditText(context).apply {
            //...
        }
    }
}
