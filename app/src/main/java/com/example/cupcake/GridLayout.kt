package com.example.cupcake.compose.ui.designsystem

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max


@Composable
fun GridLayout(
    modifier: Modifier = Modifier,
    columns: Int = 2,
    content: @Composable () -> Unit
) {
    Layout(
        modifier = modifier,
        content = content
    ) { measurables, constraints ->
        val itemSpacer = 10
        val placeables = measurables.map { it.measure(constraints) }
        var maxWidth = 0
        var maxHeight = 0

        var index = 0
        while(index < placeables.size){
            var width = itemSpacer
            var maxIndex = index + columns
            for (i in index until maxIndex) {
                if(i < placeables.size) {
                    width += placeables[i].width + itemSpacer
                }
            }

            if(width > maxWidth) {
                maxWidth = width
            }
            index += columns
        }


        for(i in 0 until columns) {
            var height = itemSpacer
            var index = i
            while (index < placeables.size) {
                height += placeables[index].height + itemSpacer
                index += columns
            }

            if(height > maxHeight) {
                maxHeight = height
            }
        }

        layout(
            width = maxWidth,
            height = maxHeight
        ) {
            var offsetX = 0
            var offsetY = 0
            var maxHeigth = 0
            offsetX = itemSpacer
            offsetY = itemSpacer

            placeables.forEachIndexed { index, placeable ->
                placeable.run { placeRelative(offsetX, offsetY) }
                offsetX += placeable.width + itemSpacer
                if(placeable.height > maxHeigth) {
                    maxHeigth = placeable.height
                }

                if(index > 0 && (index + 1) % columns == 0) {
                    offsetX = itemSpacer
                    offsetY += maxHeigth + itemSpacer
                    maxHeigth = 0
                }
            }
        }
    }
}

@Composable
@Preview
fun GridLayoutPreview() {
    GridLayout(
        columns = 2,
    ) {
        Box(modifier = Modifier.size(50.dp).background(Color.Red))
        Box(modifier = Modifier.size(50.dp).background(Color.Green))
        Box(modifier = Modifier.size(50.dp).background(Color.Blue))
        Box(modifier = Modifier.size(50.dp).background(Color.White))
        Box(modifier = Modifier.size(50.dp).background(Color.Yellow))
        Box(modifier = Modifier.size(50.dp).background(Color.Black))
    }
}

@Composable
@Preview
fun GridTextLayoutPreview() {
    GridLayout(
        columns = 2,
    ) {
        Text("Test1")
        Text("Test2")
        Text("Test3")
        Text("Test4")
        Text("Test1")
        Text("Test2")
        Text("Test3")
        Text("Test4")
    }
}