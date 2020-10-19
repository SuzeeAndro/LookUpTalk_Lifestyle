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
import com.lookuptalk.ui.Lifestyle
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GoogleFavBooksAdapter(
    private val mContext: Context,
    internal var mImdbList: List<ImdbFavModel>) :
    RecyclerView.Adapter<GoogleFavBooksAdapter.SubItemViewHolder>() {

    private lateinit var gsonObject: JsonObject

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): SubItemViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(
            R.layout.imdb_row,
            viewGroup,
            false
        )
        return SubItemViewHolder(view)
    }

    override fun onBindViewHolder(itemViewHolder: SubItemViewHolder, position: Int) {
        val mFavMoviesList = mImdbList[position]

            getBooksByID(mFavMoviesList.id, itemViewHolder)

        itemViewHolder.tvDelete.setOnClickListener(View.OnClickListener {
            val mFavMoviesList = mImdbList[position]


            val jo = JSONObject()
            jo.put("favBooks", mFavMoviesList.id)
            val jsonParser = JsonParser()
            gsonObject = jsonParser.parse(jo.toString()) as JsonObject

            /*Calling Delete Funtion*/
            (mContext as Lifestyle).deleteFavorites(gsonObject)

        })

    }

    override fun getItemCount(): Int {
        return mImdbList!!.size
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

    private fun getBooksByID(movieID: String, view: SubItemViewHolder) {

        Constants.GoogleBooks_service.getBooksByID(movieID)
            .enqueue(object :
                Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {

                    try {
                        val string = response.body()!!.string()

                        val jsonobj = JSONObject(string)
                        val volumeInfo=jsonobj.getJSONObject("volumeInfo")
                        val imageInfo=volumeInfo.getJSONObject("imageLinks")
                        val booktitle = volumeInfo.getString("title")
                        val bookImage = imageInfo.getString("thumbnail")

                        /*Assigning Values*/
                        view.ivImdb_title.setText(booktitle)
                        Glide.with(mContext).load(bookImage)
                            .placeholder(R.drawable.avatar)
                            .into(view.ivImdbImage)


                    } catch (e: Exception) {
                        e.printStackTrace()
                    }


                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Log.e(
                        "TAG",
                        "onFailure() called with: call = [" + call.request()
                            .url + "], t = [" + t + "]",
                        t
                    )


                }
            })

    }

}