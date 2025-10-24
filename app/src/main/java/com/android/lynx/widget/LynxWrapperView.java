package com.android.lynx.widget;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.lynx.R;
import com.android.lynx.callback.GlobalLynxCallbackImpl;
import com.android.lynx.callback.LynxCallback;
import com.android.lynx.util.LynxUtils;
import com.lynx.react.bridge.JavaOnlyArray;
import com.lynx.tasm.LynxError;
import com.lynx.tasm.LynxView;
import com.lynx.tasm.LynxViewBuilder;
import com.lynx.tasm.LynxViewClient;

import java.util.HashMap;
import java.util.Map;


public class LynxWrapperView extends FrameLayout {

    private LynxCallback mLynxCallback;
    private String mModuleName;

    private LynxView mLynxView;
    private LynxViewClient mLynxViewClient; // LynxViewClient
    private boolean mLoadLocal = true; // 是否加载本地资源
    private long mStartLoadTime; // 开始加载时间

    public LynxWrapperView(Context context) {
        this(context, null);
    }

    public LynxWrapperView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LynxWrapperView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private final LynxCallback mCallback = new LynxCallback() {
        @Override
        public void onModuleMethodInvoked(
                @NonNull String moduleName,
                @NonNull String method,
                @Nullable HashMap<String, String> params
        ) {
            if (mLynxCallback != null) {
                mLynxCallback.onModuleMethodInvoked(moduleName, method, params);
            }
        }
    };

    private void init(Context context) {
        View root = LayoutInflater.from(context).inflate(R.layout.layout_lynx_wrapper_view, this);
        root.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        GlobalLynxCallbackImpl.INSTANCE.removeListener(mCallback);
        GlobalLynxCallbackImpl.INSTANCE.addListener(mCallback);
        mLynxView = findViewById(R.id.lynxView);
    }

    private void loadBundle(final Map<String, Object> params) {
        if (TextUtils.isEmpty(mModuleName) || mLynxView == null) {
            return;
        }
        // 初始化
        mStartLoadTime = System.currentTimeMillis();
        if (mLynxViewClient == null) {
            mLynxViewClient = new LynxViewClient() {
                @Override
                public void onLoadSuccess() {
                    super.onLoadSuccess();
                }

                @Override
                public void onFirstScreen() {
                    super.onFirstScreen();
                }

                @Override
                public void onReceivedError(LynxError error) {
                    super.onReceivedError(error);
                    uploadErrorToServer(error.toString());
                    // 本地加载失败, 尝试加载网络资源
                    if (mLoadLocal) {
                        mLynxView.destroy();
                        mLoadLocal = false;
                        loadBundle(params);
                    }
                }
            };
        }
        LynxViewBuilder viewBuilder = LynxUtils.getLynxBuilder(getContext(), mLoadLocal);
        mLynxView.initWithLynxViewBuilder(viewBuilder);
        mLynxView.removeLynxViewClient(mLynxViewClient);
        mLynxView.addLynxViewClient(mLynxViewClient);

        String url = LynxUtils.getLoadUrl(mModuleName, mLoadLocal);
        mLynxView.renderTemplateUrl(url, params);
    }

    private void uploadErrorToServer(String errorMsg) {
        if (getContext() == null) {
            return;
        }
//        // 上传错误日志,复用课后反馈类型和type,自定义内容
//        @SuppressLint("WrongConstant") LiveLogService liveLogService = (LiveLogService) getContext().getSystemService(LiveLogService.SERVICE_NAME);
//        if (liveLogService != null) {
//            FeedbackResultBean mFeedbackResultBean = new FeedbackResultBean();
//            mFeedbackResultBean.type = FeedbackTypeBean.TYPE_OTHERS;
//            mFeedbackResultBean.content = "lynxError|" + errorMsg;
//            liveLogService.postBeanToServer(LiveLogService.USER_FEEDBACK_AFTER_CLASS_CODE, mFeedbackResultBean);
//        }
    }

    @Override
    protected void onDetachedFromWindow() {
        GlobalLynxCallbackImpl.INSTANCE.removeListener(mCallback);
        super.onDetachedFromWindow();
    }

    // 初始化
    public void setInitData(final String moduleName, final Map<String, Object> params, LynxCallback listener) {
        mModuleName = moduleName;
        mLynxCallback = listener;
        loadBundle(params);
    }

    // 发送全局事件
    public void sendGlobalEvent(String eventName, JavaOnlyArray params) {
        if (mLynxView != null) {
            mLynxView.sendGlobalEvent(eventName, params);
        }
    }
}
