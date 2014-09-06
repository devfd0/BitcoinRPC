package com.devfd0.bitcoinrpc;


import java.io.UnsupportedEncodingException;


import java.io.IOException;

import java.util.LinkedList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ProtocolVersion;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONObject;

import com.devfd0.bitcoinrpc.cobj.Lista;
import com.google.gson.Gson;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

public class ListAsyncClass extends AsyncTask<Void, String, Boolean> {
	private HttpClient httpClient;
	private HttpPost request;
	private static final ProtocolVersion PROTOCOL_VERSION = new ProtocolVersion("HTTP", 1, 0);
    private Context context;
    ProgressDialog dialog;
	private String pass="";
	private String user="";
	private String urls="";	
	private int statusCode = -1;
	public String solicitarMetodo = "";
	Lista objSalida;
	private int n = 8;
	private String response;
	private String mensajeError = "Error";
	transaccciones activity;
	
	public ListAsyncClass(Context cxt,transaccciones m) {	
		
        	this.activity=m;
            context = cxt;
            dialog = new ProgressDialog(context);
            ConfiguracionFile ini = new ConfiguracionFile("ini",context);
    		pass = ini.getvalor("pass", "");
    		user = ini.getvalor("nombre", "");
    		urls = ini.getvalor("url", "");
    		n = Integer.parseInt(ini.getvalor("number", "8"));
    		//listtransactions [account] [count=10] [from=0]
    		solicitarMetodo = "listtransactions";
    		httpClient = HttpClientTrustAll.getNewHttpClient(context, "");
    		request = new HttpPost(urls);
    		HttpParams params = new BasicHttpParams();
    		HttpConnectionParams.setConnectionTimeout(params, 30000);
    		HttpConnectionParams.setSoTimeout(params, 30000);
    		HttpProtocolParams.setVersion(params, PROTOCOL_VERSION);
    		request.setParams(params);
    		request.addHeader("Authorization", "Basic " + Base64Coder.encodeString(user+":"+pass));
        }
	
	protected boolean doJSONRequest(JSONObject jsonRequest) 
	{
		// Create HTTP/POST request with a JSON entity containing the request
		statusCode = -1;
		boolean salida = false;
		HttpEntity entity = null;
		try
		{

			entity = new JSONEntity(jsonRequest);
			
		}
		catch (UnsupportedEncodingException e1)
		{
			//throw new JSONRPCException("Unsupported encoding", e1);
		}
		request.setEntity(entity);

		try
		{
			// Execute the request and try to decode the JSON Response
			long t = System.currentTimeMillis();
			HttpResponse response = httpClient.execute(request);
			t = System.currentTimeMillis() - t;
			Log.d("json-rpc", "Request time :" + t);
			String responseString = EntityUtils.toString(response.getEntity());
			responseString = responseString.trim();
			if (responseString!=""){
				 //System.out.println(response);
	            Gson gson = new Gson();  
	            objSalida = gson.fromJson(responseString.toString(), Lista.class);
				salida = true;
				statusCode = 200;
				
			}
			else{
				mensajeError="Sin respuesta";
			}
		
		}
		// Underlying errors are wrapped into a JSONRPCException instance
		catch (ClientProtocolException e)
		{
			
		}
		catch (IOException e)
		{
			
		}

		return salida;
	}

    	public Lista getLista(){		 
    		
    		return objSalida;
    		
    	}

    	public String getMensajeError(){
    		return mensajeError;
    	}


        @Override
        protected void onPreExecute() {
            dialog.setTitle("Please wait");
            dialog.setMessage("Conectando ..");
            dialog.show();
        }

        @SuppressWarnings("resource")		@Override
        protected Boolean doInBackground(Void... unused) {
        	Boolean salida = false;	
      		JSONObject obj = new JSONObject();
  		  LinkedList p = new LinkedList();
  		  p.add("*");
  		  p.add(n);
  		  obj.put("method", "listtransactions");
  		  obj.put("params", p);
  		  obj.put("id", 1);
        	
        	salida = doJSONRequest(obj);
       
        	return salida;
        	
        }
    	String getStatusCode(){
    		return Integer.toString(statusCode);
    	}
    	String getResponse(){
    		return response;
    	}

        @Override
        protected void onPostExecute(Boolean resultado) {
            dialog.dismiss();            
            activity.cargar();            
        }
    }