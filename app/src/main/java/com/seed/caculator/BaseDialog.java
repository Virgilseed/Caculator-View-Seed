package com.seed.caculator;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;


public class BaseDialog extends Dialog {

    //点击事件监听
    private EcDialogClickListener dialogClickListener;

    public BaseDialog(Context context) {
        super(context);
    }

    public BaseDialog(Context context, int theme) {
        super(context, theme);
    }

    protected BaseDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //设置背景跟随Parent
        //调用这个方法时，按对话框以外的地方不起作用。按返回键还起作用
        //setCanceledOnTouchOutside(false);
        //调用这个方法时，按对话框以外的地方不起作用。按返回键也不起作用
        //setCancelable(false);
    }

    protected void setBackgroundFollowParent()
    {
        this.getWindow().setBackgroundDrawable(new ColorDrawable(android.R.color.transparent));
    }

    public EcDialogClickListener getDialogClickListener() {
        return dialogClickListener;
    }

    public void setDialogClickListener(EcDialogClickListener dialogClickListener) {
        this.dialogClickListener = dialogClickListener;
    }

    /**
     * ifen Dialog自定义点击事件
     */
    public interface EcDialogClickListener {
        void onClick(View v);
    }
}
