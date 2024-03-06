package com.devkick.base

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.itbookstore.resource.R

@Composable
fun BoxScope.BackdropBookImage(
    modifier: Modifier = Modifier,
    url: String?,
) {
    val painter = rememberAsyncImagePainter(
        model = url,
        error = painterResource(id = R.drawable.ic_close),
    )

    val colorFilter = when (painter.state) {
        is AsyncImagePainter.State.Loading,
        is AsyncImagePainter.State.Error,
        -> ColorFilter.tint(MaterialTheme.colorScheme.onBackground)

        else -> null
    }

    val scale = if (painter.state !is AsyncImagePainter.State.Success)
        ContentScale.Crop
    else
        ContentScale.FillBounds

    if (painter.state is AsyncImagePainter.State.Loading) {
        CircularProgressIndicator(
            modifier = modifier
                .align(Alignment.Center)
                .size(60.dp),
            color = MaterialTheme.colorScheme.onBackground
        )
    }

    Image(
        modifier = modifier
            .aspectRatio(1f)
            .clip(RoundedCornerShape(10.dp))
            .background(Color.LightGray),
        painter = painter,
        colorFilter = colorFilter,
        contentScale = scale,
        contentDescription = "itemName",
    )
}