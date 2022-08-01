package rpc.bitcoin.devfd0.bitcoinrpc;
import java.net.Authenticator;
import java.util.concurrent.ExecutionException;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;


public class Vprogreso extends Activity {
	rpcJson c = null;	
	public static String saldo="";
	public static String conexiones="";
	public static String version="";
	listaInfo i = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.vprogreso);
		c = new rpcJson(this);				
		Authenticator.setDefault (c.autenticar());		
		c.setSolicitarMetodo("getinfo");		
		Boolean estado = false;
		try {
			c.execute().get();
			if (estado){
				i = c.getInfo();		
			}  
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
		if (c.getStatus()==AsyncTask.Status.FINISHED){
			finish();	
		}
		
		
		
	}	
	@Override
	public void finish() {		
	  // Prepare data intent 
	  Intent data = new Intent();
	  Bundle bundle = new Bundle();  
	  bundle.putSerializable("info", i);
	  data.putExtras(bundle);
	  setResult(RESULT_OK, data);
	  super.finish();
	} 
	 void processFinish(String output){
	     //this you will received result fired from async class of onPostExecute(result) method.
		 finish();
	   }
	

	

}
