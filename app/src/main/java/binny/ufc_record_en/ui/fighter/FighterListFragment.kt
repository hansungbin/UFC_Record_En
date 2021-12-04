package binny.ufc_record_en.ui.fighter

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
import binny.ufc_record_en.model.Fighter
import binny.ufc_record_en.model.UfcFighter
import binny.ufc_record_en.retrofit.ApiInterface
import binny.ufc_record_en.retrofit.HttpClient
import com.bumptech.glide.Glide
import retrofit2.Call
import retrofit2.Callback

class FighterListFragment : Fragment() {

    private var fRecyclerView: RecyclerView? = null
    private val fApi: ApiInterface? = HttpClient.getRetrofit()?.create(ApiInterface::class.java)
    private var btnSearch: Button? = null
    private var etSearch: EditText? = null
    private var fighterName: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_fighter, container, false)
        fighterName = arguments?.getString("ufc_event_search")
        if (fighterName == null || fighterName == "") {
            fighterName = ""
        }


        fRecyclerView = root.findViewById(R.id.ranking_RecyclerView)
        fRecyclerView?.layoutManager = LinearLayoutManager(context)
        val fAdapter = FAdapter()
        fRecyclerView?.adapter = fAdapter

        etSearch = root.findViewById(R.id.et_ranking_search)
        btnSearch = root.findViewById(R.id.btn_ranking_search)

        getFighterApi(fAdapter)

        btnSearch!!.setOnClickListener {
            getFighterApi(fAdapter)
        }

        return root
    }

    private fun getFighterApi(fAdapter : FAdapter){

        val queries = mapOf("ufc_fighter_name" to etSearch!!.text.toString())
        val call: Call<Fighter?>? = fApi!!.getFighterData(queries)

        call?.enqueue(object : Callback<Fighter?> {
            override fun onResponse(
                call: Call<Fighter?>,
                response: retrofit2.Response<Fighter?>
            ) {
                //응답 성공시 어댑터에 결과 전달
                val result: Fighter = response.body() as Fighter

                fAdapter.setList(result.data)

            }

            override fun onFailure(call: Call<Fighter?>, t: Throwable) {
                Log.e("D/OkHttp", "onFailure - message=" + t.message)
            }
        })
    }

    inner class FAdapter : RecyclerView.Adapter<FighterListFragment.FAdapter.FvhItem>() {

        private var ufcFighter: ArrayList<UfcFighter>? = ArrayList()


        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FvhItem {
            val vhView =
                LayoutInflater.from(parent.context).inflate(R.layout.vh_fighter, parent, false)
            return FvhItem(vhView)
        }

        @SuppressLint("NotifyDataSetChanged")
        fun setList(ufcData: List<UfcFighter>?) {

            ufcFighter!!.clear()
            ufcFighter!!.addAll(ufcData!!)
            notifyDataSetChanged()

        }

        @SuppressLint("SetTextI18n")
        override fun onBindViewHolder(holder: FvhItem, position: Int) {

            val fighter: UfcFighter = ufcFighter!![position]

            holder.fighterName.text = fighter.fighter_name.toString()

            holder.fighterNationality.text = fighter.fighter_nationality.toString()
            holder.fighterRecord.text = fighter.fighter_record.toString().substring(0,10) + "\n" +fighter.fighter_record.toString().substring(11)

            holder.fighterDivision.text = fighter.currently_fighter_division.toString()


            if (fighter.fighter_other_names!!.isNotEmpty()) {
                holder.fighterOtherNames.text = fighter.fighter_other_names.toString()
            } else {holder.fighterOtherNames.visibility = View.GONE }


            Glide.with(holder.itemView.context).load(fighter.fighter_image)
                .into(holder.ufcFighterImage)

            holder.lalist.setOnClickListener {

                val intent = Intent(activity, FighterActivity::class.java)
                intent.putExtra("fighterName", holder.fighterName.text)
                startActivity(intent)

            }
        }

        override fun getItemCount(): Int {
            return ufcFighter!!.size
        }

        inner class FvhItem(itemView: View) : RecyclerView.ViewHolder(itemView) {
            var ufcFighterImage: ImageView = itemView.findViewById(R.id.iv_ufc_fighter_image)
            var fighterName: TextView = itemView.findViewById(R.id.tv_list_fighter_name)
            var fighterNationality: TextView = itemView.findViewById(R.id.tv_fighter_nationality)
            var fighterRecord: TextView = itemView.findViewById(R.id.tv_fighter_record)
            var fighterDivision: TextView = itemView.findViewById(R.id.currently_fighter_division)
            var fighterOtherNames: TextView = itemView.findViewById(R.id.tv_fighter_other_names)
            var lalist: ConstraintLayout = itemView.findViewById(R.id.la_list)
        }
    }
}