package proto.com.kotlinapp.interfaces

import proto.com.kotlinapp.models.response.LoginResponse
import proto.com.kotlinapp.models.response.SampleResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Header
import retrofit2.http.POST
import rx.Observable

/**
 * Created by rsbulanon on 5/22/17.
 */
interface ApiInterface {

    @POST("/oauth/token")
    @FormUrlEncoded
    fun login(@Field("username") username: String,
              @Field("password") password: String,
              @Field("grant_type") grant_type: String,
              @Field("otp") otp: String,
              @Field("challengeId") challengeId: String,
              @Field("challengeAnswer") challengeAnswer: String): Observable<LoginResponse>

    @POST("/oauth/token")
    @FormUrlEncoded
    fun login(@Header("Authorization") header: String,
              @Field("username") username: String,
              @Field("password") password: String,
              @Field("grant_type") grant_type: String): Observable<LoginResponse>

    @POST("/oauth/token")
    @FormUrlEncoded
    fun login(@Header("Authorization") header: String,
              @Field("username") username: String,
              @Field("password") password: String,
              @Field("grant_type") grant_type: String,
              @Field("otp") otp: String): Observable<LoginResponse>

    @POST("/oauth/token")
    @FormUrlEncoded
    fun loginViaAppid(@Header("Authorization") header: String,
                      @Field("username") username: String,
                      @Field("password") password: String,
                      @Field("grant_type") grant_type: String,
                      @Field("appId") appId: String): Observable<LoginResponse>

    @POST("/api/v1/login")
    @FormUrlEncoded
    fun login(@Header("Authorization") header: String,
              @Field("email") email: String,
              @Field("password") password: String): Observable<SampleResponse>
}