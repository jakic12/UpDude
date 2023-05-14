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
    public Boolean SetTag(String tagID, Strind tagName) {
        HashSet<String> tags = getTagNames();
        if (tags.contains(tagName)) {
            return false;
        }

        HashMap<String, String> tagsMap = getTagMap();
        tagsMap.put(tagID, tagName);
        setTagMap(tagsMap);
        return true;
    }

    @ReactMethod
    public String GetTag(String tagID) {
        HashMap<String, String> tagsMap = getTagMap();
        if (tagsMap.containsKey(tagID)) {
            return tagsMap.get(tagID);
        }
        return "";
    }

    @ReactMethod
    public String PopTag(String tagID) {
        String tag = GetTag(tagID);

        if (tag != "") {
            HashMap<String, String> tagsMap = getTagMap();
            tagsMap.remove(tagID);
            setTagMap(tagsMap);
            return tag;
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

    public HashSet<String> getTagNames() {
        String encoded = sharedPreferences.getString("nfc_tags", "");
        String[] tags = encoded.split(",");
        HashSet<String> tagNames = new HashSet<String>();
        for (String tag : tags) {
            String[] tagParts = tag.split(":");
            tagNames.add(tagParts[1]);
        }
        return tagNames;
    }
}
