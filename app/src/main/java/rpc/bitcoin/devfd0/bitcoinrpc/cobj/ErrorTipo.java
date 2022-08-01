package rpc.bitcoin.devfd0.bitcoinrpc.cobj;

import com.google.gson.annotations.Expose;

public class ErrorTipo {

@Expose
private Integer code;
@Expose
private String message;

public Integer getCode() {
return code;
}

public void setCode(Integer code) {
this.code = code;
}

public String getMessage() {
return message;
}

public void setMessage(String message) {
this.message = message;
}

}
