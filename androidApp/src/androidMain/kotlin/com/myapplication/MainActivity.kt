package dev.tkhs.handaimanager

import MainView
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.CompositionLocalProvider
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.decompose.defaultComponentContext
import di.AppModule
import io.github.xxfast.decompose.LocalComponentContext
import org.koin.core.context.startKoin
import util.FileUtil

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        startKoin {
            modules(AppModule().appModule())
        }

        super.onCreate(savedInstanceState)
        FileUtil.filesDir = applicationContext.filesDir
        val rootComponentContext: DefaultComponentContext = defaultComponentContext()
        setContent {
            CompositionLocalProvider(LocalComponentContext provides rootComponentContext) {
                MaterialTheme {
                    MainView()
                }
            }
        }
    }
}