package binny.ufc_record_en.ui.record

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import binny.ufc_record_en.R
import binny.ufc_record_en.model.Record
import binny.ufc_record_en.model.UfcEvent
import binny.ufc_record_en.retrofit.ApiInterface
import binny.ufc_record_en.retrofit.HttpClient
import com.bumptech.glide.Glide
import retrofit2.Call
import retrofit2.Callback
import java.util.ArrayList

class RecordFragment : Fragment() {

    private var rRecyclerView: RecyclerView? = null
    private var btnSearch: Button? = null
    private var etSearch: EditText? = null
    private val rApi: ApiInterface? = HttpClient.getRetrofit()?.create(ApiInterface::class.java)
    private var result: Record? = null
    private var noResultConstraintLayout : ConstraintLayout? = null
    private var recordListLayout : ConstraintLayout? = null
    private var btnFighterListBack : Button? = null
    private var btnRecordListBack : Button? = null

    private lateinit var fighter : List<String>

    val logTag = "로그 RecordFragment"
    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_record, container, false)

        rRecyclerView = root.findViewById(R.id.recordRecyclerView)
        rRecyclerView?.layoutManager = LinearLayoutManager(context)
        val rAdapter = Radapter()

        rRecyclerView?.adapter = rAdapter
        etSearch = root.findViewById(R.id.et_record_search)
        btnSearch = root.findViewById(R.id.btn_record_search)
        recordListLayout = root.findViewById(R.id.record_list_constraintLayout)
