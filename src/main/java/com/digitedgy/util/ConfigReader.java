package com.digitedgy.util;

import com.digitedgy.model.Config;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class ConfigReader {
    private static Config config = null;
    public static Config loadConfig(String file) throws Exception {
        Gson gson = new Gson();
        config = gson.fromJson(new InputStreamReader(new FileInputStream(new File(file))),Config.class);
        return config;
    }

    public static Config get() {
        return config;
    }
}
