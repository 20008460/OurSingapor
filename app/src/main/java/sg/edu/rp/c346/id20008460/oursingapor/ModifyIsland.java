package sg.edu.rp.c346.id20008460.oursingapor;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;

import java.util.ArrayList;

public class ModifyIsland extends AppCompatActivity {

    Button btnUpdate , btnDelete, btnCancel;
    EditText etTitle, etSinger , etYear;

    RatingBar rbStar;
    Island data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_island);

        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);
        btnCancel = findViewById(R.id.buttonCancel);
        rbStar = findViewById(R.id.ratingBar3);

        etTitle = findViewById(R.id.upTitle);
        etSinger = findViewById(R.id.upSingers);
        etYear = findViewById(R.id.upYear);


        Intent i = getIntent();
        data = (Island) i.getSerializableExtra("data");


        rbStar.setRating(data.getStars());
        etTitle.setText(data.getName());
        etSinger.setText(data.getDes());
        etYear.setText(Integer.toString(data.getKm()));


        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbh = new DBHelper(ModifyIsland.this);
                data.setName(etTitle.getText().toString());
                data.setDes(etSinger.getText().toString());
                data.setKm(Integer.parseInt(etYear.getText().toString()));

                float starRating = rbStar.getRating();

                data.setStars(starRating);

                dbh.updateIsland(data);
                dbh.close();

                finish();

            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DBHelper dbh = new DBHelper(ModifyIsland.this);

                AlertDialog.Builder myBuilder = new AlertDialog.Builder(ModifyIsland.this);

                myBuilder.setTitle("Danger");
                myBuilder.setMessage("Are you sure you want to delete the island " + data.getName());
                myBuilder.setCancelable(false);

                myBuilder.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dbh.deleteIsland(data.get_id());
                        finish();

                    }
                });

                myBuilder.setPositiveButton("Cancel" , null);

                AlertDialog myDialog = myBuilder.create();
                myDialog.show();



            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DBHelper dbh = new DBHelper(ModifyIsland.this);

                AlertDialog.Builder myBuilder = new AlertDialog.Builder(ModifyIsland.this);

                myBuilder.setTitle("Danger");
                myBuilder.setMessage("Are you sure you want to discard the changes " );
                myBuilder.setCancelable(false);

                myBuilder.setNegativeButton("Discard", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i = new Intent(ModifyIsland.this,
                                ShowIsland.class);
                        startActivity(i);

                    }
                });

                myBuilder.setPositiveButton("Do not discard" , null);

                AlertDialog myDialog = myBuilder.create();
                myDialog.show();

            }
        });

    }



}