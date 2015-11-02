package com.seed.caculator;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import utils.Utils;


/**
 * Created by seed on 2015/10/21.
 */
public class CaculatoreDialog extends BaseDialog {

    private Context mContext;
    private CaculatorLayout mCaculatoreLayout;
    private View mView;
    private String mCaculatorResult = null;
    private CaculatorDialogCallback mCallback;


    public CaculatoreDialog(Context context) {
        super(context);
        this.mContext = context;
    }

    public CaculatoreDialog(Context context, int theme) {
        super(context, theme);
        this.mContext = context;

    }

    public CaculatoreDialog(Context context, int theme, String iniNumber) {
        super(context, theme);
        this.mContext = context;
        mCaculatorResult = iniNumber;

    }

    protected CaculatoreDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.mContext = context;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = LayoutInflater.from(mContext);
        mView = inflater.inflate(R.layout.dialog_caculator, null, false);
        setContentView(mView);
        Window window = this.getWindow();

        setCanceledOnTouchOutside(true);
        int mSreenHeight = Utils.getScreenHeightPixelsByDisplay((Activity) mContext);
        int mSreenWidth = Utils.getScreenWidthPixelsByDisplay((Activity) mContext);
        WindowManager.LayoutParams lp = window.getAttributes();
        //212是布局文件的高度
        //    lp.height = Utils.dip2px(mContext, 212) ;
        lp.width = mSreenWidth;
        lp.horizontalMargin = 0;
        window.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL);
        window.setAttributes(lp);
        //设置无焦点后，点击窗口外边事件不响应（ ）
        window.setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
        window.setBackgroundDrawable(new ColorDrawable(mContext.getResources().getColor(R.color.seed_zhi_alpha)));
        window.setWindowAnimations(R.style.pullToAnimation);

        setCanceledOnTouchOutside(true);
        initView();
        initListener();
        setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                mCaculatorResult = mCaculatoreLayout.getResult();
                if (mCallback != null)
                {
                    if (mCaculatoreLayout.isDouble(mCaculatorResult))
                        mCallback.compleCaculaotrForDouble(Double.valueOf(mCaculatorResult));
                    else
                        mCallback.compleCaculaotrForInt(Integer.valueOf(mCaculatorResult));
                }
                mCaculatorResult = "";
                //恢复初始状态
                mCaculatoreLayout.restoreState();
            }
        });
    }

    private void initView() {
        mCaculatoreLayout = (CaculatorLayout) findViewById(R.id.dialog_caculator_layout);

            mCaculatoreLayout.setmCurrentNumber(mCaculatorResult);
        mCaculatoreLayout.setListener(new CaculatorLayout.CaculatorLayoutListener() {
            @Override
            public void itemOnListener(int action, String number) {
                mCaculatorResult = number;
                if( mCallback != null)
                {
                    mCallback.onRefreshUI(mCaculatorResult);
                }
            }

            @Override
            public void closeBack(int result) {

            }
        });
    }

    private void initListener() {

        mView.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {

                int height = mCaculatoreLayout.getTop();
                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
                        dismiss();
                    }
                }
                return true;
            }
        });
    }

    public CaculatorDialogCallback getmCallback() {
        return mCallback;
    }

    public void setmCallback(CaculatorDialogCallback mCallback) {
        this.mCallback = mCallback;
    }

    public interface CaculatorDialogCallback
    {
        /**
         *
         * @param result
         */
        void cancleForInt(int result);
        void cancleForDouble(double result);

        /**
         * 输入时候刷新数据回调
         * @param string
         */
        void onRefreshUI(String string);

        /**
         * 结束计算返回int
         * @param result
         */
        void compleCaculaotrForInt(int result);

        /**
         * 结束计算返回double
         * @param result
         */
        void compleCaculaotrForDouble(double result);
    }


}
