package com.example.cashbook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements cashBookAdapter.ItemClicked, DialogFragment.dialogClicked, DatePickerDialog.OnDateSetListener {

    Button btnAddBook;
    FragmentManager fragmentManager;
    ListFrag lfrag;
    DialogFragment dfrag;
    String name;
    String tag;
    Button datebutton;
    TextView setDate;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        fragmentManager = this.getSupportFragmentManager();
        lfrag = (ListFrag) fragmentManager.findFragmentById(R.id.list);
        dfrag = (DialogFragment) fragmentManager.findFragmentById(R.id.Dialog);

        btnAddBook = findViewById(R.id.btnAddBook);
        btnAddBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //showNoticeDialog();
                //Toast.makeText(MainActivity.this, "Successful",Toast.LENGTH_SHORT).show();
                showNoticeDialog();




                //callDate(datebutton);
                //createBook(name);



            }
        });



    }

    @Override
    public void onItemClicked(int index) {
        Intent intent = new Intent(MainActivity.this, com.example.cashbook.NoteBookDetails.class);
        Intent.
        startActivity(intent);

    }

    public void showNoticeDialog() {
        // Create an instance of the dialog fragment and show it
        DialogFragment dialog = new DialogFragment();
        dialog.show(getSupportFragmentManager(), "NoticeDialogFragment");
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog, EditText etText) {
        //One Way to get the dialog values is to use this Dialog.getDialog where dialog is an instance
        //of dialog fragment we have created and passed to from there only.

        //Second method is to pass the text which was written there and use it here by passing as
        //Here

        //But this one is the best way to do so.
        Dialog dialogView = dialog.getDialog();
        EditText et = (EditText) dialogView.findViewById(R.id.etText);
        name = (String) et.getText().toString();

        //Add Create NoteBook Here oftherwise it will be created after clicking Add Notebook twice
        // also add notifyDatachange in createBook after data being added for the same reason
        if(name != null && setDate != null)
        {
            createBook(name, setDate.getText().toString());
        }
        //Here we have used the setDate parameter to set the date of what we taken input from the date



    }


    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {

    }

    //This is my Function not inbuild function to pass the date
    //created in dialog fragment and took the dialog view items and pass them in Here
    // this on button can be created right above the positive click button in dialogfragment
    //But the view can not be set on selected date as "onDataSet" This one and not belowone can
    //not be used on dialog fragement in onCreate Dialog. Therefore it is better to pass the
    //Button via function and use it here


    //My Function
    @Override
    public void onDateset(Button btn, TextView setDate) {
        datebutton = btn;
        this.setDate =setDate;
        datebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(MainActivity.this, "Yes", Toast.LENGTH_SHORT).show();
                com.example.cashbook.DatePicker mdate = new com.example.cashbook.DatePicker();
                mdate.show(getSupportFragmentManager(),"Select Date");



            }
        });


    }


    public void createBook(String name, String tag)
    {
        Books b1 = new Books(name, tag);
        ApplicationClass.book.add(b1);
        lfrag.notifyChange();

    }



    @Override
    public void onDateSet(android.widget.DatePicker datePicker, int i, int i1, int i2) {
        Calendar mcalender = Calendar.getInstance();
        mcalender.set(Calendar.YEAR, i);
        mcalender.set(Calendar.MONTH, i1);
        mcalender.set(Calendar.DAY_OF_MONTH, i2);
        String selectedDate = DateFormat.getDateInstance(DateFormat.FULL).format(mcalender.getTime());
        setDate.setText(selectedDate);

    }
}