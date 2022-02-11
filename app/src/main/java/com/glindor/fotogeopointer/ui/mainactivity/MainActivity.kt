package com.glindor.fotogeopointer.ui.mainactivity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.AttributeSet
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.glindor.fotogeopointer.R
import com.glindor.fotogeopointer.ui.IOnBackPressed
import com.glindor.fotogeopointer.utils.Logger

class MainActivity : AppCompatActivity() {

    companion object{
        fun start(context:Context) =
            Intent(context, MainActivity::class.java).apply {
                Logger.d(this,"MainActivity start")
                context.startActivity(this)
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
        Logger.d(this,"MainActivity onCreateView parent = $parent")
        return super.onCreateView(name, context, attrs)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        android.R.id.home -> {
            onBackPressed()
            true
        }
        else -> super.onOptionsItemSelected(item)
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