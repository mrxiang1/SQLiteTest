package com.example.sqlitetest.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.sqlitetest.R;
import com.example.sqlitetest.bean.User;
import com.example.sqlitetest.util.ToastUtils;

import org.litepal.LitePal;
import org.litepal.crud.LitePalSupport;

import java.util.ArrayList;
import java.util.List;

public class RegisterActivity extends AppCompatActivity {

    private EditText userName, passWd, passWdConfirm;
    private Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        userName = findViewById(R.id.EtuserName);
        passWd = findViewById(R.id.EtpassWord);
        passWdConfirm = findViewById(R.id.EtpassWordConfirm);

        register = findViewById(R.id.Btnregister);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(userName.getText().toString())) {
                    userName.setError("请输入用户名");
                    userName.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(passWd.getText().toString())) {
                    passWd.setError("请输入密码");
                    passWd.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(passWdConfirm.getText().toString())) {
                    passWdConfirm.setError("请再次输入密码");
                    passWdConfirm.requestFocus();
                    return;
                }
                if (!passWd.getText().toString().equals(passWdConfirm.getText().toString())) {
                    passWdConfirm.setError("请输入相同的密码");
                    passWdConfirm.requestFocus();
                } else {
                    if(isExist(userName.getText().toString()))
                        ToastUtils.shortToast(RegisterActivity.this,"该账号已存在，请重新注册");
                    else {
                        User user = new User(userName.getText().toString(),passWd.getText().toString());
                       /* user.setUserName();
                        user.setPassWord();*/
                        user.save();
                        ToastUtils.shortToast(RegisterActivity.this,"注册成功");
                        finish();
                    }
                }
            }

        });


    }

    public static boolean isExist(String userName) {
        int result = LitePal.where("username = ?", userName).count(User.class);
        return result != 0;
    }
}
