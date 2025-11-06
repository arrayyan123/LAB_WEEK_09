package com.example.lab_week_09

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row // Impor Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.navigation.NavHostController // Impor baru
import androidx.navigation.NavType // Impor baru
import androidx.navigation.compose.NavHost // Impor baru
import androidx.navigation.compose.composable // Impor baru
import androidx.navigation.compose.rememberNavController // Impor baru
import androidx.navigation.navArgument // Impor baru
import com.example.lab_week_09.ui.theme.OnBackgroundItemText
import com.example.lab_week_09.ui.theme.OnBackgroundTitleText
import com.example.lab_week_09.ui.theme.PrimaryTextButton
import com.example.lab_week_09.ui.theme.LAB_WEEK_09Theme
import com.squareup.moshi.JsonClass // Impor baru
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

@JsonClass(generateAdapter = true) // Tambahkan anotasi ini
data class Student(
    var name: String
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LAB_WEEK_09Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(), // [cite: 607-609]
                    color = MaterialTheme.colorScheme.background // [cite: 610-613]
                ) {
                    // Buat NavController [cite: 614]
                    val navController = rememberNavController()
                    // Panggil root composable App [cite: 614-616]
                    App(navController = navController)
                }
            }
        }
    }
}

// Composable root baru yang menangani Navigasi [cite: 557-558]
@Composable
fun App(navController: NavHostController) { // [cite: 560]
    NavHost( // [cite: 561]
        navController = navController, // [cite: 567]
        startDestination = "home" // [cite: 568]
    ) {
        // Rute untuk "home" [cite: 569, 573]
        composable("home") {
            // Berikan lambda navigasi ke Home composable [cite: 574-577]
            Home { listDataString ->
                navController.navigate("resultContent/?listData=$listDataString")
            }
        }
        // Rute untuk "resultContent" dengan argumen [cite: 580, 586-590]
        composable(
            route = "resultContent/?listData={listData}",
            arguments = listOf(navArgument("listData") { // [cite: 591]
                type = NavType.StringType // [cite: 594]
            })
        ) {
            // Ambil argumen dan kirim ke ResultContent [cite: 597-601]
            ResultContent(
                listData = it.arguments?.getString("listData").orEmpty()
            )
        }
    }
}

@Composable
fun Home(
    navigateFromHomeToResult: (String) -> Unit
) {
    val listData = remember {
        mutableStateListOf(
            Student("Tanu"),
            Student("Tina"),
            Student("Tono")
        )
    }

    var inputField = remember { mutableStateOf(Student("")) }

    // Siapkan Moshi untuk konversi JSON
    val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()
    val listType = Types.newParameterizedType(List::class.java, Student::class.java)
    val jsonAdapter = moshi.adapter<List<Student>>(listType)

    HomeContent(
        listData = listData,
        inputField = inputField.value,
        onInputValueChange = { input -> inputField.value = inputField.value.copy(name = input) },
        onButtonClick = {
            // Menggunakan perbaikan dari Soal 1
            if (inputField.value.name.isNotBlank()) {
                listData.add(inputField.value)
                inputField.value = Student("")
            }
        },
        // Perbarui lambda ini
        navigateFromHomeToResult = {
            // Ubah list menjadi JSON string
            val listAsJsonString = jsonAdapter.toJson(listData.toList())
            navigateFromHomeToResult(listAsJsonString) //
        }
    )
}

@Composable
fun HomeContent(
    listData: SnapshotStateList<Student>, // [cite: 630]
    inputField: Student, // [cite: 631]
    onInputValueChange: (String) -> Unit, // [cite: 632]
    onButtonClick: () -> Unit, // [cite: 633]
    navigateFromHomeToResult: () -> Unit // [cite: 634]
) {
    LazyColumn { // [cite: 648]
        item {
            Column( // [cite: 649]
                modifier = Modifier
                    .padding(16.dp) // [cite: 653]
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally // [cite: 654]
            ) {
                OnBackgroundTitleText(
                    text = stringResource(id = R.string.enter_item) // [cite: 655-658]
                )

                TextField(
                    value = inputField.name, // [cite: 660]
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text // [cite: 661-663]
                    ),
                    onValueChange = {
                        onInputValueChange(it) // [cite: 665]
                    }
                )

                // Gunakan Row untuk menempatkan tombol bersebelahan [cite: 668]
                Row {
                    PrimaryTextButton(
                        text = stringResource(id = R.string.button_click), // [cite: 669]
                        onClick = { onButtonClick() } // [cite: 671]
                    )
                    PrimaryTextButton(
                        text = stringResource(id = R.string.button_navigate), // [cite: 673]
                        onClick = { navigateFromHomeToResult() } // [cite: 674-675]
                    )
                }
            }
        }

        items(listData) { item -> // [cite: 680]
            Column(
                modifier = Modifier
                    .padding(vertical = 4.dp) // [cite: 683-684]
                    .fillMaxSize(), // [cite: 685]
                horizontalAlignment = Alignment.CenterHorizontally // [cite: 686]
            ) {
                OnBackgroundItemText(text = item.name) // [cite: 688]
            }
        }
    }
}

// Composable baru untuk layar hasil [cite: 699, 702]
// [cite: 699]
@Composable
fun ResultContent(listData: String) { // [cite: 703]

    // Siapkan Moshi untuk mengurai JSON
    val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()
    val listType = Types.newParameterizedType(List::class.java, Student::class.java)
    val jsonAdapter = moshi.adapter<List<Student>>(listType)

    // Ubah JSON string kembali menjadi List<Student>
    // Tambahkan penanganan error jika JSON tidak valid
    val studentList: List<Student> = try {
        jsonAdapter.fromJson(listData) ?: emptyList()
    } catch (e: Exception) {
        emptyList()
    }

    // Gunakan LazyColumn untuk menampilkan daftar, seperti di HomeContent
    LazyColumn(
        modifier = Modifier.fillMaxSize(), // [cite: 708]
        horizontalAlignment = Alignment.CenterHorizontally // [cite: 709]
    ) {
        items(studentList) { student -> //
            Column(
                modifier = Modifier
                    .padding(vertical = 4.dp) // [cite: 707]
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Gunakan kembali UI Element kita [cite: 710-711]
                OnBackgroundItemText(text = student.name)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewHome() {
    LAB_WEEK_09Theme {
        // Preview tidak bisa menangani navigasi, jadi kita panggil Home
        // dengan lambda navigasi palsu (kosong).
        Home(navigateFromHomeToResult = {})
    }
}