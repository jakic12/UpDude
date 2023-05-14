package com.updude;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

import java.util.HashMap;
import java.util.HashSet;
import com.facebook.react.bridge.Callback;


public class TagStore extends ReactContextBaseJavaModule {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    TagStore(ReactApplicationContext context) {
        super(context);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        editor = sharedPreferences.edit();
    }

    public boolean isValidTag(String tagID) {
        HashSet<String> tags = getTagNames();
        return tags.contains(tagID);
    }
  
    @Override
    public String getName() {
      return "TagStore";
    }

    @ReactMethod
    public void SetTag(String tagID, Strind tagName, Callback cb) {
        HashSet<String> tags = getTagNames();
        if (tags.contains(tagName)) {
            cb.invoke(false);
            return;
        }

        HashMap<String, String> tagsMap = getTagMap();
        tagsMap.put(tagID, tagName);
        setTagMap(tagsMap);
        cb.invoke(true);
        return;
    }

    @ReactMethod
    public void GetTag(String tagID, Callback cb) {
        HashMap<String, String> tagsMap = getTagMap();
        if (tagsMap.containsKey(tagID)) {
            cb.invoke(tagsMap.get(tagID));
            return;
        }
        cb.invoke("");
        return;
    }

    @ReactMethod
    public void PopTag(String tagID, Callback cb) {
        String tag = GetTag(tagID);

        if (tag != "") {
            HashMap<String, String> tagsMap = getTagMap();
            tagsMap.remove(tagID);
            setTagMap(tagsMap);
            cb.invoke(tag);
            return;
        }
        cb.invoke("");
        return;
    }

    @ReactMethod
    public void GetMaping(Callback cb) {
        cb.invoke(getTagMap());
        return;
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
