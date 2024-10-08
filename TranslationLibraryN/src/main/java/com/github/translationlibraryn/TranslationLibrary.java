package com.github.translationlibraryn;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

public class TranslationLibrary {
    // JSON dosyasını assets klasöründen okuma
    private String loadJSONFromAsset(Context context) {
        String json = null;
        try {
            InputStream is = context.getAssets().open("Dictionary.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    // Kelime çeviri fonksiyonu
    public String translateWord(Context context, String word, String targetLang) {
        String json = loadJSONFromAsset(context);
        if (json == null) {
            return "Error loading JSON"; // Hata durumu
        }

        try {
            JSONObject jsonObject = new JSONObject(json);
            if (jsonObject.has(word)) {
                JSONObject translations = jsonObject.getJSONObject(word);
                if (translations.has(targetLang)) {
                    return translations.getString(targetLang); // Çeviri bulundu
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return "Translation not found"; // Çeviri yoksa hata mesajı
    }
}
