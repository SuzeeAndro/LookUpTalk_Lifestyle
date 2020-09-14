package com.lookuptalk.adapter

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.lookuptalk.R
import com.lookuptalk.model.Hobbies


class FashionAdapter(
    private val mContext: Context,
    private var mHobiesList: List<Hobbies>,
    val value: String) :
    RecyclerView.Adapter<FashionAdapter.MyViewHolder>() {

    internal lateinit var mHobiesModel: Hobbies


//    val dates = arrayOf("16th Nov,2019", "17th Nov,2019", "18th Nov,2019","19th Nov,2019", "20th Nov,2019", "21th Nov,2019")

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        //        lateinit var tvFlagName: MyTextView_Normal
         var ivFlagImage: ImageView
         var cvLayout: ConstraintLayout
         lateinit var cardLayout: CardView


        init {
//            tvFlagName = view.findViewById(R.id.tvFlagName)
            ivFlagImage = view.findViewById(R.id.ivFlagImage)
            cvLayout = view.findViewById(R.id.cvLayout)
            cardLayout = view.findViewById(R.id.cardLayout)


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.fashion_row, parent, false)


        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        mHobiesModel = mHobiesList[position]

//        if(value.equals("1")){
//            holder.tvFlagName.text = mHobiesModel.name
//            holder.tvFlagName.visibility=View.VISIBLE
//
//        }
//


        Glide.with(mContext)
            .load(mHobiesModel.url)
            .into(holder.ivFlagImage);
        holder.cvLayout.setBackgroundColor(if (mHobiesModel.isSelected) mContext.resources.getColor(R.color.selected_flag)
        else Color.WHITE)
        holder.cardLayout.setBackgroundColor(if (mHobiesModel.isSelected) mContext.resources.getColor(R.color.selected_flag)
        else Color.WHITE)


        holder.cvLayout.setOnClickListener(View.OnClickListener {

            mHobiesModel = mHobiesList[position]
            mHobiesModel.setSelected(!mHobiesModel.isSelected)
            holder.cvLayout.setBackgroundColor(
                if (mHobiesModel.isSelected()) mContext.resources.getColor(
                    R.color.selected_flag
                ) else Color.WHITE
            )
            holder.cardLayout.setBackgroundColor(
                if (mHobiesModel.isSelected()) mContext.resources.getColor(
                    R.color.selected_flag
                ) else Color.WHITE
            )

//            if (mHobiesModel.isSelected) {
//                holder.tvFlagName.setTextColor(Color.WHITE)
//            } else {
//                holder.tvFlagName.setTextColor(Color.BLACK)
//            }

            var text = ""
            for (mHobiesModel in mHobiesList) {
                if (mHobiesModel.isSelected()) {
                    text += mHobiesModel.name
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
//                    for (row in mHobiesList) {
//                        if (row.toLowerCase(Locale.ROOT).contains(charSearch.toLowerCase(Locale.ROOT))) {
//                            resultList.add(row)
//                        }
//                    }
////                    mHobiesList = resultList
//                }
//                val filterResults = FilterResults()
//                filterResults.values = mHobiesList
//                return filterResults
//            }
//
//            @Suppress("UNCHECKED_CAST")
//            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
//                mHobiesList = results?.values as ArrayList<FlagsModel>
//                notifyDataSetChanged()
//            }
//
//        }
//    }


    override fun getItemCount(): Int {
        return mHobiesList.size
    }
}

