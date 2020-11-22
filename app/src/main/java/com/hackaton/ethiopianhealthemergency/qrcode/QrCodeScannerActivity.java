package com.hackaton.ethiopianhealthemergency.qrcode;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.hackaton.ethiopianhealthemergency.R;
import com.hackaton.ethiopianhealthemergency.model.Emergency;
import com.hackaton.ethiopianhealthemergency.model.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class QrCodeScannerActivity extends AppCompatActivity {
    ZXingScannerView scannerView;
    AppCompatTextView result, status;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qrcode_scanner);
        scannerView = findViewById(R.id.qr_scanner);
        result = findViewById(R.id.scanner_result_tv);
        List<BarcodeFormat> formatList = new LinkedList<>();
        formatList.add(BarcodeFormat.QR_CODE);
        scannerView.setFormats(formatList);
        scannerView.setFocusableInTouchMode(true);
        status = findViewById(R.id.scanner_status);
        if (Build.MANUFACTURER.equalsIgnoreCase("HUAWEI"))
            scannerView.setAspectTolerance(0.5f);

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Build.VERSION.SDK_INT >= 23)
            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.CAMERA}, 99);
                return;
            }
        scannerView.startCamera();
        scannerView.setResultHandler(resultHandler());

    }

    public ZXingScannerView.ResultHandler resultHandler() {
        return new ZXingScannerView.ResultHandler() {
            @Override
            public void handleResult(Result result) {
                if (result != null) {
                    new QrCodeScannerActivity.ScannerTask().execute(result.getText());

                } else {
                    status.setText("Error Try again!");
                }
            }
        };
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
            status.setText("Camera permission denied");
        } else {
            status.setText(("Camera permission Granted"));
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        scannerView.stopCamera();
    }
    private String getSpannableText(User user) {
        StringBuilder d =
                new StringBuilder("<h4>Basic Information </h4></br>" +
                        "<b>FullName : " + user.getFullName() + "</b></br>" +
                        "<b>Phone Number : " + user.getPhoneNumber() + "</b></br>" +
                        "<b>Address : " + user.getAddress() + "</b></br>" +
                        "<b>Sex : " + user.getSex() + "</b></br>" +
                        "<b>BirthDay : " + user.getBirthDate() + "</b></br>" +

                        "<h4>Medical Information </h4></br>" +
                        "<b>Weight : " + user.getWeight() + "</b></br>" +
                        "<b>Height : " + user.getHeight() + "</b></br>" +
                        "<b>Blood Type : " + user.getBloodType() + "</b></br>" +
                        "<b>Allergy : " + user.getAllergy() + "</b></br>");

        for (Emergency emergency : user.getEmergencies()) {
             d.append("<b>Name : ").append(emergency.getName()).append("</b></br>").append("Phone : ").append(emergency.getNumber()).append("</b></br>").append("Relation : ").append(emergency.getRelation()).append("</b></br>");
        }
        return d.toString();
    }

    class ScannerTask extends AsyncTask<String, String, User> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            status.setText("Scanning...");
        }

        @Override
        protected User doInBackground(String... strings) {
            String result = strings[0];
            User user = null;
            try {
                JsonUtil util = new JsonUtil(getApplicationContext());
                JSONObject object = new JSONObject(result);
                Looper.prepare();
                user = util.readUserInfoFromJson(result);
                if(user==null){
                    user = new User();
                    user.setFullName(result);
                }


            } catch (JSONException e) {
                return null;
            }
            return user;
        }

        @Override
        protected void onPostExecute(final User user) {
            super.onPostExecute(user);
            final Handler handler = new Handler();
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    if (user.getSex() != null) {
                        scannerView.stopCamera();
                        result.setText(getSpannableText(user));
                    } else {
                        result.setText(user.getFullName());
                        scannerView.stopCamera();
                        scannerView.startCamera();
                        scannerView.setResultHandler(resultHandler());
                        status.setText("Scanning...");
                    }
                }
            };
            handler.postDelayed(runnable, 2000);
        }
    }
}
