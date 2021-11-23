package binny.ufc_record_en.ui.record

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import binny.ufc_record_en.R
import binny.ufc_record_en.databinding.ActivityDetailRecordBinding
import binny.ufc_record_en.databinding.DrRecordBinding
import binny.ufc_record_en.model.Record
import binny.ufc_record_en.model.UfcEventResult
import binny.ufc_record_en.retrofit.ApiInterface
import binny.ufc_record_en.retrofit.HttpClient
import binny.ufc_record_en.ui.fighter.FighterActivity
import com.bumptech.glide.Glide
import retrofit2.Call
import retrofit2.Callback

class DetailRecordActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailRecordBinding
    private val mApi: ApiInterface? = HttpClient.getRetrofit()?.create(ApiInterface::class.java)
    val logTag = "로그 DetailRecordActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailRecordBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val adapter = DetailViewAdapter()
        binding.mRecyclerView.adapter = adapter
        binding.mRecyclerView.layoutManager = LinearLayoutManager(this)

        val param: String? = intent.getStringExtra("ufcEventName")
        val queries = mapOf("ufc_event_search" to param)
        val call: Call<Record?>? = mApi!!.getRecordDetailData(queries)

        call?.enqueue(object : Callback<Record?> {
            override fun onResponse(call: Call<Record?>, response: retrofit2.Response<Record?>){
                // 응답 성공시 어댑터에 결과 전달
                val result : Record = response.body() as Record

                Log.d(logTag, "onResponse: result.dataDetail?.get(0)?.ufc_event_result = ${result.dataDetail?.get(0)?.ufc_event_result}")

                binding.tvMatchName.text = result.dataDetail?.get(0)!!.ufc_event_name
                binding.tvPromotionName.text = result.dataDetail?.get(0)!!.ufc_event_promotion
                binding.tvMatchVenue.text = result.dataDetail?.get(0)!!.ufc_event_venue
                binding.tvMatchCity.text = result.dataDetail?.get(0)!!.ufc_event_city
                binding.tvMatchBonus.text = result.dataDetail?.get(0)!!.ufc_event_bonus_award

                Glide.with(view.context).load(result.dataDetail?.get(0)!!.ufc_event_image)
                    .into(binding.ivMainImage)

                adapter.setList(result.dataDetail?.get(0)?.ufc_event_result)

            }

            override fun onFailure(call: Call<Record?>, t: Throwable) {
                Log.e("D/OkHttp", "onFailure - message =" + t.message)
            }
        })

    }

    override fun onDestroy() {
        super.onDestroy()
        binding.mRecyclerView.adapter  = null
    }

    inner class DetailViewAdapter : RecyclerView.Adapter<DetailViewAdapter.DRItem>() {

        private var mUfcResult : ArrayList<UfcEventResult>? = ArrayList()

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DRItem {

            val drView = LayoutInflater.from(parent.context).inflate(R.layout.dr_record, parent, false)
            return DRItem(DrRecordBinding.bind(drView))
        }

        @SuppressLint("SetTextI18n")
        override fun onBindViewHolder(holder: DRItem, position: Int) {

            val detailRecord: UfcEventResult = mUfcResult!![position]

            when {
                position == 0 -> {
                    // 첫번째 열
                    holder.matchKind.text = detailRecord.game_kind.toString()
                }
                mUfcResult!![position].game_kind != mUfcResult!![position - 1].game_kind -> {
                    holder.matchKind.text = detailRecord.game_kind.toString()
                }
                else -> {
                    holder.matchKindLayout.visibility = View.GONE
                    holder.rowLayout.visibility = View.GONE
                }
            }

            holder.gNumber.text = ((position+1).toString())
            holder.gClass.text = detailRecord.game_class.toString()
            holder.winner.text = detailRecord.game_winner.toString()
            holder.loser.text = detailRecord.game_loser.toString()
            holder.method.text = detailRecord.game_method.toString()
            holder.round.text = detailRecord.game_round.toString() + " Round"
            holder.matchTime.text = detailRecord.game_match_time.toString().substring(3)

            holder.winner.setOnClickListener { v->
                val intent = Intent(this@DetailRecordActivity , FighterActivity::class.java)
                var searchName = holder.winner.text
                searchName = searchName.replace("\\(c\\)".toRegex(),"")
                intent.putExtra("fighterName", searchName)
                startActivity(intent)
            }

            holder.round.setOnClickListener { v->
                val intent = Intent(this@DetailRecordActivity , FighterActivity::class.java)
                var searchName = holder.winner.text
                searchName = searchName.replace("\\(c\\)".toRegex(),"")
                intent.putExtra("fighterName", searchName)
                startActivity(intent)
            }

            holder.matchTime.setOnClickListener { v->
                val intent = Intent(this@DetailRecordActivity , FighterActivity::class.java)
                var searchName = holder.loser.text
                searchName = searchName.replace("\\(c\\)".toRegex(),"")
                intent.putExtra("fighterName", searchName)
                startActivity(intent)
            }

            holder.loser.setOnClickListener { v->
                val intent = Intent(this@DetailRecordActivity , FighterActivity::class.java)
                var searchName = holder.loser.text
                searchName = searchName.replace("\\(c\\)".toRegex(),"")
                intent.putExtra("fighterName", searchName)
                startActivity(intent)
            }

        }

        override fun getItemCount(): Int {
            return mUfcResult!!.size
        }

        @SuppressLint("NotifyDataSetChanged")
        fun setList(ufcResult: List<UfcEventResult>?){
            mUfcResult!!.clear()
            mUfcResult!!.addAll(ufcResult!!)
            notifyDataSetChanged()
        }

        inner class DRItem(drRecordBinding: DrRecordBinding) : RecyclerView.ViewHolder(drRecordBinding.root) {

            // 경기 결과에 관한 목록들
            var gNumber : TextView = drRecordBinding.Gnumber
            var matchKind : TextView = drRecordBinding.matchKind
            var gClass : TextView = drRecordBinding.Gclass
            var winner : TextView = drRecordBinding.winner
            var loser : TextView = drRecordBinding.loser
            var method : TextView = drRecordBinding.method
            var round : TextView = drRecordBinding.round
            var matchTime : TextView = drRecordBinding.matchTime
            var matchKindLayout : LinearLayout = drRecordBinding.matchKindLayout
            var rowLayout : RelativeLayout = drRecordBinding.row

        }
    }
}