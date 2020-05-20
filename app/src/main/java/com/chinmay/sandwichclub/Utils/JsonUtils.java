package com.chinmay.sandwichclub.Utils;

import com.chinmay.sandwichclub.Model.SandwichData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    private static final String NAME_KEY="name";
    private static final String ALSO_KNOWN_AS="alsoKnownAs";
    private static final String MAIN_NAME_KEY="mainName";
    private static final String PLACE_OF_ORIGIN="placeOfOrigin";
    private static final String DESC_KEY="description";
    private static final String IMG_KEY="image";
    private static final String INGRE_KEY="ingredients";

    public static SandwichData parseSandwichJson(String json) {
        try {
            JSONObject sandwich_object = new JSONObject(json);
            JSONObject Nome = sandwich_object.getJSONObject(NAME_KEY);
            String mainName = Nome.getString(MAIN_NAME_KEY);
            JSONArray alsoKnownAsJsonArray = Nome.getJSONArray(ALSO_KNOWN_AS);
            List<String> alsoKnownAs = new ArrayList<>();
            for (int i = 0; i < alsoKnownAsJsonArray.length(); i++) {
                alsoKnownAs.add(alsoKnownAsJsonArray.getString(i));
            }
            String placeOfOrigin = sandwich_object.getString(PLACE_OF_ORIGIN);
            String description = sandwich_object.getString(DESC_KEY);
            String image = sandwich_object.getString(IMG_KEY);
            JSONArray jsonIngredientsArray = sandwich_object.getJSONArray(INGRE_KEY);
            List<String> ingredients = new ArrayList<>();
            for (int j = 0; j < jsonIngredientsArray.length(); j++) {
                ingredients.add(jsonIngredientsArray.getString(j));
            }

            return new SandwichData(mainName,alsoKnownAs,placeOfOrigin,description,image,ingredients);


        } catch (JSONException except) {
            except.printStackTrace();
            return null;

        }
    }
}
