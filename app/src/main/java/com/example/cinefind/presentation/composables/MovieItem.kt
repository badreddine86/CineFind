package com.example.cinefind.presentation.composables

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.example.cinefind.data.models.Movie
import io.github.cdimascio.dotenv.Dotenv

@Composable
fun MovieItem(movie: Movie) {

    val dotenv: Dotenv = Dotenv.configure().directory("/assets").filename("env").load()
    val imageBaseUrl = dotenv.get("IMAGE_BASE_URL")

    fun getRatingColor(rating: Double): Color {
        return when {
            rating >= 8.0 -> Color(0xFF4CAF50)
            rating >= 5.0 -> Color(0xFFFFC107)
            else -> Color(0xFFF44336)
        }
    }

    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(), // Makes the card take full width
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            // Movie Poster
            Image(
                painter = rememberImagePainter(imageBaseUrl + movie.image),
                contentDescription = movie.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp), // Fixed height to maintain poster-like appearance
                contentScale = ContentScale.Crop // Crops the image to fill the space
            )

            // Movie Details
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                // Movie Title - Bold & Larger
                Text(
                    text = movie.title,
                    style = MaterialTheme.typography.headlineSmall, // Larger for emphasis
                    fontWeight = FontWeight.Black, // Strong presence
                    fontSize = 20.sp, // Slightly larger
                    color = Color.White // Cinema-like dark mode
                )

                Spacer(modifier = Modifier.height(6.dp))

                // Movie Synopsis - Subtle and readable
                Text(
                    text = movie.synopsis,
                    style = MaterialTheme.typography.bodyMedium,
                    fontSize = 14.sp, // Readable but secondary
                    fontWeight = FontWeight.Light, // Soft for readability
                    color = Color.Gray, // Less dominant than title
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(6.dp))

                // Release Date - Smaller, subtle, secondary info
                Text(
                    text = "Release: ${movie.date}",
                    style = MaterialTheme.typography.labelLarge,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFFFFC107) // Gold accent for style
                )

                Spacer(modifier = Modifier.height(4.dp))

                // Rating - Highlighted
                Text(
                    text = "⭐ ${movie.note}/10",
                    style = MaterialTheme.typography.titleMedium,
                    fontSize = 16.sp, // Noticeable
                    fontWeight = FontWeight.Bold, // More emphasis
                    color = getRatingColor(movie.note ?: 0.0) // Red like Rotten Tomatoes style
                )
            }
        }
    }


}
