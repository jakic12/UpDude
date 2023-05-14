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


public class ReactTagStore extends ReactContextBaseJavaModule {
    private TagStore ts;

    ReactTagStore(ReactApplicationContext context) {
        super(context);
        ts = new TagStore(context);
    }

    @Override
    public String getName() {
      return "ReactTagStore";
    }

    @ReactMethod
    public void SetTag(String tagID, String tagName, Callback cb) {
        cb.invoke(ts.SetTag(tagID, tagName));
    }

    @ReactMethod
    public void GetTag(String tagID, Callback cb) {
        cb.invoke(ts.GetTag(tagID));
    }

    @ReactMethod
    public void PopTag(String tagID, Callback cb) {
        cb.invoke(ts.PopTag(tagID));
    }
}
