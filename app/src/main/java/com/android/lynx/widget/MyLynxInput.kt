package com.android.lynx.widget

import android.content.Context
import android.graphics.Color
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.appcompat.widget.AppCompatEditText
import com.lynx.tasm.behavior.LynxContext
import com.lynx.tasm.behavior.LynxProp
import com.lynx.tasm.behavior.ui.LynxUI
import com.lynx.tasm.event.LynxCustomEvent

/**
 * Created by KeithLee on 2025/4/10.
 * Introduction:自定义组件
 */
class MyLynxInput(context: LynxContext) : LynxUI<AppCompatEditText>(context) {

    var mColor: Int = Color.parseColor("#FF0000")

    @LynxProp(name = "color")
    fun setColor(color: String) {
        Log.e("Keith", "setColor : $color")
        mColor = Color.parseColor(color)
        mView.setTextColor(mColor)
    }

    override fun createView(context: Context): AppCompatEditText {
        return AppCompatEditText(context).apply {
            //...
            addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

                override fun afterTextChanged(s: Editable?) {
                    emitEvent("myLynxInput", mapOf("value" to (s?.toString() ?: "")))
                }
            })
        }
    }

    @LynxProp(name = "value")
    fun setValue(value: String) {
        if (value != mView.text.toString()) {
            mView.setText(value)
        }
    }

    private fun emitEvent(name: String, value: Map<String, Any>?) {
        val detail = LynxCustomEvent(sign, name)
        value?.forEach { (key, v) ->
            detail.addDetail(key, v)
        }
        lynxContext.eventEmitter.sendCustomEvent(detail)
    }

    override fun onLayoutUpdated() {
        super.onLayoutUpdated()
        val paddingTop = mPaddingTop + mBorderTopWidth
        val paddingBottom = mPaddingBottom + mBorderBottomWidth
        val paddingLeft = mPaddingLeft + mBorderLeftWidth
        val paddingRight = mPaddingRight + mBorderRightWidth
        mView.setBackgroundColor(Color.parseColor("#FF00FF"))
        mView.setTextColor(Color.parseColor("#FFFFFF"))
        mView.setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom)
    }
}
