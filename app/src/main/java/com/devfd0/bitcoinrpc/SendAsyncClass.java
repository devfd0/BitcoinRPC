package com.devfd0.bitcoinrpc;


import java.io.IOException;

import java.io.UnsupportedEncodingException;

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

import com.devfd0.bitcoinrpc.cobj.Rerror;
import com.google.gson.Gson;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

public class SendAsyncClass extends AsyncTask<Void, String, Boolean> {
	private HttpClient httpClient;
	private HttpPost request;
    private Context context;
    private static final ProtocolVersion PROTOCOL_VERSION = new ProtocolVersion("HTTP", 1, 0);
    ProgressDialog dialog;
	private String pass="";
	private String user="";
	private String urls="";
	private int statusCode;
	public String solicitarMetodo = "";
	enviar activity;
	String dir="";
	String cantidad = "";
	String comentario = "";
	String comentariopara = "";
	Rerror obj;
	
	public SendAsyncClass(Context cxt,enviar m,String d,String monto,String com,String compa) {	
    	this.activity=m;
        context = cxt;
        dialog = new ProgressDialog(context);
        dir = d;
        cantidad = monto;
        comentario = com;
        comentariopara = compa;
        ConfiguracionFile ini = new ConfiguracionFile("ini",context);
		pass = ini.getvalor("pass", "");
		user = ini.getvalor("nombre", "");
		urls = ini.getvalor("url", "");
		
		solicitarMetodo = "sendtoaddress";
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
    				obj = gson.fromJson(responseString.toString(), Rerror.class);
    				salida = true;
    				statusCode = 200;
    			}
    		
    		}
    		// Underlying errors are wrapped into a JSONRPCException instance
    		catch (ClientProtocolException e)
    		{
    			salida= false;
    		}
    		catch (IOException e)
    		{
    			salida = false;
    		}

    		return salida;
    	}



        @Override
        protected void onPreExecute() {
            dialog.setTitle("Please wait");
            dialog.setMessage("Conectando ..");
            dialog.show();
        }

        @Override
        protected Boolean doInBackground(Void... unused) {
        	Boolean salida = false;   
    		JSONObject obj = new JSONObject();
    		  LinkedList p = new LinkedList();
    		  p.add(dir);
    		  if (isNumeric(cantidad)){
    			  p.add(Float.parseFloat(cantidad)); 
    		  }
    		  if (comentario.length()>0)
    			  p.add(comentario);
    		  if (comentariopara.length()>0)
    			  p.add(comentariopara);
    		  obj.put("method", solicitarMetodo);
    		  obj.put("params", p);
    		  obj.put("id", 2);
             salida = doJSONRequest(obj);      

        	
        	return salida;
       
        	
        }
        public static boolean isNumeric(String str)
        {
          return str.matches("-?\\d+(\\.\\d+)?");  //match a number with optional '-' and decimal.
        }
        Rerror getResponse(){
        	return obj;
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