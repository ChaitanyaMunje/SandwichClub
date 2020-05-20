package com.chinmay.sandwichclub;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chinmay.sandwichclub.Model.SandwichData;
import com.chinmay.sandwichclub.Utils.JsonUtils;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Arrays;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    String also_known_as = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ImageView ingredientsIv = findViewById(R.id.image_iv);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        SandwichData sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {

            closeOnError();
            return;
        }

        populateUI(sandwich);

        Picasso.get()
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());

    }
    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(SandwichData sandwich) {

        TextView akaTextView = findViewById(R.id.also_known_tv);
        if(sandwich.getAlsoKnownAs() != null) {
            for (String alsoKnownAs : sandwich.getAlsoKnownAs()) {
                if(sandwich.getAlsoKnownAs() != null) {
                    also_known_as = also_known_as.concat(alsoKnownAs.concat("\n"));
                }
            }
            if (!also_known_as.equals("")) {
                akaTextView.setText(getResources().getString(R.string.detail_also_known_as_label)+" "+also_known_as.substring(0, also_known_as.lastIndexOf('\n')));

            }
        }

        TextView originTextView =findViewById(R.id.origin_tv);
        originTextView.setText(getResources().getString(R.string.detail_place_of_origin_label)+" "+sandwich.getPlaceOfOrigin());


        TextView ingredientsTextView = findViewById(R.id.ingredients_tv);
        String emptyIngredients = "";
        if(sandwich.getIngredients() != null) {
            for (String ingredient : sandwich.getIngredients()) {
                if(sandwich.getIngredients() != null) {
                    emptyIngredients = emptyIngredients.concat(ingredient.concat("\n"));
                }
            }
            if (!emptyIngredients.isEmpty()) {
                ingredientsTextView.setText(getResources().getString(R.string.detail_ingredients_label)+" "+emptyIngredients.substring(0, emptyIngredients.lastIndexOf('\n')));
            }
        }

        TextView descriptionTextView = findViewById(R.id.description_tv);
        descriptionTextView.setText(getResources().getString(R.string.detail_description_label)+" "+sandwich.getDescription());
    }
}
