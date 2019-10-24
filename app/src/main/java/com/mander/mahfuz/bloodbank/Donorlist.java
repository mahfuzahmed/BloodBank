package com.mander.mahfuz.bloodbank;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Donorlist extends AppCompatActivity
{
    private class StableArrayAdapter extends ArrayAdapter<String>
    {

        HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

        public StableArrayAdapter(Context context, int textViewResourceId, List<String> objects)
        {
            super(context, textViewResourceId, objects);
            for (int i = 0; i < objects.size(); ++i)
            {
                mIdMap.put(objects.get(i), i);
            }
        }

        @Override
        public long getItemId(int position)
        {
            String item = getItem(position);
            return mIdMap.get(item);
        }

        @Override
        public boolean hasStableIds()
        {
            return true;
        }

    }

    ListView donors;

    Bundle inputs = getIntent().getExtras();
    String inputbg = inputs.getString("Bundlebg");
    String inputloc = inputs.getString("Bundleloc");

    TextView header;

    user u = new user();


    String[] values = new String[]{};
    String textsetter = "Donors available with "+ inputbg+" blood around"+inputloc;
    int  counter = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donorlist);

        donors = findViewById(R.id.list);
        header = findViewById(R.id.header);

        header.setText(textsetter);

        DatabaseReference blooddonors;

        blooddonors = FirebaseDatabase.getInstance().getReference();

        Query query = blooddonors.child("users").equalTo(inputbg).limitToFirst(50);

        query.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                for (DataSnapshot postsnapshot: dataSnapshot.getChildren())
                {
                    u = dataSnapshot.getValue(user.class);
                    String donorname = u.getfname() + u.getlname();
                    values[counter] = donorname;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        final ArrayList<String> list = new ArrayList<String>();

        int n=100;

        for(int i=0;i<n;i++)
        {
            list.add(values[i]);
        }

        final StableArrayAdapter adapter = new StableArrayAdapter(Donorlist.this,android.R.layout.simple_list_item_1, list);
        donors.setAdapter(adapter);

        donors.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @SuppressLint("NewApi")
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id)
            {
                final String item = (String) parent.getItemAtPosition(position);
                view.animate().setDuration(2000).alpha(0).withEndAction(new Runnable()
                {
                            @Override
                            public void run()
                            {
                                list.remove(item);
                                adapter.notifyDataSetChanged();
                                view.setAlpha(1);
                            }
                });
                Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.android.phone");
                Bundle b = new Bundle();
                b.putString("cell", u.getcell());
                launchIntent.putExtras(b);

                startActivity(launchIntent);//null pointer check in case package name was not found

            }
        });


    }

}
