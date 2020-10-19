package com.lookuptalk.adapter

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.lookuptalk.utils.UserSession
import com.lookuptalk.R
import com.lookuptalk.model.SubItem
import org.json.JSONArray
import org.json.JSONObject

class FashionSub_Adapter(private val mContext: Context, private var subItemList: List<SubItem>,lifestyle_type:String) :
    RecyclerView.Adapter<FashionSub_Adapter.SubItemViewHolder>() {

    var type: String = lifestyle_type
    lateinit var selected_movies: List<String>

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): SubItemViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(
            R.layout.fashion_row,
            viewGroup,
            false
        )
        return SubItemViewHolder(view)
    }

    override fun onBindViewHolder(itemViewHolder: SubItemViewHolder, position: Int) {
        val mHobiesModel = subItemList[position]
//        itemViewHolder.tvSubItemTitle.setText(subItem.name)


        Glide.with(mContext).load(mHobiesModel.url).into(itemViewHolder.ivFlagImage)

        if(mHobiesModel.isSelected.equals("true")){
            itemViewHolder.cardLayout.setBackgroundColor( mContext.resources.getColor(R.color.selected_flag))
        }else{
            itemViewHolder.cardLayout.setBackgroundColor(Color.WHITE)
        }

//        itemViewHolder.cvLayout.setBackgroundColor(if (mHobiesModel.isSelect) mContext.resources.getColor(R.color.selected_flag)
//        else Color.WHITE)
//        itemViewHolder.cardLayout.setBackgroundColor(if (mHobiesModel.isSelect) mContext.resources.getColor(R.color.selected_flag)
//        else Color.WHITE)


        itemViewHolder.cardLayout.setOnClickListener(View.OnClickListener {
            val mHobiesModel = subItemList[position]

            mHobiesModel.setSelect(!mHobiesModel.isSelect)
//            itemViewHolder.cvLayout.setBackgroundColor(
//                if (mHobiesModel.isSelect()) mContext.resources.getColor(
//                    R.color.selected_flag
//                ) else Color.WHITE
//            )
            itemViewHolder.cardLayout.setBackgroundColor(
                if (mHobiesModel.isSelect()) mContext.resources.getColor(
                    R.color.selected_flag
                ) else Color.WHITE
            )

//            if (mHobiesModel.isSelect) {
//                holder.tvFlagName.setTextColor(Color.WHITE)
//            } else {
//                holder.tvFlagName.setTextColor(Color.BLACK)
//            }

            var text: String = ""
            val ja = JSONArray()
            for (mHobiesModel in subItemList) {
                if (mHobiesModel.isSelect()) {

                    var value_ =  mHobiesModel._id

                    text +=   value_ + "" + ","

                }
            }
            if (text.length > 0) {
                text = text.substring(0, text.lastIndexOf(","))

                selected_movies = text.split(",")


//                Log.d("TAG", "selected " + selected_movies)

                for (i in 0 until selected_movies.size) {
                    val jo = JSONObject()
                    jo.put("_id",selected_movies[i])
                    ja.put(jo)
                }
                val fixedJSON = JSONObject()
                assert(ja != null)
                    fixedJSON.put("fashionBrands", ja)

                Log.d("TAG", "selected " + fixedJSON)

                UserSession(mContext).setLifestyleType(fixedJSON)


            }

        })

    }

    override fun getItemCount(): Int {
        return subItemList!!.size
    }


    inner class SubItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var ivFlagImage: ImageView
        var cardLayout: CardView
        var llFlag: LinearLayout

        init {
            ivFlagImage = itemView.findViewById(R.id.ivFlagImage)
            cardLayout = itemView.findViewById(R.id.cardLayout)
            llFlag = itemView.findViewById(R.id.llFlag)

        }
    }

//    fun filterList(filteredList: ArrayList<SubItem>) {
//        subItemList = filteredList
//        notifyDataSetChanged()
//    }

}