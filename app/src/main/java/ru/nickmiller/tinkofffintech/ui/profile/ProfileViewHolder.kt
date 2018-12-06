package ru.nickmiller.tinkofffintech.ui.profile

import android.support.v4.content.ContextCompat
import android.view.View
import kotlinx.android.synthetic.main.view_profile_block_header.*
import kotlinx.android.synthetic.main.view_profile_block_item.*
import ru.nickmiller.tinkofffintech.data.entity.profile.ProfileBlock
import ru.nickmiller.tinkofffintech.ui.base.BaseViewHolder


abstract class ProfileViewHolder(itemView: View) :
    BaseViewHolder<ProfileBlock>(itemView)


class HeaderViewHolder(itemView: View) : ProfileViewHolder(itemView) {

    override fun bind(item: ProfileBlock) {
        headerImg.setImageDrawable(item.blockImgId?.let { ContextCompat.getDrawable(itemView.context, it) })
        headerTitle.text = item.blockLabel
    }
}

class ItemViewHolder(itemView: View, val editable: Boolean = false) : ProfileViewHolder(itemView) {

    override fun bind(item: ProfileBlock) {
        if (!editable) {
            itemContent.keyListener = null
        }
        itemContent.hint = item.blockLabel
        itemContent.floatingLabelText = item.blockLabel
        itemContent.setText(item.value)
    }

}
 
 
