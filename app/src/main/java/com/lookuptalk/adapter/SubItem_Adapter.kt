package com.lookuptalk.adapter

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.lookuptalk.R
import com.lookuptalk.model.SubItem
import com.lookuptalk.utils.UserSession
import org.json.JSONArray
import org.json.JSONObject

class SubItem_Adapter(
    private val mContext: Context,
    private var subItemList: List<SubItem>,
    lifestyle_type: String
) :
    RecyclerView.Adapter<SubItem_Adapter.SubItemViewHolder>() {

    lateinit var selected_item: List<String>
    lateinit var previous_item: List<String>
    private var type: String = lifestyle_type

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): SubItemViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(
            R.layout.flag_row,
            viewGroup,
            false
        )
        return SubItemViewHolder(view)
    }

    override fun onBindViewHolder(itemViewHolder: SubItemViewHolder, position: Int) {
        val subItem = subItemList[position]
        val ja = JSONArray()
        val fixedJSON = JSONObject()

        itemViewHolder.tvFlagName.setText(subItem.name)
        var text = ""
        var text1 = ""
        for (subItemData in subItemList) {
            if(subItemData.isSelected.equals("true")){
                var value1_ = subItemData._id
                text1 += value1_ + "" + ","
                previous_item = text1.split(",")
                Log.e("TAG", "sample: "+previous_item )
            }

        }



        Glide.with(mContext).load(subItem.url).into(itemViewHolder.ivFlagImage)


        if (subItem.isSelected.equals("true")) {
            itemViewHolder.llFlag.setBackgroundColor(mContext.resources.getColor(R.color.selected_flag))
            itemViewHolder.tvFlagName.setTextColor(Color.WHITE)
        } else {
            itemViewHolder.llFlag.setBackgroundColor(Color.WHITE)
            itemViewHolder.tvFlagName.setTextColor(Color.BLACK)
        }

//        itemViewHolder.llFlag.setBackgroundColor(if (subItem.isSelect) mContext.resources.getColor(R.color.selected_flag) else Color.WHITE)

        itemViewHolder.llFlag.setOnClickListener(View.OnClickListener {

            val subItem = subItemList[position]
            subItem.setSelect(!subItem.isSelect)
            itemViewHolder.llFlag.setBackgroundColor(
                if (subItem.isSelect) mContext.resources.getColor(
                    R.color.selected_flag
                ) else Color.WHITE
            )

            if (subItem.isSelect) {
                itemViewHolder.tvFlagName.setTextColor(Color.WHITE)
            } else {
                itemViewHolder.tvFlagName.setTextColor(Color.BLACK)
            }

            for (mHobiesModel in subItemList) {
                if (mHobiesModel.isSelect()) {

                    var value_ = mHobiesModel._id

                    text += value_ + "" + ","

                }
            }
            if (text.length > 0) {
                text = text.substring(0, text.lastIndexOf(","))

                selected_item = text.split(",")


//                Log.d("TAG", "selected " + selected_movies)

                for (i in 0 until selected_item.size) {
                    val jo = JSONObject()
                    jo.put("_id", selected_item[i])
                    ja.put(jo)
                }
                val fixedJSON = JSONObject()
                assert(ja != null)
                if (type.equals("hobbiesInterests")) {

                    fixedJSON.put("hobbiesInterests", ja)


                } else if (type.equals("sports")) {
                    fixedJSON.put("sports", ja)


                } else if (type.equals("countriesTravelled")) {
                    fixedJSON.put("countriesTravelled", ja)

                } else if (type.equals("pets")) {
                    fixedJSON.put("pets", ja)

                }
                Log.d("TAG", "selected " + fixedJSON)

                UserSession(mContext).setLifestyleType(fixedJSON)


            }


//            Log.d("TAG", "Output : $text")

        })

    }

    override fun getItemCount(): Int {
        return subItemList!!.size
    }


    inner class SubItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvFlagName: TextView
        var ivFlagImage: ImageView
        var llFlag: LinearLayout

        init {
            llFlag = itemView.findViewById(R.id.llFlag)
            tvFlagName = itemView.findViewById(R.id.tvFlagName)
            ivFlagImage = itemView.findViewById(R.id.ivFlagImage)
        }
    }

//    fun filterList(filteredList: ArrayList<SubItem>) {
//        subItemList = filteredList
//        notifyDataSetChanged()
//    }

}