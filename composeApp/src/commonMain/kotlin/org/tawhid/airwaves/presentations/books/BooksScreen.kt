package org.tawhid.airwaves.presentations.books

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import org.tawhid.airwaves.app.navigation.Route

@Composable
fun BooksScreen(
    rootNavController: NavController
) {
    Box (
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        Button(onClick = {
            rootNavController.navigate(Route.BookList)
        }) {
            Text(text = "Books")
        }
    }
}