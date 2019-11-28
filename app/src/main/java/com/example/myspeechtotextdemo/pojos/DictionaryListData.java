
package com.example.myspeechtotextdemo.pojos;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DictionaryListData {

    @SerializedName("dictionary")
    @Expose
    private List<Dictionary> dictionary = null;

    public List<Dictionary> getDictionary() {
        return dictionary;
    }

    public void setDictionary(List<Dictionary> dictionary) {
        this.dictionary = dictionary;
    }

}
