package ru.nickmiller.tinkofffintech.data.entity.event

import ru.nickmiller.tinkofffintech.R


fun Events.map(): List<Event> {
    active.forEach { it.isActual = true }
    return active.union(archive).toList()
}


fun Event.getEventTypeColor(): Int =
    when (eventType?.name) {
        "Финтех Школа" -> R.color.colorRedSoft
        "Стажировка" -> R.color.colorOrangeSoft
        "Курсы для школьников" -> R.color.colorVioletSoft
        "Спецкурс" -> R.color.colorGreenSoft
        else -> R.color.colorBlueSoft
    }
 
 
