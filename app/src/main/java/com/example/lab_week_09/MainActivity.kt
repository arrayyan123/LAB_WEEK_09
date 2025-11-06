package com.example.lab_week_09

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.lab_week_09.ui.theme.LAB_WEEK_09Theme

class MainActivity : ComponentActivity() { // [cite: 60-61]
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { // [cite: 64]
            LAB_WEEK_09Theme { // [cite: 65]
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), // [cite: 68-70]
                    color = MaterialTheme.colorScheme.background // [cite: 71-73]
                ) {
                    val list = listOf("Tanu", "Tina", "Tono") // [cite: 212]
                    Home(items = list) // [cite: 213]
                }
            }
        }
    }
}

// [cite: 127]
@Composable
fun Home(items: List<String>) { // [cite: 128-130]
    // LazyColumn lebih efisien, mirip RecyclerView [cite: 131-133]
    LazyColumn { // [cite: 135]
        // Blok item tunggal untuk header (Input dan Tombol) [cite: 137]
        item {
            Column(
                modifier = Modifier
                    .padding(16.dp) // [cite: 146]
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally // [cite: 147, 155]
            ) {
                Text(
                    text = stringResource(id = R.string.enter_item) // [cite: 156-157]
                )

                // TextField untuk input teks [cite: 159]
                TextField(
                    value = "", // [cite: 160]
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number // [cite: 162-163]
                    ),
                    onValueChange = { // [cite: 167]
                        // Akan diisi di bagian selanjutnya
                    } // [cite: 168]
                )

                // Tombol [cite: 170]
                Button(onClick = { /* Akan diisi di bagian selanjutnya */ }) { // [cite: 173]
                    Text(text = stringResource(id = R.string.button_click)) // [cite: 175-176]
                }
            }
        }

        // Blok 'items' untuk me-render list [cite: 181-184]
        items(items) { item ->
            Column(
                modifier = Modifier
                    .padding(vertical = 4.dp) // [cite: 186]
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally // [cite: 186]
            ) {
                Text(text = item) // [cite: 188]
            }
        }
    }
}

// Composable terpisah untuk Preview [cite: 195-197]
@Preview(showBackground = true) // [cite: 198]
@Composable
fun PreviewHome() {
    LAB_WEEK_09Theme {
        Home(listOf("Tanu", "Tina", "Tono")) // [cite: 202]
    }
}