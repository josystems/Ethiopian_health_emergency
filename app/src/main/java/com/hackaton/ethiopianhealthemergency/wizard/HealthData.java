package com.hackaton.ethiopianhealthemergency.wizard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class HealthData extends Fragment {
    AppCompatEditText weight,height,allergy;
    AppCompatTextView birthday;
    AppCompatSpinner bloodType;
    AppCompatButton wizardNext;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.health_data,container,false);
        weight = view.findViewById(R.id.wizard_weight);
        height = view.findViewById(R.id.wizard_height);
        birthday = view.findViewById(R.id.wizard_birth);
        bloodType = view.findViewById(R.id.wizard_blood);
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item,getResources().getStringArray(R.array.spinner_blood_type));
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bloodType.setAdapter(spinnerAdapter);
        allergy = view.findViewById(R.id.wizard_allergy);
        wizardNext = view.findViewById(R.id.wizard_next);
        wizardNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isValidInfo()){
                    WizardMainViewModel.setPagerPos(3);
                }
            }
        });
        return view;
    }
    private boolean isValidInfo(){
        //TODO check user input validity
        return true;
    }
}