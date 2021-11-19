package binny.ufc_record_en.model

import com.google.gson.annotations.Expose

class Ranking {
    //An annotation that indicates this member should be exposed for JSON serialization or deserialization.
//    @Expose
//    var MpfpData: List<UfcMpfpRanking>? = null

    @Expose
    var data: List<UfcRanking>? = null
}