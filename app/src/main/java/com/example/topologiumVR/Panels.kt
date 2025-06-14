package com.example.topologiumVR


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

sealed class NumberType(val size: Int, val symbol: Char) {
    data object Real : NumberType(1, '●')
    data object Imaginary : NumberType(2, 'ℑ')
    data object Quaternion : NumberType(4, '∢')
    data class Vector(val dim: Int) : NumberType(dim, '➔')
}

sealed class Axis(val numberType: NumberType, val name: Char) {
    data class Custom(val t: NumberType, val n: Char) : Axis(t, n)
    sealed class Input(private val n: Char) : Axis(NumberType.Real, n) {
        data object X : Input('X')
        data object Y : Input('Y')
        data object Z : Input('Z')
    }

    sealed class Output(private val n: Char) : Axis(NumberType.Real, n) {
        data object R : Output('R')
        data object G : Output('G')
        data object B : Output('B')
        data object A : Output('A')
    }
}

abstract class RawEdit{
    @Composable
    abstract operator fun invoke()
}


data class Edit(val name: String, val raw: MutableState<RawEdit>, val onDelete: () -> Unit) {
    @Composable
    operator fun invoke(){
        Column {
            Row {
                IconButton(onDelete) {
                    Icon(Icons.Default.Delete, "Delete")
                }
                Text(name, modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(Dp.Unspecified,Dp.Unspecified,8.dp,Dp.Unspecified))
            }
            raw.value()
        }
    }
}

@Preview(apiLevel = 32)
@Composable
private fun EditPreview() {
    Edit("iuiu", mutableStateOf(object : RawEdit() {
        @Composable
        override fun invoke() {
            Text("123")
        }
    })) {}()
}


data class Collapsible(val title: String, val collapsed: MutableState<Boolean>) {
    @Composable
    operator fun invoke(edit: ()->Unit, elements: List<@Composable ()->Unit>){
        LazyColumn {
            item {
                Row {
                    IconButton({collapsed.value = collapsed.value.not()}) {
                        if (collapsed.value)
                            Icon(Icons.Default.PlayArrow, "Open Dropdown")
                        else
                            Icon(Icons.Default.ArrowDropDown, "Close Dropdown")
                    }
                    TextButton(edit) {
                        Text(title)
                    }
                }
            }
            if (collapsed.value.not())
            items(elements){
                it()
            }
        }
    }
}

@Preview(apiLevel = 32)
@Composable
private fun CollapsiblePreview() {
    val state = remember { mutableStateOf(false) }
    Collapsible("JASWINDER", state)({}, listOf { Text("text") })
}


data class Expression(val axis: Axis, val text: String) {
    @Composable
    operator fun invoke(onEdit: () -> Unit,) {
        Button(onEdit) {
            Text(
                axis.numberType.symbol.toString(),
                color = Color.Green,
                modifier = Modifier.padding(8.dp, 0.dp),
                fontWeight = FontWeight(1000)
            )
            Text(axis.name + " = ")
            Text(text)
        }
    }
}

@Preview(apiLevel = 32)
@Composable
private fun ExpressionPreview() {
    Expression(Axis.Custom(NumberType.Imaginary, 'a'), "10*x")() {}
}


data class Macro(val name: String, val inputs: List<Char>, val text: String) {
    @Composable
    operator fun invoke(onEdit: () -> Unit) {
        Button(onEdit) {
            Text(name)
            Text("(")
            for (input in inputs) {
                Text(input.toString(), modifier = Modifier.padding(2.dp, 0.dp))
            }
            Text(")")
            Text("=", modifier = Modifier.padding(3.dp, 0.dp))
            Text(text)
        }
    }
}

@Preview(apiLevel = 32)
@Composable
private fun MacroPreview() {
    Macro("spam", listOf('a'), "a*a")() {}
}

class MainPanel : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
//            var arr = remember {arrayOf(Expression(Axis.Input.X, "hi"))}
//            var box = remember {Collapsible("eq", mutableStateOf(true))}
//            box({}, arr.map { {it{}} })
        }
//        SpatialActivityManager.executeOnAppSystemActivity {
//
//        }
    }
}
