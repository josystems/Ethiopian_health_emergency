package com.hackaton.ethiopianhealthemergency.qrcode;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;

import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.hackaton.ethiopianhealthemergency.R;
import com.hackaton.ethiopianhealthemergency.utils.Utils;
import com.hackaton.ethiopianhealthemergency.wizard.WizardMain;

public class QrCodeViewer extends AppCompatActivity {
    AppCompatImageView profilePic,qrCodeView;
    AppCompatButton editInfoBtn,lockScreenBtn;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qrcode_viewer);
        profilePic = findViewById(R.id.myprofile_pic);
        qrCodeView = findViewById(R.id.myprofile_qrcode);
        editInfoBtn = findViewById(R.id.myprofile_edit_btn);
        lockScreenBtn = findViewById(R.id.myprofile_lockscreen_btn);
        final Bitmap qr = getQRCode();
        if (qr!=null)
        qrCodeView.setImageBitmap(qr);
        editInfoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(QrCodeViewer.this,WizardMain.class).putExtra("force",true));
            }
        });
        lockScreenBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.setLockScreenWallpaper(QrCodeViewer.this,qr);
            }
        });
    }
    private Bitmap getQRCode() {
        try {
            String data = "error";
            JsonUtil util = new JsonUtil(this);
            String json = util.exportUserInfo().toString();
            if (json!=null)
                data = json;
            return QrCodeHelper.getInstance(this)
                    .setMargin(2)
                    .setErrorCorrectionLevel(ErrorCorrectionLevel.L)
                    .setContent(data).getQRCode();
        }catch (Exception e){
            Toast.makeText(this, "Error "+e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    return null;
    }
}
