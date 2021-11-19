package binny.ufc_record_en.retrofit

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object HttpClient {
    private const val baseUrl = "https://happynewmind1.cafe24.com/"

    private var retrofit: Retrofit? = null

    //싱글턴으로 Retrofit 객체 생성.
    fun getRetrofit(): Retrofit? {
        if (retrofit == null) {

            val interceptor = HttpLoggingInterceptor()
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            val client: OkHttpClient = OkHttpClient.Builder().addInterceptor(interceptor).build()

            val builder = Retrofit.Builder()
            builder.baseUrl(baseUrl) //Url 설정
            builder.client(client) //Log Message 추가
            builder.addConverterFactory(GsonConverterFactory.create()) //Json으로 들어온 결과를 Response 객체로 자동 변환하게 한다.
            retrofit = builder.build()
        }
        return retrofit
    }
}