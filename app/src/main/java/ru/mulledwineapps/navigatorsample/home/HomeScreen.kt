package ru.mulledwineapps.navigatorsample.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ru.mulledwineapps.navigatorsample.ui.theme.NavigatorSampleTheme

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    modifier: Modifier = Modifier
) {
    val items by viewModel.uiState.collectAsStateWithLifecycle()
    if (items is HomeUiState.Success) {
        HomeScreen(
            items = (items as HomeUiState.Success).data,
            onSave = { },
            modifier = modifier
        )
    }
}

@Composable
internal fun HomeScreen(
    items: List<String>,
    onSave: (name: String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier) {
        var nameHome by remember { mutableStateOf("Compose") }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            TextField(
                value = nameHome,
                onValueChange = { nameHome = it }
            )

            Button(modifier = Modifier.width(96.dp), onClick = { onSave(nameHome) }) {
                Text("Save")
            }
        }
        items.forEach {
            Text("Saved item: $it")
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DefaultPreview() {
    NavigatorSampleTheme {
        HomeScreen(listOf("Compose", "Room", "Kotlin"), onSave = {})
    }
}

@Preview(showBackground = true, widthDp = 480)
@Composable
private fun PortraitPreview() {
    NavigatorSampleTheme {
        HomeScreen(listOf("Compose", "Room", "Kotlin"), onSave = {})
    }
}
