package ru.nickmiller.tinkofffintech.ui.profile

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.nickmiller.tinkofffintech.R
import ru.nickmiller.tinkofffintech.data.entity.profile.StructuredProfile
import ru.nickmiller.tinkofffintech.utils.find


class ProfileAdapter(val items: List<ProfileAdapterModel>, val editable: Boolean = false) : RecyclerView.Adapter<ProfileViewHolder>() {
    private val VIEW_TYPE_HEADER = 0
    private val VIEW_TYPE_ITEM = 1
    private val VIEW_TYPE_ITEM_LAST = 2


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileViewHolder =
        if (viewType == VIEW_TYPE_HEADER) {
            HeaderViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.view_profile_block_header,
                    parent,
                    false
                )
            )
        } else {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.view_profile_block_item, parent, false)
            if (viewType == VIEW_TYPE_ITEM_LAST) {
                view.find<View>(R.id.item_divider).visibility = View.GONE
            }
            ItemViewHolder(view, editable)
        }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ProfileViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemViewType(position: Int) =
        if (items[position].modelType == ModelType.HEADER) VIEW_TYPE_HEADER
        else {
            if (position == items.size - 1 || items[position + 1].modelType == ModelType.HEADER) VIEW_TYPE_ITEM_LAST
            else VIEW_TYPE_ITEM
        }


    class ProfileAdapterModel(
        var modelType: ModelType = ModelType.ITEM,
        var name: String,
        var value: String? = null,
        var imgId: Int? = null
    ) {
        companion object {
            fun transformList(profile: StructuredProfile, allFields: Boolean = false): List<ProfileAdapterModel> =
                mutableListOf<ProfileAdapterModel>().apply {
                    profile.blocks.filter { profileBlock ->
                        profileBlock.attrs.filter { allFields || it.value != null && it.value!!.isNotEmpty() }.isNotEmpty()
                    }.forEach { profileBlock ->
                        add(
                            ProfileAdapterModel(
                                modelType = ModelType.HEADER,
                                name = profileBlock.blockName,
                                imgId = profileBlock.blockImgId
                            )
                        )
                        profileBlock.attrs.forEach { item ->
                            if (allFields || item.value?.isNotEmpty() == true)
                                add(
                                    ProfileAdapterModel(
                                        name = item.key,
                                        value = item.value
                                    )
                                )
                        }
                    }
                }
        }
    }

    enum class ModelType {
        HEADER,
        ITEM
    }
}