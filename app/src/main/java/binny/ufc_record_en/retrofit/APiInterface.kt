package binny.ufc_record_en.retrofit

import binny.ufc_record_en.model.Fighter
import binny.ufc_record_en.model.Ranking
import binny.ufc_record_en.model.Record
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.QueryMap

@Target(AnnotationTarget.VALUE_PARAMETER)
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
internal annotation class ToJson

interface ApiInterface {

    @GET("ufc_record_select_event.php")
    fun getRecordData(@QueryMap queries: Map<String, String?>): Call<Record?>?

    @GET("ufc_fighter_select.php")
    fun getFighterData(@QueryMap queries: Map<String, String?>): Call<Fighter?>?

    @GET("ufc_fighter_detail_list.php")
    fun getFighterDetailData(@QueryMap queries: Map<String, String?>): Call<Fighter?>?

    @GET("ufc_ranking_select.php")
    fun getRankingData(@QueryMap queries: Map<String, String?>): Call<Ranking?>?
}