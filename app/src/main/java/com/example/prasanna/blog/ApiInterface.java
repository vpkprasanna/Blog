package com.example.prasanna.blog;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by prasanna on 7/3/18.
 */

public interface ApiInterface {
    @POST(NetworkURL.LOGIN)
    Call<MessageResponse> login(@Body AunthicationRequest body);

    @POST(NetworkURL.REGISTRATION)
    Call<MessageResponse> registration(@Body AunthicationRequest body);

    @GET(NetworkURL.GET_ARTICLES)
    Call<List<Article>>getArticles();
}
