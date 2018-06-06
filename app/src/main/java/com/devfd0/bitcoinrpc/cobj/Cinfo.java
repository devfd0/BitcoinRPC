package com.devfd0.bitcoinrpc.cobj;
import com.google.gson.annotations.Expose;


public class Cinfo {

@Expose
private CResultadogetwalletinfo result;
@Expose
private Object error;
@Expose
private Object id;

public CResultadogetwalletinfo getResult() {
return result;
}

public void setResult(CResultadogetwalletinfo result) {
this.result = result;
}

public Object getError() {
return error;
}

public void setError(Error e) {
this.error = error;
}

public Object getId() {
return id;
}

public void setId(Object id) {
this.id = id;
}

}
