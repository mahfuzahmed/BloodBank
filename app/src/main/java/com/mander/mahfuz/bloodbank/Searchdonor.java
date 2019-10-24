package com.mander.mahfuz.bloodbank;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;


import com.google.android.gms.maps.MapView;

import java.util.ArrayList;
import java.util.List;

public class Searchdonor extends AppCompatActivity implements AdapterView.OnItemSelectedListener
{

    Button finddonor;

    Spinner setbg, setloc;


    String bg, loc;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchdonor);


        //buttons
        finddonor = findViewById(R.id.finddonor);


        //spinners
        setbg = findViewById(R.id.setbg);
        setbg.setOnItemSelectedListener(this);

        setloc = findViewById(R.id.setloc);
        setloc.setOnItemSelectedListener(this);


        List<String> bgs = new ArrayList<String>();
        bgs.add("A+");
        bgs.add("A-");
        bgs.add("B+");
        bgs.add("B-");
        bgs.add("O+");
        bgs.add("O-");
        bgs.add("AB+");
        bgs.add("AB-");

        List<String> locs = new ArrayList<String>();
        locs.add("Dhanmondi");
        locs.add("Gulshan");
        locs.add("Uttara");
        locs.add("Banani");
        locs.add("Mirpur");
        locs.add("Motijheel");
        locs.add("Badda");
        locs.add("Lalmatia");
        locs.add("Baridhara");


        //arrayadapters
        ArrayAdapter<String> bgsadapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, bgs);
        ArrayAdapter<String> locsadapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, locs);



        bgsadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        locsadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        setbg.setAdapter(bgsadapter);
        setloc.setAdapter(locsadapter);


        setbg.setOnItemSelectedListener(this);
        setloc.setOnItemSelectedListener(this);

        bg = setbg.getSelectedItem().toString();
        loc = setloc.getSelectedItem().toString();

        finddonor.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(Searchdonor.this, Donorlist.class);
                Bundle data = new Bundle();
                data.putString("Bundlebg", setbg.getSelectedItem().toString());
                data.putString("Bundleloc", setloc.getSelectedItem().toString());
                i.putExtras(data);
                startActivity(i);

            }
        });

    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
    {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent)
    {

    }
}
