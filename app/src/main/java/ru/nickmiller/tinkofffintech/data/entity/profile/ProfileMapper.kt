package ru.nickmiller.tinkofffintech.data.entity.profile

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import ru.nickmiller.tinkofffintech.R


fun Profile.map(): StructuredProfile =
    StructuredProfile(
        mutableListOf(
            ProfileBlock(
                "Контактная информация", R.drawable.ic_contacts, linkedMapOf(
                    "Мобильный телефон" to mobile,
                    "Электронная почта" to email,
                    "Город проживания" to region
                )
            ),
            ProfileBlock(
                "Образование", R.drawable.ic_education, linkedMapOf(
                    "Школа" to school,
                    "Год окончания школы" to schoolGraduation,
                    "ВУЗ" to university,
                    "Факультет" to faculty,
                    "Год окончания ВУЗа" to universityGraduation?.toString(),
                    "Кафедра" to department
                )
            ),
            ProfileBlock(
                "Работа", R.drawable.ic_work, linkedMapOf(
                    "Текущее место работы" to currentWork
                )
            )
        )
    )


@Parcelize
data class StructuredProfile(
    val blocks: MutableList<ProfileBlock>
) : Parcelable

@Parcelize
data class ProfileBlock(
    val blockName: String,
    val blockImgId: Int?,
    val attrs: LinkedHashMap<String, String?>
) : Parcelable
 
 
