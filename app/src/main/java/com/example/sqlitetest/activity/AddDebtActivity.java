package com.example.sqlitetest.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.example.sqlitetest.R;
import com.example.sqlitetest.bean.DebtApply;
import com.example.sqlitetest.util.ToastUtils;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnSelectListener;

import org.litepal.LitePal;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddDebtActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mEtAmount, mEtStartTime, mEtEndTime, mEtBaseInterest, mEtRemark, mEtDebtName;
    private Button mBtnBond, mBtnBank, mBtnPayType, mBtnSubmit;

    private Calendar calendar = Calendar.getInstance();


    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_debt);
        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);

        mEtDebtName = findViewById(R.id.EtdebtName);
        mEtAmount = findViewById(R.id.fragment_input_debtEtAmount);
        mEtStartTime = findViewById(R.id.fragment_input_debtEtStartTime);
        mEtStartTime.setOnClickListener(this);
        mEtEndTime = findViewById(R.id.fragment_input_debtEtEndTime);
        mEtEndTime.setOnClickListener(this);
        mEtBaseInterest = findViewById(R.id.fragment_input_debtEtBaseInterest);
        mEtRemark = findViewById(R.id.fragment_input_debtEtRemark);
        mBtnBond = findViewById(R.id.fragment_input_debtBtnBondIns);
        mBtnBond.setOnClickListener(this);
        mBtnBank = findViewById(R.id.fragment_input_debtBtnBank);
        mBtnBank.setOnClickListener(this);
        mBtnPayType = findViewById(R.id.fragment_input_debtBtnPayType);
        mBtnPayType.setOnClickListener(this);
        mBtnSubmit = findViewById(R.id.fragment_input_debtBtnsubmitloan);
        mBtnSubmit.setOnClickListener(this);
        String currentDate = calendar.get(Calendar.YEAR) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.DATE) + " ";
        mEtStartTime.setText(currentDate);
        mEtEndTime.setText(currentDate);

        if (id != 0) {//点击查看进行修改
            DebtApply debtApply = LitePal.find(DebtApply.class, id);
            mEtAmount.setText(debtApply.getAmount() + "");
            mBtnBond.setText(debtApply.getBondInstitutionName());
            mBtnBank.setText(debtApply.getBankName());
            mBtnPayType.setText(debtApply.getPayTypeName());
            mEtStartTime.setText(debtApply.getDebtStartTime());
            mEtEndTime.setText(debtApply.getDebtEndTime());
            mEtBaseInterest.setText(debtApply.getBaseInterest() + "");
            mEtDebtName.setText(debtApply.getUsername());
            mEtRemark.setText(debtApply.getRemark());
        }
    }

    @Override
    public void onBackPressed() {
        toSave();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fragment_input_debtBtnBondIns:
                new XPopup.Builder(AddDebtActivity.this).asCenterList(null, new String[]{"张三", "李四", "王五", "赵六"}, new OnSelectListener() {
                    @Override
                    public void onSelect(int position, String text) {
                        mBtnBond.setText(text);
                    }
                }).show();
                break;
            case R.id.fragment_input_debtBtnPayType:
                new XPopup.Builder(AddDebtActivity.this).asCenterList(null, new String[]{"等额本息", "等额本金", "血债血偿"}, new OnSelectListener() {
                    @Override
                    public void onSelect(int position, String text) {
                        mBtnPayType.setText(text);
                    }
                }).show();
                break;
            case R.id.fragment_input_debtBtnBank:
                new XPopup.Builder(AddDebtActivity.this).asCenterList(null, new String[]{"中国银行", "湖北银行", "汉口银行", "中国农业银行"}, new OnSelectListener() {
                    @Override
                    public void onSelect(int position, String text) {
                        mBtnBank.setText(text);
                    }
                }).show();
                break;
            case R.id.fragment_input_debtEtStartTime:
                showDatePickerDialog(AddDebtActivity.this, 3, mEtStartTime, calendar);
                break;
            case R.id.fragment_input_debtEtEndTime:
                showDatePickerDialog(AddDebtActivity.this, 3, mEtEndTime, calendar);
                break;
            case R.id.fragment_input_debtBtnsubmitloan:
                toSave();
                break;
        }

    }

    private void toSave() {
        DebtApply debtApply;
        if (id != 0) {//仅修改
            debtApply = LitePal.find(DebtApply.class, id);
        } else
            debtApply = new DebtApply();
        if (TextUtils.isEmpty(mEtAmount.getText().toString())) {
            mEtAmount.setError("金额不能为空");
            mEtAmount.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(mBtnBond.getText().toString())) {
            mBtnBond.setError("债权人不能为空");
            mBtnBond.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(mBtnBank.getText().toString())) {
            mBtnBank.setError("关联银行不能为空");
            mBtnBank.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(mBtnPayType.getText().toString())) {
            mBtnPayType.setError("还款方式不能为空");
            mBtnPayType.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(mEtBaseInterest.getText().toString())) {
            mEtBaseInterest.setError("基准利率不能为空");
            mEtBaseInterest.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(mEtDebtName.getText().toString())) {
            mEtDebtName.setError("借款方不能为空");
            mEtDebtName.requestFocus();
            return;
        }
        debtApply.setAmount(Float.parseFloat(mEtAmount.getText().toString()));
        debtApply.setBondInstitutionName(mBtnBond.getText().toString());
        debtApply.setBankName(mBtnBank.getText().toString());
        debtApply.setPayTypeName(mBtnPayType.getText().toString());
        debtApply.setDebtStartTime(mEtStartTime.getText().toString());
        debtApply.setDebtEndTime(mEtEndTime.getText().toString());
        debtApply.setBaseInterest(Float.parseFloat(mEtBaseInterest.getText().toString()));
        debtApply.setRemark(mEtRemark.getText().toString());
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        debtApply.setApplyTime(df.format(new Date()));
        debtApply.setUsername(mEtDebtName.getText().toString());
        debtApply.save();
        Intent intent = new Intent(AddDebtActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
        ToastUtils.shortToast(AddDebtActivity.this, id != 0 ? "修改成功" : "插入成功");
    }

    private void showDatePickerDialog(Activity activity, int themeResId, final EditText editText, Calendar calendar) {
        // 直接创建一个DatePickerDialog对话框实例，并将它显示出来
        new DatePickerDialog(activity
                , themeResId
                // 绑定监听器(How the parent is notified that the date is set.)
                , new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year,
                                  int monthOfYear, int dayOfMonth) {
                // 此处得到选择的时间，可以进行你想要的操作
                editText.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                //在选择了终贷时间后，根据数据库自动输入基准利率
            }
        }
                // 设置初始日期
                , calendar.get(Calendar.YEAR)
                , calendar.get(Calendar.MONTH)
                , calendar.get(Calendar.DAY_OF_MONTH)).show();
    }
}
