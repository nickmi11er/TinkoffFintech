package ru.nickmiller.tinkofffintech.ui.login

import android.arch.lifecycle.Observer
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import android.widget.Toast
import org.koin.android.viewmodel.ext.android.viewModel
import ru.nickmiller.tinkofffintech.R
import ru.nickmiller.tinkofffintech.data.Resource
import ru.nickmiller.tinkofffintech.ui.MainActivity
import ru.nickmiller.tinkofffintech.ui.component.ProgressButton
import ru.nickmiller.tinkofffintech.utils.find

class LoginActivity : AppCompatActivity() {
    val viewModel by viewModel<LoginViewModel>()
    val vUsername by lazy { find<TextView>(R.id.username) }
    val vPasswd by lazy { find<TextView>(R.id.passwd) }
    val signInBtn by lazy { find<ProgressButton>(R.id.sign_in_btn) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        initObserver()

        signInBtn.setOnClickListener {
            if (vUsername.text.isEmpty() || vPasswd.text.isEmpty()) {
                Toast.makeText(this, "Все поля должны быть заполнены", Toast.LENGTH_SHORT).show()
            } else {
                viewModel.signin(vUsername.text.toString(), vPasswd.text.toString())
            }
        }
    }

    private fun initObserver() {
        viewModel.loginObserver.observe(this, Observer { res ->
            when (res?.status) {
                Resource.Status.LOADING -> startProgress()
                Resource.Status.SUCCESS -> {
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }
                Resource.Status.ERROR -> {
                    stopProgress()
                    Toast.makeText(this, res.error?.message, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    fun startProgress() {
        signInBtn.showProgress()
    }

    fun stopProgress() {
        signInBtn.hideProgress()
    }
}
