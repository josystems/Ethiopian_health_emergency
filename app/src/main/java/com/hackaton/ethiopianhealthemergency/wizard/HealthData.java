package com.hackaton.ethiopianhealthemergency.wizard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.hackaton.ethiopianhealthemergency.R;
import com.hackaton.ethiopianhealthemergency.utils.Constant;
import com.hackaton.ethiopianhealthemergency.utils.SharedPref;

public class HealthData extends Fragment {
    AppCompatEditText weight, height, allergy;
    AppCompatTextView birthday;
    AppCompatSpinner bloodType;
    AppCompatButton wizardNext;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.health_data, container, false);
        weight = view.findViewById(R.id.wizard_weight);
        height = view.findViewById(R.id.wizard_height);
        birthday = view.findViewById(R.id.wizard_birth);
        bloodType = view.findViewById(R.id.wizard_blood);
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.spinner_blood_type));
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bloodType.setAdapter(spinnerAdapter);
        MaterialDatePicker.Builder<Long> builder = MaterialDatePicker.Builder.datePicker();
        builder.setTitleText("Select a date");
        final MaterialDatePicker<Long> materialDatePicker = builder.build();
        birthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                materialDatePicker.show(getParentFragmentManager(), "MATERIAL_DATE_PICKER");
            }
        });
        materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Object selection) {
                birthday.setText(materialDatePicker.getHeaderText());
            }
        });
        allergy = view.findViewById(R.id.wizard_allergy);
        wizardNext = view.findViewById(R.id.wizard_next);
        wizardNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isValidInfo()) {
                    WizardMainViewModel.setPagerPos(3);
                }
            }
        });
        return view;
    }

    private boolean isValidInfo() {
        boolean valid = true;
        if (weight.getText().length() < 1) {
            weight.setError("input valid weight");
            valid = false;
        }
        if (height.getText().length() < 1) {
            height.setError("input valid height");
            valid = false;
        }
        if (birthday.getText().length() < 2) {
            birthday.setError("input valid birth date");
            valid = false;
        }
        if (!valid) {
            saveHealthData();
        }
        return valid;
    }

    public void saveHealthData() {
        WizardMain.user.setWeight(weight.getText().toString());
        WizardMain.user.setHeight(height.getText().toString());
        WizardMain.user.setBloodType(bloodType.getSelectedItem().toString());
        WizardMain.user.setBirthDate(birthday.getText().toString());
        WizardMain.user.setAllergy(allergy.getText().toString());
        SharedPref pref = new SharedPref(getContext());
        pref.setString(Constant.PREF_USER_WEIGHT, weight.getText().toString());
        pref.setString(Constant.PREF_USER_HEIGHT, height.getText().toString());
        pref.setString(Constant.PREF_USER_BLOOD_TYPE, bloodType.getSelectedItem().toString());
        pref.setString(Constant.PREF_USER_BIRTHDAY, birthday.getText().toString());
        pref.setString(Constant.PREF_USER_ALLERGY, allergy.getText().toString());

    }
}
