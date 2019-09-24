package com.example.sqlitetest.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.sqlitetest.R;
import com.example.sqlitetest.bean.User;
import com.example.sqlitetest.util.ToastUtils;

import org.litepal.LitePal;

public class LoginActivity extends AppCompatActivity {

    private EditText userName;
    private EditText passWd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userName = findViewById(R.id.EtUsername);
        passWd = findViewById(R.id.EtPasswd);

        Button BtnRegister = findViewById(R.id.register);
        BtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        Button login = findViewById(R.id.BtnLogin);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(userName.getText().toString())) {
                    userName.setError("请输入登陆账号");
                    userName.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(passWd.getText().toString())) {
                    passWd.setError("请输入密码");
                    passWd.requestFocus();
                } else {
                    //通过函数判断账号是否存在
                    if (RegisterActivity.isExist(userName.getText().toString())) {
                        //存在
                        if (isCorrect(userName.getText().toString(), passWd.getText().toString())) {//正确
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            intent.putExtra("userName", userName.getText().toString());
                            startActivity(intent);
                            finish();
                            ToastUtils.shortToast(LoginActivity.this, "登陆成功");
                        } else
                            ToastUtils.shortToast(LoginActivity.this, "用户名或密码有误，请重新输入");
                    } else
                        ToastUtils.shortToast(LoginActivity.this, "不存在当前用户");

                }
            }
        });
    }

    private boolean isCorrect(String userName, String passWd) {
        int result = LitePal.where("username = ? and passWord = ?", userName, passWd).count(User.class);
        return result != 0;
    }

}
