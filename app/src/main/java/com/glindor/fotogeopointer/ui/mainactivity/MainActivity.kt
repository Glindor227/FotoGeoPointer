package com.glindor.fotogeopointer.ui.mainactivity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.firebase.ui.auth.AuthUI
import com.glindor.fotogeopointer.R
import com.glindor.fotogeopointer.ui.IOnBackPressed
import com.glindor.fotogeopointer.ui.splash.SplashActivity
import com.glindor.fotogeopointer.utils.Logger
import org.jetbrains.anko.alert

class MainActivity : AppCompatActivity() {

    companion object{
        fun start(context:Context) =
            Intent(context, MainActivity::class.java).apply {
                context.startActivity(this)
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

/*
        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

 */
    }



    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        android.R.id.home -> {
            onBackPressed()
            true
        }
        R.id.action_settings -> true
        R.id.action_logout -> {
            showLogoutDialog()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    private fun showLogoutDialog() {
        alert{
            titleResource = R.string.logout_dialog_title
            messageResource = R.string.logout_dialog_message
            positiveButton(R.string.logout_dialog_button_positive) { logOut() }
            negativeButton(R.string.logout_dialog_button_negative) { dialog ->  dialog.cancel()}
        }.show()

/*
       AlertDialog.Builder(mainActivity)
            .setTitle(getString(R.string.logout_dialog_title))
            .setMessage(getString(R.string.logout_dialog_message))
            .setPositiveButton(getString(R.string.logout_dialog_button_positive)) { _, _ -> logOut() }
            .setNegativeButton(getString(R.string.logout_dialog_button_negative)) { dialog, _ -> dialog.cancel()}
            .show()
*/
    }

    private fun logOut() {
        Logger.d(this,"logOut()")

        AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener{
                    Logger.d(this,"signOut() complete")
                    SplashActivity.start(this)
                }
    }

    override fun onBackPressed() {
        Logger.d(this,"onBackPressed")
        val visibleFragment =  supportFragmentManager.fragments.find { it is NavHostFragment}
            ?.childFragmentManager?.fragments?.find { it.isVisible  }
        (visibleFragment as? IOnBackPressed)?.onBackPressed()
        if (supportFragmentManager.backStackEntryCount == 0) {
            super.onBackPressed()
        } else {
            supportFragmentManager.popBackStack()
        }
    }
}