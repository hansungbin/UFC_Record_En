package binny.ufc_record_en.ui.fighter

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import binny.ufc_record_en.R
import binny.ufc_record_en.databinding.ActivityFighterBinding
import binny.ufc_record_en.databinding.DfListBinding
import binny.ufc_record_en.model.DetailFighterResult
import binny.ufc_record_en.model.Fighter
import binny.ufc_record_en.retrofit.ApiInterface
import binny.ufc_record_en.retrofit.HttpClient
import com.bumptech.glide.Glide
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FighterActivity : AppCompatActivity(){

    private lateinit var binding: ActivityFighterBinding
    private val fApi: ApiInterface? = HttpClient.getRetrofit()?.create(ApiInterface::class.java)
    var logTag : String? = "로그 FighterActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFighterBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val adapter = DetailFighterAdapter()

        binding.fighterDetailResultRecyclerView.adapter = adapter
        binding.fighterDetailResultRecyclerView.layoutManager = LinearLayoutManager(this)

        val param : String? = intent.getStringExtra("fighterName")
        val queries = mapOf("ufc_fighter_name" to param)
        val call : Call<Fighter?>? = fApi!!.getFighterDetailData(queries)

        binding.btnFighterListBack.setOnClickListener {
            this.finish()
            binding.noFighterConstraintLayout.visibility = View.GONE
            binding.constraintLayout1.visibility = View.VISIBLE
        }

        call?.enqueue(object : Callback<Fighter?> {
            @SuppressLint("SetTextI18n")
            override fun onResponse(call: Call<Fighter?>, response: Response<Fighter?>) {
                val result: Fighter = response.body() as Fighter



                if(result.data!!.isNotEmpty()) {

                    binding.constraintLayout1.visibility = View.VISIBLE
                    binding.noFighterConstraintLayout.visibility = View.GONE

                    adapter.setList(result.data?.get(0)?.total_fighter_record)

                    if(result.data?.get(0)!!.fighter_name!!.isNotEmpty()) {
                        binding.tvMpfpFighterName.text = result.data?.get(0)!!.fighter_name
                    }else {binding.tvMpfpFighterName.visibility = View.GONE}

                    if(result.data?.get(0)!!.fighter_born!!.isNotEmpty()) {
                        binding.tvFighterBorn.text = result.data?.get(0)!!.fighter_born
                    }else {binding.tvFighterBorn.visibility = View.GONE}

                    if(result.data?.get(0)!!.fighter_other_names!!.isNotEmpty()) {
                        binding.tvFighterOtherNames.text = result.data?.get(0)!!.fighter_other_names
                    }else {binding.tvFighterOtherNames.visibility = View.GONE}

                    if(result.data?.get(0)!!.fighter_residence!!.isNotEmpty()) {
                        binding.tvFighterResidence.text = result.data?.get(0)!!.fighter_residence
                    }else {binding.tvFighterResidence.visibility = View.GONE}

                    if(result.data?.get(0)!!.fighter_nationality!!.isNotEmpty()) {
                        binding.tvFighterNationality.text = result.data?.get(0)!!.fighter_nationality
                    }else {binding.tvFighterNationality.visibility = View.GONE}

                    if(result.data?.get(0)!!.fighter_division!!.isNotEmpty()) {
                        binding.tvFighterDivision.text = result.data?.get(0)!!.fighter_division
                    }else {binding.tvFighterDivision.visibility = View.GONE}

                    binding.tvFighterResidence.text = result.data?.get(0)!!.fighter_residence
                    binding.tvWKnockout.text = result.data?.get(0)!!.w_knockout.toString()
                    binding.tvWSubmission.text = result.data?.get(0)!!.w_submission.toString()
                    binding.tvWDecision.text = result.data?.get(0)!!.w_decision.toString()
                    binding.tvLKnockout.text = result.data?.get(0)!!.l_knockout.toString()
                    binding.tvLSubmission.text = result.data?.get(0)!!.l_submission.toString()
                    binding.tvLDecision.text = result.data?.get(0)!!.l_decision.toString()
                    binding.tvTotalMatch.text = (result.data?.get(0)!!.w_knockout!! + result.data?.get(0)!!.w_submission!! +
                            result.data?.get(0)!!.w_decision!! + result.data?.get(0)!!.l_knockout!! +
                            result.data?.get(0)!!.l_submission!! + result.data?.get(0)!!.l_decision!!).toString()
                    binding.tvTotalWin.text = (result.data?.get(0)!!.w_knockout!! + result.data?.get(0)!!.w_submission!! +
                            result.data?.get(0)!!.w_decision!!).toString()
                    binding.tvTotalLose.text = (result.data?.get(0)!!.l_knockout!! +
                            result.data?.get(0)!!.l_submission!! + result.data?.get(0)!!.l_decision!!).toString()

                    if ((binding.tvTotalMatch.text as String).toInt() > 1){
                        binding.tvTotalMatch.text = binding.tvTotalMatch.text.toString() + " Matches"
                    }else { binding.tvTotalMatch.text = binding.tvTotalMatch.text.toString() + " Match" }

                    if ((binding.tvTotalWin.text as String).toInt() > 1){
                        binding.tvTotalWin.text = binding.tvTotalWin.text.toString() + " Wins"
                    }else { binding.tvTotalWin.text = binding.tvTotalWin.text.toString() + " Win" }

                    if ((binding.tvTotalLose.text as String).toInt() > 1){
                        binding.tvTotalLose.text = binding.tvTotalLose.text.toString() + " Losses"
                    }else { binding.tvTotalLose.text = binding.tvTotalLose.text.toString() + " Lose" }

                    Glide.with(view.context).load(result.data?.get(0)!!.fighter_image)
                        .into(binding.ivFghterImage)
                }else if(result.data?.size!! < 1 ) {
                    binding.constraintLayout1.visibility = View.GONE
                    binding.noFighterConstraintLayout.visibility = View.VISIBLE
                }
            }

            override fun onFailure(call: Call<Fighter?>, t: Throwable) {
                Log.e("D/OkHttp", "onFailure - message =" + t.message)
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.fighterDetailResultRecyclerView.adapter = null

    }


    inner class DetailFighterAdapter : RecyclerView.Adapter<DetailFighterAdapter.DFItem>(){

        private var fighterDetail : ArrayList<DetailFighterResult>? = ArrayList()

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DFItem {
            val fdrView = LayoutInflater.from(parent.context).inflate(R.layout.df_list, parent, false)
            return DFItem(DfListBinding.bind(fdrView))
        }


        @SuppressLint("SetTextI18n")
        override fun onBindViewHolder(holder: DFItem, position: Int) {

            val detailFighterRecord : DetailFighterResult = fighterDetail!![position]

            val againtFighter = if (detailFighterRecord.detail_fighter_against!!.length > 18) {
                detailFighterRecord.detail_fighter_against!!.substring(0, 16) + ".."
            } else {
                detailFighterRecord.detail_fighter_against!!
            }

            holder.tvIdx.text = (position+1).toString()
            holder.tvDetailFighterResult.text = detailFighterRecord.detail_fighter_result + "\n" + detailFighterRecord.detail_fighter_match_round  + " Round"
            holder.tvDetailFighterRecord.text = detailFighterRecord.detail_fighter_record + "\n" + detailFighterRecord.detail_fighter_match_time.toString().substring(3)
            holder.btnDetailFighterAgainst.text = againtFighter +"\n" + detailFighterRecord.detail_fighter_method
            holder.tvDetailFighterMatchDay.text = detailFighterRecord.detail_fighter_match_day + "\n" + detailFighterRecord.detail_fighter_event_name

            if (position %2 != 1 ){
                holder.detailConstraintLayout.setBackgroundColor(Color.parseColor("#ffffff"))
            }

            holder.btnDetailFighterAgainst.setOnClickListener {
                val intent = Intent(this@FighterActivity, FighterActivity::class.java)
                intent.putExtra("fighterName",  detailFighterRecord.detail_fighter_against)
                startActivity(intent)
            }

        }

        override fun getItemCount(): Int {
            return fighterDetail!!.size
        }

        @SuppressLint("NotifyDataSetChanged")
        fun setList(detailFighter: List<DetailFighterResult>?) {

            fighterDetail!!.clear()
            fighterDetail!!.addAll(detailFighter!!)
            notifyDataSetChanged()

        }

        inner class DFItem(dfListBinding: DfListBinding) : RecyclerView.ViewHolder(dfListBinding.root) {
            var tvIdx : TextView = dfListBinding.tvInx
            var tvDetailFighterResult : TextView = dfListBinding.tvDetailFighterResult
            var tvDetailFighterRecord: TextView = dfListBinding.tvDetailFighterRecord
            var btnDetailFighterAgainst: Button = dfListBinding.btnDetailFighterAgainst
            var tvDetailFighterMatchDay: TextView = dfListBinding.tvDetailFighterMatchDay
            var detailConstraintLayout: ConstraintLayout = dfListBinding.DetailConstraintLayout


        }

    }

}
