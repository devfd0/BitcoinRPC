package rpc.bitcoin.devfd0.bitcoinrpc;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class Dialogo {
	Activity a;
	Context contexto;
	public Dialogo(Context c,Activity aa){
		contexto = c;
		a = aa;
	}
	//muestra boton con texto personalizado y cierra
	void mostrarDialogoconunbotonpersonalizado(String titulo,String mensaje,String boton){

		AlertDialog.Builder builder = new AlertDialog.Builder(contexto);

    	builder.setTitle(titulo);
    	builder.setMessage(mensaje);
    	builder.setPositiveButton(boton, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button
            	a.finish();
            }

        });

    	AlertDialog alert = builder.create();
    	alert.show();		

	}
	//mustra dialogo con mensaje Botono OK que cierra
	void mostrarDialogoOK(String titulo,String mensaje){

		AlertDialog.Builder builder = new AlertDialog.Builder(contexto);

    	builder.setTitle(titulo);
    	builder.setMessage(mensaje);
    	builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button
            	a.finish();
            }

        });

    	AlertDialog alert = builder.create();
    	alert.show();		

	}
	void mostrarDialogoOKNoFinish(String titulo,String mensaje){

		AlertDialog.Builder builder = new AlertDialog.Builder(contexto);

    	builder.setTitle(titulo);
    	builder.setMessage(mensaje);
    	builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int id) {              
            }

        });

    	AlertDialog alert = builder.create();
    	alert.show();		

	}
	//muestra dialogo con botones ok y cancel y fibixh en ambos
	void mostrarDialogoOkCancel(String titulo,String mensaje){

		AlertDialog.Builder builder = new AlertDialog.Builder(contexto);

    	builder.setTitle(titulo);
    	builder.setMessage(mensaje);
    	
    	builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int id) {
                // User clicked OK button
            	a.finish();
            }

        });
    		builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog          
            	a.finish();
            }
    		});
    	
    	AlertDialog alert = builder.create();
    	alert.show();		

	}
	//muestra dialogo con botones ok y cancel y fibixh en ambos
	void mostrarDialogoSalida(String titulo,String mensaje){

		AlertDialog.Builder builder = new AlertDialog.Builder(contexto);

    	builder.setTitle(titulo);
    	builder.setMessage(mensaje);
    	
    	builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int id) {
            	a.finish();
            }

        });
    		builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int id) {         
            	
            }
    		});
    	
    	AlertDialog alert = builder.create();
    	alert.show();		

	}

}
