package com.hackaton.ethiopianhealthemergency.wizard;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import com.hackaton.ethiopianhealthemergency.R;
import com.hackaton.ethiopianhealthemergency.custom_view.PageIndicator;

public class WizardMain extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
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
                Log.i("Wizard Main","ViewModel Pager postion "+integer);
            }
        });
    }
}
