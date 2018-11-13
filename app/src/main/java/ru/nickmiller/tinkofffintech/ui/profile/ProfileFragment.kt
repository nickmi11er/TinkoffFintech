package ru.nickmiller.tinkofffintech.ui.profile


import android.arch.lifecycle.Observer
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import org.koin.android.viewmodel.ext.android.viewModel
import ru.nickmiller.tinkofffintech.R
import ru.nickmiller.tinkofffintech.data.Resource
import ru.nickmiller.tinkofffintech.data.entity.profile.map
import ru.nickmiller.tinkofffintech.utils.find


class ProfileFragment : Fragment() {
    val viewModel by viewModel<ProfileViewModel>()
    lateinit var profileAvatar: ImageView
    lateinit var profileName: TextView
    lateinit var profileEmail: TextView
    lateinit var recycler: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_profile, container, false)
        profileAvatar = rootView.find(R.id.profile_avatar)
        profileName = rootView.find(R.id.profile_name)
        profileEmail = rootView.find(R.id.profile_email)
        rootView.findViewById<View>(R.id.btn_logout).setOnClickListener {

        }
        recycler = rootView.find(R.id.profile_recycler)
        recycler.isNestedScrollingEnabled = false
        recycler.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        viewModel.profileObservable.observe(this, Observer { res ->
            when (res?.status) {
                Resource.Status.LOADING -> {
                }
                Resource.Status.ERROR -> {
                    Toast.makeText(context, res.error?.message, Toast.LENGTH_SHORT).show()
                }
                Resource.Status.SUCCESS -> {
                    profileName.text = "${res.data?.firstName} ${res.data?.lastName}"
                    profileEmail.text = res.data?.email
                    Glide.with(this).load("https://fintech.tinkoff.ru" + res.data?.avatar).into(profileAvatar)
                    val profile = res.data
                    val structuredProfile = profile?.map()

                    rootView.find<FloatingActionButton>(R.id.btn_edit_profile).setOnClickListener {
                        val intent = Intent(context, EditProfileActivity::class.java)
                        intent.putExtra("profile", profile)
                        startActivity(intent)
                    }

                    val adapter =
                        structuredProfile?.let { prof -> ProfileAdapter.ProfileAdapterModel.transformList(prof) }
                            ?.let { it2 ->
                                ProfileAdapter(
                                    it2
                                )
                            }

                    recycler.adapter = adapter
                }
            }
        })

        return rootView
    }
}
