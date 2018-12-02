package ru.nickmiller.tinkofffintech.ui.profile

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_edit_profile.*
import kotlinx.android.synthetic.main.view_toolbar.view.*
import ru.nickmiller.tinkofffintech.BuildConfig
import ru.nickmiller.tinkofffintech.R
import ru.nickmiller.tinkofffintech.data.entity.profile.Profile
import ru.nickmiller.tinkofffintech.data.entity.profile.map

class EditProfileActivity : AppCompatActivity() {

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
        setSupportActionBar(appBar.toolbar)
        title = getString(R.string.title_edit)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        profileRecycler.isNestedScrollingEnabled = false

        val profile = intent.getParcelableExtra<Profile>(PROFILE)
        userFio.setText("${profile.firstName} ${profile.lastName}")
        userBirthDate.setText(profile.birthday)
        Glide.with(this)
            .load(BuildConfig.MAIN_URL + profile.avatar?.substring(1))
            .into(userAvatar)

        val adapter = ProfileAdapter(
            ProfileAdapter.ProfileAdapterModel.transformList(profile.map(), true),
            true
        )
        profileRecycler.adapter = adapter
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    companion object {
        private val PROFILE = "profile"

        fun start(context: Context?, profile: Profile?) {
            context?.startActivity(Intent().apply {
                putExtra(PROFILE, profile)
            })
        }
    }
}
