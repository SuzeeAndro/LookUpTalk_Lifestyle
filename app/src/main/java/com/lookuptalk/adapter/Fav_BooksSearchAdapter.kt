package com.lookuptalk.adapter

import android.content.Context
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.AppCompatImageView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.lookuptalk.utils.UserSession
import com.lookuptalk.R
import com.lookuptalk.customfonts.Ferrara_Regular
import com.lookuptalk.model.GoogleBooks
import org.json.JSONArray
import org.json.JSONObject

class Fav_BooksSearchAdapter(
    private val mContext: Context,
    internal var mImdbList: List<GoogleBooks>) :
    RecyclerView.Adapter<Fav_BooksSearchAdapter.ItemViewHolder>() {

    lateinit var selected_movies: List<String>


//    val imdb_images= arrayOf(R.drawable.tommy,R.drawable.tommy,R.drawable.tommy,R.drawable.tommy,R.drawable.tommy,R.drawable.tommy,R.drawable.tommy)

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ItemViewHolder {
        val view =
            LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.imdbsearch_row, viewGroup, false)
        return ItemViewHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(itemViewHolder: ItemViewHolder, position: Int) {
        val mBooksList = mImdbList[position]

        if(mBooksList.volumeInfo.imageLinks==null){
//            Glide.with(mContext).load()
//                .placeholder(R.drawable.avatar)
//                .into(itemViewHolder.itemMoviePoster)
            itemViewHolder.itemMovieTitle.setText(mBooksList.volumeInfo.title)
        }else{
            Glide.with(mContext).load(mBooksList.volumeInfo.imageLinks.thumbnail)
                .placeholder(R.drawable.avatar)
                .into(itemViewHolder.itemMoviePoster)
            itemViewHolder.itemMovieTitle.setText(mBooksList.volumeInfo.title)
        }



        itemViewHolder.llimdb_row.setOnClickListener(View.OnClickListener {

            val mBooksList = mImdbList[position]
            mBooksList.setSelected(!mBooksList.isSelected)
            if (mBooksList.isSelected()) {

                itemViewHolder.ivSelect.visibility = View.VISIBLE

            } else {
                itemViewHolder.ivSelect.visibility = View.GONE
            }
//            itemViewHolder.llimdb_row.setBackgroundColor(if (mMoviesList.isSelected()) mContext.resources.getDrawable(R.drawable.ic_select)else Color.WHITE)


            var text: String = ""
            val ja = JSONArray()
            for (mHobiesModel in mImdbList) {
                if (mHobiesModel.isSelected()) {

                    var value_ =  mHobiesModel.id

                    text +=   value_ + "" + ","

                }
            }
            if (text.length > 0) {
                text = text.substring(0, text.lastIndexOf(","))

                selected_movies = text.split(",")


                for (i in 0 until selected_movies.size) {
                    val jo = JSONObject()
                    jo.put("_id",selected_movies[i])
                    ja.put(jo)
                }
                val fixedJSON = JSONObject()
                assert(ja != null)
                    fixedJSON.put("favBooks", ja)

                Log.d("TAG", "selected " + fixedJSON)

                UserSession(mContext).setSelectedMovies(fixedJSON)


            }


        })


    }


    override fun getItemCount(): Int {
        return mImdbList.size
    }

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemMoviePoster: AppCompatImageView
        val itemMovieTitle: Ferrara_Regular
        val llimdb_row: CardView
        val ivSelect: ImageView

        init {
            ivSelect = itemView.findViewById(R.id.ivSelect)
            llimdb_row = itemView.findViewById(R.id.llimdb_row)
            itemMovieTitle = itemView.findViewById(R.id.itemMovieTitle)
            itemMoviePoster = itemView.findViewById(R.id.itemMoviePoster)
        }
    }

//    fun subfilterList(filteredList: ArrayList<SubItem>) {
//        subItemAdapter!!.filterList(filteredList)
//    }
}