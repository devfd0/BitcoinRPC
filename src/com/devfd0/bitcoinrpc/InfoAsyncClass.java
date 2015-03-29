package com.devfd0.bitcoinrpc;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import java.net.HttpURLConnection;




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




import com.devfd0.bitcoinrpc.cobj.Cinfo;
import com.google.gson.Gson;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;



public class InfoAsyncClass extends AsyncTask<Void, String, Boolean> {
	private HttpClient httpClient;
	private HttpPost request;
	private static final ProtocolVersion PROTOCOL_VERSION = new ProtocolVersion("HTTP", 1, 0);
	///
    private Context context;
    ProgressDialog dialog;
	private String pass="";
	private String user="";
	private String urls="";
	private HttpURLConnection connection = null;
	private int statusCode = -2;
	private String mensajeError = "desconocido";
	public String solicitarMetodo = "";
	//private JSONObject request= null;
	MainActivity activity;
	Cinfo obj;
	String contentType = "application/json-rpc; charset=utf-8";
	 
	
	public InfoAsyncClass(Context cxt,MainActivity m) {	
        	this.activity=m;
            context = cxt;
            dialog = new ProgressDialog(context);
            ConfiguracionFile ini = new ConfiguracionFile("ini",context);
    		pass = ini.getvalor("pass", "");
    		user = ini.getvalor("nombre", "");
    		urls = ini.getvalor("url", "");
   		
    		solicitarMetodo = "getinfo";
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
            Log.i("mio",responseString);
			responseString = responseString.trim();
			if (responseString!=""){
				//System.out.println("Respuesta: "+response);
                if (responseString.contains("version")) {
                    Gson gson = new Gson();
                    obj = gson.fromJson(responseString.toString(), Cinfo.class);
                    salida = true;
                    statusCode = 200;

                }
                else
                {
                    statusCode = -13;
                    mensajeError = responseString;

                }
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
    	public Cinfo getInfo(){		 
    				return obj;
    		
    	}
   

    	void desconectar( ){
    		connection.disconnect();
    	}

        @Override
        protected void onPreExecute() {
            dialog.setTitle(context.getText(R.string.waitplease));
            dialog.setMessage(context.getText(R.string.wait));
            dialog.show();
        }

        @Override
        protected Boolean doInBackground(Void... unused) {
        	Boolean salida = false;   
			JSONObject r = new JSONObject();
        	r.put("method", solicitarMetodo);	
             salida = doJSONRequest(r);      

        	
        	return salida;
        	
        }
    	String getStatusCode(){
    		return Integer.toString(statusCode);
    	}
    	String getMensajeError(){
    		return mensajeError;
    	}

        @Override
        protected void onPostExecute(Boolean resultado) {
            dialog.dismiss();            
            activity.cargar();            
        }
    }