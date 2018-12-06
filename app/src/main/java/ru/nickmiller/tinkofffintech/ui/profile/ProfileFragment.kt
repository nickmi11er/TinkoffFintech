package ru.nickmiller.tinkofffintech.ui.profile


import android.annotation.SuppressLint
import android.arch.lifecycle.Observer
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.*
import android.widget.Toast
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_profile.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import ru.nickmiller.tinkofffintech.BuildConfig
import ru.nickmiller.tinkofffintech.R
import ru.nickmiller.tinkofffintech.data.entity.profile.filterNotEmpty
import ru.nickmiller.tinkofffintech.data.entity.profile.flattenBlocks
import ru.nickmiller.tinkofffintech.data.entity.profile.map
import ru.nickmiller.tinkofffintech.ui.MainActivity
import ru.nickmiller.tinkofffintech.ui.login.LoginActivity
import ru.nickmiller.tinkofffintech.utils.cookies.CookiesStore


class ProfileFragment : Fragment() {

    val viewModel by viewModel<ProfileViewModel>()
    val cStore by inject<CookiesStore>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        observeData()
    }


    private fun initViews() {
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        profile_recycler.isNestedScrollingEnabled = false
        profile_recycler.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        profile_refresh.setOnRefreshListener { viewModel.updateProfile(true) }
    }

    @SuppressLint("SetTextI18n")
    private fun observeData() {
//        viewModel.dataObservable.observe(this, Observer { profile ->
//            root_view.visibility = View.VISIBLE
//
//            collapsing_toolbar.title = "${profile?.firstName} ${profile?.lastName}"
//            profile_name.text = "${profile?.firstName} ${profile?.lastName}"
//            profile_email.text = profile?.email
//            Glide.with(this)
//                .load(BuildConfig.MAIN_URL + profile?.avatar?.substring(1))
//                .into(profile_avatar)
//
//            val profileBlocks = profile?.map()
//
//            btn_edit_profile.setOnClickListener {
//                EditProfileActivity.start(context, profile)
//            }
//
//            val blocks = profileBlocks?.filterNotEmpty()?.flattenBlocks()
//            val adapter = blocks?.let { ProfileAdapter(blocks) }
//            profile_recycler.adapter = adapter
//        })

        viewModel.errorsObservable.observe(this, Observer {
            Toast.makeText(context, it?.message, Toast.LENGTH_SHORT).show()
        })

        viewModel.getDataObservable()?.observe(this, Observer { profile ->
            root_view.visibility = View.VISIBLE

            collapsing_toolbar.title = "${profile?.firstName} ${profile?.lastName}"
            profile_name.text = "${profile?.firstName} ${profile?.lastName}"
            profile_email.text = profile?.email
            Glide.with(this)
                .load(BuildConfig.MAIN_URL + profile?.avatar?.substring(1))
                .into(profile_avatar)

            val profileBlocks = profile?.map()

            btn_edit_profile.setOnClickListener {
                EditProfileActivity.start(context, profile)
            }

            val blocks = profileBlocks?.filterNotEmpty()?.flattenBlocks()
            val adapter = blocks?.let { ProfileAdapter(blocks) }
            profile_recycler.adapter = adapter
        })

        viewModel.loadingObservable.observe(this, Observer {
            with(activity as MainActivity) {
                if (it == true) {
                    startProgress()
                } else {
                    profile_refresh.isRefreshing = false
                    stopProgress()
                }
            }
        })
    }


    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.menu_profile, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.profile_signout -> {
                cStore.clearCookies()
                startActivity(Intent(context, LoginActivity::class.java))
                activity?.finish()
            }
        }
        return true
    }
}
