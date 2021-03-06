package com.example.cashbook;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;


public class DialogFragment extends BottomSheetDialogFragment implements DatePickerDialog.OnDateSetListener, View.OnClickListener {


    //DatePicker Dialog is Used here to implement DatePicker Dlalog but as we are note Currently using Date picker here
    //We will just left as it is.
    View view;
    int pos;
    dialogClicked activity;
    EditText etText;
    String name;
    String selectedDate;
    TextView dateText;
    TextView AddBookTag;
    Button dateBut, save;
    boolean val;





    @Override
    public void onClick(View view) {

    }


    public interface dialogClicked
    {
        public void onDialogPositiveClick(BottomSheetDialog dialog, EditText etText);
        public void onDialogNegativeClick(BottomSheetDialog dialog);
        public void onDateset(Button btn, TextView setDate);
        public void onDialogDeleteClick(BottomSheetDialog dialog);
        public void onDialogLangSet(BottomSheetDialog dialog);

    }

    public DialogFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.dialogue, container, false);
        MainActivity activity_main = (MainActivity) getActivity();
        val = activity_main.hideDeleteButton();
        return view;

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        activity = (dialogClicked) context;

    }



    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        BottomSheetDialog builder = new BottomSheetDialog(getActivity());
        LayoutInflater layoutInflater = getActivity().getLayoutInflater();

        View inflatedView = layoutInflater.inflate(R.layout.dialogue,null);
        builder.setContentView(inflatedView);
        //etText = (TextView) inflatedView.findViewById(R.id.etText);
        etText = (EditText) inflatedView.findViewById(R.id.etText);
        dateBut = inflatedView.findViewById(R.id.dataButton);
        dateText = inflatedView.findViewById(R.id.password);
        AddBookTag = inflatedView.findViewById(R.id.AddBookTag);
        Button save = builder.findViewById(R.id.Save);

        //It this builder.setView couldnot be used then we are unable to use anyitems on
        //the view and all they return is null or empty
        builder.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                BottomSheetDialog d = (BottomSheetDialog) dialogInterface;
                activity.onDialogLangSet(d);
                activity.onDateset(d.findViewById(R.id.dataButton), d.findViewById(R.id.password));
                d.findViewById(R.id.Save).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        pos =1;
                        activity.onDialogPositiveClick(builder,etText);
                        dismiss();
                    }
                });
                d.findViewById(R.id.Cancel).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        activity.onDialogNegativeClick(builder);
                        dismiss();
                    }
                });
                if(val) {
                    d.findViewById(R.id.Delete).setVisibility(View.VISIBLE);
                }
                else
                {
                    d.findViewById(R.id.Delete).setVisibility(View.GONE);

                }
                d.findViewById(R.id.Delete).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        activity.onDialogDeleteClick(builder);
                    }
                });
            }
        });


/*
        builder.setCancelable(false)
                .setPositiveButton(R.string.add, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        pos = 1;
                        activity.onDialogPositiveClick(DialogFragment.this,etText);



                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                pos = 2;
                activity.onDialogNegativeClick(DialogFragment.this);

            }

        });

        //getDialog().findViewById(R.id.etText);
        //Toast.makeText(getActivity(),getDialog().findViewById(R.id.etText),Toast.LENGTH_SHORT).show();
        //Books b1 = new Books(etText.getText().toString(), "dated");
        //ApplicationClass.book.add(b1);

 */

            return builder;

    }

    //THis can not be used here as selectDate is intialised in on Create Dialog and therfore can not be usedhere
    //Therefore we have used this in Main activity
    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        Calendar mcalender = Calendar.getInstance();
        mcalender.set(Calendar.YEAR, i);
        mcalender.set(Calendar.MONTH, i1);
        mcalender.set(Calendar.DAY_OF_MONTH, i2);
        selectedDate = DateFormat.getDateInstance(DateFormat.FULL).format(mcalender.getTime());
        dateText.setText(selectedDate);
    }

    public void setLanguageDialogFragment(String b)
    {
        AddBookTag.setText(b);
    }


}