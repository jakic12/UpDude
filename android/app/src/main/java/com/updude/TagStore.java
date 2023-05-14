package com.updude;

import android.content.SharedPreferences;
import android.content.Context;
import android.preference.PreferenceManager;
import android.util.Log;

import java.util.HashMap;
import java.util.HashSet;


public class TagStore {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    TagStore(Context context) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        editor = sharedPreferences.edit();
    }

    public boolean isTagRegistered(String tagID) {
        HashSet<String> tags = getTagIds();
        return tags.contains(tagID);
    }

    public Boolean SetTag(String tagID, String tagName) {
        HashSet<String> tags = getTagIds();
        if (tags.contains(tagID)) {
            return false;
        }

        HashMap<String, String> tagsMap = getTagMap();
        tagsMap.put(tagID, tagName);
        setTagMap(tagsMap);
        return true;
    }

    public String GetTag(String tagID) {
        HashMap<String, String> tagsMap = getTagMap();
        if (tagsMap.containsKey(tagID)) {
            return tagsMap.get(tagID);
        }
        return "";
    }

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
            if(tag.equals("")) continue;
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

    public HashSet<String> getTagIds() {
        String encoded = sharedPreferences.getString("nfc_tags", "");
        Log.d("nfc_tags", encoded);
        String[] tags = encoded.split(",");
        HashSet<String> tagNames = new HashSet<String>();
        for (String tag : tags) {
            if(tag.equals("")) continue;
            String[] tagParts = tag.split(":");
            tagNames.add(tagParts[0]);
        }
        return tagNames;
    }
}
