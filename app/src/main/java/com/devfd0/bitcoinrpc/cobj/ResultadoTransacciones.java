package com.devfd0.bitcoinrpc.cobj;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;


public class ResultadoTransacciones {

@Expose
private String account;
@Expose
private String address;
@Expose
private String category;
@Expose
private Double amount;
@Expose
private Integer confirmations;
@Expose
private String blockhash;
@Expose
private Integer blockindex;
@Expose
private Integer blocktime;
@Expose
private String txid;
@Expose
private List<Object> walletconflicts = new ArrayList<Object>();
@Expose
private Integer time;
@Expose
private Integer timereceived;
@Expose
private Double fee;

public String getAccount() {
return account;
}

public void setAccount(String account) {
this.account = account;
}

public String getAddress() {
return address;
}

public void setAddress(String address) {
this.address = address;
}

public String getCategory() {
return category;
}

public void setCategory(String category) {
this.category = category;
}

public Double getAmount() {
return amount;
}

public void setAmount(Double amount) {
this.amount = amount;
}

public Integer getConfirmations() {
return confirmations;
}

public void setConfirmations(Integer confirmations) {
this.confirmations = confirmations;
}

public String getBlockhash() {
return blockhash;
}

public void setBlockhash(String blockhash) {
this.blockhash = blockhash;
}

public Integer getBlockindex() {
return blockindex;
}

public void setBlockindex(Integer blockindex) {
this.blockindex = blockindex;
}

public Integer getBlocktime() {
return blocktime;
}

public void setBlocktime(Integer blocktime) {
this.blocktime = blocktime;
}

public String getTxid() {
return txid;
}

public void setTxid(String txid) {
this.txid = txid;
}

public List<Object> getWalletconflicts() {
return walletconflicts;
}

public void setWalletconflicts(List<Object> walletconflicts) {
this.walletconflicts = walletconflicts;
}

public Integer getTime() {
return time;
}

public void setTime(Integer time) {
this.time = time;
}

public Integer getTimereceived() {
return timereceived;
}

public void setTimereceived(Integer timereceived) {
this.timereceived = timereceived;
}

public Double getFee() {
return fee;
}

public void setFee(Double fee) {
this.fee = fee;
}

}
