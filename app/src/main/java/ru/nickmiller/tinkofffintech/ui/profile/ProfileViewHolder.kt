package ru.nickmiller.tinkofffintech.ui.profile

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.rengwuxian.materialedittext.MaterialEditText
import ru.nickmiller.tinkofffintech.R
import ru.nickmiller.tinkofffintech.ui.base.BaseViewHolder
import ru.nickmiller.tinkofffintech.utils.find


abstract class ProfileViewHolder(itemView: View) : BaseViewHolder<ProfileAdapter.ProfileAdapterModel>(itemView)

class HeaderViewHolder(itemView: View) : ProfileViewHolder(itemView) {
    val headerImg = itemView.find<ImageView>(R.id.header_img)
    val headerTitle = itemView.find<TextView>(R.id.header_title)

    override fun bind(item: ProfileAdapter.ProfileAdapterModel) {
        //headerImg.setImageDrawable(item.imgId?.let { ContextCompat.getDrawable(MyApp.appContext, it) })
        headerTitle.text = item.name
    }
}

class ItemViewHolder(itemView: View, val editable: Boolean = false) : ProfileViewHolder(itemView) {
    val itemContent = itemView.find<MaterialEditText>(R.id.item_content)

    override fun bind(item: ProfileAdapter.ProfileAdapterModel) {
        if (!editable) {
            itemContent.keyListener = null
        }
        itemContent.hint = item.name
        itemContent.floatingLabelText = item.name
        itemContent.setText(item.value)
    }

}
 
 
