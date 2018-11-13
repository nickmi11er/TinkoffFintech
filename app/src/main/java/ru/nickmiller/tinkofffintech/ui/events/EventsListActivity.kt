package ru.nickmiller.tinkofffintech.ui.events

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.Menu
import com.miguelcatalan.materialsearchview.MaterialSearchView
import ru.nickmiller.tinkofffintech.R
import ru.nickmiller.tinkofffintech.data.entity.event.Event
import ru.nickmiller.tinkofffintech.utils.find


class EventsListActivity : AppCompatActivity() {
    val toolbar by lazy { find<Toolbar>(R.id.toolbar) }
    val searchView by lazy { find<MaterialSearchView>(R.id.search_view) }
    val recycler by lazy { find<RecyclerView>(R.id.recycler) }
    var adapter: EventsAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_events_list)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        recycler.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        searchView.setHint("Поиск мероприятий")
        searchView.setOnQueryTextListener(object : MaterialSearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                adapter?.filter?.filter(query)
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                adapter?.filter?.filter(newText)
                return false
            }
        })


        val eventsType = intent.getSerializableExtra(EVENTS_TYPE) as EventsType
        val events = intent.getParcelableArrayListExtra<Event>(EVENTS)

        val (title, adapter) = when (eventsType) {
            EventsType.ACTUAL -> Pair("Актуальное", EventsAdapter(this, events.toList(), true, true))
            EventsType.ARCHIVE -> Pair("Прошедшие", EventsAdapter(this, events.toList(), large = true))
        }

        this.title = title
        this.adapter = adapter
        recycler.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar, menu)
        val item = menu?.findItem(R.id.action_search)
        searchView.setMenuItem(item)
        return true

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    companion object {
        private val EVENTS_TYPE = "events_type"
        private val EVENTS = "events"

        fun start(context: Context?, type: EventsType, events: List<Event>) {
            val intent = Intent(context, EventsListActivity::class.java)
            intent.putExtra(EVENTS_TYPE, type)
            intent.putExtra(EVENTS, ArrayList(events))
            context?.startActivity(intent)
        }
    }

    enum class EventsType {
        ACTUAL,
        ARCHIVE
    }
}
