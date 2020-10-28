package com.example.security;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.SmsManager;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.app.PendingIntent;
import android.content.Intent;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class Main2Activity extends AppCompatActivity implements View.OnClickListener, SeekBar.OnSeekBarChangeListener, LocationListener, CompoundButton.OnCheckedChangeListener {
    private SeekBar seeker;
    private ToggleButton power;
    private Button contact;
    private TextView interval;
    private TextView latlon;
    private double lati,longti;
    private LocationListener ll;
    private LocationManager lm;
    private String prog="15";
    private Handler hand;
    private int inter=1;

    private Boolean poweron=false;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        latlon=(TextView)findViewById(R.id.loctext);
        seeker=(SeekBar)findViewById(R.id.seek);
        power=(ToggleButton)findViewById(R.id.power);
        contact=(Button)findViewById(R.id.contact);
        interval=(TextView)findViewById(R.id.interval);
        seeker.setOnSeekBarChangeListener(this);
        contact.setOnClickListener(this);
        power.setOnCheckedChangeListener(this);

        try {
            lm=(LocationManager)getSystemService(Context.LOCATION_SERVICE);
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
            Location location= lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if(location!=null) {
                lati = (float) location.getLatitude();
                longti = (float) location.getLongitude();
                latlon.setText("Latitude    " + (float) location.getLatitude() + "\nLongtitude " + (float) location.getLongitude());
            }
            else{
                latlon.setText("returns NULL");
            }
        }
        catch(SecurityException sc)
        {
            latlon.setText("no permission");
        }
    }
    @Override
    public void onLocationChanged(Location location) {
        lati=(float)location.getLatitude();
        longti=(float)location.getLongitude();
        latlon.setText("Latitide:"+(float)location.getLatitude()+"\nLongtitude:"+(float)location.getLongitude()+"\nAltitude:"+(float)location.getAltitude());


    }

    @Override
    public void onClick(View v)  {
        // do something when the button is clicked



    }
    public void sendmsg()
    {
        String mesg="";


        try{
            power.setText("STOP \n SECURITY");
            android.telephony.SmsManager sms=android.telephony.SmsManager.getDefault();
             mesg="latitude:"+lati+" "+"longtitude"+longti+". Im SAFE!";

            sms.sendTextMessage("08637655257",null,mesg,null,null);


        }
        catch (SecurityException e)
        {
            if(ContextCompat.checkSelfPermission(this,Manifest.permission.SEND_SMS)==PackageManager.PERMISSION_GRANTED)
            {
                latlon.setText("no sms permission");

            }

        }

    }
    private Runnable runnable=new Runnable() {
        @Override
        public void run() {
            sendmsg();
            if (power.isChecked()) {
                onCheckedChanged(power, true);
            }
            else{
            onCheckedChanged(power,false);}
        }
    };


    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }




    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if(seeker.getProgress()<1)
        {
            seeker.setProgress(1);
        }
        int x=seeker.getProgress();

        prog=String.valueOf(x);
        interval.setText(String.valueOf(x));
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(isChecked)
        {
            inter=Integer.parseInt(prog);
            sendmsg();


            Toast toast=Toast.makeText(getApplicationContext(),"Sending Security Message!",Toast.LENGTH_SHORT);
            toast.show();
            hand=new Handler();
            hand.postDelayed(runnable,1000*60*inter);





        }
        else{
            hand.removeCallbacksAndMessages(null);
            Toast toast=Toast.makeText(getApplicationContext(),"Stopping Services!",Toast.LENGTH_SHORT);
            toast.show();
        }

    }
}
