package binny.ufc_record_en.model

import com.google.gson.annotations.Expose

class DetailFighterResult {

    @Expose
    var idx : String? = null

    @Expose
    var detail_fighter_main : String? = null

    @Expose
    var detail_fighter_result : String? = null

    @Expose
    var detail_fighter_record : String? = null

    @Expose
    var detail_fighter_against : String? = null

    @Expose
    var detail_fighter_method : String? = null

    @Expose
    var detail_fighter_event_name : String? = null

    @Expose
    var detail_fighter_match_day : String? = null

    @Expose
    var detail_fighter_match_round : String? = null

    @Expose
    var detail_fighter_match_time : String? = null
}