package binny.ufc_record_en.model

import com.google.gson.annotations.Expose

// 리스트
class UfcEvent {
    @Expose
    var ufc_event_name: String? = null

    @Expose
    var ufc_event_date: String? = null

    @Expose
    var ufc_event_city: String? = null

    @Expose
    var ufc_event_promotion: String? = null

    @Expose
    var ufc_event_image: String? = null

    @Expose
    var ufc_event_count: String? = null

    @Expose
    var ufc_event_venue: String? = null

    @Expose
    var ufc_event_bonus_award: String? = null

    @Expose
    var ufc_event_fighter: String? = null
}