package com.example.sqlitetest.bean;


import org.litepal.crud.LitePalSupport;

public class DebtApply extends LitePalSupport {

    private int id;

    private float amount;//金额

    private String bondInstitutionName;//债券人

    private String bankName;//关联银行

    private String applyTime;

    private String debtStartTime;

    private String debtEndTime;

    private float baseInterest;

    private String username;

    private String remark;

    private String payTypeName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public String getBondInstitutionName() {
        return bondInstitutionName;
    }

    public void setBondInstitutionName(String bondInstitutionName) {
        this.bondInstitutionName = bondInstitutionName;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(String applyTime) {
        this.applyTime = applyTime;
    }

    public String getDebtStartTime() {
        return debtStartTime;
    }

    public void setDebtStartTime(String debtStartTime) {
        this.debtStartTime = debtStartTime;
    }

    public String getDebtEndTime() {
        return debtEndTime;
    }

    public void setDebtEndTime(String debtEndTime) {
        this.debtEndTime = debtEndTime;
    }

    public float getBaseInterest() {
        return baseInterest;
    }

    public void setBaseInterest(float baseInterest) {
        this.baseInterest = baseInterest;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getPayTypeName() {
        return payTypeName;
    }

    public void setPayTypeName(String payTypeName) {
        this.payTypeName = payTypeName;
    }
}
