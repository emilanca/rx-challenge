package org.sample.rx.deals;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Helper {

    private static final Gson GSON = new GsonBuilder().create();

    public static <T> T readFromFile(String file, Class<T> expected) throws Exception {
        String jsonStr = Resources.toString(Resources.getResource(file), Charsets.UTF_8);
        return GSON.fromJson(jsonStr, expected);
    }
}
