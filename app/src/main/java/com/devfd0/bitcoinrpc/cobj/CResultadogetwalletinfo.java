package com.devfd0.bitcoinrpc.cobj;

import com.google.gson.annotations.Expose;

public class CResultadogetwalletinfo {



    @Expose
    private Integer version;
    @Expose
    private String walletname;
    @Expose
    private Integer walletversion;
    @Expose
    private Double balance;
    @Expose
    private Integer txcount;
    @Expose
    private Integer keypoololdest;
    @Expose
    private Integer keypoolsize;
    @Expose
    private Double paytxfee;
    @Expose
    private String errors;

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }



    public Integer getWalletversion() {
        return walletversion;
    }

    public void setWalletversion(Integer walletversion) {
        this.walletversion = walletversion;
    }

    public Double getBalance() {

        return balance;

    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }


    public Integer getKeypoololdest() {
        return keypoololdest;
    }

    public void setKeypoololdest(Integer keypoololdest) {
        this.keypoololdest = keypoololdest;
    }

    public Integer getKeypoolsize() {
        return keypoolsize;
    }

    public void setKeypoolsize(Integer keypoolsize) {
        this.keypoolsize = keypoolsize;
    }

    public Double getPaytxfee() {
        return paytxfee;
    }

    public void setPaytxfee(Double paytxfee) {
        this.paytxfee = paytxfee;
    }

    public String getErrors() {
        return errors;
    }

    public void setErrors(String errors) {
        this.errors = errors;
    }


}
