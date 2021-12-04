package binny.ufc_record_en.ui.ranking

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import binny.ufc_record_en.R
import binny.ufc_record_en.model.Ranking
import binny.ufc_record_en.model.UfcRanking
import binny.ufc_record_en.retrofit.ApiInterface
import binny.ufc_record_en.retrofit.HttpClient
import binny.ufc_record_en.ui.fighter.FighterActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RankingFragment : Fragment() {
    private var mpfpRankingRecyclerView: RecyclerView? = null
    private var flywRankingRecyclerView: RecyclerView? = null
    private var bantamwRankingRecyclerView: RecyclerView? = null
    private var featherwRankingRecyclerView: RecyclerView? = null
    private var lightwRankingRecyclerView: RecyclerView? = null
    private var welterwRankingRecyclerView: RecyclerView? = null
    private var middlewRankingRecyclerView: RecyclerView? = null
    private var lightheavywRankingRecyclerView: RecyclerView? = null
    private var heavywRankingRecyclerView: RecyclerView? = null
    private var wpfpRankingRecyclerView: RecyclerView? = null
    private var wstrawwRankingRecyclerView: RecyclerView? = null
    private var wflywRankingRecyclerView: RecyclerView? = null
    private var wbantamwRankingRecyclerView: RecyclerView? = null

    private val rApi: ApiInterface? = HttpClient.getRetrofit()?.create(ApiInterface::class.java)
    private var etRankingSearch: EditText? = null
    private var ranking: Ranking? = null

    private var btnRanking0: Button? = null //Men's pound-for-pound
    private var btnRanking1: Button? = null //Flyweight
    private var btnRanking2: Button? = null //Bantamweight
    private var btnRanking3: Button? = null //Featherweight
    private var btnRanking4: Button? = null //Lightweight
    private var btnRanking5: Button? = null //Welterweight
    private var btnRanking6: Button? = null //Middleweight
    private var btnRanking7: Button? = null //Lightheavyweight
    private var btnRanking8: Button? = null //Heavyweight
    private var btnRanking9: Button? = null //Women's pound-for-pound
    private var btnRanking10: Button? = null //Women's Strawweight
    private var btnRanking11: Button? = null //Women's Flyweight
    private var btnRanking12: Button? = null //Women's Bantamweight

    private var mpfpRankingAdapter = MpfpRankingAdapter()
    private var flywRankingAdapter = FlywRankingAdapter()
    private var bantamwRankingAdapter = BantamwRankingAdapter()
    private var featherwRankingAdapter = FeatherwRankingAdapter()
    private var lightwRankingAdapter = LightwRankingAdapter()
    private var welterwRankingAdapter = WelterwRankingAdapter()
    private var middlewRankingAdapter = MiddlewRankingAdapter()
    private var lightheavywRankingAdapter = LightheavywRankingAdapter()
    private var heavywRankingAdapter = HeavywRankingAdapter()
    private var wpfpRankingAdapter = WpfpRankingAdapter()
    private var wstrawwRankingAdapter = WstrawwRankingAdapter()
    private var wflywRankingAdapter = WflywRankingAdapter()
    private var wbantamwRankingAdapter = WbantamwRankingAdapter()

    val listStatus: MutableList<String> = ArrayList()

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        for (i in 0..13) {
            listStatus.add("Off")
        }

        val root = inflater.inflate(R.layout.fragment_ranking, container, false)

        mpfpRankingRecyclerView = root.findViewById(R.id.mpfp_ranking_RecyclerView)
        flywRankingRecyclerView = root.findViewById(R.id.flyw_ranking_RecyclerView)
        bantamwRankingRecyclerView = root.findViewById(R.id.bantamw_ranking_RecyclerView)
        featherwRankingRecyclerView = root.findViewById(R.id.featherw_ranking_RecyclerView)
        lightwRankingRecyclerView = root.findViewById(R.id.lightw_ranking_RecyclerView)
        welterwRankingRecyclerView = root.findViewById(R.id.welterw_ranking_RecyclerView)
        middlewRankingRecyclerView = root.findViewById(R.id.middlew_ranking_RecyclerView)
        lightheavywRankingRecyclerView = root.findViewById(R.id.lightheavyw_ranking_RecyclerView)
        heavywRankingRecyclerView = root.findViewById(R.id.heavyw_ranking_RecyclerView)
        wpfpRankingRecyclerView = root.findViewById(R.id.wpfp_ranking_RecyclerView)
        wstrawwRankingRecyclerView = root.findViewById(R.id.wstraww_ranking_RecyclerView)
        wflywRankingRecyclerView = root.findViewById(R.id.wflyw_ranking_RecyclerView)
        wbantamwRankingRecyclerView = root.findViewById(R.id.wbantamw_ranking_RecyclerView)

        mpfpRankingRecyclerView?.layoutManager = LinearLayoutManager(context)
        flywRankingRecyclerView?.layoutManager = LinearLayoutManager(context)
        bantamwRankingRecyclerView?.layoutManager = LinearLayoutManager(context)
        featherwRankingRecyclerView?.layoutManager = LinearLayoutManager(context)
        lightwRankingRecyclerView?.layoutManager = LinearLayoutManager(context)
        welterwRankingRecyclerView?.layoutManager = LinearLayoutManager(context)
        middlewRankingRecyclerView?.layoutManager = LinearLayoutManager(context)
        lightheavywRankingRecyclerView?.layoutManager = LinearLayoutManager(context)
        heavywRankingRecyclerView?.layoutManager = LinearLayoutManager(context)
        wpfpRankingRecyclerView?.layoutManager = LinearLayoutManager(context)
        wstrawwRankingRecyclerView?.layoutManager = LinearLayoutManager(context)
        wflywRankingRecyclerView?.layoutManager = LinearLayoutManager(context)
        wbantamwRankingRecyclerView?.layoutManager = LinearLayoutManager(context)

        mpfpRankingRecyclerView?.adapter = mpfpRankingAdapter
        flywRankingRecyclerView?.adapter = flywRankingAdapter
        bantamwRankingRecyclerView?.adapter = bantamwRankingAdapter
        featherwRankingRecyclerView?.adapter = featherwRankingAdapter
        lightwRankingRecyclerView?.adapter = lightwRankingAdapter
        welterwRankingRecyclerView?.adapter = welterwRankingAdapter
        middlewRankingRecyclerView?.adapter = middlewRankingAdapter
        lightheavywRankingRecyclerView?.adapter = lightheavywRankingAdapter
        heavywRankingRecyclerView?.adapter = heavywRankingAdapter
        wpfpRankingRecyclerView?.adapter = wpfpRankingAdapter
        wstrawwRankingRecyclerView?.adapter = wstrawwRankingAdapter
        wflywRankingRecyclerView?.adapter = wflywRankingAdapter
        wbantamwRankingRecyclerView?.adapter = wbantamwRankingAdapter

        btnRanking0 = root.findViewById(R.id.btn_mpfp)
        btnRanking1 = root.findViewById(R.id.btn_flyw)
        btnRanking2 = root.findViewById(R.id.btn_bantamw)
        btnRanking3 = root.findViewById(R.id.btn_featherw)
        btnRanking4 = root.findViewById(R.id.btn_lightw)
        btnRanking5 = root.findViewById(R.id.btn_welterw)
        btnRanking6 = root.findViewById(R.id.btn_middlew)
        btnRanking7 = root.findViewById(R.id.btn_lightheavyw)
        btnRanking8 = root.findViewById(R.id.btn_heavyw)
        btnRanking9 = root.findViewById(R.id.btn_wpfp)
        btnRanking10 = root.findViewById(R.id.btn_wstraww)
        btnRanking11 = root.findViewById(R.id.btn_wflyw)
        btnRanking12 = root.findViewById(R.id.btn_wbantamw)

        etRankingSearch = root.findViewById(R.id.et_ranking_search)

        GetRankingApi(
            mpfpRankingAdapter,
            flywRankingAdapter,
            bantamwRankingAdapter,
            featherwRankingAdapter,
            lightwRankingAdapter,
            welterwRankingAdapter,
            middlewRankingAdapter,
            lightheavywRankingAdapter,
            heavywRankingAdapter,
            wpfpRankingAdapter,
            wstrawwRankingAdapter,
            wflywRankingAdapter,
            wbantamwRankingAdapter,

            )

        btnRanking0!!.setOnClickListener {
            if (listStatus[0] == "Off") {
                initList(0)
                listStatus[0] = "On"
                btnRanking0!!.text = getString(R.string.close)
                GetRankingApi(
                    mpfpRankingAdapter,
                    flywRankingAdapter,
                    bantamwRankingAdapter,
                    featherwRankingAdapter,
                    lightwRankingAdapter,
                    welterwRankingAdapter,
                    middlewRankingAdapter,
                    lightheavywRankingAdapter,
                    heavywRankingAdapter,
                    wpfpRankingAdapter,
                    wstrawwRankingAdapter,
                    wflywRankingAdapter,
                    wbantamwRankingAdapter
                )
            } else if (listStatus[0] == "On") {
                initList(0)
                listStatus[0] = "Off"
                btnRanking0!!.text = getString(R.string.more_information)
                GetRankingApi(
                    mpfpRankingAdapter,
                    flywRankingAdapter,
                    bantamwRankingAdapter,
                    featherwRankingAdapter,
                    lightwRankingAdapter,
                    welterwRankingAdapter,
                    middlewRankingAdapter,
                    lightheavywRankingAdapter,
                    heavywRankingAdapter,
                    wpfpRankingAdapter,
                    wstrawwRankingAdapter,
                    wflywRankingAdapter,
                    wbantamwRankingAdapter
                )
            }
        }

        btnRanking1!!.setOnClickListener {
            if (listStatus[1] == "Off") {
                initList(1)
                listStatus[1] = "On"
                btnRanking1!!.text = getString(R.string.close)
                GetRankingApi(
                    mpfpRankingAdapter,
                    flywRankingAdapter,
                    bantamwRankingAdapter,
                    featherwRankingAdapter,
                    lightwRankingAdapter,
                    welterwRankingAdapter,
                    middlewRankingAdapter,
                    lightheavywRankingAdapter,
                    heavywRankingAdapter,
                    wpfpRankingAdapter,
                    wstrawwRankingAdapter,
                    wflywRankingAdapter,
                    wbantamwRankingAdapter
                )
            } else if (listStatus[1] == "On") {
                initList(1)
                listStatus[1] = "Off"
                btnRanking1!!.text = getString(R.string.more_information)
                GetRankingApi(
                    mpfpRankingAdapter,
                    flywRankingAdapter,
                    bantamwRankingAdapter,
                    featherwRankingAdapter,
                    lightwRankingAdapter,
                    welterwRankingAdapter,
                    middlewRankingAdapter,
                    lightheavywRankingAdapter,
                    heavywRankingAdapter,
                    wpfpRankingAdapter,
                    wstrawwRankingAdapter,
                    wflywRankingAdapter,
                    wbantamwRankingAdapter
                )
            }
        }

        btnRanking2!!.setOnClickListener {
            if (listStatus[2] == "Off") {
                initList(2)
                listStatus[2] = "On"
                btnRanking2!!.text = getString(R.string.close)
                GetRankingApi(
                    mpfpRankingAdapter,
                    flywRankingAdapter,
                    bantamwRankingAdapter,
                    featherwRankingAdapter,
                    lightwRankingAdapter,
                    welterwRankingAdapter,
                    middlewRankingAdapter,
                    lightheavywRankingAdapter,
                    heavywRankingAdapter,
                    wpfpRankingAdapter,
                    wstrawwRankingAdapter,
                    wflywRankingAdapter,
                    wbantamwRankingAdapter
                )
            } else if (listStatus[2] == "On") {
                initList(2)
                listStatus[2] = "Off"
                btnRanking2!!.text = getString(R.string.more_information)
                GetRankingApi(
                    mpfpRankingAdapter,
                    flywRankingAdapter,
                    bantamwRankingAdapter,
                    featherwRankingAdapter,
                    lightwRankingAdapter,
                    welterwRankingAdapter,
                    middlewRankingAdapter,
                    lightheavywRankingAdapter,
                    heavywRankingAdapter,
                    wpfpRankingAdapter,
                    wstrawwRankingAdapter,
                    wflywRankingAdapter,
                    wbantamwRankingAdapter
                )
            }
        }

        btnRanking3!!.setOnClickListener {
            if (listStatus[3] == "Off") {
                initList(3)
                listStatus[3] = "On"
                btnRanking3!!.text = getString(R.string.close)
                GetRankingApi(
                    mpfpRankingAdapter,
                    flywRankingAdapter,
                    bantamwRankingAdapter,
                    featherwRankingAdapter,
                    lightwRankingAdapter,
                    welterwRankingAdapter,
                    middlewRankingAdapter,
                    lightheavywRankingAdapter,
                    heavywRankingAdapter,
                    wpfpRankingAdapter,
                    wstrawwRankingAdapter,
                    wflywRankingAdapter,
                    wbantamwRankingAdapter
                )
            } else if (listStatus[3] == "On") {
                initList(3)
                listStatus[3] = "Off"
                btnRanking3!!.text = getString(R.string.more_information)
                GetRankingApi(
                    mpfpRankingAdapter,
                    flywRankingAdapter,
                    bantamwRankingAdapter,
                    featherwRankingAdapter,
                    lightwRankingAdapter,
                    welterwRankingAdapter,
                    middlewRankingAdapter,
                    lightheavywRankingAdapter,
                    heavywRankingAdapter,
                    wpfpRankingAdapter,
                    wstrawwRankingAdapter,
                    wflywRankingAdapter,
                    wbantamwRankingAdapter
                )
            }
        }

        btnRanking4!!.setOnClickListener {
            if (listStatus[4] == "Off") {
                initList(4)
                listStatus[4] = "On"
                btnRanking4!!.text = getString(R.string.close)
                GetRankingApi(
                    mpfpRankingAdapter,
                    flywRankingAdapter,
                    bantamwRankingAdapter,
                    featherwRankingAdapter,
                    lightwRankingAdapter,
                    welterwRankingAdapter,
                    middlewRankingAdapter,
                    lightheavywRankingAdapter,
                    heavywRankingAdapter,
                    wpfpRankingAdapter,
                    wstrawwRankingAdapter,
                    wflywRankingAdapter,
                    wbantamwRankingAdapter
                )
            } else if (listStatus[4] == "On") {
                initList(4)
                listStatus[4] = "Off"
                btnRanking4!!.text = getString(R.string.more_information)
                GetRankingApi(
                    mpfpRankingAdapter,
                    flywRankingAdapter,
                    bantamwRankingAdapter,
                    featherwRankingAdapter,
                    lightwRankingAdapter,
                    welterwRankingAdapter,
                    middlewRankingAdapter,
                    lightheavywRankingAdapter,
                    heavywRankingAdapter,
                    wpfpRankingAdapter,
                    wstrawwRankingAdapter,
                    wflywRankingAdapter,
                    wbantamwRankingAdapter
                )
            }
        }

        btnRanking5!!.setOnClickListener {
            if (listStatus[5] == "Off") {
                initList(5)
                listStatus[5] = "On"
                btnRanking5!!.text = getString(R.string.close)
                GetRankingApi(
                    mpfpRankingAdapter,
                    flywRankingAdapter,
                    bantamwRankingAdapter,
                    featherwRankingAdapter,
                    lightwRankingAdapter,
                    welterwRankingAdapter,
                    middlewRankingAdapter,
                    lightheavywRankingAdapter,
                    heavywRankingAdapter,
                    wpfpRankingAdapter,
                    wstrawwRankingAdapter,
                    wflywRankingAdapter,
                    wbantamwRankingAdapter
                )
            } else if (listStatus[5] == "On") {
                initList(5)
                listStatus[5] = "Off"
                btnRanking5!!.text = getString(R.string.more_information)
                GetRankingApi(
                    mpfpRankingAdapter,
                    flywRankingAdapter,
                    bantamwRankingAdapter,
                    featherwRankingAdapter,
                    lightwRankingAdapter,
                    welterwRankingAdapter,
                    middlewRankingAdapter,
                    lightheavywRankingAdapter,
                    heavywRankingAdapter,
                    wpfpRankingAdapter,
                    wstrawwRankingAdapter,
                    wflywRankingAdapter,
                    wbantamwRankingAdapter
                )
            }
        }

        btnRanking6!!.setOnClickListener {
            if (listStatus[6] == "Off") {
                initList(6)
                listStatus[6] = "On"
                btnRanking6!!.text = getString(R.string.close)
                GetRankingApi(
                    mpfpRankingAdapter,
                    flywRankingAdapter,
                    bantamwRankingAdapter,
                    featherwRankingAdapter,
                    lightwRankingAdapter,
                    welterwRankingAdapter,
                    middlewRankingAdapter,
                    lightheavywRankingAdapter,
                    heavywRankingAdapter,
                    wpfpRankingAdapter,
                    wstrawwRankingAdapter,
                    wflywRankingAdapter,
                    wbantamwRankingAdapter
                )
            } else if (listStatus[6] == "On") {
                initList(6)
                listStatus[6] = "Off"
                btnRanking6!!.text = getString(R.string.more_information)
                GetRankingApi(
                    mpfpRankingAdapter,
                    flywRankingAdapter,
                    bantamwRankingAdapter,
                    featherwRankingAdapter,
                    lightwRankingAdapter,
                    welterwRankingAdapter,
                    middlewRankingAdapter,
                    lightheavywRankingAdapter,
                    heavywRankingAdapter,
                    wpfpRankingAdapter,
                    wstrawwRankingAdapter,
                    wflywRankingAdapter,
                    wbantamwRankingAdapter
                )
            }
        }

        btnRanking7!!.setOnClickListener {
            if (listStatus[7] == "Off") {
                initList(7)
                listStatus[7] = "On"
                btnRanking7!!.text = getString(R.string.close)
                GetRankingApi(
                    mpfpRankingAdapter,
                    flywRankingAdapter,
                    bantamwRankingAdapter,
                    featherwRankingAdapter,
                    lightwRankingAdapter,
                    welterwRankingAdapter,
                    middlewRankingAdapter,
                    lightheavywRankingAdapter,
                    heavywRankingAdapter,
                    wpfpRankingAdapter,
                    wstrawwRankingAdapter,
                    wflywRankingAdapter,
                    wbantamwRankingAdapter
                )
            } else if (listStatus[7] == "On") {
                initList(7)
                listStatus[7] = "Off"
                btnRanking7!!.text = getString(R.string.more_information)
                GetRankingApi(
                    mpfpRankingAdapter,
                    flywRankingAdapter,
                    bantamwRankingAdapter,
                    featherwRankingAdapter,
                    lightwRankingAdapter,
                    welterwRankingAdapter,
                    middlewRankingAdapter,
                    lightheavywRankingAdapter,
                    heavywRankingAdapter,
                    wpfpRankingAdapter,
                    wstrawwRankingAdapter,
                    wflywRankingAdapter,
                    wbantamwRankingAdapter
                )
            }
        }

        btnRanking8!!.setOnClickListener {
            if (listStatus[8] == "Off") {
                initList(8)
                listStatus[8] = "On"
                btnRanking8!!.text = getString(R.string.close)
                GetRankingApi(
                    mpfpRankingAdapter,
                    flywRankingAdapter,
                    bantamwRankingAdapter,
                    featherwRankingAdapter,
                    lightwRankingAdapter,
                    welterwRankingAdapter,
                    middlewRankingAdapter,
                    lightheavywRankingAdapter,
                    heavywRankingAdapter,
                    wpfpRankingAdapter,
                    wstrawwRankingAdapter,
                    wflywRankingAdapter,
                    wbantamwRankingAdapter
                )
            } else if (listStatus[8] == "On") {
                initList(8)
                listStatus[8] = "Off"
                btnRanking8!!.text = getString(R.string.more_information)
                GetRankingApi(
                    mpfpRankingAdapter,
                    flywRankingAdapter,
                    bantamwRankingAdapter,
                    featherwRankingAdapter,
                    lightwRankingAdapter,
                    welterwRankingAdapter,
                    middlewRankingAdapter,
                    lightheavywRankingAdapter,
                    heavywRankingAdapter,
                    wpfpRankingAdapter,
                    wstrawwRankingAdapter,
                    wflywRankingAdapter,
                    wbantamwRankingAdapter
                )
            }
        }

        btnRanking9!!.setOnClickListener {
            if (listStatus[9] == "Off") {
                initList(9)
                listStatus[9] = "On"
                btnRanking9!!.text = getString(R.string.close)
                GetRankingApi(
                    mpfpRankingAdapter,
                    flywRankingAdapter,
                    bantamwRankingAdapter,
                    featherwRankingAdapter,
                    lightwRankingAdapter,
                    welterwRankingAdapter,
                    middlewRankingAdapter,
                    lightheavywRankingAdapter,
                    heavywRankingAdapter,
                    wpfpRankingAdapter,
                    wstrawwRankingAdapter,
                    wflywRankingAdapter,
                    wbantamwRankingAdapter
                )
            } else if (listStatus[9] == "On") {
                initList(9)
                listStatus[9] = "Off"
                btnRanking9!!.text = getString(R.string.more_information)
                GetRankingApi(
                    mpfpRankingAdapter,
                    flywRankingAdapter,
                    bantamwRankingAdapter,
                    featherwRankingAdapter,
                    lightwRankingAdapter,
                    welterwRankingAdapter,
                    middlewRankingAdapter,
                    lightheavywRankingAdapter,
                    heavywRankingAdapter,
                    wpfpRankingAdapter,
                    wstrawwRankingAdapter,
                    wflywRankingAdapter,
                    wbantamwRankingAdapter
                )
            }
        }

        btnRanking10!!.setOnClickListener {
            if (listStatus[10] == "Off") {
                initList(10)
                listStatus[10] = "On"
                btnRanking10!!.text = getString(R.string.close)
                GetRankingApi(
                    mpfpRankingAdapter,
                    flywRankingAdapter,
                    bantamwRankingAdapter,
                    featherwRankingAdapter,
                    lightwRankingAdapter,
                    welterwRankingAdapter,
                    middlewRankingAdapter,
                    lightheavywRankingAdapter,
                    heavywRankingAdapter,
                    wpfpRankingAdapter,
                    wstrawwRankingAdapter,
                    wflywRankingAdapter,
                    wbantamwRankingAdapter
                )
            } else if (listStatus[10] == "On") {
                initList(10)
                listStatus[10] = "Off"
                btnRanking10!!.text = getString(R.string.more_information)
                GetRankingApi(
                    mpfpRankingAdapter,
                    flywRankingAdapter,
                    bantamwRankingAdapter,
                    featherwRankingAdapter,
                    lightwRankingAdapter,
                    welterwRankingAdapter,
                    middlewRankingAdapter,
                    lightheavywRankingAdapter,
                    heavywRankingAdapter,
                    wpfpRankingAdapter,
                    wstrawwRankingAdapter,
                    wflywRankingAdapter,
                    wbantamwRankingAdapter
                )
            }
        }

        btnRanking11!!.setOnClickListener {
            if (listStatus[11] == "Off") {
                initList(11)
                listStatus[11] = "On"
                btnRanking11!!.text = getString(R.string.close)
                GetRankingApi(
                    mpfpRankingAdapter,
                    flywRankingAdapter,
                    bantamwRankingAdapter,
                    featherwRankingAdapter,
                    lightwRankingAdapter,
                    welterwRankingAdapter,
                    middlewRankingAdapter,
                    lightheavywRankingAdapter,
                    heavywRankingAdapter,
                    wpfpRankingAdapter,
                    wstrawwRankingAdapter,
                    wflywRankingAdapter,
                    wbantamwRankingAdapter
                )
            } else if (listStatus[11] == "On") {
                initList(11)
                listStatus[11] = "Off"
                btnRanking11!!.text = getString(R.string.more_information)
                GetRankingApi(
                    mpfpRankingAdapter,
                    flywRankingAdapter,
                    bantamwRankingAdapter,
                    featherwRankingAdapter,
                    lightwRankingAdapter,
                    welterwRankingAdapter,
                    middlewRankingAdapter,
                    lightheavywRankingAdapter,
                    heavywRankingAdapter,
                    wpfpRankingAdapter,
                    wstrawwRankingAdapter,
                    wflywRankingAdapter,
                    wbantamwRankingAdapter
                )
            }
        }

        btnRanking12!!.setOnClickListener {
            if (listStatus[12] == "Off") {
                initList(12)
                listStatus[12] = "On"
                btnRanking12!!.text = getString(R.string.close)
                GetRankingApi(
                    mpfpRankingAdapter,
                    flywRankingAdapter,
                    bantamwRankingAdapter,
                    featherwRankingAdapter,
                    lightwRankingAdapter,
                    welterwRankingAdapter,
                    middlewRankingAdapter,
                    lightheavywRankingAdapter,
                    heavywRankingAdapter,
                    wpfpRankingAdapter,
                    wstrawwRankingAdapter,
                    wflywRankingAdapter,
                    wbantamwRankingAdapter
                )
            } else if (listStatus[12] == "On") {
                initList(12)
                listStatus[12] = "Off"
                btnRanking12!!.text = getString(R.string.more_information)
                GetRankingApi(
                    mpfpRankingAdapter,
                    flywRankingAdapter,
                    bantamwRankingAdapter,
                    featherwRankingAdapter,
                    lightwRankingAdapter,
                    welterwRankingAdapter,
                    middlewRankingAdapter,
                    lightheavywRankingAdapter,
                    heavywRankingAdapter,
                    wpfpRankingAdapter,
                    wstrawwRankingAdapter,
                    wflywRankingAdapter,
                    wbantamwRankingAdapter
                )
            }
        }

        return root
    }

    private fun initList(i : Int) {
        listStatus.clear()
        for (j in 0..13) {
            if(j != i) {
                listStatus.add("Off")
            }
        }
        if (i != 0) {
            btnRanking0!!.text = getString(R.string.more_information)
        }

        if(i != 1) {
            btnRanking1!!.text = getString(R.string.more_information)
        }

        if(i != 2) {
            btnRanking2!!.text = getString(R.string.more_information)
        }

        if(i != 3) {
            btnRanking3!!.text = getString(R.string.more_information)
        }

        if(i != 4) {
            btnRanking4!!.text = getString(R.string.more_information)
        }

        if(i != 5) {
            btnRanking5!!.text = getString(R.string.more_information)
        }

        if(i != 6) {
            btnRanking6!!.text = getString(R.string.more_information)
        }

        if(i != 7) {
            btnRanking7!!.text = getString(R.string.more_information)
        }

        if(i != 8) {
            btnRanking8!!.text = getString(R.string.more_information)
        }

        if(i != 9) {
            btnRanking9!!.text = getString(R.string.more_information)
        }

        if(i != 10) {
            btnRanking10!!.text = getString(R.string.more_information)
        }

        if(i != 11) {
            btnRanking11!!.text = getString(R.string.more_information)
        }

        if(i != 12) {
            btnRanking12!!.text = getString(R.string.more_information)
        }
    }

    fun initAdapter() {

        mpfpRankingAdapter.clear()
        flywRankingAdapter.clear()
        bantamwRankingAdapter.clear()
        featherwRankingAdapter.clear()
        lightwRankingAdapter.clear()
        welterwRankingAdapter.clear()
        middlewRankingAdapter.clear()
        lightheavywRankingAdapter.clear()
        heavywRankingAdapter.clear()
        wpfpRankingAdapter.clear()
        wstrawwRankingAdapter.clear()
        wflywRankingAdapter.clear()
        wbantamwRankingAdapter.clear()

        mpfpRankingAdapter.setList(ranking!!.data!!.subList(1, 4))
        flywRankingAdapter.setList(ranking!!.data!!.subList(0, 3))
        bantamwRankingAdapter.setList(ranking!!.data!!.subList(0, 3))
        featherwRankingAdapter.setList(ranking!!.data!!.subList(0, 3))
        lightwRankingAdapter.setList(ranking!!.data!!.subList(0, 3))
        welterwRankingAdapter.setList(ranking!!.data!!.subList(0, 3))
        middlewRankingAdapter.setList(ranking!!.data!!.subList(0, 3))
        lightheavywRankingAdapter.setList(ranking!!.data!!.subList(0, 3))
        heavywRankingAdapter.setList(ranking!!.data!!.subList(0, 3))
        wpfpRankingAdapter.setList(ranking!!.data!!.subList(1, 4))
        wstrawwRankingAdapter.setList(ranking!!.data!!.subList(0, 3))
        wflywRankingAdapter.setList(ranking!!.data!!.subList(0, 3))
        wbantamwRankingAdapter.setList(ranking!!.data!!.subList(0, 3))
    }

    private fun GetRankingApi(
        mpfpRankingAdapter: MpfpRankingAdapter,
        flywRankingAdapter: FlywRankingAdapter,
        bantamwRankingAdapter: BantamwRankingAdapter,
        featherwRankingAdapter: FeatherwRankingAdapter,
        lightwRankingAdapter: LightwRankingAdapter,
        welterwRankingAdapter: WelterwRankingAdapter,
        middlewRankingAdapter: MiddlewRankingAdapter,
        lightheavywRankingAdapter: LightheavywRankingAdapter,
        heavywRankingAdapter: HeavywRankingAdapter,
        wpfpRankingAdapter: WpfpRankingAdapter,
        wstrawwRankingAdapter: WstrawwRankingAdapter,
        wflywRankingAdapter: WflywRankingAdapter,
        wbantamwRankingAdapter: WbantamwRankingAdapter
    ) {

        val call: Call<Ranking?>? = rApi!!.getRankingData()
        call?.enqueue(object : Callback<Ranking?> {

            override fun onResponse(call: Call<Ranking?>, response: Response<Ranking?>) {

                ranking = response.body() as Ranking

                when {
                    listStatus[0] == "On" -> {
                        initAdapter()
                        mpfpRankingAdapter.clear()
                        mpfpRankingAdapter.setList(ranking!!.data!!.subList(1, 16))
                    }
                    listStatus[1] == "On" -> {
                        initAdapter()
                        flywRankingAdapter.clear()
                        flywRankingAdapter.setList(ranking!!.data!!.subList(0, 16))
                    }
                    listStatus[2] == "On" -> {
                        initAdapter()
                        bantamwRankingAdapter.clear()
                        bantamwRankingAdapter.setList(ranking!!.data!!.subList(0, 16))
                    }
                    listStatus[3] == "On" -> {
                        initAdapter()
                        featherwRankingAdapter.clear()
                        featherwRankingAdapter.setList(ranking!!.data!!.subList(0, 16))
                    }
                    listStatus[4] == "On" -> {
                        initAdapter()
                        lightwRankingAdapter.clear()
                        lightwRankingAdapter.setList(ranking!!.data!!.subList(0, 16))
                    }
                    listStatus[5] == "On" -> {
                        initAdapter()
                        welterwRankingAdapter.clear()
                        welterwRankingAdapter.setList(ranking!!.data!!.subList(0, 16))
                    }
                    listStatus[6] == "On" -> {
                        initAdapter()
                        middlewRankingAdapter.clear()
                        middlewRankingAdapter.setList(ranking!!.data!!.subList(0, 16))
                    }
                    listStatus[7] == "On" -> {
                        initAdapter()
                        lightheavywRankingAdapter.clear()
                        lightheavywRankingAdapter.setList(ranking!!.data!!.subList(0, 16))
                    }
                    listStatus[8] == "On" -> {
                        initAdapter()
                        heavywRankingAdapter.clear()
                        heavywRankingAdapter.setList(ranking!!.data!!.subList(0, 16))
                    }
                    listStatus[9] == "On" -> {
                        initAdapter()
                        wpfpRankingAdapter.clear()
                        wpfpRankingAdapter.setList(ranking!!.data!!.subList(1, 16))
                    }
                    listStatus[10] == "On" -> {
                        initAdapter()
                        wstrawwRankingAdapter.clear()
                        wstrawwRankingAdapter.setList(ranking!!.data!!.subList(0, 16))
                    }
                    listStatus[11] == "On" -> {
                        initAdapter()
                        wflywRankingAdapter.clear()
                        wflywRankingAdapter.setList(ranking!!.data!!.subList(0, 16))
                    }
                    listStatus[12] == "On" -> {
                        initAdapter()
                        wbantamwRankingAdapter.clear()
                        wbantamwRankingAdapter.setList(ranking!!.data!!.subList(0, 16))
                    }
                    else -> {
                        initAdapter()
                    }
                }
            }

            override fun onFailure(call: Call<Ranking?>, t: Throwable) {
                Log.e("D/OkHttp", "onFailure - message=" + t.message)
            }
        })
    }

    override fun onDestroyView() {

        super.onDestroyView()
        mpfpRankingRecyclerView?.adapter = null
        flywRankingRecyclerView?.adapter = null
        bantamwRankingRecyclerView?.adapter = null
        featherwRankingRecyclerView?.adapter = null
        lightwRankingRecyclerView?.adapter = null
        welterwRankingRecyclerView?.adapter = null
        middlewRankingRecyclerView?.adapter = null
        lightheavywRankingRecyclerView?.adapter = null
        heavywRankingRecyclerView?.adapter = null
        wpfpRankingRecyclerView?.adapter = null
        wstrawwRankingRecyclerView?.adapter = null
        wflywRankingRecyclerView?.adapter = null
        wbantamwRankingRecyclerView?.adapter = null

        mpfpRankingRecyclerView = null
        flywRankingRecyclerView = null
        bantamwRankingRecyclerView = null
        featherwRankingRecyclerView = null
        lightwRankingRecyclerView = null
        welterwRankingRecyclerView = null
        middlewRankingRecyclerView = null
        lightheavywRankingRecyclerView = null
        heavywRankingRecyclerView = null
        wpfpRankingRecyclerView = null
        wstrawwRankingRecyclerView = null
        wflywRankingRecyclerView = null
        wbantamwRankingRecyclerView = null

    }

    inner class MpfpRankingAdapter :
        RecyclerView.Adapter<RankingFragment.MpfpRankingAdapter.MpfpRankingItem>() {

        private var mpfpRanking: ArrayList<UfcRanking>? = ArrayList()

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MpfpRankingItem {
            val vhMpfpView =
                LayoutInflater.from(parent.context).inflate(R.layout.vh_mpfp_ranking, parent, false)

            return MpfpRankingItem(vhMpfpView)
        }

        @SuppressLint("NotifyDataSetChanged")
        fun setList(ufcRanking: List<UfcRanking>) {
            notifyDataSetChanged()
            this.mpfpRanking!!.addAll(ufcRanking)
            notifyDataSetChanged()

        }

        fun clear() {
            val size: Int = mpfpRanking!!.size
            mpfpRanking!!.clear()
            notifyItemRangeChanged(0, size)
        }

        override fun onBindViewHolder(holder: MpfpRankingItem, position: Int) {

            val mpfpRanking: UfcRanking = mpfpRanking!![position]
            val mpfpFighterName: List<String> = mpfpRanking.fighter_list!!.split(",")

            holder.tvMpfpNo.text = ((mpfpRanking.idx!! - 1).toString())

            val splitArray = mpfpFighterName[0].split("|")
            var fighterName =""
            splitArray.forEach { element ->
                fighterName += element
            }
            holder.tvMpfpfighterName.text = fighterName

            holder.constraintLayoutMpfp.setOnClickListener {
                val intent = Intent(activity, FighterActivity::class.java)
                intent.putExtra("fighterName",  splitArray[0])
                startActivity(intent)
            }
        }

        override fun getItemCount(): Int {
            return mpfpRanking!!.size
        }

        inner class MpfpRankingItem(itemView: View) : RecyclerView.ViewHolder(itemView) {

            var tvMpfpNo: TextView = itemView.findViewById(R.id.tv_mpfp_no)
            var tvMpfpfighterName: TextView = itemView.findViewById(R.id.tv_mpfp_fighter_name)
            var constraintLayoutMpfp: ConstraintLayout =
                itemView.findViewById(R.id.constraintLayout_recyc_mpfp)

        }
    }

    inner class FlywRankingAdapter : RecyclerView.Adapter<FlywRankingAdapter.FlywRankingItem>() {

        private var flywRanking: ArrayList<UfcRanking>? = ArrayList()
        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): FlywRankingItem {
            val flywView =
                LayoutInflater.from(parent.context).inflate(R.layout.vh_flyw_ranking, parent, false)

            return FlywRankingItem(flywView)
        }

        @SuppressLint("NotifyDataSetChanged")
        fun setList(ufcRanking: List<UfcRanking>) {
            notifyDataSetChanged()
            this.flywRanking!!.addAll(ufcRanking)
            notifyDataSetChanged()

        }

        fun clear() {
            val size: Int = flywRanking!!.size
            flywRanking!!.clear()
            notifyItemRangeChanged(0, size)
        }

        @SuppressLint("ResourceAsColor")
        override fun onBindViewHolder(holder: FlywRankingItem, position: Int) {

            val flywRanking: UfcRanking = flywRanking!![position]
            val flywFighterList: List<String> = flywRanking.fighter_list!!.split(",")

            if (flywRanking.idx!! == 1) {
                holder.tvFlywNo.text = getString(R.string.champion)
                holder.tvFlywFighterName.setTypeface(null, Typeface.BOLD_ITALIC)
                holder.tvFlywFighterName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16F)
                holder.tvFlywFighterName.setTextColor(R.color.black)
            } else {
                holder.tvFlywNo.text = ((flywRanking.idx!! - 1).toString())
            }

            val splitArray = flywFighterList[1].split("|")
            var fighterName =""
            splitArray.forEach { element ->
                fighterName += element
            }
            holder.tvFlywFighterName.text = fighterName

            holder.constraintLayoutFlyw.setOnClickListener {
                val intent = Intent(activity, FighterActivity::class.java)
                intent.putExtra("fighterName", splitArray[0])
                startActivity(intent)
            }
        }

        override fun getItemCount(): Int {
            return flywRanking!!.size
        }

        inner class FlywRankingItem(itemView: View) : RecyclerView.ViewHolder(itemView) {
            var tvFlywNo: TextView = itemView.findViewById(R.id.tv_flyw_no)
            var tvFlywFighterName: TextView = itemView.findViewById(R.id.tv_flyw_fighter_name)
            var constraintLayoutFlyw: ConstraintLayout =
                itemView.findViewById(R.id.constraintLayout_recyc_flyw)
        }

    }


    inner class BantamwRankingAdapter :
        RecyclerView.Adapter<BantamwRankingAdapter.BantamwRankingItem>() {

        private var bantamwRanking: ArrayList<UfcRanking>? = ArrayList()
        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): BantamwRankingItem {
            val bantamwView =
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.vh_bantamw_ranking, parent, false)

            return BantamwRankingItem(bantamwView)
        }

        @SuppressLint("NotifyDataSetChanged")
        fun setList(ufcRanking: List<UfcRanking>) {
            notifyDataSetChanged()
            this.bantamwRanking!!.addAll(ufcRanking)
            notifyDataSetChanged()

        }

        fun clear() {
            val size: Int = bantamwRanking!!.size
            bantamwRanking!!.clear()
            notifyItemRangeChanged(0, size)
        }

        @SuppressLint("ResourceAsColor")
        override fun onBindViewHolder(holder: BantamwRankingItem, position: Int) {

            val bantamwRanking: UfcRanking = bantamwRanking!![position]
            val bantamwFighterList: List<String> = bantamwRanking.fighter_list!!.split(",")

            if (bantamwRanking.idx!! == 1) {
                holder.tvBantamwNo.text = getString(R.string.champion)
                holder.tvBantamwFighterName.setTypeface(null, Typeface.BOLD_ITALIC)
                holder.tvBantamwFighterName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16F)
                holder.tvBantamwFighterName.setTextColor(R.color.black)
            } else {
                holder.tvBantamwNo.text = ((bantamwRanking.idx!! - 1).toString())
            }

            val splitArray = bantamwFighterList[2].split("|")
            var fighterName =""
            splitArray.forEach { element ->
                fighterName += element
            }
            holder.tvBantamwFighterName.text = fighterName

            holder.constraintLayoutBantamw.setOnClickListener {
                val intent = Intent(activity, FighterActivity::class.java)
                intent.putExtra("fighterName", splitArray[0])
                startActivity(intent)
            }
        }

        override fun getItemCount(): Int {
            return bantamwRanking!!.size
        }

        inner class BantamwRankingItem(itemView: View) : RecyclerView.ViewHolder(itemView) {
            var tvBantamwNo: TextView = itemView.findViewById(R.id.tv_bantamw_no)
            var tvBantamwFighterName: TextView = itemView.findViewById(R.id.tv_bantamw_fighter_name)
            var constraintLayoutBantamw: ConstraintLayout =
                itemView.findViewById(R.id.constraintLayout_recyc_bantamw)
        }

    }

    inner class FeatherwRankingAdapter :
        RecyclerView.Adapter<FeatherwRankingAdapter.FeatherwRankingItem>() {

        private var featherwRanking: ArrayList<UfcRanking>? = ArrayList()
        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): FeatherwRankingItem {
            val featherwView = LayoutInflater.from(parent.context)
                .inflate(R.layout.vh_featherw_ranking, parent, false)

            return FeatherwRankingItem(featherwView)
        }

        @SuppressLint("NotifyDataSetChanged")
        fun setList(ufcRanking: List<UfcRanking>) {
            notifyDataSetChanged()
            this.featherwRanking!!.addAll(ufcRanking)
            notifyDataSetChanged()

        }

        fun clear() {
            val size: Int = featherwRanking!!.size
            featherwRanking!!.clear()
            notifyItemRangeChanged(0, size)
        }

        override fun onBindViewHolder(holder: FeatherwRankingItem, position: Int) {

            val featherwRanking: UfcRanking = featherwRanking!![position]

            val featherwFighterList: List<String> = featherwRanking.fighter_list!!.split(",")

            if (featherwRanking.idx!! == 1) {
                holder.tvFeatherwNo.text = getString(R.string.champion)
                holder.tvFeatherwfighterName.setTypeface(null, Typeface.BOLD_ITALIC)
                holder.tvFeatherwfighterName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16F)
            } else {
                holder.tvFeatherwNo.text = ((featherwRanking.idx!! - 1).toString())
            }

            val splitArray = featherwFighterList[3].split("|")
            var fighterName =""
            splitArray.forEach { element ->
                fighterName += element
            }
            holder.tvFeatherwfighterName.text = fighterName

            holder.constraintLayoutBantamw.setOnClickListener {
                val intent = Intent(activity, FighterActivity::class.java)
                intent.putExtra("fighterName", splitArray[0])
                startActivity(intent)
            }
        }

        override fun getItemCount(): Int {
            return featherwRanking!!.size
        }

        inner class FeatherwRankingItem(itemView: View) : RecyclerView.ViewHolder(itemView) {
            var tvFeatherwNo: TextView = itemView.findViewById(R.id.tv_featherw_no)
            var tvFeatherwfighterName: TextView =
                itemView.findViewById(R.id.tv_featherw_fighter_name)
            var constraintLayoutBantamw: ConstraintLayout =
                itemView.findViewById(R.id.constraintLayout_recyc_featherw)
        }
    }

    inner class LightwRankingAdapter :
        RecyclerView.Adapter<LightwRankingAdapter.LightwRankingItem>() {

        private var lightwRanking: ArrayList<UfcRanking>? = ArrayList()
        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): LightwRankingItem {
            val lightwView = LayoutInflater.from(parent.context)
                .inflate(R.layout.vh_lightw_ranking, parent, false)

            return LightwRankingItem(lightwView)
        }

        @SuppressLint("NotifyDataSetChanged")
        fun setList(ufcRanking: List<UfcRanking>) {
            notifyDataSetChanged()
            this.lightwRanking!!.addAll(ufcRanking)
            notifyDataSetChanged()

        }

        fun clear() {
            val size: Int = lightwRanking!!.size
            lightwRanking!!.clear()
            notifyItemRangeChanged(0, size)
        }

        override fun onBindViewHolder(holder: LightwRankingItem, position: Int) {

            val lightwRanking: UfcRanking = lightwRanking!![position]

            val lightwFighterList: List<String> = lightwRanking.fighter_list!!.split(",")

            if (lightwRanking.idx!! == 1) {
                holder.tvLightwNo.text = getString(R.string.champion)
                holder.tvLightwfighterName.setTypeface(null, Typeface.BOLD_ITALIC)
                holder.tvLightwfighterName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16F)
            } else {
                holder.tvLightwNo.text = ((lightwRanking.idx!! - 1).toString())
            }

            val splitArray = lightwFighterList[4].split("|")
            var fighterName =""
            splitArray.forEach { element ->
                fighterName += element
            }
            holder.tvLightwfighterName.text = fighterName

            holder.constraintLayoutRecycLightw.setOnClickListener {
                val intent = Intent(activity, FighterActivity::class.java)
                intent.putExtra("fighterName", splitArray[0])
                startActivity(intent)
            }
        }

        override fun getItemCount(): Int {
            return lightwRanking!!.size
        }

        inner class LightwRankingItem(itemView: View) : RecyclerView.ViewHolder(itemView) {
            var tvLightwNo: TextView = itemView.findViewById(R.id.tv_lightw_no)
            var tvLightwfighterName: TextView = itemView.findViewById(R.id.tv_lightw_fighter_name)
            var constraintLayoutRecycLightw: ConstraintLayout =
                itemView.findViewById(R.id.constraintLayout_recyc_lightw)
        }
    }

    inner class WelterwRankingAdapter :
        RecyclerView.Adapter<WelterwRankingAdapter.WelterwRankingItem>() {

        private var welterwRanking: ArrayList<UfcRanking>? = ArrayList()
        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): WelterwRankingItem {
            val welterwView =
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.vh_welterw_ranking, parent, false)

            return WelterwRankingItem(welterwView)
        }

        @SuppressLint("NotifyDataSetChanged")
        fun setList(ufcRanking: List<UfcRanking>) {
            notifyDataSetChanged()
            this.welterwRanking!!.addAll(ufcRanking)
            notifyDataSetChanged()

        }

        fun clear() {
            val size: Int = welterwRanking!!.size
            welterwRanking!!.clear()
            notifyItemRangeChanged(0, size)
        }

        @SuppressLint("ResourceAsColor")
        override fun onBindViewHolder(holder: WelterwRankingItem, position: Int) {

            val welterwRanking: UfcRanking = welterwRanking!![position]
            val welterwFighterList: List<String> = welterwRanking.fighter_list!!.split(",")

            if (welterwRanking.idx!! == 1) {
                holder.tvWelterwNo.text = getString(R.string.champion)
                holder.tvWelterwFighterName.setTypeface(null, Typeface.BOLD_ITALIC)
                holder.tvWelterwFighterName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16F)
                holder.tvWelterwFighterName.setTextColor(R.color.black)
            } else {
                holder.tvWelterwNo.text = ((welterwRanking.idx!! - 1).toString())
            }

            val splitArray = welterwFighterList[5].split("|")
            var fighterName =""
            splitArray.forEach { element ->
                fighterName += element
            }
            holder.tvWelterwFighterName.text = fighterName

            holder.constraintLayoutWelterw.setOnClickListener {
                val intent = Intent(activity, FighterActivity::class.java)
                intent.putExtra("fighterName", splitArray[0])
                startActivity(intent)
            }
        }

        override fun getItemCount(): Int {
            return welterwRanking!!.size
        }

        inner class WelterwRankingItem(itemView: View) : RecyclerView.ViewHolder(itemView) {
            var tvWelterwNo: TextView = itemView.findViewById(R.id.tv_welterw_no)
            var tvWelterwFighterName: TextView = itemView.findViewById(R.id.tv_welterw_fighter_name)
            var constraintLayoutWelterw: ConstraintLayout =
                itemView.findViewById(R.id.welterw_constraintLayout)
        }

    }

    inner class MiddlewRankingAdapter :
        RecyclerView.Adapter<MiddlewRankingAdapter.MiddlewRankingItem>() {

        private var middlewRanking: ArrayList<UfcRanking>? = ArrayList()
        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): MiddlewRankingItem {
            val middlewView =
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.vh_middlew_ranking, parent, false)

            return MiddlewRankingItem(middlewView)
        }

        @SuppressLint("NotifyDataSetChanged")
        fun setList(ufcRanking: List<UfcRanking>) {
            notifyDataSetChanged()
            this.middlewRanking!!.addAll(ufcRanking)
            notifyDataSetChanged()

        }

        fun clear() {
            val size: Int = middlewRanking!!.size
            middlewRanking!!.clear()
            notifyItemRangeChanged(0, size)
        }

        @SuppressLint("ResourceAsColor")
        override fun onBindViewHolder(holder: MiddlewRankingItem, position: Int) {

            val middlewRanking: UfcRanking = middlewRanking!![position]
            val middlewFighterList: List<String> = middlewRanking.fighter_list!!.split(",")

            if (middlewRanking.idx!! == 1) {
                holder.tvMiddlewNo.text = getString(R.string.champion)
                holder.tvMiddlewFighterName.setTypeface(null, Typeface.BOLD_ITALIC)
                holder.tvMiddlewFighterName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16F)
                holder.tvMiddlewFighterName.setTextColor(R.color.black)
            } else {
                holder.tvMiddlewNo.text = ((middlewRanking.idx!! - 1).toString())
            }

            val splitArray = middlewFighterList[6].split("|")
            var fighterName =""
            splitArray.forEach { element ->
                fighterName += element
            }
            holder.tvMiddlewFighterName.text = fighterName

            holder.constraintLayoutMiddlew.setOnClickListener {
                val intent = Intent(activity, FighterActivity::class.java)
                intent.putExtra("fighterName", splitArray[0])
                startActivity(intent)
            }
        }

        override fun getItemCount(): Int {
            return middlewRanking!!.size
        }

        inner class MiddlewRankingItem(itemView: View) : RecyclerView.ViewHolder(itemView) {
            var tvMiddlewNo: TextView = itemView.findViewById(R.id.tv_middlew_no)
            var tvMiddlewFighterName: TextView = itemView.findViewById(R.id.tv_middlew_fighter_name)
            var constraintLayoutMiddlew: ConstraintLayout =
                itemView.findViewById(R.id.middlew_constraintLayout)
        }
    }

    inner class LightheavywRankingAdapter :
        RecyclerView.Adapter<LightheavywRankingAdapter.LightheavywRankingItem>() {

        private var lightheavywRanking: ArrayList<UfcRanking>? = ArrayList()
        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): LightheavywRankingItem {
            val lightheavywView =
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.vh_lightheavyw_ranking, parent, false)

            return LightheavywRankingItem(lightheavywView)
        }

        @SuppressLint("NotifyDataSetChanged")
        fun setList(ufcRanking: List<UfcRanking>) {
            notifyDataSetChanged()
            this.lightheavywRanking!!.addAll(ufcRanking)
            notifyDataSetChanged()

        }

        fun clear() {
            val size: Int = lightheavywRanking!!.size
            lightheavywRanking!!.clear()
            notifyItemRangeChanged(0, size)
        }

        @SuppressLint("ResourceAsColor")
        override fun onBindViewHolder(holder: LightheavywRankingItem, position: Int) {

            val lightheavywRanking: UfcRanking = lightheavywRanking!![position]
            val lightheavywFighterList: List<String> = lightheavywRanking.fighter_list!!.split(",")

            if (lightheavywRanking.idx!! == 1) {
                holder.tvLightheavywNo.text = getString(R.string.champion)
                holder.tvLightheavywFighterName.setTypeface(null, Typeface.BOLD_ITALIC)
                holder.tvLightheavywFighterName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16F)
                holder.tvLightheavywFighterName.setTextColor(R.color.black)
            } else {
                holder.tvLightheavywNo.text = ((lightheavywRanking.idx!! - 1).toString())
            }

            val splitArray = lightheavywFighterList[7].split("|")
            var fighterName =""
            splitArray.forEach { element ->
                fighterName += element
            }
            holder.tvLightheavywFighterName.text = fighterName

            holder.constraintLayoutLightheavyw.setOnClickListener {
                val intent = Intent(activity, FighterActivity::class.java)
                intent.putExtra("fighterName", splitArray[0])
                startActivity(intent)
            }
        }

        override fun getItemCount(): Int {
            return lightheavywRanking!!.size
        }

        inner class LightheavywRankingItem(itemView: View) : RecyclerView.ViewHolder(itemView) {
            var tvLightheavywNo: TextView = itemView.findViewById(R.id.tv_lightheavyw_no)
            var tvLightheavywFighterName: TextView =
                itemView.findViewById(R.id.tv_lightheavyw_fighter_name)
            var constraintLayoutLightheavyw: ConstraintLayout =
                itemView.findViewById(R.id.lightheavyw_constraintLayout)
        }
    }

    inner class HeavywRankingAdapter :
        RecyclerView.Adapter<HeavywRankingAdapter.HeavywRankingItem>() {

        private var heavywRanking: ArrayList<UfcRanking>? = ArrayList()
        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): HeavywRankingItem {
            val heavywView =
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.vh_heavyw_ranking, parent, false)

            return HeavywRankingItem(heavywView)
        }

        @SuppressLint("NotifyDataSetChanged")
        fun setList(ufcRanking: List<UfcRanking>) {
            notifyDataSetChanged()
            this.heavywRanking!!.addAll(ufcRanking)
            notifyDataSetChanged()

        }

        fun clear() {
            val size: Int = heavywRanking!!.size
            heavywRanking!!.clear()
            notifyItemRangeChanged(0, size)
        }

        @SuppressLint("ResourceAsColor")
        override fun onBindViewHolder(holder: HeavywRankingItem, position: Int) {

            val heavywRanking: UfcRanking = heavywRanking!![position]
            val heavywFighterList: List<String> = heavywRanking.fighter_list!!.split(",")

            if (heavywRanking.idx!! == 1) {
                holder.tvHeavywNo.text = getString(R.string.champion)
                holder.tvHeavywFighterName.setTypeface(null, Typeface.BOLD_ITALIC)
                holder.tvHeavywFighterName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16F)
                holder.tvHeavywFighterName.setTextColor(R.color.black)
            } else {
                holder.tvHeavywNo.text = ((heavywRanking.idx!! - 1).toString())
            }

            val splitArray = heavywFighterList[8].split("|")
            var fighterName =""
            splitArray.forEach { element ->
                fighterName += element
            }
            holder.tvHeavywFighterName.text = fighterName

            holder.constraintLayoutHeavyw.setOnClickListener {
                val intent = Intent(activity, FighterActivity::class.java)
                intent.putExtra("fighterName", splitArray[0])
                startActivity(intent)
            }
        }

        override fun getItemCount(): Int {
            return heavywRanking!!.size
        }

        inner class HeavywRankingItem(itemView: View) : RecyclerView.ViewHolder(itemView) {
            var tvHeavywNo: TextView = itemView.findViewById(R.id.tv_heavyw_no)
            var tvHeavywFighterName: TextView = itemView.findViewById(R.id.tv_heavyw_fighter_name)
            var constraintLayoutHeavyw: ConstraintLayout =
                itemView.findViewById(R.id.heavyw_constraintLayout)
        }
    }

    inner class WpfpRankingAdapter :
        RecyclerView.Adapter<WpfpRankingAdapter.WpfpRankingItem>() {

        private var wpfpRanking: ArrayList<UfcRanking>? = ArrayList()
        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): WpfpRankingItem {
            val wpfpView =
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.vh_wpfp_ranking, parent, false)

            return WpfpRankingItem(wpfpView)
        }

        @SuppressLint("NotifyDataSetChanged")
        fun setList(ufcRanking: List<UfcRanking>) {
            notifyDataSetChanged()
            this.wpfpRanking!!.addAll(ufcRanking)
            notifyDataSetChanged()

        }

        fun clear() {
            val size: Int = wpfpRanking!!.size
            wpfpRanking!!.clear()
            notifyItemRangeChanged(0, size)
        }

        @SuppressLint("ResourceAsColor")
        override fun onBindViewHolder(holder: WpfpRankingItem, position: Int) {

            val wpfpRanking: UfcRanking = wpfpRanking!![position]
            val wpfpFighterList: List<String> = wpfpRanking.fighter_list!!.split(",")

            holder.tvWpfpNo.text = ((wpfpRanking.idx!! - 1).toString())

            val splitArray = wpfpFighterList[9].split("|")
            var fighterName =""
            splitArray.forEach { element ->
                fighterName += element
            }
            holder.tvWpfpFighterName.text = fighterName

            holder.constraintLayoutWpfp.setOnClickListener {
                val intent = Intent(activity, FighterActivity::class.java)
                intent.putExtra("fighterName", splitArray[0])
                startActivity(intent)
            }
        }

        override fun getItemCount(): Int {
            return wpfpRanking!!.size
        }

        inner class WpfpRankingItem(itemView: View) : RecyclerView.ViewHolder(itemView) {
            var tvWpfpNo: TextView = itemView.findViewById(R.id.tv_wpfp_no)
            var tvWpfpFighterName: TextView = itemView.findViewById(R.id.tv_wpfp_fighter_name)
            var constraintLayoutWpfp: ConstraintLayout =
                itemView.findViewById(R.id.wpfp_constraintLayout)
        }
    }

    inner class WstrawwRankingAdapter :
        RecyclerView.Adapter<WstrawwRankingAdapter.WstrawwRankingItem>() {

        private var wstrawwRanking: ArrayList<UfcRanking>? = ArrayList()
        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): WstrawwRankingItem {
            val wstrawwView =
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.vh_wstraww_ranking, parent, false)

            return WstrawwRankingItem(wstrawwView)
        }

        @SuppressLint("NotifyDataSetChanged")
        fun setList(ufcRanking: List<UfcRanking>) {
            notifyDataSetChanged()
            this.wstrawwRanking!!.addAll(ufcRanking)
            notifyDataSetChanged()

        }

        fun clear() {
            val size: Int = wstrawwRanking!!.size
            wstrawwRanking!!.clear()
            notifyItemRangeChanged(0, size)
        }

        @SuppressLint("ResourceAsColor")
        override fun onBindViewHolder(holder: WstrawwRankingItem, position: Int) {

            val wstrawwRanking: UfcRanking = wstrawwRanking!![position]
            val wstrawwFighterList: List<String> = wstrawwRanking.fighter_list!!.split(",")

            if (wstrawwRanking.idx!! == 1) {
                holder.tvWstrawwNo.text = getString(R.string.champion)
                holder.tvWstrawwFighterName.setTypeface(null, Typeface.BOLD_ITALIC)
                holder.tvWstrawwFighterName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16F)
                holder.tvWstrawwFighterName.setTextColor(R.color.black)
            } else {
                holder.tvWstrawwNo.text = ((wstrawwRanking.idx!! - 1).toString())
            }

            val splitArray = wstrawwFighterList[10].split("|")
            var fighterName =""
            splitArray.forEach { element ->
                fighterName += element
            }
            holder.tvWstrawwFighterName.text = fighterName

            holder.constraintLayoutWstraww.setOnClickListener {
                val intent = Intent(activity, FighterActivity::class.java)
                intent.putExtra("fighterName", splitArray[0])
                startActivity(intent)
            }
        }

        override fun getItemCount(): Int {
            return wstrawwRanking!!.size
        }

        inner class WstrawwRankingItem(itemView: View) : RecyclerView.ViewHolder(itemView) {
            var tvWstrawwNo: TextView = itemView.findViewById(R.id.tv_wstraww_no)
            var tvWstrawwFighterName: TextView = itemView.findViewById(R.id.tv_wstraww_fighter_name)
            var constraintLayoutWstraww: ConstraintLayout =
                itemView.findViewById(R.id.wstraww_constraintLayout)
        }
    }

    inner class WflywRankingAdapter :
        RecyclerView.Adapter<WflywRankingAdapter.WflywRankingItem>() {

        private var wflywRanking: ArrayList<UfcRanking>? = ArrayList()
        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): WflywRankingItem {
            val wflywView =
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.vh_wflyw_ranking, parent, false)

            return WflywRankingItem(wflywView)
        }

        @SuppressLint("NotifyDataSetChanged")
        fun setList(ufcRanking: List<UfcRanking>) {
            notifyDataSetChanged()
            this.wflywRanking!!.addAll(ufcRanking)
            notifyDataSetChanged()

        }

        fun clear() {
            val size: Int = wflywRanking!!.size
            wflywRanking!!.clear()
            notifyItemRangeChanged(0, size)
        }

        @SuppressLint("ResourceAsColor")
        override fun onBindViewHolder(holder: WflywRankingItem, position: Int) {

            val wflywRanking: UfcRanking = wflywRanking!![position]
            val wflywFighterList: List<String> = wflywRanking.fighter_list!!.split(",")

            if (wflywRanking.idx!! == 1) {
                holder.tvWflywNo.text = getString(R.string.champion)
                holder.tvWflywFighterName.setTypeface(null, Typeface.BOLD_ITALIC)
                holder.tvWflywFighterName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16F)
                holder.tvWflywFighterName.setTextColor(R.color.black)
            } else {
                holder.tvWflywNo.text = ((wflywRanking.idx!! - 1).toString())
            }

            val splitArray = wflywFighterList[11].split("|")
            var fighterName =""
            splitArray.forEach { element ->
                fighterName += element
            }
            holder.tvWflywFighterName.text =fighterName

            holder.constraintLayoutWflyw.setOnClickListener {
                val intent = Intent(activity, FighterActivity::class.java)
                intent.putExtra("fighterName", splitArray[0])
                startActivity(intent)
            }
        }

        override fun getItemCount(): Int {
            return wflywRanking!!.size
        }

        inner class WflywRankingItem(itemView: View) : RecyclerView.ViewHolder(itemView) {
            var tvWflywNo: TextView = itemView.findViewById(R.id.tv_wflyw_no)
            var tvWflywFighterName: TextView = itemView.findViewById(R.id.tv_wflyw_fighter_name)
            var constraintLayoutWflyw: ConstraintLayout =
                itemView.findViewById(R.id.wflyw_constraintLayout)
        }
    }

    inner class WbantamwRankingAdapter :
        RecyclerView.Adapter<WbantamwRankingAdapter.WbantamwRankingItem>() {

        private var wbantamwRanking: ArrayList<UfcRanking>? = ArrayList()
        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): WbantamwRankingItem {
            val wbantamwView =
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.vh_wbantamw_ranking, parent, false)

            return WbantamwRankingItem(wbantamwView)
        }

        @SuppressLint("NotifyDataSetChanged")
        fun setList(ufcRanking: List<UfcRanking>) {
            notifyDataSetChanged()
            this.wbantamwRanking!!.addAll(ufcRanking)
            notifyDataSetChanged()

        }

        fun clear() {
            val size: Int = wbantamwRanking!!.size
            wbantamwRanking!!.clear()
            notifyItemRangeChanged(0, size)
        }

        @SuppressLint("ResourceAsColor")
        override fun onBindViewHolder(holder: WbantamwRankingItem, position: Int) {

            val wbantamwRanking: UfcRanking = wbantamwRanking!![position]
            val wbantamwFighterList: List<String> = wbantamwRanking.fighter_list!!.split(",")

            if (wbantamwRanking.idx!! == 1) {
                holder.tvWbantamwNo.text = getString(R.string.champion)
                holder.tvWbantamwFighterName.setTypeface(null, Typeface.BOLD_ITALIC)
                holder.tvWbantamwFighterName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16F)
                holder.tvWbantamwFighterName.setTextColor(R.color.black)
            } else {
                holder.tvWbantamwNo.text = ((wbantamwRanking.idx!! - 1).toString())
            }

            val splitArray = wbantamwFighterList[12].split("|")
            var fighterName =""
            splitArray.forEach { element ->
                fighterName += element
            }
            holder.tvWbantamwFighterName.text = fighterName

            holder.constraintLayoutWbantamw.setOnClickListener {
                val intent = Intent(activity, FighterActivity::class.java)
                intent.putExtra("fighterName", splitArray[0])
                startActivity(intent)
            }
        }

        override fun getItemCount(): Int {
            return wbantamwRanking!!.size
        }

        inner class WbantamwRankingItem(itemView: View) : RecyclerView.ViewHolder(itemView) {
            var tvWbantamwNo: TextView = itemView.findViewById(R.id.tv_wbantamw_no)
            var tvWbantamwFighterName: TextView =
                itemView.findViewById(R.id.tv_wbantamw_fighter_name)
            var constraintLayoutWbantamw: ConstraintLayout =
                itemView.findViewById(R.id.wbantamw_constraintLayout)
        }

    }
}