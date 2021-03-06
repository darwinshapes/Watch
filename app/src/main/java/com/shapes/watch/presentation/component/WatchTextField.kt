package com.shapes.watch.presentation.component

import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.shapes.watch.ui.theme.onSurfaceCarbon

@Composable
fun WatchTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: @Composable (() -> Unit)? = null,
    placeholder: @Composable (() -> Unit)? = null,
    singleLine: Boolean = false
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        textStyle = MaterialTheme.typography.body1,
        label = label,
        placeholder = placeholder,
        singleLine = singleLine,
        colors = TextFieldDefaults
            .outlinedTextFieldColors(
                unfocusedBorderColor = MaterialTheme.colors.onSurfaceCarbon
            )
    )
}
