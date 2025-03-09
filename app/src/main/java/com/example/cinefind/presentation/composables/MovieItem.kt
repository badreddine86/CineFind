package com.example.cinefind.presentation.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.example.cinefind.data.models.Movie

@Composable
fun MovieItem(movie: Movie) {
    Card(
        modifier = Modifier.padding(8.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
    ) {
        Row(
            modifier = Modifier.padding(16.dp)
        ) {
            Image(
                painter = rememberImagePainter(movie.image),
                contentDescription = null,
                modifier = Modifier.weight(1f).padding(end = 16.dp)
            )
            Column(
                modifier = Modifier.weight(3f)
            ) {
                Text(text = movie.title, style = androidx.compose.material3.MaterialTheme.typography.titleSmall)
                Text(text = movie.synopsis, style = androidx.compose.material3.MaterialTheme.typography.bodyMedium)
                Text(text = "Release: ${movie.date}", style = androidx.compose.material3.MaterialTheme.typography.bodySmall)
                Text(text = "Rating: ${movie.note}", style = androidx.compose.material3.MaterialTheme.typography.bodyMedium)
            }
        }
    }
}