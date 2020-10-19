package com.lookuptalk.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.RecycledViewPool
import com.lookuptalk.R
import com.lookuptalk.customfonts.Ferrara_Regular
import com.lookuptalk.model.SearchItem
import com.lookuptalk.model.SubItem
import java.util.*

class FashionListAdapter (private val mContext: Context, private val itemList: List<SearchItem>, var type: String) :
    RecyclerView.Adapter<FashionListAdapter.ItemViewHolder>() {
    private val viewPool = RecycledViewPool()
    private var subItemAdapter: FashionSub_Adapter? = null
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
        subItemAdapter = FashionSub_Adapter(mContext,item.subItemList,type)
        itemViewHolder.rvSubItem.layoutManager = layoutManager
        itemViewHolder.rvSubItem.adapter = subItemAdapter
        itemViewHolder.rvSubItem.setRecycledViewPool(viewPool)
    }


    override fun getItemCount(): Int {
        return itemList.size
    }

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
         val tvItemTitle: Ferrara_Regular
         val rvSubItem: RecyclerView

        init {
            tvItemTitle = itemView.findViewById(R.id.tv_item_title)
            rvSubItem = itemView.findViewById(R.id.rv_sub_item)
        }
    }

//    fun subfilterList(filteredList: ArrayList<SubItem>) {
//        subItemAdapter!!.filterList(filteredList)
//    }
}