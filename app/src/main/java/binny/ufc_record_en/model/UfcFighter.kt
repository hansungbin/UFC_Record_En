package binny.ufc_record_en.model

import binny.ufc_record_en.model.DetailFighterResult
import com.google.gson.annotations.Expose

class UfcFighter {

    @Expose
    var fighter_name : String? = null

    @Expose
    var fighter_born : String? = null

    //닉네임
    @Expose
    var fighter_other_names : String? = null

    @Expose
    var fighter_residence : String? = null

    //출신국
    @Expose
    var fighter_nationality : String? = null

    //키
    @Expose
    var fighter_height : String? = null

    //체중
    @Expose
    var fighter_weight : String? = null

    //체급
    @Expose
    var fighter_division : String? = null

    //리치
    @Expose
    var fighter_reach : String? = null

    //거주지
    @Expose
    var fighting_out_of : String? = null

    //소속 팀
    @Expose
    var fighter_team : String? = null

    //코치
    @Expose
    var fighter_trainer : String? = null

    //활동연도
    @Expose
    var fighter_years_active : String? = null

    //이미지
    @Expose
    var fighter_image : String? = null

    //순위
    @Expose
    var fighter_ranking : String? = null

    //기록
    @Expose
    var fighter_record : String? = null

    //현체급
    @Expose
    var currently_fighter_division : String? = null

    //win knockout
    @Expose
    var w_knockout     : Int? = null

    //win submission
    @Expose
    var w_submission : Int? = null

    //win decision
    @Expose
    var w_decision      : Int? = null

    //lose knockout
    @Expose
    var l_knockout      : Int? = null

    //lose submission
    @Expose
    var l_submission  : Int? = null

    //lose decision
    @Expose
    var l_decision       : Int? = null

    @Expose
    var total_fighter_record:  List<DetailFighterResult>? = null
}