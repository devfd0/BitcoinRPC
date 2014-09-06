package com.devfd0.bitcoinrpc;



import com.devfd0.bitcoinrpc.cobj.ErrorTipo;
import com.devfd0.bitcoinrpc.cobj.Rerror;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class enviar  extends Activity{
	Button enviar = null;
	Button cancelar = null;
	SendAsyncClass a = null;
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
	enviar = (Button)findViewById(R.id.button2);
	cancelar = (Button)findViewById(R.id.button1);
	dialogo = (View)findViewById(R.id.textoDialogo);
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
}
void enviarBTC(String d,String c,String com,String cpara){
	a = new SendAsyncClass(instance,this,d,c,com,cpara);
	a.execute();	
}
void cargar(){
	Dialogo d = new Dialogo(this,this);
	ErrorTipo e = null;	
	Rerror r = a.getResponse();
	if (r!=null){
		e = r.getError();
		d.mostrarDialogoOK("Error", e.getMessage());
		if (a.getStatusCode()=="200") finish();
	}	
	else{
		d.mostrarDialogoOK("No conecta","Sin respuesta del servidor");		
	}
	
}

}