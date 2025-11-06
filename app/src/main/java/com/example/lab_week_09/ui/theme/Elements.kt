package com.example.lab_week_09.ui.theme

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

// [cite: 423]
@Composable
fun OnBackgroundTitleText(text: String) {
    TitleText(text = text, color = MaterialTheme.colorScheme.onBackground) // [cite: 426-427]
}

// [cite: 428]
@Composable
fun TitleText(text: String, color: Color) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleLarge, // [cite: 431]
        color = color // [cite: 432]
    )
}

// [cite: 437]
@Composable
fun OnBackgroundItemText(text: String) {
    ItemText(text = text, color = MaterialTheme.colorScheme.onBackground) // [cite: 440-441]
}

// [cite: 442]
@Composable
fun ItemText(text: String, color: Color) {
    Text(
        text = text,
        style = MaterialTheme.typography.bodySmall, // [cite: 445]
        color = color // [cite: 446]
    )
}

// [cite: 448]
@Composable
fun PrimaryTextButton(text: String, onClick: () -> Unit) {
    TextButton( // [cite: 450]
        text = text,
        textColor = Color.White, // [cite: 451]
        onClick = onClick // [cite: 452]
    )
}

// [cite: 456]
@Composable
fun TextButton(text: String, textColor: Color, onClick: () -> Unit) {
    Button(
        onClick = onClick, // [cite: 458]
        modifier = Modifier.padding(8.dp), // [cite: 459]
        colors = ButtonDefaults.buttonColors( // [cite: 460-461]
            containerColor = Color.DarkGray, // [cite: 462]
            contentColor = textColor // [cite: 463]
        )
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelMedium // [cite: 466-467]
        )
    }
}