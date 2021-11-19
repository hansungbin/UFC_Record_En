package binny.ufc_record_en.model

import com.google.gson.annotations.Expose

class UfcRanking {
    @Expose
    var idx: Int? = null

    @Expose
    var fighter_list : String? = null

    @Expose
    var ranked_day: String? = null
}