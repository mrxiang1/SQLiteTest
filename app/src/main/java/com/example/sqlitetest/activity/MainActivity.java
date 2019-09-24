package com.example.sqlitetest.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.drm.DrmStore;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sqlitetest.R;
import com.example.sqlitetest.adapter.DebtAdapter;
import com.example.sqlitetest.bean.DebtApply;
import com.example.sqlitetest.bean.User;
import com.example.sqlitetest.util.ToastUtils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.enums.PopupAnimation;
import com.lxj.xpopup.interfaces.OnConfirmListener;
import com.lxj.xpopup.interfaces.OnInputConfirmListener;

import org.litepal.LitePal;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private RecyclerView recyclerView;
    private DebtAdapter adapter;
    private NavigationView navigationView;
    private List<DebtApply> lists = new ArrayList<>();
    private String account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        XPopup.setPrimaryColor(getResources().getColor(R.color.colorPrimary));
        navigationView = findViewById(R.id.mainNavigationView);
        Intent intent = getIntent();
        account = intent.getStringExtra("userName");
        View header = navigationView.getHeaderView(0);
        TextView mTvusername = header.findViewById(R.id.layout_nav_headerTvName);
        mTvusername.setText(account);
        mDrawerLayout = findViewById(R.id.activity_mainDL);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        }


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.nav_resetPassword:
                        new XPopup.Builder(MainActivity.this).popupAnimation(PopupAnimation.TranslateFromRight)
                                .asInputConfirm("修改密码", "请输入新密码。",
                                        new OnInputConfirmListener() {
                                            @Override
                                            public void onConfirm(String text) {
                                                if (text.equals("")) {
                                                    ToastUtils.longToast(MainActivity.this, "密码不可为空");
                                                }else{
//                                                    List<User> users = LitePal.where("userName=?",account).find(User.class);
                                                    User user = new User();
                                                    user.setPassWord(text);
                                                    user.updateAll("userName=?",account);
                                                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                                                    startActivity(intent);
                                                    finish();
                                                    ToastUtils.shortToast(MainActivity.this, "修改成功，请重新登录");
                                                }
                                            }
                                        })
                                .show();
                        break;
                    case R.id.nav_logout:
                        new XPopup.Builder(MainActivity.this).asConfirm(null, "确认注销？",
                                new OnConfirmListener() {
                                    @Override
                                    public void onConfirm() {
                                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                                        startActivity(intent);
                                        finish();
                                        ToastUtils.shortToast(MainActivity.this, "注销成功");
                                    }
                                })
                                .show();
                        break;
                }
                mDrawerLayout.closeDrawers();
                return true;
            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddDebtActivity.class);
                startActivity(intent);
                finish();
            }
        });

        recyclerView = findViewById(R.id.recyclerView);
        lists = LitePal.findAll(DebtApply.class);
        adapter = new DebtAdapter(lists);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this, RecyclerView.VERTICAL, false));
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new DebtAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(MainActivity.this, AddDebtActivity.class);
                intent.putExtra("id", lists.get(position).getId());
                startActivity(intent);
                finish();
            }

            @Override
            public void onItemLongClick(View view, final int position) {
                new XPopup.Builder(MainActivity.this).asConfirm(null, "确认删除？",
                        new OnConfirmListener() {
                            @Override
                            public void onConfirm() {
                                adapter.deleteItem(position);
                            }
                        })
                        .show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.about:
                new XPopup.Builder(MainActivity.this)
                        .popupAnimation(PopupAnimation.TranslateFromRight)
                        .asConfirm("关于", "数据库课程设计：Java+SQLite",
                                new OnConfirmListener() {
                                    @Override
                                    public void onConfirm() {
                                    }
                                })
                        .show();
                break;
            case R.id.exit:
                finish();
                break;
            case R.id.query:
                Intent intent = new Intent(MainActivity.this, QueryActivity.class);
                startActivity(intent);
                break;
        }
        return true;
    }

    /**
     * 利用反射机制调用MenuBuilder的setOptionalIconsVisible方法设置mOptionalIconsVisible为true，
     * 给菜单设置图标时才可见 让菜单同时显示图标和文字
     */
    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        if (menu != null) {
            if (menu.getClass().getSimpleName().equalsIgnoreCase("MenuBuilder")) {
                try {
                    Method method = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
                    method.setAccessible(true);
                    method.invoke(menu, true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return super.onMenuOpened(featureId, menu);
    }

}
