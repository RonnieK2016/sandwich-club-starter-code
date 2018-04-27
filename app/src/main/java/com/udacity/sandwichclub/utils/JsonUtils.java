package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.constants.JsonConstants;
import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class JsonUtils {

    private static final String TAG = "JSON_UTILS";

    private static List<String> jsonArrayToList(JSONArray jsonArray) throws JSONException {
        if(jsonArray == null || jsonArray.length() == 0) {
            return Collections.emptyList();
        }
        List<String> resultList = new LinkedList<>();
        for(int i = 0; i < jsonArray.length(); i++) {
            resultList.add(jsonArray.getString(i));
        }
        return resultList;
    }

    public static Sandwich parseSandwichJson(String json) {
        try {
            JSONObject rootObj = new JSONObject(json);
            JSONObject nameObj = rootObj.getJSONObject(JsonConstants.SANDWICH_NAME_TAG);
            String mainName = nameObj.getString(JsonConstants.SANDWICH_MAIN_NAME_FIELD);
            JSONArray alsoKnownAsArr = nameObj.getJSONArray(JsonConstants.SANDWICH_ALSO_KNOWN_AS_ARRAY);
            List<String> alsoKnownAsList = jsonArrayToList(alsoKnownAsArr);
            String placeOfOrigin = rootObj.getString(JsonConstants.SANDWICH_PLACE_OF_ORIGIN_FIELD);
            String description = rootObj.getString(JsonConstants.SANDWICH_DESCRIPTION_FIELD);
            String image = rootObj.getString(JsonConstants.SANDWICH_IMAGE_FIELD);
            JSONArray ingredientsArr = rootObj.getJSONArray(JsonConstants.SANDWICH_INGREDIENTS_ARRAY);
            List<String> ingredientsList = jsonArrayToList(ingredientsArr);
            return new Sandwich(mainName, alsoKnownAsList, placeOfOrigin, description, image, ingredientsList);
        }
        catch (JSONException io) {
            Log.d(TAG, "parseSandwichJson exception: ", io);
            return null;
        }

    }
}
