package com.example.dialer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText edxnumber , edxmsg;
    Button btncall , btnmsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edxnumber = findViewById(R.id.edxnumber);
        edxmsg = findViewById(R.id.edxmsg);
        btncall = findViewById(R.id.btncall);
        btnmsg = findViewById(R.id.btnmsg);

        btnmsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
                        sendSMS();
                    } else {
                        requestPermissions(new String[]{Manifest.permission.SEND_SMS}, 1);
                    }
                }
            }
        });

        btncall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makecall();
            }
        });
    }

    private void sendSMS(){
        String MOBILE = edxnumber.getText().toString().trim();
        String SMS = edxmsg.getText().toString().trim();

        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(MOBILE, null, SMS, null, null);
            Toast.makeText(getApplicationContext(), "Message sent successfully", Toast.LENGTH_SHORT).show();

            edxnumber.setText("");
            edxmsg.setText("");
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Failed to sent..", Toast.LENGTH_SHORT).show();
        }


    }

    private void makecall() {

        Intent intentcall = new Intent(Intent.ACTION_CALL);

        String MOBILE = edxnumber.getText().toString();

        if (MOBILE.trim().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please enter mobile number", Toast.LENGTH_SHORT).show();
        }
        else {
            intentcall.setData(Uri.parse("tel:"+MOBILE));
        }
        if (ActivityCompat.checkSelfPermission(getApplicationContext(),Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED)
        {
            Toast.makeText(getApplicationContext(), "Please allow the permission ", Toast.LENGTH_SHORT).show();
            requestPermission();

        }
        else {
            startActivity(intentcall);
        }
    }
    private void requestPermission()        {
        ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.CALL_PHONE},1);
    }
}