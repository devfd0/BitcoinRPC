/*
 * Basic no frills app which integrates the ZBar barcode scanner with
 * the camera.
 * 
 * Created by lisah0 on 2012-02-24
 */
package com.devfd0.bitcoinrpc;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.Button;

import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.hardware.Camera.PreviewCallback;
import android.hardware.Camera.AutoFocusCallback;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.Size;

import android.widget.TextView;
import android.graphics.ImageFormat;

/* Import ZBar Class files */
import net.sourceforge.zbar.ImageScanner;
import net.sourceforge.zbar.Image;
import net.sourceforge.zbar.Symbol;
import net.sourceforge.zbar.SymbolSet;
import net.sourceforge.zbar.Config;

public class CameraActivity extends Activity
{
    
	private Camera mCamera;
    private CameraPreview mPreview;
    private Handler autoFocusHandler;
    String dir = "";    
    TextView scanText;
    Button scanButton;

    private ImageScanner scanner;

    private boolean barcodeScanned = false;
    private boolean previewing = true;

    static {
        System.loadLibrary("iconv");
    } 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.camarascan);
        Dialogo d = new Dialogo(this,this);
        

        autoFocusHandler = new Handler();
        mCamera = getCameraInstance();
        if (!existeCamara()){
        	d.mostrarDialogoOK(getString(R.string.aviso), getString(R.string.camaraNoDisponible));
        }
        else
        {
        	setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        /* Instance barcode scanner */
        scanner = new ImageScanner();
        scanner.setConfig(0, Config.X_DENSITY, 3);
        scanner.setConfig(0, Config.Y_DENSITY, 3);

        mPreview = new CameraPreview(this, mCamera, previewCb, autoFocusCB);
        FrameLayout preview = (FrameLayout)findViewById(R.id.cameraPreview);
        preview.addView(mPreview);

        scanText = (TextView)findViewById(R.id.scanText);

        scanButton = (Button)findViewById(R.id.ScanButton);

        scanButton.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    if (barcodeScanned) {
                        barcodeScanned = false;
                        scanText.setText("Scanning...");
                        mCamera.setPreviewCallback(previewCb);
                        mCamera.startPreview();
                        previewing = true;
                        mCamera.autoFocus(autoFocusCB);
                    }
                }
        
            });
        }
    }
    @SuppressWarnings("static-access")
	public boolean existeCamara(){
    	if (isCameraAvailable()== false)
    		return false;    	

		PackageManager packageManager = this.getPackageManager(); 
		if (!packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
			return false;
		}		
        int numCam = 0;
        numCam =mCamera.getNumberOfCameras(); 
        if (numCam>0)
        {
        	while (numCam > 0) {
        	       CameraInfo cInfo = new CameraInfo();
        	       numCam--;
        	       Camera.getCameraInfo(numCam, cInfo);
        	       if (cInfo.facing == cInfo.CAMERA_FACING_FRONT) {
        	    	   return true;
        	        
        	       }
        	}
        	
        }
        else
        {
        	return false;
        }        
        return true;
    }
    public boolean isCameraAvailable() {
    	PackageManager pm = getPackageManager();
    		return pm.hasSystemFeature(PackageManager.FEATURE_CAMERA);
    	}
    
    public void onPause() {
        super.onPause();
        releaseCamera();
    }

    /** A safe way to get an instance of the Camera object. */
    public static Camera getCameraInstance(){
        Camera c = null;
        try {
            c = Camera.open();
        } catch (Exception e){
        }
        return c;
    }

    private void releaseCamera() {
        if (mCamera != null) {
            previewing = false;
            mCamera.setPreviewCallback(null);
            mCamera.release();
            mCamera = null;
        }
    }

    private Runnable doAutoFocus = new Runnable() {
            public void run() {
                if (previewing)
                    mCamera.autoFocus(autoFocusCB);
            }
        };

    PreviewCallback previewCb = new PreviewCallback() {
            public void onPreviewFrame(byte[] data, Camera camera) {
                Camera.Parameters parameters = camera.getParameters();
                Size size = parameters.getPreviewSize();

                Image barcode = new Image(size.width, size.height, "Y800");
                barcode.setData(data);

                int result = scanner.scanImage(barcode);
                
                if (result != 0) {
                    previewing = false;
                    mCamera.setPreviewCallback(null);
                    mCamera.stopPreview();
                    
                    SymbolSet syms = scanner.getResults();
                    for (Symbol sym : syms) {
                    	dir = sym.getData();
                        //scanText.setText("barcode result " + sym.getData());                        
                        barcodeScanned = true;
                        
                        finish();
                    }
                }
            }
        };

    // Mimic continuous auto-focusing
    AutoFocusCallback autoFocusCB = new AutoFocusCallback() {
            public void onAutoFocus(boolean success, Camera camera) {
                autoFocusHandler.postDelayed(doAutoFocus, 1000);
            }
        };
        @Override
        public void finish() {
          // Prepare data intent 
          Intent data = new Intent();
          data.putExtra("code", dir);          
          // Activity finished ok, return the data
          setResult(RESULT_OK, data);
          super.finish();
        } 
}
