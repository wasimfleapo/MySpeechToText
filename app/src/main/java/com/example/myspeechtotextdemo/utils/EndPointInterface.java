package com.example.myspeechtotextdemo.utils;



import com.example.myspeechtotextdemo.pojos.DictionaryListData;

import retrofit2.Call;
import retrofit2.http.GET;

public interface EndPointInterface {







    @GET(Constants.END_POINT)
    Call<DictionaryListData> DictionaryFetchService();







}


