package sg.edu.rp.c346.id20008460.oursingapor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter {

    Context parent_context;
    int layout_id;
    ArrayList<Island> versionList;

    public CustomAdapter(Context context, int resource, ArrayList<Island> objects) {
        // resource = the resource u created (aka row.xml)

        super(context, resource, objects);

        parent_context = context;
        layout_id = resource;
        versionList = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        // Obtain the LayoutInflater object
        LayoutInflater inflater = (LayoutInflater) parent_context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // "Inflate" the View for each row
        View rowView = inflater.inflate(layout_id, parent, false);

        // Obtain the UI components and do the necessary binding
        TextView tvTitle = rowView.findViewById(R.id.textViewTitle);
        //TextView tvStar = rowView.findViewById(R.id.textViewStar);
        TextView tvYear = rowView.findViewById(R.id.textViewYear);
        TextView tvSinger = rowView.findViewById(R.id.textViewSinger);
        ImageView ivNew = rowView.findViewById(R.id.imageViewNew);

        RatingBar rbStar = rowView.findViewById(R.id.ratingBar);
        // Obtain the Android Version information based on the position
        Island currentVersion = versionList.get(position);

        // Set values to the TextView to display the corresponding information
        tvTitle.setText(currentVersion.getName());
        tvYear.setText(Integer.toString(currentVersion.getKm()));
        //tvStar.setText(currentVersion.toString());
        tvSinger.setText(currentVersion.getDes());
        rbStar.setRating(currentVersion.getStars());

        int year = currentVersion.getKm();
        if (year >= 2019) {
            ivNew.setImageResource(R.drawable.newsong);
        } else {
            ivNew.setVisibility(View.INVISIBLE);
        }

        return rowView;


    }


}
