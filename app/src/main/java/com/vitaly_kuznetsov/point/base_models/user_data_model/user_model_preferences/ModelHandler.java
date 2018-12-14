package com.vitaly_kuznetsov.point.base_models.user_data_model.user_model_preferences;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.vitaly_kuznetsov.point.R;
import com.vitaly_kuznetsov.point.base_models.user_data_model.model.UserDataModel;

public final class ModelHandler{

    private static UserDataModel model;
    private static Gson gson;
    private static SharedPreferences sharedPreferences;

    public static UserDataModel getInstance(Context context) {
        if (ModelHandler.model == null)
            ModelHandler.model = getUserDataModel(context);
        return ModelHandler.model;
    }

    private static UserDataModel getUserDataModel(Context context){
        sharedPreferences = context.getSharedPreferences
                (UserDataModel.PREFERENCES_FILE, Context.MODE_PRIVATE);

        gson = new Gson();
        String json;

        if (!sharedPreferences.contains(UserDataModel.PREFERENCES_DATA_STRING)) {
            UserDataModel userDataModel = new UserDataModel();
            json = changeUserDataModel(userDataModel);
        }
        else
            json = sharedPreferences.getString(UserDataModel.PREFERENCES_DATA_STRING, "Error");

        return gson.fromJson(json, UserDataModel.class);
    }

    public static String changeUserDataModel(UserDataModel userDataModel){
        String json = gson.toJson(userDataModel);

        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();

        prefsEditor.putString(UserDataModel.PREFERENCES_DATA_STRING, json);
        prefsEditor.apply();

        return json;
    }
}
