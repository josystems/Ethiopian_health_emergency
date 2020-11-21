package com.hackaton.ethiopianhealthemergency.wizard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;

import com.hackaton.ethiopianhealthemergency.R;

public class BasicInfo extends Fragment {
    AppCompatEditText fullName,phone,address;
    AppCompatSpinner sex;
    AppCompatButton wizardNext;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.basic_info,container,false);
        fullName = view.findViewById(R.id.wizard_fullname);
        phone = view.findViewById(R.id.wizard_phone);
        address = view.findViewById(R.id.wizard_address);
        sex = view.findViewById(R.id.wizard_sex);
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item,getResources().getStringArray(R.array.spinner_sex));
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sex.setAdapter(spinnerAdapter);
        sex.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
             //TODO save gender
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        wizardNext = view.findViewById(R.id.wizard_next);
        wizardNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isValidInfo())
                    WizardMainViewModel.setPagerPos(2);
            }
        });
        return view;
    }
    private boolean isValidInfo(){
        //TODO check entered information validity
        return true;
    }
}
