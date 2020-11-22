package com.hackaton.ethiopianhealthemergency.wizard;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.fragment.app.Fragment;

import com.hackaton.ethiopianhealthemergency.MainActivity;
import com.hackaton.ethiopianhealthemergency.R;
import com.hackaton.ethiopianhealthemergency.database.Database;
import com.hackaton.ethiopianhealthemergency.model.Emergency;
import com.hackaton.ethiopianhealthemergency.utils.Constant;
import com.hackaton.ethiopianhealthemergency.utils.SharedPref;

public class EmergencyContact extends Fragment {
    private static int I = 0;
    AppCompatEditText eName, ePhone;
    AppCompatImageButton pickContact;
    AppCompatSpinner relation;
    AppCompatButton wizardNext, addNew;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.emergency_contact, container, false);
        eName = view.findViewById(R.id.wizard_fullname);
        ePhone = view.findViewById(R.id.wizard_phone);
        pickContact = view.findViewById(R.id.wizard_pick_contact);
        relation = view.findViewById(R.id.wizard_relation);
        addNew = view.findViewById(R.id.wizard_emergency_add);
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.spinner_relation));
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        relation.setAdapter(spinnerAdapter);
        pickContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        addNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isValidInfo();
                eName.setText("");
                ePhone.setText("");
            }
        });
        wizardNext = view.findViewById(R.id.wizard_next);
        wizardNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isValidInfo()) {
                    Database database = new Database(getContext(),1);
                    try {
                        database.saveUserInfo(WizardMain.user);
                        new SharedPref(getContext()).setBool(Constant.PREF_FRIST_RUN, false);
                    }catch (Exception e){
                        Toast.makeText(getContext(), "Error "+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    startActivity(new Intent(getContext(), MainActivity.class));
                    if (getActivity()!=null)getActivity().finish();
                }
            }
        });
        return view;
    }

    private boolean isValidInfo() {
        //TODO check user input validity
        boolean valid = true;
        if (eName.getText().length() < 1) {
            eName.setError("input valid name");
            valid = false;
        }
        if (ePhone.getText().length() < 1) {
            ePhone.setError("input valid phone number");
            valid = false;
        }
        if (valid) {
            saveEmergency(I++);
            if (I > 1)
                addNew.setEnabled(false);
        }
        return valid;
    }

    private void saveEmergency(int i) {
        SharedPref pref = new SharedPref(getContext());
        Database db = new Database(getContext(),1);
        Emergency emergency = new Emergency(eName.getText().toString(),ePhone.getText().toString(),relation.getSelectedItem().toString());
        db.addEmergencyUser(emergency);
        switch (i) {
            case 1:
                pref.setString(Constant.PREF_EMERGENCY2_NAME, eName.getText().toString());
                pref.setString(Constant.PREF_EMERGENCY2_PHONE, ePhone.getText().toString());
                pref.setString(Constant.PREF_EMERGENCY2_RELATION, relation.getSelectedItem().toString());
                break;
            case 0:
                pref.setString(Constant.PREF_EMERGENCY1_NAME, eName.getText().toString());
                pref.setString(Constant.PREF_EMERGENCY1_PHONE, ePhone.getText().toString());
                pref.setString(Constant.PREF_EMERGENCY1_RELATION, relation.getSelectedItem().toString());
            default:
                pref.setString(Constant.PREF_EMERGENCY3_NAME, eName.getText().toString());
                pref.setString(Constant.PREF_EMERGENCY3_PHONE, ePhone.getText().toString());
                pref.setString(Constant.PREF_EMERGENCY3_RELATION, relation.getSelectedItem().toString());
                break;

        }
    }
}