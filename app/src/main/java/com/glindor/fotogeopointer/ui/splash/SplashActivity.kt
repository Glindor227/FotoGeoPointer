package com.glindor.fotogeopointer.ui.splash

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.firebase.ui.auth.AuthUI
import com.glindor.fotogeopointer.R
import com.glindor.fotogeopointer.data.error.NotAuthentication
import com.glindor.fotogeopointer.ui.mainactivity.MainActivity
import com.glindor.fotogeopointer.utils.Logger
import org.koin.android.viewmodel.ext.android.viewModel
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts
import com.glindor.fotogeopointer.data.entity.User
import com.glindor.fotogeopointer.ui.base.BaseViewState


class SplashActivity : AppCompatActivity() {
    companion object{
        fun start(context: Context) =
                Intent(context, SplashActivity::class.java).apply {
                    context.startActivity(this)
                }
    }
    private val viewModel: SplashViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Logger.d(this,"SplashActivity onCreate")
        initViewModel()

//        setContentView(R.layout.activity_splash) //TODO прыгает экран в какойто момент
    }

    override fun onCreateView(parent: View?, name: String, context: Context, attrs: AttributeSet): View? {
        Logger.d(this,"SplashActivity onCreateView parent = $parent")
        Logger.d(this,"SplashActivity onCreateView name = $name")
        Logger.d(this,"SplashActivity onCreateView context = $context")
        Logger.d(this,"SplashActivity onCreateView attrs = $attrs ")
        Logger.d(this,"SplashActivity onCreateView ________________________________")
        return super.onCreateView(parent, name, context, attrs)
    }

    override fun onResume() {
        super.onResume()
        Logger.d(this,"SplashActivity onResume")
        viewModel.initUser()
    }

    private fun initViewModel() {
        val viewModel1 = viewModel.getViewState()
        Logger.d(this,"SplashActivity initViewModel -> " + viewModel1.value)
        viewModel1.observeForever { baseViewState:BaseViewState<User?> ->
//            Logger.d(this, "observeForever Получение ViewState(SplashActivity) $viewStateTemp ${viewStateTemp.value}")
            baseViewState.error?.let { error ->
                error.takeIf {it is NotAuthentication }.let {
                    Logger.d(it,"${it?.javaClass?.name}: Not user")
                    StartAuthentication()
                }
            }
            baseViewState.value?.let {
                Logger.d(this,"User(${it.user}) start MainActivity")
                MainActivity.start(this)
            }
        }
        Logger.d(this,"SplashActivity initViewModel <-")
    }

    private val activityLauncher: ActivityResultLauncher<Intent> = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()){
        if (it.resultCode == RESULT_OK) {
            Logger.d(this,"StartAuthentication complete -> start MainActivity-")
            MainActivity.start(this)
            Logger.d(this,"StartAuthentication complete -> start MainActivity+")
            finish()
            Logger.d(this,"SplashActivity finish")
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