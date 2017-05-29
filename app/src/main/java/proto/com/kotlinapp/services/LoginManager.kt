package proto.com.kotlinapp.services

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import proto.com.kotlinapp.interfaces.ApiInterface
import proto.com.kotlinapp.interfaces.OnApiRequestListener
import proto.com.kotlinapp.models.response.SampleResponse
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

/**
 * Created by rsbulanon on 5/22/17.
 */
class LoginManager(val onApiRequestListener: OnApiRequestListener) {

    private val apiInterface: ApiInterface

    init {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build()

        val retrofit = Retrofit.Builder()
                .baseUrl("http://192.168.243.105:7000")
                .client(okHttpClient)
                .addConverterFactory(MoshiConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build()

        apiInterface = retrofit.create(ApiInterface::class.java)
    }

    fun login(email: String, password: String) {
        apiInterface.login("Basic YWRtaW5AY29uZG9iaWxscy5jb206UEBzc3cwcmQ=", email, password)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object :Subscriber<SampleResponse>() {
                    override fun onNext(t: SampleResponse) {
                        onApiRequestListener.onApiRequestSuccess(t)
                    }

                    override fun onCompleted() {
                    }

                    override fun onError(e: Throwable?) {
                        Log.d("login", "error " + e?.message)
                    }
                })
    }
}