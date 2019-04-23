package com.jackandphantom.stackquestion.Interface;

import com.jackandphantom.stackquestion.model.QuestionItemData;
import com.jackandphantom.stackquestion.model.TagResponse;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


/**
 * It has all the api required get the questions , tag and auth
 * different api has different values so each one has the model class
 * each api has different parameter need to made the api
 * */
public interface StackApi {

    @GET("oauth/dialog")
    Call<ResponseBody>stackAuthCall(@Query("client_id") int client_id, @Query("redirect_uri ") String redirect_uri);

    @GET("2.2/tags")
    Call<TagResponse>stackTagCall(@Query("page")int page, @Query("order") String order, @Query("sort") String popular,
                                  @Query("site")String site);

    @GET("2.2/questions")
    Call<QuestionItemData>stackQuestionCall(@Query("page")int page, @Query("order") String order, @Query("sort") String sort
                                            ,@Query("tagged") String tag,@Query("site")String site, @Query("filter") String filter);
}
