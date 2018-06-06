package com.devfd0.bitcoinrpc;


import java.util.Calendar;
import java.util.GregorianCalendar;


import com.devfd0.bitcoinrpc.cobj.CResultadogetwalletinfo;
import com.devfd0.bitcoinrpc.cobj.Cinfo;
import com.devfd0.bitcoinrpc.cobj.ResultadoInfo;
import com.google.gson.Gson;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;



public class MainActivity extends Activity  implements AsyncResponse {

    private static Context context;
    private static MainActivity instance;
    TextView verBalance = null, verVersion = null, verConexions = null,verFecha = null,verServidor = null;
    Button Actualizar = null,detener = null,opciones = null;
    ConfiguracionFile ini = null;
    CCliente a;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        instance = this;
        setContentView(R.layout.activity_main);
        verBalance =  (TextView)findViewById(R.id.muestraBalance);
        verVersion =  (TextView)findViewById(R.id.TextView01);
        verConexions =  (TextView)findViewById(R.id.TextView02);
        verFecha = (TextView)findViewById(R.id.TextView03);
        verServidor = (TextView)findViewById(R.id.TextView06);
        Actualizar = (Button)findViewById(R.id.actualizar);
        opciones = (Button)findViewById(R.id.opciones);
        detener = (Button)findViewById(R.id.detener);
        ini = new ConfiguracionFile("ini",this.getApplicationContext());
        cargarDatos();
        Actualizar.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                conectar();
            }
        });
        opciones.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                openOptionsMenu();
            }
        });
        detener.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                detener();
            }
        });

    }
    public static Context getAppContext() {
        return MainActivity.context;
    }
    void cargarDatos(){
        String valor = "";
        verFecha.setText(ini.getvalor("fecha", "No data"));
        verBalance.setText(ini.getvalor("balance", "No data"));
        verVersion.setText(ini.getvalor("version", "Sin carga"));
        verConexions.setText(ini.getvalor("conexiones", "Sin carga"));
        valor = ini.getvalor("url", "http://localhost:8332");
        ini.setValor("url", valor);//lo guardamos en el fichero
        verServidor.setText(valor);
        valor = ini.getvalor("pass", "password");
        ini.setValor("pass", valor);//lo guardamos en el fichero
        valor = ini.getvalor("nombre", "usuario");
        ini.setValor("nombre", valor);//lo guardamos en el fichero
    }
    void guardarDatos(){
        ini.setValor("fecha", getFecha());
        ini.setValor("balance", verBalance.getText().toString());
        ini.setValor("version", verVersion.getText().toString());
        ini.setValor("conexiones", verConexions.getText().toString());
    }
    String getFecha(){
        Calendar fecha = new GregorianCalendar();
        String anho =  String.valueOf(fecha.get(Calendar.YEAR));
        String mes = Utiles.rellenarCadena(String.valueOf(fecha.get(Calendar.MONTH)), 2, "0", false);
        String dia = Utiles.rellenarCadena(String.valueOf(fecha.get(Calendar.DAY_OF_MONTH)),2,"0",false);
        String hora = Utiles.rellenarCadena(String.valueOf(fecha.get(Calendar.HOUR_OF_DAY)),2,"0",false);
        String minuto = Utiles.rellenarCadena(String.valueOf(fecha.get(Calendar.MINUTE)),2,"0",false);

        return dia+"/"+mes+"/"+anho+" "+hora+":"+minuto;

    }
    @Override
    public void onBackPressed() {
        Dialogo d = new Dialogo(this,this);
        d.mostrarDialogoSalida(getString(R.string.salidaTitulo),getString(R.string.salida));
    }
    @Override
    public void finish() {
        super.finish();
    }

    void conectar(){
      //  a = new CCliente(this,"getinfo","",1);
        a = new CCliente(this,"getwalletinfo","",1);
        a.delegate = this;
        a.execute();


    }
    void detener(){
        a = new CCliente(this,"stop","",2);
        a.delegate = this;
        a.execute();


    }
    @Override
    public void onProcessFinish(String result, int id) {
        if(id == 1){
            if (result.contains("balance")) {
                Gson gson = new Gson();
                Cinfo obj= gson.fromJson(result, Cinfo.class);
                CResultadogetwalletinfo ii = obj.getResult();
                if (ii!=null){
                    if (result.contains(("balance")))
                        verBalance.setText(ii.getBalance().toString());
                        //verVersion.setText(ii.getVersion().toString());
                        //verConexions.setText(ii.getConnections().toString());
                        verFecha.setText(getFecha());
                        guardarDatos();
                }
                else
                {
                    Dialogo d = new Dialogo(this,this);
                    d.mostrarDialogoOKNoFinish(getString(R.string.error), result);
                }

            }
            else
            {
                Dialogo d = new Dialogo(this,this);
                d.mostrarDialogoOKNoFinish(getString(R.string.error), result);
            }


        }
    }

    @Override
    protected void onResume() {
        cargarDatos();
        super.onResume();
    }

    @Override
    public void onActionModeStarted(ActionMode mode) {
        cargarDatos();
        super.onActionModeStarted(mode);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.Enviar:
                Intent intente = new Intent();
                intente.setClass(this,enviar.class);
                startActivity(intente);
                return true;
            case R.id.lista:
                Intent intentt = new Intent();
                intentt.setClass(this,transaccciones.class);
                startActivity(intentt);
                return true;
            case R.id.mconfiguracion:
                Intent intent = new Intent();
                intent.setClass(this,Configuracion.class);
                startActivity(intent);
                return true;
            case R.id.salir:
                Dialogo d = new Dialogo(this,this);
                d.mostrarDialogoSalida(getString(R.string.salidaTitulo),getString(R.string.salida));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }




}