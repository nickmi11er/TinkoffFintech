package ru.nickmiller.tinkofffintech.ui.profile

import android.support.v4.content.ContextCompat
import android.view.View
import kotlinx.android.synthetic.main.view_profile_block_header.*
import kotlinx.android.synthetic.main.view_profile_block_item.*
import ru.nickmiller.tinkofffintech.ui.base.BaseViewHolder


abstract class ProfileViewHolder(itemView: View) :
    BaseViewHolder<ProfileAdapter.ProfileAdapterModel>(itemView)


class HeaderViewHolder(itemView: View) : ProfileViewHolder(itemView) {

    override fun bind(item: ProfileAdapter.ProfileAdapterModel) {
        headerImg.setImageDrawable(item.imgId?.let { ContextCompat.getDrawable(itemView.context, it) })
        headerTitle.text = item.name
    }
}

class ItemViewHolder(itemView: View, val editable: Boolean = false) : ProfileViewHolder(itemView) {

    override fun bind(item: ProfileAdapter.ProfileAdapterModel) {
        if (!editable) {
            itemContent.keyListener = null
        }
        itemContent.hint = item.name
        itemContent.floatingLabelText = item.name
        itemContent.setText(item.value)
    }

}
 
 
