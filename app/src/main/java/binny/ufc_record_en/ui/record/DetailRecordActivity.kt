package binny.ufc_record_en.ui.record

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
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
    private var recordLayout : Int = 0
    
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

                binding.tvMatchName.text = result.dataDetail?.get(0)!!.ufc_event_name
                binding.tvPromotionName.text = result.dataDetail?.get(0)!!.ufc_event_promotion
                binding.tvMatchVenue.text = result.dataDetail?.get(0)!!.ufc_event_venue
                binding.tvMatchCity.text = result.dataDetail?.get(0)!!.ufc_event_city
                binding.tvMatchBonus.text = result.dataDetail?.get(0)!!.ufc_event_bonus_award
                binding.tvEventDate.text = result.dataDetail?.get(0)!!.ufc_event_date

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
            val searchWinerName = detailRecord.game_winner.toString().split("|")
            val searchLoserName = detailRecord.game_loser.toString().split("|")

            var winner = ""
            var loser = ""

            //
            for (element in searchWinerName) {
                winner += element
            }

            for (element in searchLoserName) {
                loser += element
            }

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

            if(position % 2 != 0) {
                holder.relayoutrow.setBackgroundColor(Color.parseColor("#e1e1e1"))
                holder.detailRecordConstraintlayout.setBackgroundColor(Color.parseColor("#ffffff"))
            }

            holder.gNumber.text = ((position+1).toString())

            val weightClass = if (detailRecord.game_class.toString().length > 14) {
                detailRecord.game_class!!.substring(0, 12) + ".."
            } else {
                detailRecord.game_class.toString()
            }

            val gameMethod = if ( detailRecord.game_method.toString().length > 23){
                detailRecord.game_method!!.substring(0, 21) + ".."
            } else {
                detailRecord.game_method!!.toString()
            }

            holder.gRound.text = detailRecord.game_round.toString()+" Round\n" + detailRecord.game_match_time

            if (winner.length > 15) {
                holder.gWinner.text =
                    winner!!.substring(0, 13) + ".." + "\n" + gameMethod
            } else {
                holder.gWinner.text =
                    winner + "\n" + gameMethod
            }

            if (detailRecord.game_loser.toString().length > 15) {
                holder.gLoser.text =
                    loser!!.substring(0, 13) + ".." + "\n" + gameMethod
            } else {
                holder.gLoser.text =
                    loser + "\n" + gameMethod
            }

            holder.gWinner.setOnClickListener {
                val intent = Intent(this@DetailRecordActivity , FighterActivity::class.java)
                intent.putExtra("fighterName", searchWinerName[0])
                startActivity(intent)
            }

            holder.gLoser.setOnClickListener {
                val intent = Intent(this@DetailRecordActivity , FighterActivity::class.java)
                intent.putExtra("fighterName", searchLoserName[0])
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
            var gNumber : TextView = drRecordBinding.tvGameNumber
            var matchKind : TextView = drRecordBinding.matchKind
            var gRound : TextView = drRecordBinding.Ground
            var gWinner : TextView = drRecordBinding.Gwinner
            var gLoser : TextView = drRecordBinding.Gloser
            var matchKindLayout : LinearLayout = drRecordBinding.matchKindLayout
            var rowLayout : RelativeLayout = drRecordBinding.row
            var detailRecordConstraintlayout : ConstraintLayout = drRecordBinding.detailRecordConstraintlayout
            var relayoutrow : RelativeLayout = drRecordBinding.row

//            detail_record_onstraintlayout
        }
    }
}