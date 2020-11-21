package com.hackaton.ethiopianhealthemergency.wizard;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.fragment.app.Fragment;

import com.hackaton.ethiopianhealthemergency.MainActivity;
import com.hackaton.ethiopianhealthemergency.R;

public class EmergencyContact extends Fragment {
    AppCompatEditText eName,ePhone;
    AppCompatImageButton pickContact;
    AppCompatSpinner relation;
    AppCompatButton wizardNext;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.emergency_contact,container,false);
        eName = view.findViewById(R.id.wizard_fullname);
        ePhone = view.findViewById(R.id.wizard_phone);
        pickContact = view.findViewById(R.id.wizard_pick_contact);
        relation = view.findViewById(R.id.wizard_relation);
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item,getResources().getStringArray(R.array.spinner_relation));
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        relation.setAdapter(spinnerAdapter);
        wizardNext = view.findViewById(R.id.wizard_next);
        wizardNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isValidInfo()){
                   startActivity(new Intent(getContext(),MainActivity.class));
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