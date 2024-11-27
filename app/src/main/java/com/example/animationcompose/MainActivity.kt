package com.example.animationcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.animationcompose.ui.theme.AnimationComposeTheme
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AnimationComposeApp()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnimationComposeApp() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Animation Compose") }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            TweenAnimationSection()
            SpringAnimationSection()
            KeyframesAnimationSection()
            InfiniteRepeatableAnimationSection()
        }
    }
}

@Composable
fun TweenAnimationSection() {
    var offSetX by remember { mutableFloatStateOf(0f) }

    val animatedOffSetX = animateFloatAsState(
        targetValue = offSetX,
        animationSpec = tween(
            durationMillis = 1000,
            easing = FastOutSlowInEasing
        ), label = ""
    )

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Box(
            modifier = Modifier
                .size(50.dp)
                .offset(x = animatedOffSetX.value.dp)
                .background(Color.Blue)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            offSetX = if (offSetX == 0f) 200f else 0f
        }) {
            Text(text = "Start Animation")
        }
    }
}

@Composable
fun SpringAnimationSection() {
    var offSetY by remember { mutableFloatStateOf(0f) }

    val animatedOffSetY = animateFloatAsState(
        targetValue = offSetY,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ), label = ""
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(50.dp)
                .offset(y = animatedOffSetY.value.dp)
                .background(Color.Magenta)
                .clickable {
                    offSetY = if (offSetY == 0f) 100f else 0f
                }
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Click the box to see the bounce!",
            style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun KeyframesAnimationSection() {
    var size by remember { mutableStateOf(200.dp) }

    val animatedSize = animateDpAsState(
        targetValue = size,
        animationSpec = keyframes {
            durationMillis = 1000
            50.dp at 0
            100.dp at 300
            75.dp at 600
        }, label = ""
    )

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp),
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(animatedSize.value)
                .background(Color.Green)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            size = when (size) {
                50.dp -> 100.dp
                100.dp -> 75.dp
                75.dp -> 150.dp
                else -> 50.dp
            }
        }) {
            Text(text = "Start Animation")
        }
    }
}

@Composable
fun InfiniteRepeatableAnimationSection() {
    var colorState by remember { mutableStateOf(Color.Blue) }

    val animatedColor by animateColorAsState(
        targetValue = colorState,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1000,
                easing = FastOutLinearInEasing
            ),
            repeatMode = RepeatMode.Reverse
        )
    )

    LaunchedEffect(Unit) {
        while (true) {
            delay(5000)
            colorState = if (colorState == Color.Blue) Color.Yellow else Color.Red
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(100.dp)
                .background(animatedColor, CircleShape)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Infinite Color Change",
            style = MaterialTheme.typography.bodyMedium
        )
    }
}
