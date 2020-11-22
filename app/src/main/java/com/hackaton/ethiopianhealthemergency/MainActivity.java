package com.hackaton.ethiopianhealthemergency;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.hackaton.ethiopianhealthemergency.model.Emergency;
import com.hackaton.ethiopianhealthemergency.qrcode.QrCodeScannerActivity;
import com.hackaton.ethiopianhealthemergency.qrcode.QrCodeViewer;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.view.LayoutInflater;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {
    CardView scanner,profile,emergency;
// hackaton v10 hk
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        scanner = findViewById(R.id.main_scanner);
        profile = findViewById(R.id.main_profile);
        emergency = findViewById(R.id.main_emergency);
        scanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, QrCodeScannerActivity.class));
            }
        });
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, QrCodeViewer.class));
            }
        });
        emergency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, EmergencyActivity.class));
            }
        });


    }
}