package com.devfd0.bitcoinrpc;

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

import org.json.simple.JSONObject;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

public class AsyncClass extends AsyncTask<Void, String, Boolean> {
    private Context context;
    ProgressDialog dialog;
	private String pass="";
	private String user="";
	private String urls="";
	private HttpURLConnection connection;
	private URL url;
	private int statusCode;
	public String solicitarMetodo = "";
	private JSONObject request=new JSONObject();
	private String response;
	public MainActivity activity;
	
        public AsyncClass(Context cxt,String metodo,MainActivity m) {
        	this.activity=m;
            context = cxt;
            dialog = new ProgressDialog(context);
            ConfiguracionFile ini = new ConfiguracionFile("ini",context);
    		pass = ini.getvalor("pass", "");
    		user = ini.getvalor("nombre", "");
    		urls = ini.getvalor("url", "");
    		solicitarMetodo = metodo;
        }
        void setSolicitarMetodo(String cad){
    		solicitarMetodo = cad;
    	}
    	public listaInfo getInfo(){		 
    		listaInfo salida = null;
    		if (response!=null){			
    			salida = new listaInfo(response);			
    			Log.i("mio","fin getInfo c");
    		}
    		return salida;
    		
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
        	request.put("method", Operacion);		
    		(outputStream=connection.getOutputStream()).write(request.toJSONString().getBytes("UTF-8"));
    		outputStream.close();    			
    	
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

        @Override
        protected void onPreExecute() {
            dialog.setTitle("Please wait");
            dialog.show();
        }

        @Override
        protected Boolean doInBackground(Void... unused) {
        	Boolean salida = false;	
        	
        	
        	if (!conectar()) return salida;
        	try {        		
        		solicitarMetodo(solicitarMetodo);    	
     	
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

        @Override
        protected void onPostExecute(Boolean resultado) {
            dialog.dismiss();
            activity.cargar();            
        }
    }