
package com.devfd0.bitcoinrpc;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.TimeZone;import android.content.Context;import android.net.ConnectivityManager;import android.net.NetworkInfo;

public class Utiles {
	
	public static String rellenarcon(String cadena,int numero,char caracter, int modo){//modo 1 rellena por la derecha, distinto por la izquierda
        StringBuffer copia =  new StringBuffer(cadena);
        if (modo==1)
        {               
               for (int i=cadena.length();i<numero;i++){
                        copia.insert(i,caracter);                     }
       }
        else
        {
                for (int i=0;i<numero-cadena.length();i++){
                        copia.insert(i, caracter);
                }       
       }
        return copia.toString();	  }
	
	//rellenarCadena
	public static String rellenarCadena(String text, int size, String caracter, boolean modo)	{
        //modo = true => relleno por la derecha
        //modo = false => relleno por la izquierda
        if (text.length() >= size)
        	return text;
        String salida = "";
        String aux = "";
        int t = size - text.length();
        for (int i = 0; i < t; i++) {
            aux += caracter;
        }
        if (modo == false) {
            salida = aux + text;
        } else {
            salida = text + aux;
        }
        return salida;
    }
	
	String convertirTimeStamp(long unixSecons){		
		Date date = new Date(unixSecons*1000L); // *1000 is to convert seconds to milliseconds
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // the format of your date
		sdf.setTimeZone(TimeZone.getTimeZone("GMT-1"));
		String formattedDate = sdf.format(date);
		return  formattedDate;
	}
}
