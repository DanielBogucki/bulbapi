package com.dbogucki.bulbapi.utils;

import com.dbogucki.bulbapi.enums.Category;
import com.dbogucki.bulbapi.enums.ResultType;
import com.dbogucki.bulbapi.results.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import java.lang.reflect.Type;

public class DataFormatter {
    public static Gson GSON = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

    public static Result generateResultFromJson(String data, ResultType type) throws JsonSyntaxException {
        Result result;
        switch (type) {
            case YEELIGHT_OK:
                result = GSON.fromJson(data, YeelightResultOk.class);
                break;
            case YEELIGHT_ERROR:
                result = GSON.fromJson(data, YeelightResultFailed.class);
                break;
            default:
                result = new NullResult();
        }

        return result;

    }
}
