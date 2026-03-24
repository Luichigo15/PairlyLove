package app.luichigo15.pairly.ui.home.common.question.widget

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import app.luichigo15.pairly.ui.theme.PLTheme
import kotlinx.coroutines.launch

@Composable
fun PLQuestionCard(
    question: String,
    onSwiped: () -> Unit,
    modifier: Modifier = Modifier
) {
    val offsetX = remember { Animatable(0f) }
    val rotation = remember { derivedStateOf { offsetX.value / 60 } }
    val scope = rememberCoroutineScope()

    ElevatedCard(
        modifier = modifier
            .fillMaxSize()
            .padding(15.dp)
            .offset { IntOffset(offsetX.value.toInt(), 0) }
            .graphicsLayer {
                rotationZ = rotation.value
            }
            .pointerInput(Unit) {
                detectDragGestures(
                    onDrag = { change, dragAmount ->
                        change.consume()
                        scope.launch {
                            offsetX.snapTo(offsetX.value + dragAmount.x)
                        }
                    },
                    onDragEnd = {
                        scope.launch {
                            when {
                                offsetX.value > 300 -> {
                                    offsetX.animateTo(1000f)
                                    onSwiped()
                                }

                                offsetX.value < -300 -> {
                                    offsetX.animateTo(-1000f)
                                    onSwiped()
                                }

                                else -> {
                                    offsetX.animateTo(0f)
                                }
                            }
                        }
                    }
                )
            }
    ) {
        Box(
            modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
        ) {
            Text(
                question,
                style = MaterialTheme.typography.headlineLarge,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PLQuestionCardPreview() {
    PLTheme(darkTheme = true) {
        PLQuestionCard("Question", {})
    }
}