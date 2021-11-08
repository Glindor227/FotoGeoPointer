package com.glindor.fotogeopointer.ui.splash

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.firebase.ui.auth.AuthUI
import com.glindor.fotogeopointer.R
import com.glindor.fotogeopointer.data.error.NotAuthentication
import com.glindor.fotogeopointer.ui.mainactivity.MainActivity
import com.glindor.fotogeopointer.utils.Logger


class SplashActivity : AppCompatActivity() {
    companion object{
        fun start(context: Context) =
                Intent(context, SplashActivity::class.java).apply {
                    context.startActivity(this)
                }
    }
    private val viewModel: SplashViewModel by lazy {
        ViewModelProvider(this).get(SplashViewModel::class.java)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_splash) //TODO прыгает экран в какойто момент
        initViewModel()
    }

    override fun onResume() {
        super.onResume()
        Logger.d(this,"onResume() + initUser()")
        viewModel.initUser()
    }

    private fun initViewModel() {
        viewModel.getViewState().observe(this, {splashViewState ->
            splashViewState.error?.let { error ->
                error.takeIf {it is NotAuthentication }.run {
                    Logger.d(this,"${this?.javaClass?.name}: Not user")
                    StartAuthentication()
                }
            }
            splashViewState.value?.let {
                Logger.d(this,"User(${it.user}) start MainActivity")
                MainActivity.start(this)
            }
        })
    }

    private val activityLauncher: ActivityResultLauncher<Intent> = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()){
        if (it.resultCode == RESULT_OK) {
            Logger.d(this,"StartAuthentication complete -> start MainActivity")
            MainActivity.start(this)
            finish()
        }
    }

    private fun StartAuthentication(){
        Logger.d(this,"StartAuthentication")
        activityLauncher.launch(
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(
                    arrayOf(
                        AuthUI.IdpConfig.GoogleBuilder().build()
                    ).toMutableList()
                )
                .setLogo(R.drawable.android_robot)
                .setTheme(R.style.SplashTheme).build()
        )
    }
}