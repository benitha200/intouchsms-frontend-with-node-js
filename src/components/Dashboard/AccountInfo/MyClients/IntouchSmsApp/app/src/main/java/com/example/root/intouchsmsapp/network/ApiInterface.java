package com.example.root.intouchsmsapp.network;

import com.example.root.intouchsmsapp.ApiData.loginData;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;


public interface ApiInterface {

    @POST("login")
    Call<String> getLoginDetails(@Body loginData loginData);


    @POST("logout")
    Call<String> logout(
            @Header("Authorization") String token_header
    );

    @GET("getsendernames")
    Call<ArrayList> getSenderNames(
            @Header("Authorization") String token_header
    );

    @FormUrlEncoded
    @POST("appsendsms")
    Call<String> sendsms(
            @Header("Authorization") String token_header,
            @Field("recipients") String recipients,
            @Field("sender") String sender,
            @Field("message") String message
    );

    @POST("getuserunitprice")
    Call<ArrayList> getUserUnitPrice(
            @Header("Authorization") String token_header
    );

    @POST("getmmnetworks")
    Call<ArrayList> getMMNetworks(
            @Header("Authorization") String token_header
    );

    @FormUrlEncoded
    @POST("appaddtopup")
    Call<ArrayList> topup(
            @Header("Authorization") String token_header,
            @Field("packagepk") int packagepk,
            @Field("amount") String amount,
            @Field("unitpricetaxincl") String unitpricetaxincl,
            @Field("network") String network,
            @Field("phone") String phone,
            @Field("tax") String tax
    );

    @FormUrlEncoded
    @POST("appecashtopup")
    Call<ArrayList> ecashtopup(
            @Header("Authorization") String token_header,
            @Field("amount") String amount,
            @Field("network") String network,
            @Field("phone") String phone
    );

    @FormUrlEncoded
    @POST("appgettransactions")
    Call<ArrayList> gettransactions(
            @Header("Authorization") String token_header,
            @Field("start") long start,
            @Field("limit") long limit
    );

    @FormUrlEncoded
    @POST("appgetcredittransfers")
    Call<ArrayList> getmycredittransfers(
            @Header("Authorization") String token_header,
            @Field("start") long start,
            @Field("limit") long limit,
            @Field("pattern") String pattern
    );

    @POST("getmybalance")
    Call<ArrayList> getmybalance(
            @Header("Authorization") String token_header
    );

    @POST("getmyprofile")
    Call<ArrayList> getmyprofile(
            @Header("Authorization") String token_header
    );

    @FormUrlEncoded
    @POST("appgetmycustomersbyrole")
    Call<ArrayList> getmycustomers(
            @Header("Authorization") String token_header,
            @Field("start") long start,
            @Field("limit") long limit,
            @Field("pattern") String pattern
    );

    @FormUrlEncoded
    @POST("appgetmyclients")
    Call<ArrayList> getmyclients(
            @Header("Authorization") String token_header,
            @Field("start") long start,
            @Field("limit") long limit,
            @Field("pattern") String pattern
    );

    @FormUrlEncoded
    @POST("appaddmyclient")
    Call<ArrayList> addmyclient(
            @Header("Authorization") String token_header,
            @Field("user_name") String user_name,
            @Field("first_name") String first_name,
            @Field("email") String email,
            @Field("password") String password,
            @Field("phone_number") String phone_number,
            @Field("telephone") String telephone,
            @Field("address") String address,
            @Field("description") String description
    );

    @FormUrlEncoded
    @POST("appeditmyclient")
    Call<ArrayList> editmyclient(
            @Header("Authorization") String token_header,
            @Field("pk") int pk,
            @Field("user_name") String user_name,
            @Field("first_name") String first_name,
            @Field("email") String email,
            @Field("password") String password,
            @Field("phone_number") String phone_number,
            @Field("telephone") String telephone,
            @Field("address") String address,
            @Field("description") String description
    );

    @FormUrlEncoded
    @POST("appaddcredittransfer")
    Call<ArrayList> addmycredittransfer(
            @Header("Authorization") String token_header,
            @Field("receipientcustomerpk") int receipientcustomerpk,
            @Field("transferamount") int transferamount,
            @Field("description") String description
    );

    @FormUrlEncoded
    @POST("getpublishedpackages")
    Call<ArrayList> getpublishedpackages(
            @Header("Authorization") String token_header,
            @Field("start") long start,
            @Field("limit") long limit
    );


    @FormUrlEncoded
    @POST("appgetmessagelogsummary")
    Call<ArrayList> getmessagelogsummary(
            @Header("Authorization") String token_header,
            @Field("start") long start,
            @Field("limit") long limit,
            @Field("pattern") String pattern
    );

    @FormUrlEncoded
    @POST("appgetmessagelogdetails")
    Call<ArrayList> getmessagelogdetails(
            @Header("Authorization") String token_header,
            @Field("summary") int summary,
            @Field("start") long start,
            @Field("limit") long limit
    );


}
