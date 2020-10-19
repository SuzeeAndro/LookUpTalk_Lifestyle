package com.lookuptalk.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.lookuptalk.utils.Constants
import com.lookuptalk.R
import com.lookuptalk.customfonts.Ferrara_Regular
import com.lookuptalk.model.ImdbFavModel
import com.lookuptalk.model.SpotifyModel
import com.lookuptalk.ui.Lifestyle
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Spotify_Adapter(
    private val mContext: Context,
    internal var mSpotifyArtistList: List<SpotifyModel>) :
    RecyclerView.Adapter<Spotify_Adapter.SubItemViewHolder>() {

    private lateinit var gsonObject: JsonObject


    //val idslist= arrayOf("1234","asdfds","55454","49944")
    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): SubItemViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(
            R.layout.imdb_row,
            viewGroup,
            false
        )
        return SubItemViewHolder(view)
    }

    override fun onBindViewHolder(itemViewHolder: SubItemViewHolder, position: Int) {
        val mArtistList = mSpotifyArtistList[position]

//        getArtistByID(mArtistList.id, itemViewHolder)

        itemViewHolder.ivImdb_title.setText(mArtistList.name)

        Glide.with(mContext).load(mArtistList.images.get(0).url)
            .placeholder(R.drawable.avatar)
            .into(itemViewHolder.ivImdbImage)
        itemViewHolder.tvDelete.visibility=View.GONE

    }

    override fun getItemCount(): Int {
        return mSpotifyArtistList.size
    }


    inner class SubItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var ivImdbImage: ImageView
        var tvDelete: ImageView
        var ivImdb_title: Ferrara_Regular


        init {
            ivImdbImage = itemView.findViewById(R.id.ivImdbImage)
            ivImdb_title = itemView.findViewById(R.id.ivImdb_title)
            tvDelete = itemView.findViewById(R.id.tvDelete)
        }
    }

}