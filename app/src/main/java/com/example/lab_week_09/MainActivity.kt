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
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.lab_week_09.ui.theme.LAB_WEEK_09Theme

// [cite: 239]
data class Student( // [cite: 240]
    var name: String // [cite: 241]
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LAB_WEEK_09Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(), // [cite: 386-388]
                    color = MaterialTheme.colorScheme.background // [cite: 389-391]
                ) {
                    Home() // [cite: 393]
                }
            }
        }
    }
}

@Composable
fun Home() { // [cite: 247]
    // State untuk list, menggunakan remember agar tidak reset saat recomposition [cite: 248-250]
    val listData = remember { // [cite: 260]
        mutableStateListOf( // [cite: 255]
            Student("Tanu"), // [cite: 262]
            Student("Tina"), // [cite: 263]
            Student("Tono") // [cite: 264]
        )
    }

    // State untuk input field [cite: 265-266]
    var inputField = remember { mutableStateOf(Student("")) } // [cite: 267]

    // Memanggil composable content, meneruskan state dan event handler [cite: 268-273]
    HomeContent(
        listData = listData, // [cite: 274]
        inputField = inputField.value, // [cite: 275]
        onInputValueChange = { input -> inputField.value = inputField.value.copy(name = input) }, // [cite: 276]
        onButtonClick = { // [cite: 278]
            if (inputField.value.name.isNotBlank()) { // [cite: 279]
                listData.add(inputField.value) // [cite: 277]
            }
            inputField.value = Student("") // [cite: 281]
        }
    )
}

// Composable baru yang berisi UI, dipisahkan dari logic state [cite: 291-292]
@Composable
fun HomeContent(
    listData: SnapshotStateList<Student>, // [cite: 295]
    inputField: Student, // [cite: 296]
    onInputValueChange: (String) -> Unit, // [cite: 297]
    onButtonClick: () -> Unit // [cite: 298]
) {
    LazyColumn { // [cite: 299]
        item { // [cite: 300]
            Column(
                modifier = Modifier
                    .padding(16.dp) // [cite: 311]
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally // [cite: 312, 316]
            ) {
                Text(
                    text = stringResource(id = R.string.enter_item) // [cite: 317-320]
                )

                TextField(
                    value = inputField.name, // [cite: 323]
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text // [cite: 325-327]
                    ),
                    onValueChange = {
                        onInputValueChange(it) // [cite: 338, 344]
                    }
                )

                Button(onClick = {
                    onButtonClick() // [cite: 348, 352]
                }) {
                    Text(text = stringResource(id = R.string.button_click)) // [cite: 355, 361]
                }
            }
        }

        // Menampilkan list dari state [cite: 362-365]
        items(listData) { item -> // [cite: 366]
            Column(
                modifier = Modifier
                    .padding(vertical = 4.dp) // [cite: 368]
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally // [cite: 368]
            ) {
                Text(text = item.name) // [cite: 371]
            }
        }
    }
}


@Preview(showBackground = true) // [cite: 198]
@Composable
fun PreviewHome() {
    LAB_WEEK_09Theme {
        // Home() sekarang mengelola state-nya sendiri, jadi tidak perlu parameter
        Home()
    }
}