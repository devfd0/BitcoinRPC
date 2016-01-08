package com.devfd0.bitcoinrpc;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class Configuracion extends Activity {
	EditText url = null;
	EditText nombre = null;
	EditText pass = null;
	EditText number =null;
	Button guardar = null;
	ConfiguracionFile ini = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {			
			super.onCreate(savedInstanceState);
			setContentView(R.layout.configuracion);		
			guardar = (Button)findViewById(R.id.cancelarEnvio);
			url = (EditText)findViewById(R.id.editText1);
			nombre = (EditText)findViewById(R.id.editText2);
			pass = (EditText)findViewById(R.id.EditTextPass);			
			ini = new ConfiguracionFile("ini",this.getApplicationContext());
			url.setText(ini.getvalor("url", ""));
			nombre.setText(ini.getvalor("nombre", ""));
			pass.setText(ini.getvalor("pass", ""));			
			number = (EditText)findViewById(R.id.editTextNumber);
			number.setText(ini.getvalor("number","8"));
			
			guardar.setOnClickListener(new View.OnClickListener() {
				
				public void onClick(View v) {
					guardarDatos();
					
				}
			});
			
			
	}
	void guardarDatos(){
		
		ini.setValor("url", url.getText().toString());
		ini.setValor("nombre", nombre.getText().toString());
		ini.setValor("pass", pass.getText().toString());
		ini.setValor("number", number.getText().toString());
		Dialogo d = new Dialogo(this, this);
		d.mostrarDialogoOK(getString(R.string.aviso), getString(R.string.gDatos));
	}		
	
}
