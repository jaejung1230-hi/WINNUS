package com.winnus.winnus.config

import android.app.Application
import android.content.SharedPreferences
import com.kakao.sdk.common.KakaoSdk
import com.winnus.winnus.src.main.home.model.ResultPopularWine
import com.winnus.winnus.src.main.home.model.ResultTodayWine
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApplicationClass : Application() {
    object FilterBoolean{
        var arr = arrayListOf(arrayListOf(false,false,false,false), arrayListOf(false,false,false,false,false,false),
            arrayListOf(false,false,false,false,false,false,false,false,false,false,false,false,false), arrayListOf(false,false,false,false,false,false))
        var sweetnessGlobal : Int = 0
        var acidityGlobal : Int = 0
        var bodyGlobal : Int = 0
        var tanninGlobal : Int = 0
    }
    object PopularWine{
        var PopularWineArr = arrayListOf<ArrayList<ArrayList<ResultPopularWine>>?>(null,null,null,null,null)
    }
    object themeWine{
        val themeWineArr = arrayListOf<List<ResultTodayWine>?>(null,null,null)
    }

    object todayWine{
        var todayWineArr : ArrayList<ResultTodayWine>? = null
    }

    object wineNames{
        var wordList : MutableList<String>? = null
    }

    // 테스트 서버 주소
    //val API_URL = "https://test.winnus.club/"

    // 실 서버 주소
    val API_URL = "https://prod.winnus.club/"

    // 코틀린의 전역변수 문법
    companion object {
        // 만들어져있는 SharedPreferences 를 사용해야합니다. 재생성하지 않도록 유념해주세요
        lateinit var sSharedPreferences: SharedPreferences

        // JWT Token Header 키 값
        val X_ACCESS_TOKEN = "X-ACCESS-TOKEN"
        val LOG_IN_USER = "LOG-IN-USER"

        // Retrofit 인스턴스, 앱 실행시 한번만 생성하여 사용합니다.
        lateinit var sRetrofit: Retrofit
    }

    // 앱이 처음 생성되는 순간, SP를 새로 만들어주고, 레트로핏 인스턴스를 생성합니다.
    override fun onCreate() {
        super.onCreate()

        KakaoSdk.init(this, "92235b85b53b7f21b67176ac72daf8f0")

        com.winnus.winnus.config.ApplicationClass.Companion.sSharedPreferences =
            applicationContext.getSharedPreferences("SOFTSQUARED_TEMPLATE_APP", MODE_PRIVATE)
        // 레트로핏 인스턴스 생성
        initRetrofitInstance()
    }

    // 레트로핏 인스턴스를 생성하고, 레트로핏에 각종 설정값들을 지정해줍니다.
    // 연결 타임아웃시간은 5초로 지정이 되어있고, HttpLoggingInterceptor를 붙여서 어떤 요청이 나가고 들어오는지를 보여줍니다.
    private fun initRetrofitInstance() {
        val client: OkHttpClient = OkHttpClient.Builder()
            .readTimeout(20000, TimeUnit.MILLISECONDS)
            .connectTimeout(20000, TimeUnit.MILLISECONDS)
            // 로그캣에 okhttp.OkHttpClient로 검색하면 http 통신 내용을 보여줍니다.
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .addNetworkInterceptor(com.winnus.winnus.config.XAccessTokenInterceptor()) // JWT 자동 헤더 전송
            .build()

        // sRetrofit 이라는 전역변수에 API url, 인터셉터, Gson을 넣어주고 빌드해주는 코드
        // 이 전역변수로 http 요청을 서버로 보내면 됩니다.
        com.winnus.winnus.config.ApplicationClass.Companion.sRetrofit = Retrofit.Builder()
            .baseUrl(API_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}