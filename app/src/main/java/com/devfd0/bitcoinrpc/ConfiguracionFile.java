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
	/*
	  
	  public int getUser_id() {
 return appSharedPrefs.getInt(user_id, 0);
 }
 
public void setUser_id(int _user_id) {
 prefsEditor.putInt(user_id, _user_id).commit();
}
public String getUser_name() {
 return appSharedPrefs.getString(user_name, "unkown");
 }
 
 public void setUser_name( String _user_name) {
 prefsEditor.putString(user_name, _user_name).commit();
 }
	 */
}
