package com.updude;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import java.util.HashMap;


public class TagStore extends ReactContextBaseJavaModule {  
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    TagStore(ReactApplicationContext context) {
        super(context);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        editor = sharedPreferences.edit();
    }
  
    @Override
    public String getName() {
      return "TagStore";
    }
  
    @ReactMethod
    public Boolean SetTag(String tag, Strind tagID) {
        HashMap<String, String> tagsMap = getTagMap();
        if (tagsMap.containsKey(tag)) {
            return false;
        }

        tagsMap.put(tag, tagID);
        setTagMap(tagsMap);
        return true;
    }

    @ReactMethod
    public String GetTag(String tag) {
        HashMap<String, String> tagsMap = getTagMap();
        if (tagsMap.containsKey(tag)) {
            return tagsMap.get(tag);
        }
        return "";
    }

    @ReactMethod
    public String PopTag(String tag) {
        HashMap<String, String> tagsMap = getTagMap();
        if (tagsMap.containsKey(tag)) {
            String tagID = tagsMap.get(tag);
            tagsMap.remove(tag);
            setTagMap(tagsMap);
            return tagID;
        }
        return "";
    }

    public HashMap<String, String> getTagMap() {
        String encoded = sharedPreferences.getString("nfc_tags", "");
        String[] tags = encoded.split(",");
        HashMap<String, String> tagsMap = new HashMap<String, String>();
        for (String tag : tags) {
            String[] tagParts = tag.split(":");
            tagsMap.put(tagParts[0], tagParts[1]);
        }
        return tagsMap;
    }

    public void setTagMap(HashMap<String, String> tags) {
        String encoded = "";
        for (String key : tags.keySet()) {
            encoded += key + ":" + tags.get(key) + ",";
        }

        editor.putString("nfc_tags", encoded);
        editor.commit();
    }
}
