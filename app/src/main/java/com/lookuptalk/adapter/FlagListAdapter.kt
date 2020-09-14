package com.lookuptalk.adapter

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.lookuptalk.R
import com.lookuptalk.customfonts.MyTextView_Normal
import com.lookuptalk.model.FlagsModel
import com.lookuptalk.model.ModelFlag
import java.util.*
import kotlin.collections.ArrayList

class FlagListAdapter(private val mContext: Context, private var mflagsList: List<ModelFlag>) :
    RecyclerView.Adapter<FlagListAdapter.MyViewHolder>() {

    internal lateinit var mFlagmodel: ModelFlag


//    val dates = arrayOf("16th Nov,2019", "17th Nov,2019", "18th Nov,2019","19th Nov,2019", "20th Nov,2019", "21th Nov,2019")

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        lateinit var tvFlagName: MyTextView_Normal
        lateinit var ivFlagImage: ImageView
        lateinit var llFlag: LinearLayout


        init {
            tvFlagName = view.findViewById(R.id.tvFlagName)
            ivFlagImage = view.findViewById(R.id.ivFlagImage)
            llFlag = view.findViewById(R.id.llFlag)


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.flag_row, parent, false)


        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        mFlagmodel = mflagsList[position]


//        holder.tvtimeline_time.text = mTimeline.updatedTime
        Glide.with(mContext)
            .load(mFlagmodel.url)
            .into(holder.ivFlagImage);



        holder.llFlag.setBackgroundColor(if (mFlagmodel.isSelected) mContext.resources.getColor(R.color.selected_flag) else Color.WHITE)
        holder.llFlag.setOnClickListener(View.OnClickListener {

            mFlagmodel = mflagsList[position]
            mFlagmodel.setSelected(!mFlagmodel.isSelected)
            holder.llFlag.setBackgroundColor(
                if (mFlagmodel.isSelected()) mContext.resources.getColor(
                    R.color.selected_flag
                ) else Color.WHITE
            )

            if (mFlagmodel.isSelected) {
                holder.tvFlagName.setTextColor(Color.WHITE)
            } else {
                holder.tvFlagName.setTextColor(Color.BLACK)
            }

            var text = ""
            for (mFlagmodel in mflagsList) {
                if (mFlagmodel.isSelected()) {
                    text += mFlagmodel.name
                }
            }
            Log.d("TAG", "Output : $text")

        })

    }

//    override fun getFilter(): Filter {
//        return object : Filter() {
//            override fun performFiltering(constraint: CharSequence?): FilterResults {
//                val charSearch = constraint.toString()
//                if (charSearch.isEmpty()) {
//
//                } else {
//                    val resultList = ArrayList<FlagsModel>()
//                    for (row in mflagsList) {
//                        if (row.toLowerCase(Locale.ROOT).contains(charSearch.toLowerCase(Locale.ROOT))) {
//                            resultList.add(row)
//                        }
//                    }
////                    mflagsList = resultList
//                }
//                val filterResults = FilterResults()
//                filterResults.values = mflagsList
//                return filterResults
//            }
//
//            @Suppress("UNCHECKED_CAST")
//            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
//                mflagsList = results?.values as ArrayList<FlagsModel>
//                notifyDataSetChanged()
//            }
//
//        }
//    }


    override fun getItemCount(): Int {
        return mflagsList.size
    }
}

