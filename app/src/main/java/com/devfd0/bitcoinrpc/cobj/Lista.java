
package com.devfd0.bitcoinrpc.cobj;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;


public class Lista {

@Expose
private List<ResultadoTransacciones> result = new ArrayList<ResultadoTransacciones>();
@Expose
private Object error;
@Expose
private Integer id;

public List<ResultadoTransacciones> getResult() {
return result;
}

public void setResult(List<ResultadoTransacciones> result) {
this.result = result;
}

public Object getError() {
return error;
}

public void setError(Object error) {
this.error = error;
}

public Integer getId() {
return id;
}

public void setId(Integer id) {
this.id = id;
}

}
