package rpc.bitcoin.devfd0.bitcoinrpc.cobj;
import com.google.gson.annotations.Expose;


public class Rerror {

@Expose
private Object result;
@Expose
private ErrorTipo error;
@Expose
private Integer id;

public Object getResult() {
return result;
}

public void setResult(Object result) {
this.result = result;
}

public ErrorTipo getError() {
return error;
}

public void setError(ErrorTipo error) {
this.error = error;
}

public Integer getId() {
return id;
}

public void setId(Integer id) {
this.id = id;
}

}
