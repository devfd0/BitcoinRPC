package com.devfd0.bitcoinrpc;
import java.text.DecimalFormat;

import java.util.List;

import com.devfd0.bitcoinrpc.cobj.Lista;
import com.devfd0.bitcoinrpc.cobj.ResultadoTransacciones;import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;

import android.widget.TextView;


public class transaccciones extends Activity{
	Button actualizar = null;
	ListAsyncClass a;
	private static transaccciones instance;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		instance = this;	
		setContentView(R.layout.transacciones);

		conectar();

	}
	
	void conectar(){		
		a = new ListAsyncClass(instance,this);			
		a.execute();	
	}
	public void cargar(){
		
		String error = a.getStatusCode();
		Lista i = a.getLista();		
		
		if (Integer.parseInt(error) == 200)
		{
			 LinearLayout linearPadre = (LinearLayout) findViewById(R.id.padre);     
			if (i!=null){
			List<ResultadoTransacciones>  misTransacciones =  i.getResult();
				//si todo fue bien pintamos
				int salida = misTransacciones.size()-1;
				
				for (int f=salida;f>=0;f--){
					ResultadoTransacciones l = misTransacciones.get(f);
					TextView tvLinea = new TextView(this);
					TextView tvLineadir = new TextView(this);
					TextView tvLineamonto = new TextView(this);
                    tvLinea.setTypeface(Typeface.MONOSPACE);
                    tvLineadir.setTypeface(Typeface.MONOSPACE);
                    tvLineamonto.setTypeface(Typeface.MONOSPACE);
                    tvLineamonto.setGravity(17);//center


     
                    String date = new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new java.util.Date (l.getTimereceived()*1000L));
                    
                    String cadena = Utiles.rellenarcon(date, 20, ' ', 1);
                            cadena = cadena.concat(Utiles.rellenarcon(l.getAddress(),40,' ',1)) ;
                            
                            DecimalFormat df = new DecimalFormat("#.########");
                            
                            cadena = cadena.concat(Utiles.rellenarcon(df.format(l.getAmount()).toString(),10,' ',1));
                  
                            
                            
                    tvLinea.setText(date);
                    tvLinea.setTextSize(2, 15);
                    tvLineadir.setText(l.getAddress());
                    tvLineadir.setTextSize(2, 12);
                    tvLineamonto.setText(df.format(l.getAmount()).toString());
                    tvLineamonto.setTextSize(2, 17);
                    linearPadre.addView(tvLinea);
                    linearPadre.addView(tvLineadir);
                    linearPadre.addView(tvLineamonto);
					
				}

			}
			//guardarDatos();
		}
		else
		{			Dialogo d = new Dialogo(this,this);			d.mostrarDialogoOK("Error code "+error," Mensaje: "+a.getResponse());			
		}			
	
}

}
