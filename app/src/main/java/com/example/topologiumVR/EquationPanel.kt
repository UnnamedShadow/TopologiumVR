package com.example.topologiumVR


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

class EquationPanel : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Greeting("Meta")
        }
    }

//    companion object {
//        lateinit var viewModel: GraphViewModel
//    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

//@Preview(showBackground = true, apiLevel = 32)
//@Composable
//fun GreetingPreview() {
//    TopologiumVRTheme {
//        Greeting("Android")
//    }
//}

//class GraphViewModel : ViewModel() {
//    private val _equations = mutableStateOf(listOf<Equation>())
//    val equations: State<List<Equation>> = _equations
//}

enum class Axis {
    X, Y, Z, R, G, B
}

enum class NumberType(val size: Int, val symbol: Char) {
    Real(1, '·'), Imaginary(2, '→'), Quaternion(3, '↺')
}

data class Expression(val text: String, val numberTypes: NumberType, val transparency: Float)
data class Macro(val text: String, val inputs: List<String>)

@Composable
fun Definitions(expressions: List<Expression>, macros: Map<String, Macro>, onClickExpression: (index: Int) -> Unit, onClickFunction: (id: String) -> Unit) {
    LazyColumn {
        itemsIndexed(expressions) {
            i: Int, exp: Expression -> Button(onClick = {onClickExpression(i)}) {
                Row (modifier = Modifier){
                    Text(exp.text)
                    Text(exp.numberTypes.symbol.toString(), modifier = Modifier.padding(4.dp).size(12.dp).align(Alignment.Top), color = Color.Red)
                }
            }
        }
        item {
            HorizontalDivider()
        }
        items(macros.entries.toList()){

        }
    }
}

@Preview(apiLevel = 32)
@Composable
fun DefinitionsPreview() {
    val macros = HashMap<String, Macro>()
    macros["f"] = Macro("a*b/2", listOf("a, b"))
    Definitions(expressions = listOf(Expression("3*f(x*2+1, )+4*x^x", NumberType.Real, 0f)), macros = macros, {}, {})
}
