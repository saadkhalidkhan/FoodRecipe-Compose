package com.droidgeeks.foodrecipeapp.presentation.components

import androidx.compose.animation.core.*
import androidx.compose.animation.transition
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.dp

@Composable

fun PulseDemo() {

    val color = MaterialTheme.colors.primary

    val pulseAnim = transition(
        definition = PulseAnimationDefinitions.pulseDefinition,
        initState = PulseAnimationDefinitions.PulseState.INITIAL,
        toState = PulseAnimationDefinitions.PulseState.FINAL
    )

    val pulseMagnitude = pulseAnim[PulseAnimationDefinitions.pulsePropKey]

    Canvas(modifier = Modifier
        .fillMaxWidth()
        .height(55.dp), onDraw = {

        drawCircle(
            radius = pulseMagnitude,
            brush = SolidColor(color)
        )

    })


}

object PulseAnimationDefinitions {
    enum class PulseState {
        INITIAL, FINAL
    }

    val pulsePropKey = FloatPropKey("pulseKey")

    val pulseDefinition = transitionDefinition<PulseState> {
        state(PulseState.INITIAL) {
            this[pulsePropKey] = 40f
        }
        state(PulseState.FINAL) {
            this[pulsePropKey] = 50f
        }

        transition(
            PulseState.INITIAL to PulseState.FINAL
        ) {
            pulsePropKey using infiniteRepeatable(
                animation = tween(
                    durationMillis = 500,
                    easing = FastOutSlowInEasing
                ),
                repeatMode = RepeatMode.Reverse
            )
        }
    }
}