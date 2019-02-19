package com.escalate.fontbind;


import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.util.Log;


import com.escalate.utils.MyApplication;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


/**
 *
 * Created by Rajeev Kr. Sharma [rajeevrocker7@gmail.com] on 27/6/18.
 *
 * A simple font cache that makes a font once when it's first asked for and keeps it for the
 * life of the application.
 * <p/>
 * To use it, put your fontbind in /assets/fontbind.  You can access them in XML by their filename, minus
 * the extension (e.g. "Roboto-BoldItalic" or "roboto-bolditalic" for Roboto-BoldItalic.ttf).
 * <p/>
 * To set custom names for fontbind other than their filenames, call addFont().
 */
public class FontCache {

    private static final String FONT_DIR = "fontbind";
    private static String TAG = "FontCache";
    private static Map<String, Typeface> cache = new HashMap<>();
    private static Map<String, String> fontMapping = new HashMap<>();
    private static FontCache instance;

    private FontCache() {
        AssetManager am = MyApplication.getInstance().getResources().getAssets();
        String fileList[];
        try {
            fileList = am.list(FONT_DIR);
        } catch (IOException e) {
            Log.e(TAG, "Error loading fontbind from assets/fontbind.");
            return;
        }

        for (String filename : fileList) {
            String alias = filename.substring(0, filename.lastIndexOf('.'));
            fontMapping.put(alias, filename);
            fontMapping.put(alias.toLowerCase(), filename);
        }
    }

    public static FontCache getInstance() {
        if (instance == null) {
            instance = new FontCache();
        }
        return instance;
    }

    public void addFont(String name, String fontFilename) {
        fontMapping.put(name, fontFilename);
    }

    public Typeface get(String fontName) {
        String fontFilename = fontMapping.get(fontName);
        if (fontFilename == null) {
            Log.e(TAG, "Couldn't find font " + fontName + ". Maybe you need to call addFont() first ?");
            return null;
        }
        if (cache.containsKey(fontName)) {
            return cache.get(fontName);
        } else {
            Typeface typeface = Typeface.createFromAsset(MyApplication.getInstance().getAssets(),fontFilename);
            cache.put(fontFilename, typeface);
            return typeface;
        }
    }
}
