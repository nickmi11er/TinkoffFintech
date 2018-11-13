package ru.nickmiller.tinkofffintech.ui.profile

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.rengwuxian.materialedittext.MaterialEditText
import ru.nickmiller.tinkofffintech.R
import ru.nickmiller.tinkofffintech.data.entity.profile.Profile
import ru.nickmiller.tinkofffintech.data.entity.profile.map
import ru.nickmiller.tinkofffintech.utils.find

class EditProfileActivity : AppCompatActivity() {
    lateinit var toolbar: Toolbar
    lateinit var userFio: MaterialEditText
    lateinit var userBirthDate: MaterialEditText
    lateinit var userSummary: MaterialEditText
    lateinit var userAvatar: ImageView
    lateinit var recycler: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        title = "Редактировать"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        userFio = find(R.id.field_fio)
        userBirthDate = find(R.id.field_birth_date)
        userSummary = find(R.id.field_summary)
        userAvatar = find(R.id.profile_avatar)
        recycler = find(R.id.profile_recycler)
        recycler.isNestedScrollingEnabled = false
        recycler.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        val profile = intent.getParcelableExtra<Profile>("profile")
        userFio.setText("${profile.firstName} ${profile.lastName}")
        userBirthDate.setText(profile.birthday)
        Glide.with(this).load("https://fintech.tinkoff.ru" + profile.avatar).into(userAvatar)

        val adapter = ProfileAdapter(ProfileAdapter.ProfileAdapterModel.transformList(profile.map(), true), true)
        recycler.adapter = adapter
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
