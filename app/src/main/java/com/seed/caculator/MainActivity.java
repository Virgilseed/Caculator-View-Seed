package com.seed.caculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import utils.Utils;

public class MainActivity extends AppCompatActivity {

    private EditText mTotalVualeET;
    private String mTotol;

    private CaculatoreDialog mCaculatoreDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTotol = "0";
        mTotalVualeET = (EditText) findViewById(R.id.et_caculator);
        //隐藏系统的软键盘
        Utils.hideSoftInputMethod(this,mTotalVualeET);
        mTotalVualeET.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                showCaculatordialog();
                return false;
            }
        });

    }








    /**
     * 弹出计算器
     */
    private void showCaculatordialog() {
        if (mCaculatoreDialog != null && mCaculatoreDialog.isShowing())
        {
            mCaculatoreDialog.dismiss();
            return;
        }
        mCaculatoreDialog = new CaculatoreDialog(this, R.style.EcDialogStyle, mTotol);
        mCaculatoreDialog.setmCallback(new CaculatoreDialog.CaculatorDialogCallback() {
            @Override
            public void cancleForInt(int result) {

            }

            @Override
            public void cancleForDouble(double result) {

            }

            @Override
            public void onRefreshUI(String result) {
                mTotalVualeET.setText(result);
                mTotol = result;
                mTotalVualeET.setSelection(String.valueOf(result).length());
            }

            @Override
            public void compleCaculaotrForInt(int result) {
                //    mBeiZhuEt.requestFocus();
                mTotalVualeET.setText(String.valueOf(result));
                mTotol = String.valueOf(result);
                mTotalVualeET.setSelection(String.valueOf(result).length());
            }

            @Override
            public void compleCaculaotrForDouble(double result) {
                //    mBeiZhuEt.requestFocus();
                mTotalVualeET.setText(String.valueOf(result));
                mTotol = String.valueOf(result);
                mTotalVualeET.setSelection(String.valueOf(result).length());
            }
        });
        mCaculatoreDialog.show();
    }


}
