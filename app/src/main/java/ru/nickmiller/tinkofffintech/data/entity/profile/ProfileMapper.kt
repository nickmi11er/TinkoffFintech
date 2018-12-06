package ru.nickmiller.tinkofffintech.data.entity.profile

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import ru.nickmiller.tinkofffintech.R
import java.util.concurrent.TimeUnit

enum class ProfileField {
    HEADER_CONTACTS,
    PHONE,
    EMAIL,
    REGION,
    HEADER_EDUCATION,
    SCHOOL,
    SCHOOL_GRADUATION,
    UNIVERSITY,
    FACULTY,
    UNIVERSITY_GRADUATION,
    DEPARTMENT,
    HEADER_WORK,
    WORK,
}


fun Profile.map() = mutableListOf(
    HeaderProfileBlock(
        ProfileField.HEADER_CONTACTS, "Контактная информация", R.drawable.ic_contacts,
        listOf(
            ProfileBlock(ProfileField.PHONE, "Мобильный телефон", mobile),
            ProfileBlock(ProfileField.EMAIL, "Электронная почта", email),
            ProfileBlock(ProfileField.REGION, "Город проживания", region)
        )
    ),
    HeaderProfileBlock(
        ProfileField.HEADER_EDUCATION, "Образование", R.drawable.ic_education,
        listOf(
            ProfileBlock(ProfileField.SCHOOL, "Школа", school),
            ProfileBlock(ProfileField.SCHOOL_GRADUATION, "Год окончания школы", schoolGraduation),
            ProfileBlock(ProfileField.UNIVERSITY, "ВУЗ", university),
            ProfileBlock(ProfileField.FACULTY, "Факультет", faculty),
            ProfileBlock(ProfileField.UNIVERSITY_GRADUATION, "Год окончания ВУЗа", universityGraduation?.toString()),
            ProfileBlock(ProfileField.DEPARTMENT, "Кафедра", department)
        )
    ),
    HeaderProfileBlock(
        ProfileField.HEADER_WORK, "Работа", R.drawable.ic_work,
        listOf(
            ProfileBlock(ProfileField.WORK, "Текущее место работы", currentWork)
        )
    )
)


fun List<ProfileBlock>.filterNotEmpty(): List<ProfileBlock> {
    this.forEach {
        it.nestedBlocks = it.nestedBlocks?.filter { !it.value.isNullOrEmpty() }
    }
    return this.filter { !it.nestedBlocks.isNullOrEmpty() }
}


fun List<ProfileBlock>.flattenBlocks(): MutableList<ProfileBlock> {
    val list = mutableListOf<ProfileBlock>()
    forEach {
        if (it.blockType == ProfileBlock.BlockType.HEADER) {
            list.add(HeaderProfileBlock(it.blockName, it.blockLabel, it.blockImgId))
            list.addAll(ArrayList(it.nestedBlocks!!))
        }
    }
    return list
}


fun Profile.minsFromLastUpdate() =
    TimeUnit.MILLISECONDS.toMinutes(System.currentTimeMillis() - lastUpdatedTime)


@Parcelize
open class ProfileBlock(
    open val blockName: ProfileField,
    open val blockLabel: String,
    var value: String? = null,
    open val blockImgId: Int? = null,
    open var nestedBlocks: List<ProfileBlock>? = null,
    val blockType: BlockType = BlockType.ITEM
) : Parcelable {

    enum class BlockType {
        HEADER,
        ITEM
    }

}

data class HeaderProfileBlock(
    override val blockName: ProfileField,
    override val blockLabel: String,
    override val blockImgId: Int? = null,
    override var nestedBlocks: List<ProfileBlock>? = null
) : ProfileBlock(
    blockType = BlockType.HEADER,
    blockLabel = blockLabel,
    blockName = blockName,
    nestedBlocks = nestedBlocks
)
 
