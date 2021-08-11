package sg.edu.rp.c346.id20008460.oursingapor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cursoradapter.widget.CursorAdapter;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;


public class ShowIsland extends AppCompatActivity {


    Button btnFiveStars;

    ListView lvSong;
    //ArrayAdapter<Song> aa ;
    CustomAdapter customAdapter;
    ArrayAdapter<Integer> aaKm; // store all the years
    ArrayList<Island> alIsland  , alDuplicate ;
    ArrayList<Integer> alKm ;
    Spinner spnKm;
    HashSet<Integer> hs;

    //
    private String filteredYear;
    private int intFilterKm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_island);

        btnFiveStars = findViewById(R.id.button5stars);
        lvSong = findViewById(R.id.lvSongs);
        spnKm = findViewById(R.id.spinner);

        DBHelper dbh = new DBHelper(ShowIsland.this);

        alIsland = new ArrayList<Island>();
        alDuplicate = new ArrayList<Island>();
        alKm = new ArrayList<Integer>();

        alIsland.addAll(dbh.getAllIsland());

        for (int i = 0; i < alIsland.size(); i++) {
            alKm.add(alIsland.get(i).getKm());
        }

        Collections.sort(alKm);


        hs = new HashSet();
        hs.addAll(alKm);

        alKm.clear();
        alKm.addAll(hs);

        customAdapter = new CustomAdapter(ShowIsland.this, R.layout.row , alIsland);

        aaKm = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, alKm);
        spnKm.setAdapter(aaKm);

        lvSong.setAdapter(customAdapter);

        lvSong.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int
                    position, long identity) {
                Island data = alIsland.get(position);
                Intent i = new Intent(ShowIsland.this,
                        ModifyIsland.class);

                float starValue = alIsland.get(position).getStars();
                i.putExtra("data", data);
                startActivity(i);
            }
        });

        btnFiveStars.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbh = new DBHelper(ShowIsland.this);
                alIsland.clear();
                alDuplicate.clear();
                int keyword = 5;


                alDuplicate.addAll(dbh.getAllIsland(keyword));

                for (int i = 0 ; i < alDuplicate.size(); i++) {
                    if (alDuplicate.get(i).getKm() == intFilterKm && alDuplicate.get(i).getStars() == 5) {
                        alIsland.add(alDuplicate.get(i));
                    }
                }


                customAdapter.notifyDataSetChanged();
            }
        });

        spnKm.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Spinner mySpinner = (Spinner) findViewById(R.id.spinner);
                String text = mySpinner.getSelectedItem().toString(); // get the year of the spinner selected
//                Toast.makeText(getApplicationContext(), position+1 + " : " + text , Toast.LENGTH_LONG).show();

                int yearCount = spnKm.getAdapter().getCount(); // how many unique years there are
                SharedPreferences prefs = getPreferences(MODE_PRIVATE);
                SharedPreferences.Editor prefeditor = prefs.edit();

                prefeditor.putInt("spnIndex" , position);


                int year = Integer.parseInt(text); // change it into int (year)
                DBHelper dbh = new DBHelper(ShowIsland.this);
                int keyword = year;

                for (int i = 0 ; i < yearCount ; i ++) {
                    if (position == i) {
                        alIsland.clear();
                        alIsland.addAll(dbh.filterYear(keyword));
                        filteredYear = Integer.toString(keyword);
                        intFilterKm = keyword;

                    }
                }

                prefeditor.putInt("year" , keyword );
                customAdapter.notifyDataSetChanged();
                prefeditor.commit();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences prefs = getPreferences(MODE_PRIVATE);
        int x = prefs.getInt("spnIndex" , 0);
        int year = prefs.getInt("year" , 0);

        spnKm.setSelection(x); // let it remain at the spinner selection

        alIsland.clear();
        alDuplicate.clear();
        alKm.clear();

        DBHelper dbh = new DBHelper(ShowIsland.this);
        alIsland.addAll(dbh.getAllIsland());


        // to retrieve song years
        for (int i = 0 ; i < alIsland.size() ; i ++) {
            alKm.add(alIsland.get(i).getKm());
        }


        // To store results that contains the year in a duplciate array
        for (int t = 0 ; t < alIsland.size() ; t ++) {
            if (alIsland.get(t).getKm()  == year) {
                alDuplicate.add(alIsland.get(t));
            }
        }
        alIsland.clear();


        for (int h = 0 ; h < alDuplicate.size() ; h ++) {
            alIsland.add(alDuplicate.get(h));
        }


        alDuplicate.clear();
        customAdapter.notifyDataSetChanged();

        hs.clear();
        hs.addAll(alKm);

        alKm.clear();
        alKm.addAll(hs);

        Collections.sort(alKm);

        aaKm.notifyDataSetChanged();


    }



}