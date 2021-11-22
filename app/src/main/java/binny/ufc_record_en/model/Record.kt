package binny.ufc_record_en.model

import com.google.gson.annotations.Expose

class Record {
    //An annotation that indicates this member should be exposed for JSON serialization or deserialization.
    @Expose
    var data: List<UfcEvent>? = null

    @Expose
    var dataDetail: List<UfcDetailEvent>? = null
}