package com.hackaton.ethiopianhealthemergency;

import android.app.PendingIntent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;

import com.hackaton.ethiopianhealthemergency.database.Database;
import com.hackaton.ethiopianhealthemergency.model.Emergency;
//
public class EmergencyActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.emergency_activity);
        CardView callAmbulance = findViewById(R.id.emergency_call_ambulance);
        CardView alertFamily = findViewById(R.id.emergency_call_family);
        CardView alertNearby = findViewById(R.id.emergency_nearby);
        AppCompatButton close = findViewById(R.id.emergency_close);
        callAmbulance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(EmergencyActivity.this, "Alerting nearby Health center (coming soon)", Toast.LENGTH_SHORT).show();
            }
        });
        alertFamily.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage("I AM IN BAD SITUATION I NEED HELP !!!");
            }
        });
        alertNearby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(EmergencyActivity.this, "Alerting nearby people (coming soon)", Toast.LENGTH_SHORT).show();
            }
        });
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    public void sendMessage(String text){
        Database db = new Database(this,1);
        SmsManager manager = SmsManager.getDefault();
        for (Emergency emergency : db.getEmergencyUser()) {
            if (emergency.getNumber()!=null)
           manager.sendTextMessage(emergency.getNumber(),"+251911299708",text,null,null);
        }
        if (db.getEmergencyUser().size()==0)
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, "Alert sent successfully", Toast.LENGTH_SHORT).show();
    }
}