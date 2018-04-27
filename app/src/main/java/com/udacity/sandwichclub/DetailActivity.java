package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

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
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        TextView mainNameView = findViewById(R.id.main_name_tv);
        TextView mainNameLabelView = findViewById(R.id.main_name_label_tv);
        displayOrHideView(mainNameLabelView, mainNameView, sandwich.getMainName());

        TextView originView = findViewById(R.id.origin_tv);
        TextView originLabelView = findViewById(R.id.detail_place_of_origin_label_tv);
        displayOrHideView(originLabelView, originView, sandwich.getPlaceOfOrigin());

        TextView descriptionView = findViewById(R.id.description_tv);
        TextView descriptionLabelView = findViewById(R.id.detail_description_label_tv);
        displayOrHideView(descriptionLabelView, descriptionView, sandwich.getDescription());

        TextView ingredientsView = findViewById(R.id.ingredients_tv);
        TextView ingredientsLabelView = findViewById(R.id.detail_ingredients_label_tv);
        displayOrHideView(ingredientsLabelView, ingredientsView, listToFormattedString(sandwich.getIngredients()));

        TextView alsoKnownAsLabelView = findViewById(R.id.detail_also_known_as_label_tv);
        TextView alsoKnownAsView = findViewById(R.id.also_known_tv);
        displayOrHideView(alsoKnownAsLabelView, alsoKnownAsView, listToFormattedString(sandwich.getAlsoKnownAs()));
    }

    private void displayOrHideView(TextView labelView, TextView infoView, String text) {
        if(text == null || text.length() == 0) {
            labelView.setVisibility(View.GONE);
            infoView.setVisibility(View.GONE);
        }
        else {
            infoView.setText(text);
        }
    }

    private String listToFormattedString(List<String> strings) {
        StringBuilder resultStr = new StringBuilder();
        int size = strings.size();
        for(String string : strings) {
            resultStr.append(string);
            size--;
            if(size > 0) {
                resultStr.append(", ");
            }
        }
        return resultStr.toString();
    }
}
