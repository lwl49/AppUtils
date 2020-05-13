package com.allen.apputils.ui.adapter

import com.allen.apputils.R
import com.allen.apputils.bean.ItemModel
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import kotlinx.android.synthetic.main.item_index.view.*

/**
 * @user Allen
 * @date 2020/5/12 0012 15:05
 * @purpose
 */
class RecycleAdapter(layoutResId: Int, data: ArrayList<ItemModel>) : BaseQuickAdapter<ItemModel, BaseViewHolder>(layoutResId, data) {
    override fun convert(holder: BaseViewHolder, item: ItemModel) {
        holder.setImageResource(R.id.item_img,item.src)

    }

}