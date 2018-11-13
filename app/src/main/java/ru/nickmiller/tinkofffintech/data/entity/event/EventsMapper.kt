package ru.nickmiller.tinkofffintech.data.entity.event


fun Events.map(): List<Event> {
    active.forEach { it.isActual = true }
    return active.union(archive).toList()
}
 
 
