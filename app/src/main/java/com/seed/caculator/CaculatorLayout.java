package com.seed.caculator;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

/**
 * Created by seed on 2015/10/20.
 */
public class CaculatorLayout extends LinearLayout implements View.OnClickListener {


    private Context mContext;
    private View mLayout;
    private CaculatorLayoutListener listener;
    //7、8、9、删除按钮
    private Button mNumberSevenBtn, mNumberEightBtn, mNumberNineBtn;
    //4、5、6、加按钮
    private Button mNumberFourBtn, mNumberFiveBtn, mNumberSixBtn, mAddBtn;
    //1、2、3、减按妞
    private Button mNumberOneBtn, mNumberTwoBtn, mNumberThreeBtn, mReduceBtn;
    //0、小数点、等于按钮
    private Button mNumberZeroBtn, mPointBtn, mResultBtn;
    private Button mDeleteBtn;

    private static final int ACTION_ADD = 1;
    private static final int ACTION_REDUCE = 2;
    private static final int ACTION_RESULT = 3;
    private static final int ACTION_DELETE = 4;
    private static final int ACTION_POINT = 5;
    //处于输入数字状态
    private static final int ACTION_NONE = 0;
    private static final int ACTION_EMPTY = -2;


    private int mCurrentAction = ACTION_NONE;
    private static final String BGGIN_CACULATOR = "begin";
    //初始计算
    private String mCurrentNumber = BGGIN_CACULATOR;
    private double[] mHistoryNumber = new double[10000];
    private int[] mActions = new int[10000];
    //标识输入的数字个数
    private int mNumberAcounts = 0;
    private String mInputString = "";

    public CaculatorLayout(Context context) {
        super(context);
        this.mContext = context;
        iniView();
        addView(mLayout);
    }

