package com.devfd0.bitcoinrpc;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;


public class ConfiguracionFile extends Activity {	
	public SharedPreferences prefs = null;
	SharedPreferences.Editor editor = null;

	ConfiguracionFile(String nombreFile,Context context){
		this.prefs = context.getSharedPreferences(nombreFile, Activity.MODE_PRIVATE);
		this.editor = prefs.edit();
	}

	public String getvalor(String campo,String valorPorDefecto){		
		String salida = prefs.getString(campo, valorPorDefecto);
		return salida;
		
	}
	public void setValor(String campo,String valor){				
		editor.putString(campo, valor);		
		editor.commit();
	}

}
