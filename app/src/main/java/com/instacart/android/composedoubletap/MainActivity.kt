package com.instacart.android.composedoubletap

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.instacart.android.composedoubletap.intent.TestViewModel
import com.instacart.android.composedoubletap.intent.UIEvent
import com.instacart.android.composedoubletap.intent.ViewState
import com.instacart.android.composedoubletap.ui.theme.ComposeDoubleTapTheme

class MainActivity : ComponentActivity() {
    private val viewModel: TestViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // normally use collectAsStateWithLifecycle
            // simplify to workaround this bug: https://issuetracker.google.com/issues/336842920#comment8
            val viewState by viewModel.viewState.collectAsState()

            ToggleDemoApp(viewState) { uiEvent ->
                viewModel.onUIEvent(uiEvent)
            }
        }
    }
}

@Composable
fun ToggleDemoApp(viewState: ViewState, uiEvent: (UIEvent) -> Unit) {
    ComposeDoubleTapTheme {
        Box(modifier = Modifier.fillMaxSize().background(Color.White)) {
            Row(
                modifier = Modifier.align(Alignment.Center),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Switch(
                    checked = viewState.isToggled,
                    onCheckedChange = { isChecked ->
                        uiEvent.invoke(UIEvent.SwitchToggle(isChecked))
                    }
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("toggle is ${if (viewState.isToggled) "on" else "off"}")
            }
        }
    }
}