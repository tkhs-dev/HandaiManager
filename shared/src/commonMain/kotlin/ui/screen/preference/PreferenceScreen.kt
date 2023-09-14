package ui.screen.preference

import HandaiManager.shared.BuildConfig
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

private val STYLE_TITLE = TextStyle(fontSize = 20.sp)
private val STYLE_DESCRIPTION = TextStyle(fontSize = 15.sp)

@Composable
expect fun PreferenceScreen()

@Composable
fun ConfigSection(title:String, content : @Composable () -> Unit){
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(title, style = TextStyle(fontSize = 22.sp, fontWeight = FontWeight.Bold))
        Column(modifier = Modifier.padding(10.dp,0.dp)) {
            content()
        }
    }
}

@Composable
fun BooleanElement(title: String, description:String, value: Boolean, onValueChange: (Boolean) -> Unit) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row{
            Box(modifier = Modifier.align(Alignment.CenterVertically)){
                Text(title, style = STYLE_TITLE, modifier = Modifier.wrapContentHeight())
            }
            Box(modifier = Modifier.weight(1f))
            Switch(
                value,
                modifier = Modifier.scale(0.8f),
                thumbContent = {
                    if(value) {
                        Icon(
                            imageVector = Icons.Filled.Check,
                            contentDescription = null,
                            modifier = Modifier.size(SwitchDefaults.IconSize)
                        )
                    }
                },
                onCheckedChange = {onValueChange(it)}
            )
        }
        Text(description, style = STYLE_DESCRIPTION)
    }
}

@Composable
fun versionInfo(){
    Column(modifier = Modifier.padding(0.dp,10.dp)) {
        Text(text = "ビルド情報", style = STYLE_DESCRIPTION)
        Text(text = "バージョン: ${BuildConfig.APP_VERSION}", style = STYLE_DESCRIPTION)
        Text(text = "ビルド日時: ${Instant.fromEpochMilliseconds(BuildConfig.BUILD_TIME).toLocalDateTime(TimeZone.UTC)}", style = STYLE_DESCRIPTION)
    }
}