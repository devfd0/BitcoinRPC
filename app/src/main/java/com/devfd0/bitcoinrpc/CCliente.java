package com.devfd0.bitcoinrpc;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.PasswordAuthentication;
import java.net.Proxy;
import java.net.Socket;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;


public class CCliente extends AsyncTask<Void, String, String> {
	private int id;
	ProgressDialog  dialog  = null;
    String contentType = "application/json";
    String requestBody = "{\"jsonrpc\":\"1.0\",\"id\":\"1\",\"method\":\"metodo\",\"params\":[parametros]}";



    //{ "jsonrpc":"2.0","id":"1","method": "listtransactions", "params" : [ "*", 10, 0 ] }
	//{"jsonrpc": "1.0", "id":"curltest", "method": "sendtoaddress", "params": ["1DuMcLEZjagrvD7e6yHVX9ymh27HE2Lx4D", 0.1, "donation", "seans outpost"] }
    URL url;
    HttpURLConnection connection = null;
    String urlParameters = "";
    Context mContext = null;
    ConfiguracionFile ini = null;
    String pass = "",nombre = "",error = "";
	 public AsyncResponse delegate = null;

    CCliente(Context cxt,String metodo,String p,int id){
		this.id = id;
        mContext= cxt;

        try {
			dialog = new ProgressDialog(mContext);
			requestBody = requestBody.replace("metodo",metodo);
            requestBody = requestBody.replace("parametros",p);
            ini = new ConfiguracionFile("ini",mContext);
            pass = ini.getvalor("pass","");
            nombre = ini.getvalor("nombre","");
            String urls = ini.getvalor("url", "");
            url = new URL(urls);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Authenticator.setDefault(new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(nombre, pass.toCharArray());
            }
        });

    }

    String leer(){
        String salida = "";
        String line = "";
        try {
            //Get Response
            InputStream is = connection.getInputStream();
            connection.setConnectTimeout(50000);
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            StringBuffer response = new StringBuffer();
            while((line = rd.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }
            rd.close();
            salida= response.toString();
        }catch (Exception e){
            error="Read Error:"+e.getMessage();
        }
        return salida;
    }
    boolean enviar(){
        boolean salida = false;
        try {
            //Send request
            DataOutputStream wr = new DataOutputStream(connection.getOutputStream ());
            wr.write(requestBody.getBytes());
            wr.flush();
            wr.close();
            salida = true;
        }catch(Exception e ){
            error="Send Error"+e.getMessage();
        }
        return  salida;
    }
    @Override
    protected void onPreExecute() {
		dialog.setTitle(mContext.getText(R.string.waitplease));
        dialog.setMessage(mContext.getText(R.string.wait));
		dialog.setCancelable(false);
        dialog.show();

    }
    @Override
    protected String doInBackground(Void... voids) {
        String salida = "";
        try {
            connection = (HttpURLConnection)url.openConnection(Proxy.NO_PROXY);
            connection.setRequestMethod("POST");

            connection.setRequestProperty("Content-Type",contentType);
            connection.setRequestProperty("Content-Length", "" + Integer.toString(requestBody.getBytes().length));
            connection.setUseCaches (true);
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setConnectTimeout(55000);

        }catch (Exception e){
            String er = e.getMessage();
            er="";
        }
        enviar();
        salida = leer();
        return salida;
    }

    @Override
    protected void onPostExecute(String resultado) {
		  dialog.dismiss();
            if(resultado.length()<1)
                resultado = error;
		  delegate.onProcessFinish(resultado, id);
    }

}