//        btnFighterListBack = root.findViewById(R.id.btn_fighter_list_back)
        noResultConstraintLayout = root.findViewById(R.id.no_result_constraintLayout)
        btnRecordListBack = root.findViewById(R.id.btn_record_list_back)

        getRecordApi(rAdapter)

        btnSearch!!.setOnClickListener {
            getRecordApi(rAdapter)
        }

        btnRecordListBack!!.setOnClickListener {

            noResultConstraintLayout!!.visibility = View.GONE
            recordListLayout!!.visibility = View.VISIBLE
            etSearch!!.text = null
            getRecordApi(rAdapter)
        }

        return root
    }

    private fun getRecordApi(rAdapter: Radapter) {

        //쿼리 값을 Map 으로 생성하여 api 호출
        val queries = mapOf("ufc_event_search" to etSearch!!.text.toString())
        val call: Call<Record?>? = rApi!!.getRecordData(queries)

        call?.enqueue(object : Callback<Record?> {
            @SuppressLint("NotifyDataSetChanged")
            override fun onResponse(
                call: Call<Record?>,
                response: retrofit2.Response<Record?>
            ) {

                if (response.body()!!.data!!.isNotEmpty()) {
                    noResultConstraintLayout!!.visibility = View.GONE
                    recordListLayout!!.visibility = View.VISIBLE
                    //응답 성공시 어댑터에 결과 전달
                    result = response.body() as Record

                    rAdapter.clear()

                    rAdapter.setList(result!!.data)
                } else {
                    noResultConstraintLayout!!.visibility = View.VISIBLE
                    recordListLayout!!.visibility = View.GONE
                }

            }

            override fun onFailure(call: Call<Record?>, t: Throwable) {
                Log.e("D/OkHttp", "onFailure - message=" + t.message)
            }
        })
    }

    override fun onDestroyView() {

        super.onDestroyView()
        rRecyclerView?.adapter = null
        rRecyclerView = null
    }

    inner class Radapter : RecyclerView.Adapter<RecordFragment.Radapter.RvhItem>() {

        private var showMatchCount: Int? = 0
        private var matchCount: Int? = 0
        private var mUfcRecord: ArrayList<UfcEvent>? = ArrayList()

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RvhItem {

            val vhView =
                LayoutInflater.from(parent.context).inflate(R.layout.vh_record, parent, false)
            return RvhItem(vhView)
        }


        @SuppressLint("NotifyDataSetChanged")
        fun setList(ufcData: List<UfcEvent>?) {
            notifyDataSetChanged()
            mUfcRecord!!.addAll(ufcData!!)
            notifyDataSetChanged()
        }

        @SuppressLint("NotifyDataSetChanged")
        fun clear() {
            val size: Int = mUfcRecord!!.size
            notifyDataSetChanged()
            mUfcRecord!!.clear()
            notifyItemRangeRemoved(0, size)
        }


        @SuppressLint("SetTextI18n")
        override fun onBindViewHolder(holder: RvhItem, position: Int) {

            val record: UfcEvent = mUfcRecord!![position]

            holder.ufcEventName.text = record.ufc_event_name
            holder.ufcEventDate.text = record.ufc_event_date
            holder.ufcEventCity.text = record.ufc_event_city
            holder.ufcEventPromotion.text = record.ufc_event_promotion
            showMatchCount = record.ufc_event_count?.toInt()
            fighter = emptyList()

            if(record.ufc_event_fighter != null) {
                fighter = record.ufc_event_fighter!!.split(",")
            }

            var ufcEventWinner: String? = null
            var ufcEventLoser: String? = null

            var i = 0

            var matchCount = 0
            if(!(record.ufc_event_fighter == null || record.ufc_event_fighter == "Canceled")) {
                while (matchCount < 3) {

                    val winner: String =
                        if (fighter[i].length > 14) {
                            fighter[i].substring(0, 12) + ".."
                        } else {
                            fighter[i]
                        }

                    val loser: String =
                        if (fighter[i+1].length > 14) {
                            fighter[i+1].substring(0, 12) + ".."
                        } else {
                            fighter[i+1]
                        }

                    if (ufcEventWinner == null) {
                        ufcEventWinner = "(W)$winner"
                        ufcEventLoser = "(L)$loser"
                    } else {
                        ufcEventWinner += "\n(W)$winner"
                        ufcEventLoser += "\n(L)$loser"
                    }
                    i += 2
                    matchCount += 1
                }
                holder.ufcEventWinner.text = ufcEventWinner
                holder.ufcEventLoser.text = ufcEventLoser

                holder.listLayout.setOnClickListener {

                    val intent = Intent(activity, DetailRecordActivity::class.java)
                    intent.putExtra("ufcEventName", holder.ufcEventName.text)
                    startActivity(intent)
                }

                if (fighter.isNotEmpty()) {
                    holder.ufcEventMore.text = "+ ${fighter.size / 2 - matchCount} match\nmore"
                }
                if (fighter.isEmpty()) {
                    holder.ufcEventMore.text = "No Match"
                    holder.tvVs.visibility = View.INVISIBLE
                } else {holder.tvVs.visibility = View.VISIBLE}
            } else {
                holder.conMatchPreview.visibility = View.GONE
                holder.matchCancel.visibility = View.VISIBLE
            }

            if (!(record.ufc_event_image == null || record.ufc_event_image == "Canceled")) {
                Glide.with(holder.itemView.context).load(record.ufc_event_image)
                    .into(holder.ufcEventImage)
            }else {
//                holder.ufcEventImage.layoutParams.height = ConstraintLayout.LayoutParams.WRAP_CONTENT
//                holder.ufcEventImage.layoutParams.height = 80
            }
        }

        override fun getItemCount(): Int {

            return mUfcRecord!!.size
        }

        inner class RvhItem(itemView: View) : RecyclerView.ViewHolder(itemView) {
            var ufcEventName: TextView = itemView.findViewById(R.id.tv_ufc_event_name)
            var ufcEventImage: ImageView = itemView.findViewById(R.id.iv_ufc_poster_image)
            var ufcEventDate: TextView = itemView.findViewById(R.id.tv_ufc_event_date)
            var ufcEventCity: TextView = itemView.findViewById(R.id.tv_ufc_event_city)
            var ufcEventPromotion: TextView = itemView.findViewById(R.id.tv_ufc_Event_Promotion)
            var ufcEventWinner: TextView = itemView.findViewById(R.id.currently_fighter_division)
            var ufcEventLoser: TextView = itemView.findViewById(R.id.tv_ufc_event_loser)
            var ufcEventMore: TextView = itemView.findViewById(R.id.tv_more_match)
            var tvVs: TextView = itemView.findViewById(R.id.tv_vs)
            var listLayout: ConstraintLayout = itemView.findViewById(R.id.la_list)
            var conMatchPreview: ConstraintLayout = itemView.findViewById(R.id.constraintlayout_match_preview)
            var matchCancel: TextView = itemView.findViewById(R.id.tv_match_cancel)
        }
    }
}