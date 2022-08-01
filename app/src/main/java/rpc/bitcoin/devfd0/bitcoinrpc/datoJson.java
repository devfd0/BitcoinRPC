package rpc.bitcoin.devfd0.bitcoinrpc;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;

import org.json.*;

public class datoJson implements Serializable{
	/**12dfk91BhndDvBioYbZan1Cy2WL795PLxc
	 * {"result":"b9557f50d1b772bb84609480692ef740095bbd8a1565e2fd0dd6d522754adf9e","error":null,"id":2}
	 * {"result":null,"error":{"code":-5,"message":"Invalid Bitcoin address"},"id":2}
	 */
	private static final long serialVersionUID = 1L;
	String response;	
	String code = ""; ///comun en caso de error
	String mensaje = ""; //comun en caso de error
	String result = "";
	datoJson(String recibido){
		response = recibido;
		code = getValor("code");
		mensaje = getValor("message").replace("\"", "").replace("}", "").replace("\\", "");
		result = getValor("result");
	}
	String getValor(String campo){
		String salida = "";
		String inicio="\\\""+campo+"\\\":";
		String fin=",\\";
		String resultado = "";//JSONValue.escape(response);
		if (resultado.contains(inicio)){
			int posInicial = resultado.indexOf(inicio) + inicio.length();
			String temp = resultado.substring(posInicial, resultado.length());

			int posFinal = temp.indexOf(fin);
			salida = (String) temp.subSequence(0, posFinal);
		}
		else salida ="";
		//System.out.println(salida);
		
		
		return  salida;
	}
	int getValorInt(String campo) {		
		String salida = "";
		String inicio="\\\""+campo+"\\\":";
		String fin=",\\";
		String resultado = "";
		int s = 0;
		resultado = "";//JSONValue.escape(response);
		if (resultado.contains(inicio)){
			int posInicial = resultado.indexOf(inicio) + inicio.length();
			String temp = resultado.substring(posInicial, resultado.length());

			int posFinal = temp.indexOf(fin);
			salida = (String) temp.subSequence(0, posFinal);
		}
		else salida ="no datos";
		//System.out.println(salida);
		s = Integer.parseInt(salida);

		return  s;
	}
	double getValorDouble(String campo) throws UnsupportedEncodingException{		
		String salida = "";
		String inicio="\\\""+campo+"\\\":";
		String fin=",\\";
		String resultado = "";
		double s = 0;
		resultado = "";//JSONValue.escape(response);
		if (resultado.contains(inicio)){
			int posInicial = resultado.indexOf(inicio) + inicio.length();
			String temp = resultado.substring(posInicial, resultado.length());

			int posFinal = temp.indexOf(fin);
			salida = (String) temp.subSequence(0, posFinal);
		}
		else salida ="no datos";
		//System.out.println(salida);
		s = Double.parseDouble(salida);

		return  s;
	}
	boolean getValorBoolean(String campo) throws UnsupportedEncodingException{		
		String salida = "";
		String inicio="\\\""+campo+"\\\":";
		String fin=",\\";
		String resultado = "";
		resultado = "";//JSONValue.escape(response);
		if (resultado.contains(inicio)){
			int posInicial = resultado.indexOf(inicio) + inicio.length();
			String temp = resultado.substring(posInicial, resultado.length());

			int posFinal = temp.indexOf(fin);
			salida = (String) temp.subSequence(0, posFinal);
		}
		else salida ="no datos";
		//System.out.println(salida);
		if (salida == "true")
			return true;
		else return false;
		

		
	}

}
