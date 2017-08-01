package com.fast.google.rest.api;


import com.fast.google.bean.speech.RequestSpeech;
import com.fast.google.bean.speech.ResponseSpeech;
import com.fast.google.bean.translate.RequestTranslate;
import com.fast.google.bean.translate.ResponseTranslate;
import com.fast.google.rest.request.RequestLogin;
import com.fast.google.rest.response.ResponseLogin;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

import static android.R.attr.format;
import static android.R.attr.key;

/**
 * User related API service, such as login, register
 */
public interface LoginApiService {

    //手机号码和验证码登录
    @POST("login/mobile")
    Observable<ResponseLogin> login(
            @Body RequestLogin login);


    @POST("v1/speech:recognize")
    Observable<ResponseSpeech> speech(
            @Query("key") String key,
            @Body RequestSpeech speech);

//
//    //2.1.2 游客登录
//    @POST("login/visitor")
//    Call<BaseResponse<BaseLogin>> loginSkip(
//            @Body LoginRequest request);
//
//    //第三方登录
//    @POST("login/thirdParty")
//    Call<BaseResponse<BaseLogin>> thirdlogin(@Body ThirdLoginRequest request);
//
//    //获取短信验证码
//    @GET("sms/verifyCode/getLogin")
//    Call<BaseResponse<BaseData>> requestLoginSmsCode(
//            @Query("deviceType") String deviceType,
//            @Query("deviceUUID") String deviceUUID,
//            @Query("phoneNum") String phoneNum);
//
//    //获取短信验证码
//    @GET("sms/verifyCode/get")
//    Call<BaseResponse<BaseData>> requestSmsCode(
//            @Query("deviceType") String deviceType,
//            @Query("deviceUUID") String deviceUUID,
//            @Query("phoneNum") String phoneNum);
//
//    //注册
//    @POST("register")
//    Call<BaseResponse<BaseLogin>> register(
//            @Body RegisterReq registerReq);
//
//
//    //东方时尚学员注册
//    @POST("dfssRegister")
//    Call<BaseResponse<BaseLogin>> traineeRegister(
//            @Body RequestTrainee requestTrainee);
//
//    //东方时尚学员获取短信验证码
//    @FormUrlEncoded
//    @POST("sms/verifyCode/checkStudent")
//    Call<BaseResponse<BaseData>> getSmsCode(
//            @Field("studentId") String studentId,
//            @Field("phoneNum") String phoneNum,
//            @Field("password") String password);
//
//    //获取科目进度
//    //获取短信验证码
//    @GET("user/courseProgress")
//    Call<BaseResponse<BaseLogin>> courseProgress(
//            @Query("token") String token
//    );

}
