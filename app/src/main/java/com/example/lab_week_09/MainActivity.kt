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
// Hapus impor 'Button' dan 'Text' dari material3 jika tidak digunakan lagi di file ini
// import androidx.compose.material3.Button
// import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
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
// Impor composable baru
import com.example.lab_week_09.ui.theme.OnBackgroundItemText
import com.example.lab_week_09.ui.theme.OnBackgroundTitleText
import com.example.lab_week_09.ui.theme.PrimaryTextButton
import com.example.lab_week_09.ui.theme.LAB_WEEK_09Theme

data class Student(
    var name: String
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LAB_WEEK_09Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Home()
                }
            }
        }
    }
}

@Composable
fun Home() {
    val listData = remember {
        mutableStateListOf(
            Student("Tanu"),
            Student("Tina"),
            Student("Tono")
        )
    }

    var inputField = remember { mutableStateOf(Student("")) }

    HomeContent(
        listData = listData,
        inputField = inputField.value,
        onInputValueChange = { input -> inputField.value = inputField.value.copy(name = input) },
        onButtonClick = {
            if (inputField.value.name.isNotBlank()) {
                listData.add(inputField.value)
            }
            inputField.value = Student("")
        }
    )
}

// Perbarui HomeContent untuk menggunakan UI Elements yang baru
@Composable
fun HomeContent(
    listData: SnapshotStateList<Student>,
    inputField: Student,
    onInputValueChange: (String) -> Unit,
    onButtonClick: () -> Unit
) {
    LazyColumn { // [cite: 477]
        item { // [cite: 478]
            Column( // [cite: 479]
                modifier = Modifier
                    .padding(16.dp) // [cite: 487]
                    .fillMaxSize(), // [cite: 488]
                horizontalAlignment = Alignment.CenterHorizontally // [cite: 493]
            ) {
                // Menggunakan UI Element kustom [cite: 496]
                OnBackgroundTitleText(
                    text = stringResource(id = R.string.enter_item) // [cite: 497-499]
                )

                TextField( // [cite: 500]
                    value = inputField.name, // [cite: 502]
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text // [cite: 505, 507]
                    ),
                    onValueChange = {
                        onInputValueChange(it) // [cite: 513]
                    } // [cite: 514]
                ) // [cite: 515]

                // Menggunakan UI Element kustom [cite: 516]
                PrimaryTextButton(
                    text = stringResource(id = R.string.button_click), // [cite: 517-519]
                    onClick = { onButtonClick() } // [cite: 521]
                ) // [cite: 522]
            } // [cite: 523]
        } // [cite: 524]

        items(listData) { item -> // [cite: 530]
            Column( // [cite: 531]
                modifier = Modifier
                    .padding(vertical = 4.dp) // [cite: 535-536]
                    .fillMaxSize(), // [cite: 537]
                horizontalAlignment = Alignment.CenterHorizontally // [cite: 538]
            ) {
                // Menggunakan UI Element kustom [cite: 540]
                OnBackgroundItemText(text = item.name) // [cite: 541]
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewHome() {
    LAB_WEEK_09Theme {
        Home()
    }
}