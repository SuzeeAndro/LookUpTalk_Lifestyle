package com.lookuptalk.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.RecycledViewPool
import com.lookuptalk.utils.UserSession
import com.lookuptalk.R
import com.lookuptalk.customfonts.Ferrara_Regular
import com.lookuptalk.model.SearchItem
import com.lookuptalk.model.SubItem
import java.util.*

class CusineListAdapter (private val mContext: Context, private val itemList: List<SearchItem>, var type: String) :
    RecyclerView.Adapter<CusineListAdapter.ItemViewHolder>() {
    private val viewPool = RecycledViewPool()
    private var subItemAdapter: CusineSub_Adapter? = null
    private val subItemList: ArrayList<SubItem>? = null
    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ItemViewHolder {
        val view =
            LayoutInflater.from(viewGroup.context).inflate(R.layout.layout_item, viewGroup, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(itemViewHolder: ItemViewHolder, i: Int) {
        val item = itemList[i]
        itemViewHolder.tvItemTitle.text = item.itemTitle



        // Create layout manager with initial prefetch item count
        val layoutManager = GridLayoutManager(itemViewHolder.rvSubItem.context, 2)
        layoutManager.initialPrefetchItemCount = item.subItemList.size

        // Create sub item view adapter
        subItemAdapter = CusineSub_Adapter(mContext,item.subItemList,type)
        itemViewHolder.rvSubItem.layoutManager = layoutManager
        itemViewHolder.rvSubItem.adapter = subItemAdapter
        itemViewHolder.rvSubItem.setRecycledViewPool(viewPool)

        val subtitle= UserSession(mContext).getLifestylevalue()
        if(subtitle.equals("movies")){
            itemViewHolder.tv_item_subtitle.visibility=View.VISIBLE
            itemViewHolder.tv_item_subtitle.setText(R.string.select_tvgenre)
        }else if(subtitle.equals("tvshows")){
            itemViewHolder.tv_item_subtitle.visibility=View.VISIBLE
            itemViewHolder.tv_item_subtitle.setText(R.string.select_tvchannel)
        }else if(subtitle.equals("music")){
            itemViewHolder.tv_item_subtitle.visibility=View.VISIBLE
            itemViewHolder.tv_item_subtitle.setText(R.string.select_tvgenre)
        }else if(subtitle.equals("books")){
            itemViewHolder.tv_item_subtitle.visibility=View.VISIBLE
            itemViewHolder.tv_item_subtitle.setText(R.string.select_tvgenre)
        }else{
            itemViewHolder.tv_item_subtitle.visibility=View.GONE
        }

    }


    override fun getItemCount(): Int {
        return itemList.size
    }

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
         val tvItemTitle: Ferrara_Regular
         val tv_item_subtitle: Ferrara_Regular
         val rvSubItem: RecyclerView


        init {
            tvItemTitle = itemView.findViewById(R.id.tv_item_title)
            tv_item_subtitle = itemView.findViewById(R.id.tv_item_subtitle)
            rvSubItem = itemView.findViewById(R.id.rv_sub_item)
        }
    }

//    fun subfilterList(filteredList: ArrayList<SubItem>) {
//        subItemAdapter!!.filterList(filteredList)
//    }
}