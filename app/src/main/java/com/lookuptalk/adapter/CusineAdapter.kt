package com.lookuptalk.adapter

import android.content.Context
import android.graphics.Color
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import coil.Coil
import coil.ImageLoader
import coil.api.load
import com.android.volley.toolbox.ImageRequest
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.facebook.shimmer.Shimmer
import com.facebook.shimmer.ShimmerDrawable
import com.lookuptalk.R
import com.lookuptalk.customfonts.MyTextView_Normal
import com.lookuptalk.model.Hobbies
import okhttp3.HttpUrl

class CusineAdapter(private val mContext: Context, private var mHobiesList: List<Hobbies>) :
    RecyclerView.Adapter<CusineAdapter.MyViewHolder>() {

    internal lateinit var mHobiesModel: Hobbies



    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        lateinit var tvFlagName: MyTextView_Normal
        lateinit var ivFlagImage: ImageView
        var cvLayout: ConstraintLayout
//        lateinit var cardLayout: CardView


        init {
            tvFlagName = view.findViewById(R.id.tvFlagName)
            ivFlagImage = view.findViewById(R.id.ivFlagImage)
            cvLayout = view.findViewById(R.id.cvLayout)
//            cardLayout = view.findViewById(R.id.cardLayout)


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.cusine_row, parent, false)


        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        mHobiesModel = mHobiesList[position]

//        Glide.with(mContext).load(mHobiesModel.url)
//            .into(holder.ivFlagImage)

        Glide.with(mContext)
            .load(mHobiesModel.url)
            .placeholder(R.drawable.avatar)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(holder.ivFlagImage);

        holder.tvFlagName.text = mHobiesModel.name



        holder.cvLayout.setBackgroundColor(
            if (mHobiesModel.isSelected) mContext.resources.getColor(
                R.color.selected_flag
            ) else Color.WHITE
        )

        holder.cvLayout.setOnClickListener(View.OnClickListener {

            mHobiesModel = mHobiesList[position]
            mHobiesModel.setSelected(!mHobiesModel.isSelected)

            holder.cvLayout.setBackgroundColor(
                if (mHobiesModel.isSelected()) mContext.resources.getColor(
                    R.color.selected_flag
                ) else Color.WHITE
            )

            if (mHobiesModel.isSelected) {
                holder.tvFlagName.setTextColor(Color.WHITE)
            } else {
                holder.tvFlagName.setTextColor(Color.BLACK)
            }

            var text = ""
            for (mHobiesModel in mHobiesList) {
                if (mHobiesModel.isSelected()) {
                    text += mHobiesModel.name
                }
            }
//            Log.d("TAG", "Output : $text")

        })


    }


    override fun getItemCount(): Int {
        return mHobiesList.size
    }
}

