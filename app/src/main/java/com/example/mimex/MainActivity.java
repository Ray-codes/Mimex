package com.example.mimex;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.MenuItem;
import android.view.SurfaceView;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.CvType;
import org.opencv.core.Mat;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

//implements NavigationView.OnNavigationItemSelectedListener
public class MainActivity extends AppCompatActivity implements CameraBridgeViewBase.CvCameraViewListener2, NavigationView.OnNavigationItemSelectedListener {
    static {
        if(OpenCVLoader.initDebug()){
            Log.d("MainActivity: ","Opencv is loaded");
        }
        else {
            Log.d("MainActivity: ","Opencv failed to load");
        }
    }



    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    private Mat mRgba;
    private Mat mGray;

    private signLanguageClass signLanguageClass;
    private Button clear_button,add_button,tts_button,space_button;
    private TextView change_text;
    private TextToSpeech textToSpeech;

    private CameraBridgeViewBase mOpenCvCameraView;

    private BaseLoaderCallback mLoaderCallback =new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            switch (status){
                case LoaderCallbackInterface
                        .SUCCESS:{
                    Log.i("MainActivity","OpenCv Is loaded");
                    mOpenCvCameraView.enableView();
                }
                default:
                {
                    super.onManagerConnected(status);

                }
                break;
            }
        }
    };

    public MainActivity(){
        Log.i("MainActivity","Instantiated new "+this.getClass());
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        int MY_PERMISSIONS_REQUEST_CAMERA=0;
        // if camera permission is not given it will ask for it on device
        if (ContextCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(MainActivity.this, new String[] {Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_CAMERA);
        }
        setContentView(R.layout.activity_main);
        //opencv camera
        mOpenCvCameraView=(CameraBridgeViewBase) findViewById(R.id.frame_Surface1);
        mOpenCvCameraView.setVisibility(SurfaceView.VISIBLE);
        mOpenCvCameraView.setCameraPermissionGranted();
        mOpenCvCameraView.setCvCameraViewListener(this);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigationView);


        //tool bar modification
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigationOpen, R.string.navigationClose);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.hamburger);

        // navigation menu
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);

        clear_button = findViewById(R.id.clear_button);
        add_button = findViewById(R.id.add_button);
        change_text = findViewById(R.id.change_text);
        tts_button = findViewById(R.id.tts_button);
        space_button = findViewById(R.id.space_button);

        textToSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if(i!= TextToSpeech.ERROR){
                    textToSpeech.setLanguage(Locale.ENGLISH);
                }
            }
        });

        try{

            signLanguageClass = new signLanguageClass(space_button,textToSpeech,tts_button,clear_button,add_button,change_text,getAssets(),"hand_model.tflite","custom_label.txt",300,"Sign_language_recognition.tflite",96);
            Log.d("MainActivity","Model is successfully loaded");
        }
        catch (IOException e){
            Log.d("MainActivity","Getting some error");
            e.printStackTrace();
        }


    }
    protected void onResume() {
        super.onResume();
        if (OpenCVLoader.initDebug()){
            //if load success
            Log.d("MainActivity","Opencv initialization is done");
            mLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);
        }
        else{
            //if not loaded
            Log.d("MainActivity","Opencv is not loaded. try again");
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION,this,mLoaderCallback);
        }
    }
    protected void onPause() {
        super.onPause();
        if (mOpenCvCameraView !=null){
            mOpenCvCameraView.disableView();
        }
    }
    public void onDestroy(){
        super.onDestroy();
        if(mOpenCvCameraView !=null){
            mOpenCvCameraView.disableView();
        }

    }
    public void onCameraViewStarted(int width ,int height){
        mRgba=new Mat(height,width, CvType.CV_8UC4);
        mGray =new Mat(height,width,CvType.CV_8UC1);
    }
    public void onCameraViewStopped(){
        mRgba.release();
    }
    public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame){
        mRgba=inputFrame.rgba();
        mGray=inputFrame.gray();
        // Before watching this video please watch previous video of loading tensorflow lite model

        // now call that function
        Mat out=new Mat();
        out=signLanguageClass.recognizeImage(mRgba);

        return out;
    }

    //@Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){

            case R.id.signs_menu:
                startActivity(new Intent(MainActivity.this,signActivity.class));
            case R.id.translator_menu:
                startActivity(new Intent(MainActivity.this,translatorActivity.class));
            case R.id.guide_menu:
                startActivity(new Intent(MainActivity.this,guideActivity.class));
            case R.id.about_menu:
                startActivity(new Intent(MainActivity.this,aboutActivity.class));
        }
        return true;
    }


}