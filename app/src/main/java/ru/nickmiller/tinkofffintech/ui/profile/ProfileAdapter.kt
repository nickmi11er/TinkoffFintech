package ru.nickmiller.tinkofffintech.ui.profile

import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.view_profile_block_item.*
import ru.nickmiller.tinkofffintech.R
import ru.nickmiller.tinkofffintech.data.entity.profile.ProfileBlock
import ru.nickmiller.tinkofffintech.data.entity.profile.ProfileField
import ru.nickmiller.tinkofffintech.utils.find


class ProfileAdapter(var items: List<ProfileBlock>, val editable: Boolean = false) :
    RecyclerView.Adapter<ProfileViewHolder>() {
    private val VIEW_TYPE_HEADER = 0
    private val VIEW_TYPE_ITEM = 1
    private val VIEW_TYPE_ITEM_LAST = 2

    private var fieldChangedListener: ((name: ProfileField, value: String) -> Unit)? = null
    private var onFieldClicked: ((position: Int) -> Unit)? = null


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
        val item = items[position]
        holder.bind(item)
        if (item.blockType == ProfileBlock.BlockType.ITEM) {
            holder.itemView.setOnClickListener {
                onFieldClicked?.invoke(position)
            }
            holder.itemContent.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable) {
                    fieldChangedListener?.invoke(item.blockName, s.toString())
                }

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            })
        }
    }

    override fun getItemViewType(position: Int) =
        if (items[position].blockType == ProfileBlock.BlockType.HEADER) VIEW_TYPE_HEADER
        else {
            if (position == items.size - 1 || items[position + 1].blockType == ProfileBlock.BlockType.HEADER) VIEW_TYPE_ITEM_LAST
            else VIEW_TYPE_ITEM
        }

    fun setOnFieldChangedListener(listener: (name: ProfileField, value: String) -> Unit) {
        fieldChangedListener = listener
    }

    fun setOnFieldClickListener(listener: (position: Int) -> Unit) {
        onFieldClicked = listener
    }


//    data class ProfileAdapterModel(
//        var modelType: ModelType = ModelType.ITEM,
//        var name: String,
//        var value: String? = null,
//        var imgId: Int? = null
//    ) {
//        companion object {
//            fun transformList(profile: StructuredProfile, allFields: Boolean = false): List<ProfileAdapterModel> =
//                mutableListOf<ProfileAdapterModel>().apply {
//                    profile.blocks.filter { profileBlock ->
//                        profileBlock.attrs.filter { allFields || it.value != null && it.value!!.isNotEmpty() }
//                            .isNotEmpty()
//                    }.forEach { profileBlock ->
//                        add(
//                            ProfileAdapterModel(
//                                modelType = ModelType.HEADER,
//                                name = profileBlock.blockName,
//                                imgId = profileBlock.blockImgId
//                            )
//                        )
//                        profileBlock.attrs.forEach { item ->
//                            if (allFields || item.value?.isNotEmpty() == true)
//                                add(
//                                    ProfileAdapterModel(
//                                        name = item.key,
//                                        value = item.value
//                                    )
//                                )
//                        }
//                    }
//                }
//
//            fun mapList(models: List<ProfileAdapterModel>): HashMap<String, String> {
//                val map = hashMapOf<String, String>()
//                models.forEach {
//                    if (it.modelType == ModelType.ITEM) {
//                        map[it.name] = it.value!!
//                    }
//                }
//                return map
//            }
//        }
//    }
//
//    enum class ModelType {
//        HEADER,
//        ITEM
//    }
}