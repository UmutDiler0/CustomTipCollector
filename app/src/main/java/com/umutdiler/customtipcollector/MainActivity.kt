package com.umutdiler.customtipcollector

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.VisibleForTesting
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.umutdiler.customtipcollector.ui.theme.CustomTipCollectorTheme
import java.text.NumberFormat

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CustomTipCollectorTheme {
                Surface(
                   modifier = Modifier.fillMaxSize(),
                ){
                    CustomTipCollector()
                }
            }
        }
        NumberFormat.getNumberInstance().format(2)
    }
}

@Composable
fun CustomTipCollector(modifier: Modifier = Modifier) {
    var billAmount by remember { mutableStateOf("") }
    var tipPercent by remember { mutableStateOf("") }
    var roundUp by remember { mutableStateOf(false) }

    val amount : Double = billAmount.toDoubleOrNull() ?: 0.0
    val percent : Double = tipPercent.toDoubleOrNull() ?: 0.0
    var tip = calculateTip(amount, percent, roundUp)

    Column{
       Text(
           text = "Calculate Tip",
           fontSize = 18.sp,
           modifier = Modifier.padding(top = 100.dp,start = 50.dp)
       )
        EditNumberField(value = billAmount,
            onValueChange = { billAmount = it },
            modifier = Modifier
                .align(CenterHorizontally)
                .padding(top = 50.dp)
                .width(300.dp)
        )
        EditNumberField2(value = tipPercent,
            onValueChange ={tipPercent = it},
            modifier = Modifier
                .align(CenterHorizontally)
                .padding(top = 50.dp)
                .width(300.dp)

        )
        Row {
            RoundUp(
                roundUp = roundUp,
                onCheckedChange = { roundUp = it },
                modifier = Modifier.padding(top = 50.dp)
            )
        }
        Text(
            text = "Tip Amount: $tip",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(top = 50.dp)

        )
    }
}

@Composable
fun EditNumberField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    TextField(
        value = value,
        singleLine = true,
        onValueChange = onValueChange,
        label = { Text(stringResource(R.string.bill)) },
        modifier = modifier,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
    )
}

@Composable
fun EditNumberField2(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    TextField(
        value = value,
        singleLine = true,
        onValueChange = onValueChange,
        label = { Text(stringResource(R.string.percent)) },
        modifier = modifier,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
    )
}

@Composable
fun RoundUp(roundUp : Boolean,
            modifier: Modifier = Modifier,
            onCheckedChange: (Boolean) -> Unit){
    Row(
        modifier.padding(start = 150.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Round Up",
            fontSize = 18.sp,
        )
        Spacer(modifier = Modifier.width(30.dp))
        Switch(
            checked = roundUp,
            onCheckedChange = onCheckedChange,
        )
    }
}

fun calculateTip(amount: Double, tipPercent: Double,roundUp : Boolean): String{
    if(roundUp){
        return (amount * tipPercent/100).toInt().toString()
    }
    return (amount * tipPercent/100).toString()

}

@Preview(showBackground = true,
    showSystemUi = true)
@Composable
fun GreetingPreview() {
    CustomTipCollectorTheme {
        CustomTipCollector()
    }
}