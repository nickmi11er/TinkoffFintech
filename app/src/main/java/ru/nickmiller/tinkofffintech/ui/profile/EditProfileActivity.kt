package ru.nickmiller.tinkofffintech.ui.profile

import android.annotation.SuppressLint
import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_edit_profile.*
import org.koin.android.viewmodel.ext.android.viewModel
import ru.nickmiller.tinkofffintech.BaseException
import ru.nickmiller.tinkofffintech.BuildConfig
import ru.nickmiller.tinkofffintech.R
import ru.nickmiller.tinkofffintech.data.Resource
import ru.nickmiller.tinkofffintech.data.entity.profile.Profile
import ru.nickmiller.tinkofffintech.data.entity.profile.ProfileField
import ru.nickmiller.tinkofffintech.data.entity.profile.flattenBlocks
import ru.nickmiller.tinkofffintech.data.entity.profile.map
import ru.nickmiller.tinkofffintech.utils.onChange


class EditProfileActivity : AppCompatActivity() {

    val viewModel by viewModel<ProfileViewModel>()
    lateinit var adapter: ProfileAdapter

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
        setSupportActionBar(toolbar)
        title = getString(R.string.title_edit)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        profileRecycler.isNestedScrollingEnabled = false

        viewModel.errorsObservable.postValue(BaseException(message = "Ошибка!!!"))

        //profile = intent.getParcelableExtra(PROFILE)
        //originProfile = profile


        viewModel.getDataObservable()?.observe(this, Observer { profile ->

            initViews(profile!!)
            initMainWatchers(profile)
            profileRecycler.adapter = adapter
            btnSave.setOnClickListener { viewModel.editProfile(profile) }
        })

//        btnCancel.setOnClickListener {
//            profile = originProfile.copy()
//            initViews()
//        }



        viewModel.editProfileObservable.observe(this, Observer {
            when (it?.status) {
                Resource.Status.LOADING -> {}
                Resource.Status.SUCCESS -> {
                    if (it.data == true) {
                        Toast.makeText(this, "Профиль успешно обновлен", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, "Не удалось обновить данные", Toast.LENGTH_SHORT).show()
                    }
                }
                Resource.Status.ERROR -> {}
            }
        })
    }

    private fun initViews(profile: Profile) {
        val blocks = profile.map().flattenBlocks()
        userFio.setText("${profile.firstName} ${profile.lastName}")
        userBirthDate.setText(profile.birthday)
        Glide.with(this)
            .load(BuildConfig.MAIN_URL + profile.avatar?.substring(1))
            .into(userAvatar)

        adapter = ProfileAdapter(blocks, true)
    }

    private fun initMainWatchers(profile: Profile) {
        userFio.onChange {
            val args = it.split(" ")
            if (args.isNotEmpty()) profile.firstName = args[0]
            if (args.size > 1) profile.lastName = args[1]
            if (args.size > 2) profile.middleName = args[2]
        }
        userBirthDate.onChange { profile.birthday = it }
        userSummary.onChange { profile.description = it }

        adapter.setOnFieldChangedListener { name, value ->
            with(profile) {
                when (name) {
                    ProfileField.PHONE -> mobile = value
                    ProfileField.EMAIL -> email = value
                    ProfileField.REGION -> region = value
                    ProfileField.SCHOOL -> school = value
                    ProfileField.SCHOOL_GRADUATION -> schoolGraduation = value
                    ProfileField.UNIVERSITY -> university = value
                    ProfileField.FACULTY -> faculty = value
                    ProfileField.UNIVERSITY_GRADUATION -> universityGraduation = value
                    ProfileField.DEPARTMENT -> department = value
                    ProfileField.WORK -> currentWork = value
                }
            }
            //btnSave.isClickable = originProfile.hashCode() != profile.hashCode()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    companion object {
        private val PROFILE = "profile"

        fun start(context: Context?, profile: Profile?) {
            context?.startActivity(Intent(context, EditProfileActivity::class.java).apply {
                putExtra(PROFILE, profile)
            })
        }
    }
}
