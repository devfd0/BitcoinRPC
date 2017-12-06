package com.devfd0.bitcoinrpc;
import android.app.ProgressDialog;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.PasswordAuthentication;
import java.net.ProtocolException;
import java.net.URL;



import org.json.JSONObject;



import android.content.Context;

import android.os.AsyncTask;
import android.os.SystemClock;
import android.util.Log;

public class rpcJson extends AsyncTask<URL, Integer, Boolean>{
	Context contexto = null;
	private String pass="";
	private String user="";
	private String urls="";
	private HttpURLConnection connection;
	private URL url;
	private JSONObject request=new JSONObject();
	ProgressDialog pDialog = null;
	private String response;
	private int statusCode;	
	public String solicitarMetodo = "";
	void setSolicitarMetodo(String cad){
		solicitarMetodo = cad;
	}
	
	public rpcJson(Context c) {		
		contexto = c;
		crearDialogo();
		ConfiguracionFile ini = new ConfiguracionFile("ini",contexto);
		pass = ini.getvalor("pass", "");
		user = ini.getvalor("nombre", "");
		urls = ini.getvalor("url", "");	

    }

	Authenticator autenticar(){
		return new Authenticator() {			
	        protected PasswordAuthentication getPasswordAuthentication() {	        	
	        	return new PasswordAuthentication (user, pass.toCharArray());
	        }
		};	    
	}

	boolean  conectar(){
		try {
			url = new URL(urls);
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return false;
		}
		 try {
			connection = (HttpURLConnection) url.openConnection();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		 connection.setConnectTimeout(15000);
		 connection.setReadTimeout(30000);
		 try {
			connection.setRequestMethod("POST");
		} catch (ProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}	     
		 try {
			connection.connect();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	    return true;
	}
	@SuppressWarnings("unchecked")
	void solicitarMetodo(String Operacion) throws UnsupportedEncodingException, IOException{
		OutputStream outputStream;
    	//request.put("method", Operacion);
		//(outputStream=connection.getOutputStream()).write(request.toJSONString().getBytes("UTF-8"));
	//	outputStream.close();
		
	
	}

	void recibirRespuesta( ) throws IOException{
		InputStream inputStream;		
		statusCode = connection.getResponseCode();		
		 if (statusCode != 200){
			 inputStream = connection.getErrorStream();
		 }else{			 
			 inputStream=connection.getInputStream();
			 response = convertStreamToString(inputStream);
			 Log.i("mio",response);
			 inputStream.close();
		 }
	}
	void desconectar( ){
		connection.disconnect();
	}

	

	@Override	protected Boolean doInBackground(URL... arg0) {
	Boolean salida = false;	
	if (!conectar()) return salida;
	try {
		
		solicitarMetodo(solicitarMetodo);
	
			SystemClock.sleep(2000);
	
	} catch (UnsupportedEncodingException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();		
		return salida;
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();		
		return salida;
	}
	try {		
		recibirRespuesta();
		salida = true;		
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();				
		return salida;
	}	
	return salida;
	
	
	}
	
	String getStatusCode(){
		return Integer.toString(statusCode);
	}
	
	public listaInfo getInfo(){		 
		listaInfo salida = null;
		if (response!=null){			
			salida = new listaInfo(response);			
			Log.i("mio","fin getInfo c");
		}
		return salida;
		
	}
	@Override
	protected void onPreExecute() {
		Log.i("mio","onPreExecute c");
		super.onPreExecute();
		pDialog.setTitle("Please wait");
		pDialog.show();
	}
		
	@Override
	protected void onProgressUpdate(Integer... values) {	
		Log.i("mio","onProgressUpdate c");
		pDialog.setProgress(values[0]);
		
	}
	@Override
	protected void onPostExecute(Boolean result) {
		// TODO Auto-generated method stub		
		super.onPostExecute(result);
		pDialog.dismiss();
		Log.i("mio","fin onPostExecute c");
	}
	  
	  void crearDialogo(){
			pDialog = new ProgressDialog(contexto);
			pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			pDialog.setMessage("Conectando...");
			pDialog.setCancelable(true);
			pDialog.setMax(100);	
			
	  }

	private static String convertStreamToString(InputStream is) {
     BufferedReader reader = new BufferedReader(new InputStreamReader(is));
     StringBuilder sb = new StringBuilder();

     String line = null;
     try {
         while ((line = reader.readLine()) != null) {
             sb.append(line + "\n");
         }
     } catch (IOException e) {
         e.printStackTrace();
     } finally {
         try {
             is.close();
         } catch (IOException e) {
             e.printStackTrace();
         }
     }
     return sb.toString();
 }
	  
	
}