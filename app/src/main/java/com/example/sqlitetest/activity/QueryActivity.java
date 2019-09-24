package com.example.sqlitetest.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.sqlitetest.R;
import com.example.sqlitetest.adapter.DebtAdapter;
import com.example.sqlitetest.bean.DebtApply;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnConfirmListener;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

import scut.carson_ho.searchview.ICallBack;
import scut.carson_ho.searchview.SearchView;
import scut.carson_ho.searchview.bCallBack;

public class QueryActivity extends AppCompatActivity {

    private SearchView searchView;
    private RecyclerView recyclerView;
    private DebtAdapter adapter;
    private List<DebtApply> lists = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query);

        searchView = findViewById(R.id.search_view);
        recyclerView = findViewById(R.id.recyclerView);
//        lists = LitePal.findAll(DebtApply.class);
        adapter = new DebtAdapter(lists);
        recyclerView.setLayoutManager(new LinearLayoutManager(QueryActivity.this, RecyclerView.VERTICAL, false));
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new DebtAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                /*Intent intent = new Intent(QueryActivity.this, AddDebtActivity.class);
                intent.putExtra("id", lists.get(position).getId());
                startActivity(intent);*/
            }

            @Override
            public void onItemLongClick(View view, final int position) {
            }
        });

        //  设置点击键盘上的搜索按键后的操作（通过回调接口）
        // 参数 = 搜索框输入的内容
        searchView.setOnClickSearch(new ICallBack() {
            @Override
            public void SearchAciton(String string) {//模糊查询

                List<DebtApply> newLists = LitePal.where("bankname like ? or username like ? " +
                        "or bondinstitutionname like ? or applytime like ? or REMARK like ?",
                        "%" + string + "%",
                        "%" + string + "%",
                        "%" + string + "%",
                        "%" + string + "%",
                        "%" + string + "%"
                ).find(DebtApply.class);
                lists.clear();
                lists.addAll(newLists);
                adapter.notifyDataSetChanged();
            }
        });

        // 5. 设置点击返回按键后的操作（通过回调接口）
        searchView.setOnClickBack(new bCallBack() {
            @Override
            public void BackAciton() {
                finish();
            }
        });
    }
}
