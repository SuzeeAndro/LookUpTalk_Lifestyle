package com.lookuptalk.adapter

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.lookuptalk.utils.UserSession
import com.lookuptalk.R
import com.lookuptalk.customfonts.MyTextView_Normal
import com.lookuptalk.model.SubItem
import org.json.JSONArray
import org.json.JSONObject

class CusineSub_Adapter(private val mContext: Context, private var subItemList: List<SubItem>,lifestyle_type:String) :
    RecyclerView.Adapter<CusineSub_Adapter.CusineViewHolder>() {

    lateinit var selected_movies: List<String>
    private  var type:String=lifestyle_type
    private lateinit var ja:JSONArray

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): CusineViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(
            R.layout.cusine_row,
            viewGroup,
            false
        )
        return CusineViewHolder(view)
    }

    override fun onBindViewHolder(cusineHolder: CusineSub_Adapter.CusineViewHolder, position: Int) {
        val subItem = subItemList[position]

        if(subItem.isSelected.equals("true")){
            cusineHolder.cvLayout.setBackgroundColor( mContext.resources.getColor(R.color.selected_flag))
            cusineHolder.tvFlagName.setTextColor(Color.WHITE)
        }

        Glide.with(mContext)
            .load(subItem.url)
            .placeholder(R.drawable.avatar)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(cusineHolder.ivFlagImage);

        cusineHolder.tvFlagName.setText(subItem.name)



//        cusineHolder.llFlag.setBackgroundColor(if (subItem.isSelect) mContext.resources.getColor(R.color.selected_flag) else Color.WHITE)
        cusineHolder.llFlag.setOnClickListener(View.OnClickListener {

            val subItem = subItemList[position]
            subItem.setSelect(!subItem.isSelect)
            cusineHolder.cvLayout.setBackgroundColor(if (subItem.isSelect()) mContext.resources.getColor(R.color.selected_flag) else Color.WHITE)

            if (subItem.isSelect){
                cusineHolder.tvFlagName.setTextColor(Color.WHITE)
            }else{
                cusineHolder.tvFlagName.setTextColor(Color.BLACK)
            }

            var text = ""
             ja = JSONArray()
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
                    jo.put("_id", selected_movies[i])
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

                }else if (type.equals("cuisines")) {
                    fixedJSON.put("cuisines", ja)

                }else if (type.equals("books")) {
                    fixedJSON.put("books", ja)

                }
                Log.d("TAG", "selected " + fixedJSON)

                UserSession(mContext).setLifestyleType(fixedJSON)
            }

        })



    }

    override fun getItemCount(): Int {
        return subItemList!!.size
    }


    inner class CusineViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvFlagName: MyTextView_Normal
        var ivFlagImage: ImageView
        var llFlag: LinearLayout
        var cvLayout: ConstraintLayout

        init {
            tvFlagName = itemView.findViewById(R.id.tvFlagName)
            ivFlagImage = itemView.findViewById(R.id.ivFlagImage)
            llFlag = itemView.findViewById(R.id.llFlag)
            cvLayout = itemView.findViewById(R.id.cvLayout)
        }
    }

//    fun filterList(filteredList: ArrayList<SubItem>) {
//        subItemList = filteredList
//        notifyDataSetChanged()
//    }

}