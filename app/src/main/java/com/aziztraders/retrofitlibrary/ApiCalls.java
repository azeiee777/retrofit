package com.aziztraders.retrofitlibrary;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiCalls {
    @GET("posts")
    Call<List<Model>> getModels();
}
