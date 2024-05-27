package io.github.shams66789.instashorts.screen

import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import io.github.shams66789.instashorts.InterMedium
import io.github.shams66789.instashorts.InterRegular
import io.github.shams66789.instashorts.InterSemiBold
import io.github.shams66789.instashorts.R
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

@Composable
fun HomeScreen(viewModel: GetNewsViewModel) {
    val newsData = (viewModel.res.value as? Result.Success)?.data
    newsData?.let { data ->
        Column(modifier = Modifier.fillMaxSize()) {
            TopBar(
                title = stringResource(id = R.string.app_name),
                onNavigationIconClick = { /* Handle navigation icon click */ }
            )
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(data.articles) { article ->
                    OutlinedCard(
                        modifier = Modifier.padding(top = 15.dp, start = 10.dp, end = 10.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
                    ) {
                        Column(modifier = Modifier.padding(10.dp)) {
                            val url = article.urlToImage
                            val imageModel = if (!url.isNullOrEmpty()) {
                                url
                            } else {
                                R.drawable.error
                            }
                            AsyncImage(
                                model = imageModel,
                                modifier = Modifier.size(height = 200.dp, width = 350.dp),
                                contentDescription = null,
                                contentScale = ContentScale.Crop
                            )

                            if (!article.description.isNullOrEmpty()) {
                                Text(
                                    text = article.description,
                                    modifier = Modifier.padding(top = 20.dp),
                                    fontFamily = InterMedium
                                )
                            } else {
                                Text(
                                    text = article.title,
                                    modifier = Modifier.padding(top = 20.dp),
                                    fontFamily = InterMedium
                                )
                            }

                            val formattedDate = formatDate(article.publishedAt)

                            // Display date and button
                            DateAndButton(
                                formattedDate = formattedDate,
                                articleUrl = article.url
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DateAndButton(formattedDate: String, articleUrl: String) {
    Row(
        modifier = Modifier
            .padding(top = 10.dp, end = 10.dp, bottom = 10.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = formattedDate,
            modifier = Modifier.padding(top = 10.dp),
            fontFamily = InterRegular
        )
        OpenLinkButton(articleUrl)
    }
}

@Composable
fun TopBar(title: String, onNavigationIconClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.Menu,
            contentDescription = "Menu",
            modifier = Modifier
                .clickable(onClick = onNavigationIconClick)
                .size(35.dp),
        )
        Text(text = title, style = TextStyle(fontSize = 20.sp), fontFamily = InterSemiBold)
        Spacer(modifier = Modifier.size(20.dp)) // Adjust spacing as needed
    }
}


@Composable
fun OpenLinkButton(articleUrl: String) {
    val openUrlLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { }

    Button(
        onClick = {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse(articleUrl)
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            openUrlLauncher.launch(intent)
        },
        colors = ButtonDefaults.buttonColors(containerColor = Color.Blue)
    ) {
        Text(
            text = "Read Article",
            color = Color.White,
            fontFamily = InterRegular,
            style = TextStyle(fontSize = 14.sp)
        )
    }
}


fun formatDate(isoDate: String): String {
    val zonedDateTime = ZonedDateTime.parse(isoDate)
    val formatter = DateTimeFormatter.ofPattern("dd MMM yyyy, HH:mm")
    return zonedDateTime.format(formatter)
}