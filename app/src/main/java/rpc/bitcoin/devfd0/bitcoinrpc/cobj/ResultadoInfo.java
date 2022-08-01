package rpc.bitcoin.devfd0.bitcoinrpc.cobj;
import com.google.gson.annotations.Expose;

public class ResultadoInfo{



	@Expose
	private Integer version;
	@Expose
	private Integer protocolversion;
	@Expose
	private Integer walletversion;
	@Expose
	private Double balance;
	@Expose
	private Integer blocks;
	@Expose
	private Integer timeoffset;
	@Expose
	private Integer connections;
	@Expose
	private String proxy;
	@Expose
	private Double difficulty;
	@Expose
	private Boolean testnet;
	@Expose
	private Integer keypoololdest;
	@Expose
	private Integer keypoolsize;
	@Expose
	private Double paytxfee;
	@Expose
	private Double relayfee;
	@Expose
	private String errors;

	public Integer getVersion() {
	return version;
	}

	public void setVersion(Integer version) {
	this.version = version;
	}

	public Integer getProtocolversion() {
	return protocolversion;
	}

	public void setProtocolversion(Integer protocolversion) {
	this.protocolversion = protocolversion;
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

	public Integer getBlocks() {
	return blocks;
	}

	public void setBlocks(Integer blocks) {
	this.blocks = blocks;
	}

	public Integer getTimeoffset() {
	return timeoffset;
	}

	public void setTimeoffset(Integer timeoffset) {
	this.timeoffset = timeoffset;
	}

	public Integer getConnections() {
	return connections;
	}

	public void setConnections(Integer connections) {
	this.connections = connections;
	}

	public String getProxy() {
	return proxy;
	}

	public void setProxy(String proxy) {
	this.proxy = proxy;
	}

	public Double getDifficulty() {
	return difficulty;
	}

	public void setDifficulty(Double difficulty) {
	this.difficulty = difficulty;
	}

	public Boolean getTestnet() {
	return testnet;
	}

	public void setTestnet(Boolean testnet) {
	this.testnet = testnet;
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

	public Double getRelayfee() {
	return relayfee;
	}

	public void setRelayfee(Double relayfee) {
	this.relayfee = relayfee;
	}

	public String getErrors() {
	return errors;
	}

	public void setErrors(String errors) {
	this.errors = errors;
	}

}

