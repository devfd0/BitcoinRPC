package com.devfd0.bitcoinrpc;





import com.devfd0.bitcoinrpc.cobj.ErrorTipo;
import com.devfd0.bitcoinrpc.cobj.Lista;
import com.devfd0.bitcoinrpc.cobj.Rerror;
import com.devfd0.bitcoinrpc.cobj.ResultadoTransacciones;
import com.google.gson.Gson;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.List;


public class enviar  extends Activity implements AsyncResponse {
	Button enviar = null;
	Button cancelar = null;
	Button camara = null;
	CCliente a = null;
	View dialogo = null;
	EditText direccion = null;
	EditText cantidad = null;
	EditText comentario = null;
	EditText comentariopara = null;
	 private static enviar instance;
@Override
protected void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	instance = this;
	super.onCreate(savedInstanceState);	
	setContentView(R.layout.enviar);
	camara = (Button)findViewById(R.id.button3);
	enviar = (Button)findViewById(R.id.button2);
	cancelar = (Button)findViewById(R.id.cancelarEnvio);
	dialogo = findViewById(R.id.textoDialogo);
	direccion = (EditText)findViewById(R.id.editText1);
	cantidad = (EditText)findViewById(R.id.editText4);
	comentario = (EditText)findViewById(R.id.editText2);
	comentariopara = (EditText)findViewById(R.id.editTextNumber);


    

	
	enviar.setOnClickListener(new View.OnClickListener() {
				
				public void onClick(View v) {					
					enviarBTC(direccion.getText().toString(),cantidad.getText().toString(),
							comentario.getText().toString(),comentariopara.getText().toString());
					
				}
			});
	cancelar.setOnClickListener(new View.OnClickListener() {
		
		public void onClick(View v) {
			finish();
			
		}
	});
	camara.setOnClickListener(new View.OnClickListener() {
		
		public void onClick(View v) {					
			//llamamos activity cam	        
			Intent intente = new Intent();
    		intente.setClass(getApplicationContext(),CameraActivity.class);
    		String code = "";
    		intente.putExtra("code", code);
    		startActivityForResult(intente, 1);
    		//startActivity(intente);
		}
	});
}


protected void onActivityResult(int requestCode, int resultCode, Intent data) {

	if (resultCode == RESULT_OK && requestCode == 1) {
		direccion.setText(data.getExtras().getString("code"));
	}

}

void enviarBTC(String d,String c,String com,String cpara){
	if (d.length()>23)
	{
		a = new CCliente(this,"sendtoaddress","\\"+d+"\\,"+c+",\\"+com+"\\,\\"+cpara+"\\"+"",3);
		//(instance,this,d,c,com,cpara);
		a.delegate = this;
		a.execute();
	}
	else
	{
		Dialogo dd = new Dialogo(this, this);
		dd.mostrarDialogoOKNoFinish(getString(R.string.error),getString(R.string.longDireccion));
	}	
}
    @Override
    public void onProcessFinish(String result, int id) {
        if(id == 3){
                Dialogo d = new Dialogo(this,this);
                d.mostrarDialogoOKNoFinish(getString(R.string.error), result);
        }
    }
    /*
void cargar(){
	Dialogo d = new Dialogo(this,this);
	ErrorTipo e;
	Rerror r = a.getResponse();
	if (r!=null){
		e = r.getError();
		d.mostrarDialogoOK("Error", e.getMessage());
		if (a.getStatusCode().contains("200")) finish();
	}	
	else{
		d.mostrarDialogoOK(getString(R.string.error),getString(R.string.sinrespuesta));		
	}
	
}
*/
}
