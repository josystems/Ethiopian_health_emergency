package com.hackaton.ethiopianhealthemergency.wizard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;

import com.hackaton.ethiopianhealthemergency.R;
import com.hackaton.ethiopianhealthemergency.utils.Utils;

public class LangSelect extends Fragment {
    RadioGroup rg1,rg2;
    AppCompatButton wizardNext;
    AppCompatTextView select_lang;
    RadioGroup.OnCheckedChangeListener listener1,listener2;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.lang_select,container,false);
        rg1 = view.findViewById(R.id.wizard_lang_r1);
        rg2 = view.findViewById(R.id.wizard_lang_r2);
        select_lang = view.findViewById(R.id.lang_title);
        wizardNext = view.findViewById(R.id.wizard_next);
        rg2.clearCheck();
        rg1.clearCheck();
         listener1 = new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i != -1) {
                    rg2.setOnCheckedChangeListener(null); // remove the listener before clearing so we don't throw that stackoverflow exception(like Vladimir Volodin pointed out)
                    rg2.clearCheck(); // clear the second RadioGroup!
                    rg2.setOnCheckedChangeListener(listener2); //reset the listener
                    Utils.loadLanguage(getContext(),i==R.id.wizard_lang_eng?"en":"am-rET");
                    loadResource();
                }
            }
        };
        listener2 = new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i != -1) {
                    rg1.setOnCheckedChangeListener(null);
                    rg1.clearCheck();
                    rg1.setOnCheckedChangeListener(listener1);
                    Utils.loadLanguage(getContext(),i==R.id.wizard_lang_oro?"om-rET":"ti-rET");
                    loadResource();
                }
            }
        };
        rg1.setOnCheckedChangeListener(listener1);
        rg2.setOnCheckedChangeListener(listener2);
        wizardNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WizardMainViewModel.setPagerPos(1);
            }
        });
        return view;
    }
    public void loadResource(){
     select_lang.setText(R.string.select_language);
    }
}
