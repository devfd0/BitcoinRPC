
package com.devfd0.bitcoinrpc.cobj;
import com.google.gson.annotations.Expose;


public class Cinfo {

@Expose
private ResultadoInfo result;
@Expose
private Object error;
@Expose
private Object id;

public ResultadoInfo getResult() {
return result;
}

public void setResult(ResultadoInfo result) {
this.result = result;
}

public Object getError() {
return error;
}

public void setError(Object error) {
this.error = error;
}

public Object getId() {
return id;
}

public void setId(Object id) {
this.id = id;
}

}