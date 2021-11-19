package binny.ufc_record_en.model

import com.google.gson.annotations.Expose

class UfcEventResult {
    @Expose
    var game_class : String? = null

    @Expose
    var game_winner : String? = null

    @Expose
    var game_loser : String? = null

    @Expose
    var game_method : String? = null

    @Expose
    var game_round : String? = null

    @Expose
    var game_match_time : String? = null

    @Expose
    var game_kind : String? = null
}