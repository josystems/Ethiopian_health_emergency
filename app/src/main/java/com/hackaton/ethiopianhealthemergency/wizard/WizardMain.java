package com.hackaton.ethiopianhealthemergency.wizard;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.Observer;
import androidx.viewpager.widget.ViewPager;

import com.hackaton.ethiopianhealthemergency.MainActivity;
import com.hackaton.ethiopianhealthemergency.R;
import com.hackaton.ethiopianhealthemergency.custom_view.PageIndicator;
import com.hackaton.ethiopianhealthemergency.model.User;
import com.hackaton.ethiopianhealthemergency.utils.Constant;
import com.hackaton.ethiopianhealthemergency.utils.SharedPref;

public class WizardMain extends AppCompatActivity {
    public static User user = new User();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        SharedPref pref = new SharedPref(getApplicationContext());
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS, Manifest.permission.CAMERA}, 2);

        if (!pref.getBool(Constant.PREF_FRIST_RUN, true)) {
            if (!getIntent().getBooleanExtra("force", false)) {
                startActivity(new Intent(this, MainActivity.class));
                finish();
            }
        }
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        final ViewPager pager = findViewById(R.id.wizard_pager);
        final PageIndicator indicator = findViewById(R.id.wizard_pager_indicator);
        WizardFragmentAdapter adapter = new WizardFragmentAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        pager.setAdapter(adapter);
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                indicator.setCurrentPage(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        WizardMainViewModel.getPagerPos().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                pager.setCurrentItem(integer);
                Log.i("Wizard Main", "ViewModel Pager postion " + integer);
            }
        });
    }
}