    public CaculatorLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        iniView();
        addView(mLayout);
    }

    public CaculatorLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        iniView();
        addView(mLayout);
    }

    private void iniView() {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        mLayout = inflater.inflate(R.layout.popupwindow_caculator, null, false);

        mNumberSevenBtn = (Button) mLayout.findViewById(R.id.caculator_btn7);
        mNumberEightBtn = (Button) mLayout.findViewById(R.id.caculator_btn8);
        mNumberNineBtn = (Button) mLayout.findViewById(R.id.caculator_btn9);
        mDeleteBtn = (Button) mLayout.findViewById(R.id.caculator_delete);

        mNumberFourBtn = (Button) mLayout.findViewById(R.id.caculator_btn4);
        mNumberFiveBtn = (Button) mLayout.findViewById(R.id.caculator_btn5);
        mNumberSixBtn = (Button) mLayout.findViewById(R.id.caculator_btn6);
        mAddBtn = (Button) mLayout.findViewById(R.id.caculator_add_btn);

        mNumberOneBtn = (Button) mLayout.findViewById(R.id.caculator_btn1);
        mNumberTwoBtn = (Button) mLayout.findViewById(R.id.caculator_btn2);
        mNumberThreeBtn = (Button) mLayout.findViewById(R.id.caculator_btn3);
        mReduceBtn = (Button) mLayout.findViewById(R.id.caculator_reduce_btn);

        mNumberZeroBtn = (Button) mLayout.findViewById(R.id.caculator_btn0);
        mPointBtn = (Button) mLayout.findViewById(R.id.caculator_point_btn);
        mResultBtn = (Button) mLayout.findViewById(R.id.caculator_result_btn);

        mNumberSevenBtn.setOnClickListener(this);
        mNumberEightBtn.setOnClickListener(this);
        mNumberNineBtn.setOnClickListener(this);
        mDeleteBtn.setOnClickListener(this);

        mNumberFourBtn.setOnClickListener(this);
        mNumberFiveBtn.setOnClickListener(this);
        mNumberSixBtn.setOnClickListener(this);
        mAddBtn.setOnClickListener(this);

        mNumberOneBtn.setOnClickListener(this);
        mNumberTwoBtn.setOnClickListener(this);
        mNumberThreeBtn.setOnClickListener(this);
        mReduceBtn.setOnClickListener(this);

        mNumberZeroBtn.setOnClickListener(this);
        mPointBtn.setOnClickListener(this);
        mResultBtn.setOnClickListener(this);
    }

    /**
     * 设置当前数字，不设置则为默认的“begin”
     * @param initnNumber
     */
    public void setmCurrentNumber(String initnNumber)
    {
        this.mCurrentNumber = initnNumber;
    }


    private void caculator(int number) {
        switch (mCurrentAction) {
            case ACTION_NONE:
                if (mCurrentNumber.equals(BGGIN_CACULATOR) || "".equals(mCurrentNumber)) {
                    mCurrentNumber = String.valueOf(number);
                } else {
                    if ("0".equals(mCurrentNumber)) {
                        mCurrentNumber = String.valueOf(number);
                    } else {
                        mCurrentNumber = mCurrentNumber + String.valueOf(number);
                    }
                }
                break;

            case ACTION_POINT:
                if (mCurrentNumber.equals(BGGIN_CACULATOR) || mCurrentNumber.equals("")) {
                    mCurrentNumber = "0." + String.valueOf(number);
                } else {
                    mCurrentNumber = mCurrentNumber + String.valueOf(number);
                }

                break;
        }
    }

    private String showInput() {
        String result = "";
        //没有输入一个完整数字情况下（没有输入任何运算符（+、-））
        if (mNumberAcounts == 0) {
            if (mCurrentNumber.equals(BGGIN_CACULATOR) || "".equals(mCurrentNumber))
                result = "0";
            else
                result = mCurrentNumber;
        } else {
            for (int i = 0; i <= mNumberAcounts; i++) {
                if (i == 0) {
                    if (isDouble(mHistoryNumber[i]))
                        result = String.valueOf(filterDouble(mHistoryNumber[i]));
                    else
                        result = String.valueOf(mHistoryNumber[i]);
                    continue;
                }

                if (i >= 1) {
                    if (mActions[i - 1] == ACTION_ADD) {
                        if (i <= mNumberAcounts - 1) {
                            if (isDouble(mHistoryNumber[i]))
                                result = result + "+" + String.valueOf(filterDouble(mHistoryNumber[i]));
                            else
                                result = result + "+" + String.valueOf(mHistoryNumber[i]);
                        } else {
                            if (!"".equals(mCurrentNumber)) {
                                if (isDouble(mCurrentNumber)) {
                                    result = result + "+" + String.valueOf(filterDouble(mCurrentNumber));
                                } else
                                    result = result + "+" + mCurrentNumber;
                            } else {
                                result = result + "+";
                            }
                        }
                    } else {
                        if (i <= mNumberAcounts - 1) {
                            if (isDouble(mHistoryNumber[i]))
                                result = result + "-" + String.valueOf(filterDouble(mHistoryNumber[i]));
                            else
                                result = result + "-" + String.valueOf(mHistoryNumber[i]);
                        } else {
                            if (!"".equals(mCurrentNumber)) {
                                if (isDouble(mCurrentNumber)) {
                                    result = result + "-" + String.valueOf(filterDouble(mCurrentNumber));
                                } else
                                    result = result + "-" + mCurrentNumber;
                            } else {
                                result = result + "+";
                            }
                        }
                    }
                } else {
                    result = String.valueOf(filterDouble(mHistoryNumber[i]));
                }
            }

        }
        return result;
    }

    @Override
    public void onClick(View view) {
        if (listener == null)
            return;
        switch (view.getId()) {
            case R.id.caculator_btn7:
                caculator(7);
                listener.itemOnListener(mCurrentAction, showInput());
                break;
            case R.id.caculator_btn8:
                caculator(8);
                listener.itemOnListener(mCurrentAction, showInput());
                break;
            case R.id.caculator_btn9:
                caculator(9);
                listener.itemOnListener(mCurrentAction, showInput());
                break;
            case R.id.caculator_btn6:
                caculator(6);
                listener.itemOnListener(mCurrentAction, showInput());
                break;
            case R.id.caculator_btn5:
                caculator(5);
                listener.itemOnListener(mCurrentAction, showInput());
                break;
            case R.id.caculator_btn4:
                caculator(4);
                listener.itemOnListener(mCurrentAction, showInput());
                break;
            case R.id.caculator_btn3:
                caculator(3);
                listener.itemOnListener(mCurrentAction, showInput());
                break;
            case R.id.caculator_btn2:
                caculator(2);
                listener.itemOnListener(mCurrentAction, showInput());
                break;
            case R.id.caculator_btn1:
                caculator(1);
                listener.itemOnListener(mCurrentAction, showInput());
                break;
            case R.id.caculator_btn0:
                caculator(0);
                listener.itemOnListener(mCurrentAction, showInput());
                break;
            case R.id.caculator_point_btn:
                point();
                listener.itemOnListener(mCurrentAction, showInput());
                break;
            case R.id.caculator_result_btn:
                //计算结果
                String result = getResult();
                listener.itemOnListener(mCurrentAction, result);
                break;
            case R.id.caculator_add_btn:
                add();
                listener.itemOnListener(mCurrentAction, showInput());
                break;
            case R.id.caculator_reduce_btn:
                reduce();
                listener.itemOnListener(mCurrentAction, showInput());
                break;
            case R.id.caculator_delete:
                delete();
                listener.itemOnListener(mCurrentAction, showInput());
                break;
        }
    }

    private void point()
    {
        //小数点操作如下：判断当前是否为小数输入模式，若不是小数点，则设置当前模式为小数点模式
        if (mCurrentAction != ACTION_POINT) {
            mCurrentAction = ACTION_POINT;
            //判断当前输入的数字是否为空，若不是，咋将当前数字设置为“0.”，若不是，则在当前数字后面加个小数点
            if (!"".equals(mCurrentNumber))
                mCurrentNumber += ".";
            else {
                mCurrentNumber = "0.";
            }
            //刷新页面
    //        listener.itemOnListener(mCurrentAction, showInput());
        } else {

        }
    }


    private void add()
    {
        if ("".equals(mCurrentNumber) || mCurrentNumber == null) {
            Toast.makeText(mContext, "输入非法！", Toast.LENGTH_SHORT).show();
            return;
        }

        //操作如减号
        mNumberAcounts += 1;
        mActions[mNumberAcounts - 1] = ACTION_ADD;
        mHistoryNumber[mNumberAcounts - 1] = Double.valueOf(mCurrentNumber);
        mCurrentNumber = "";
    //    listener.itemOnListener(mCurrentAction, showInput());
        mCurrentAction = ACTION_NONE;
    }


    private void reduce()
    {
        if ("".equals(mCurrentNumber) || mCurrentNumber == null) {
            Toast.makeText(mContext, "输入非法！", Toast.LENGTH_SHORT).show();
            return;
        }
        //点击减号操作如下：完整的数字+1，并保存
        mNumberAcounts += 1;
        mHistoryNumber[mNumberAcounts - 1] = Double.valueOf(mCurrentNumber);
        //设置当前数字为""
        mCurrentNumber = "";
        //运算符+1并保存
        mActions[mNumberAcounts - 1] = ACTION_REDUCE;
        //恢复默认的输入模式为整数模式
        mCurrentAction = ACTION_NONE;
        //通过接口刷新输入框
    //    listener.itemOnListener(mCurrentAction, showInput());
    }




    /**
     * 处理删除事件
     */
    private void delete()
    {
        //处理没有运算符情况
        if (mNumberAcounts <= 0) {
            if (mCurrentNumber.equals("")) {
                return;
            } else {
                if (mCurrentNumber.length() == 1)
                    mCurrentNumber = "0";
                else
                    mCurrentNumber = mCurrentNumber.substring(mCurrentNumber.length() - 1, mCurrentNumber.length());
            }
        //    listener.itemOnListener(mCurrentAction, showInput());
            return;
        }

        //处理运算符后面没数据点击删除的情况
        if (mCurrentNumber == null || "".equals(mCurrentNumber)) {
            if (isDouble(mHistoryNumber[mNumberAcounts])) {
                mCurrentNumber = String.valueOf(filterDouble(mHistoryNumber[mNumberAcounts]));
                mCurrentAction = ACTION_POINT;
            } else {
                mCurrentNumber = String.valueOf(mHistoryNumber[mNumberAcounts]);
                mCurrentAction = ACTION_NONE;
            }
            mHistoryNumber[mNumberAcounts] = 0.0;
            mActions[mNumberAcounts - 1] = ACTION_NONE;
            mNumberAcounts = mNumberAcounts - 1;
        //    listener.itemOnListener(mCurrentAction, showInput());
            return;
        }

        //处理有运算符且运算符后面数据不为空

        //只有一个数字
        if (mCurrentNumber.length() == 1) {
            mCurrentNumber = "";
            listener.itemOnListener(mCurrentAction, showInput());
            return;
        }
        if (mCurrentNumber.length() == 2) {
            //处理刚刚切换为小数情况：类似1.和2.时候
            if (mCurrentNumber.endsWith(".")) {
                mCurrentNumber = mCurrentNumber.substring(0, 1);
                mCurrentAction = ACTION_NONE;
            } else {
                mCurrentNumber = mCurrentNumber.substring(0, 1);
            }
        //    listener.itemOnListener(mCurrentAction, showInput());
            return;
        }
        //处理2个数字以上删除
        if (mCurrentNumber.length() >= 3) {
            mCurrentNumber = mCurrentNumber.substring(0, mCurrentNumber.length() - 1);
        //    listener.itemOnListener(mCurrentAction, showInput());
            return;
        }
    }


    /**
     * 计算结果
     * @return
     */
    public String  getResult()
    {
        //等号操作如下：判断是否已有完整的输入数字，若没有，则直接返回，并刷新页面
        if (mNumberAcounts <= 0) {
            if (mCurrentNumber.equals(BGGIN_CACULATOR))
                return "0";
            else
                return mCurrentNumber;
        }
        double end = 0;
        for (int i = 0; i <= mNumberAcounts; i++) {
            //第一个数字
            if (i == 0) {
                end = mHistoryNumber[i];
                continue;
            }
            //遍历数组，加上所有的完整数字
            if (mActions[i - 1] == ACTION_ADD) {
                if (i <= mNumberAcounts - 1)
                    end = end + mHistoryNumber[i];
                else {
                    //最后加上，当前数字（未输入完整的数字）
                    if (mCurrentNumber == null || "".equals(mCurrentNumber))
                        //当前数字为空（未输入），
                        end = end + 0;
                    else
                        //当前数字不为空
                        end = end + Double.valueOf(mCurrentNumber);
                }

            } else {
                //遍历数组，加上所有的完整数字
                if (i <= mNumberAcounts - 1) {
                    end = end - mHistoryNumber[i];
                } else {
                    //最后加上，当前数字（未输入完整的数字）
                    if (mCurrentNumber == null || "".equals(mCurrentNumber))
                        //当前数字为空（未输入），
                        end = end - 0;
                    else
                        //当前数字不为空
                        end = end - Double.valueOf(mCurrentNumber);
                }
            }
        }
        String endstring = "";
        //最后处理末尾为.0的情况
        if (isDouble(end))
            endstring = String.valueOf(filterDouble(end));
        else
            endstring = String.valueOf(end);
        //刷新页面
    //    listener.itemOnListener(mCurrentAction, endstring);
        //设置当前数字为上面计算过的结果
        mCurrentNumber = endstring;
        //清空历史数字记录
        mHistoryNumber = new double[10000];
        //清空历史运算符
        mActions = new int[10000];
        //重置已输入完整数字的个数为0
        mNumberAcounts = 0;
        //判断当前是否带有小数
        mCurrentAction = ACTION_NONE;
        return endstring;
    }

    /**
     * 恢复初始状态，避免第二次打开计算出错
     */
    public void restoreState() {
        mCurrentNumber = BGGIN_CACULATOR;
        mCurrentAction = ACTION_NONE;
        mActions = new int[10000];
        mNumberAcounts = 0;
        mHistoryNumber = new double[10000];
    }

    /**
     * 让末尾为.0类的double数字转换为整数
     * @param number
     * @return
     */
    public int filterDouble(double number) {
        int end = 0;
        String temp = String.valueOf(number);
        if (temp.endsWith(".0")) {
            temp = temp.substring(0, temp.length() - 2);
            end = Integer.valueOf(temp);
        }
        return end;
    }

    /**
     * 让末尾为.0类的double数字字符串转换为整数
     * @param number
     * @return
     */
    public int filterDouble(String number) {
        int end = 0;
        String temp = number;
        if (temp.endsWith(".0")) {
            temp = temp.substring(0, temp.length() - 2);
            end = Integer.valueOf(temp);
        }
        return end;
    }

    /**
     * 判断是否是整数
     * @param number
     * @return
     */
    public boolean isDouble(double number) {
        int end = 0;
        String temp = String.valueOf(number);
        if (temp.endsWith(".0"))
            return true;
        else
            return false;
    }

    /**
     * 判断是否是整数
     * @param number
     * @return
     */
    public boolean isDouble(String number) {
        int end = 0;
        if (number.endsWith(".0"))
            return true;
        else
            return false;
    }


    public CaculatorLayoutListener getListener() {
        return listener;
    }

    public void setListener(CaculatorLayoutListener listener) {
        this.listener = listener;
    }



    public interface CaculatorLayoutListener {
        //计算操作动作监听器
        void itemOnListener(int action, String number);

        void closeBack(int result);
    }
}
