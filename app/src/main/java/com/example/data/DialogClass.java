package com.example.data;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDialogFragment;

public class DialogClass extends AppCompatDialogFragment {
    private EditText name;
    private EditText age;
    private Spinner gender;
    private Button button;

    private DialogClassListener listener;

    private String[] values = {"Male", "Female"};

    private String username,userAge,userGender;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.login,null);

        name = view.findViewById(R.id.name);
        age = view.findViewById(R.id.age);
        gender = view.findViewById(R.id.gender);

        button = view.findViewById(R.id.submit_button);

        gender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                userGender = values[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        ArrayAdapter adp=new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_item,values);
        adp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gender.setAdapter(adp);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = name.getText().toString();
                userAge = age.getText().toString();
                if(username.isEmpty() || userAge.isEmpty()){
                    Toast.makeText(getContext(), "Fields cannot be empty", Toast.LENGTH_SHORT).show();
                }
                else {
                    listener.applyTexts(username, userAge, userGender);
                }
            }
        });



        builder.setView(view);

        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            listener = (DialogClassListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    "must implement ExampleDialogListener");
        }


    }



    public interface DialogClassListener{
        void applyTexts(String Username, String Userage, String Usergender);
    }
}
