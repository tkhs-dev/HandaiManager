package dev.tkhs.handaimanager

import MainView
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.MaterialTheme
import di.AppModule
import org.koin.core.context.startKoin
import util.ContextUtil
import util.FileUtil

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        startKoin {
            modules(AppModule().appModule())
        }

        super.onCreate(savedInstanceState)
        ContextUtil.mContext = this

        FileUtil.filesDir = applicationContext.filesDir
        setContent {
            MaterialTheme {
                MainView()
            }
        }
    }
}