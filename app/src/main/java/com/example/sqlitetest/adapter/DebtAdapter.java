package com.example.sqlitetest.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sqlitetest.R;
import com.example.sqlitetest.bean.DebtApply;

import org.litepal.LitePal;

import java.util.List;


public class DebtAdapter extends RecyclerView.Adapter<DebtAdapter.DebtViewHolder> {

    private OnItemClickListener onItemClickListener;
    private List<DebtApply> debtApplies;

    public DebtAdapter(List<DebtApply> debtApplies) {
        this.debtApplies = debtApplies;
    }

    @NonNull
    @Override
    public DebtViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DebtViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_debt, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull DebtViewHolder holder, int position) {
        DebtApply debtApply = debtApplies.get(position);
        holder.amount.setText("￥" + debtApply.getAmount() + "元");
        holder.bondName.setText(debtApply.getBondInstitutionName());
        holder.debtTime.setText(debtApply.getDebtStartTime() + " 至 " + debtApply.getDebtEndTime());
        holder.userName.setText(debtApply.getUsername());
        holder.applyTime.setText(debtApply.getApplyTime());
    }

    //删除id对应的记录
    public void deleteItem(int position) {
        LitePal.delete(DebtApply.class, debtApplies.get(position).getId());
        debtApplies.remove(position);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return debtApplies.size();
    }

    class DebtViewHolder extends RecyclerView.ViewHolder {
        private TextView amount;
        private TextView bondName;
        private TextView debtTime;
        private TextView userName;
        private TextView applyTime;

        public DebtViewHolder(@NonNull final View itemView) {
            super(itemView);
            amount = itemView.findViewById(R.id.layout_debtqueryitemTvamount);
            bondName = itemView.findViewById(R.id.layout_debtqueryitemTvboundInstituation);
            debtTime = itemView.findViewById(R.id.layout_debtqueryitemTvdate);
            applyTime = itemView.findViewById(R.id.layout_debtqueryitemTvoperateDate);
            userName = itemView.findViewById(R.id.layout_debtqueryitemTvName);
            //单击事件
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null)
                        onItemClickListener.onItemClick(itemView, getLayoutPosition());
                }
            });
            //长按事件
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (onItemClickListener != null)
                        onItemClickListener.onItemLongClick(itemView, getLayoutPosition());
                    return true;
                }
            });


        }
    }

    /**
     * 设置回调监听
     *
     * @param onItemClickListener
     */
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);//单击事件

        void onItemLongClick(View view, int position);//长按事件
    }
}