package presentation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import java.text.SimpleDateFormat
import java.util.*
import java.util.Calendar


fun getFirstDate(month: Int): Int {
    val calendar = Calendar.getInstance()
    calendar.time = Date()
    calendar[Calendar.MONTH] = month
    calendar[Calendar.DAY_OF_MONTH] = calendar.getActualMinimum(Calendar.DAY_OF_MONTH)
    calendar[Calendar.HOUR_OF_DAY] = 0
    calendar[Calendar.MINUTE] = 0
    calendar[Calendar.SECOND] = 0
    calendar[Calendar.MILLISECOND] = 0
    return calendar.get(Calendar.DAY_OF_WEEK)
}

fun copyDate(day: Int, fromCal: MutableState<Calendar>): Calendar {
    val calendar = Calendar.getInstance()
    calendar.time = Date()
    calendar[Calendar.YEAR] = fromCal.value.get(Calendar.YEAR)
    calendar[Calendar.MONTH] = fromCal.value.get(Calendar.MONTH)
    calendar[Calendar.DAY_OF_MONTH] = day
    calendar[Calendar.HOUR_OF_DAY] = 0
    calendar[Calendar.MINUTE] = 0
    calendar[Calendar.SECOND] = 0
    calendar[Calendar.MILLISECOND] = 0
    return calendar
}

@Composable
fun emptyDiv() {
        Column(
            modifier = Modifier.padding(15.dp)
        ) {
            Text(
                buildAnnotatedString {
//                    append("welcome to ")
                    withStyle(style = SpanStyle(fontWeight = FontWeight.W900, color = Color(0xFF4552B8))
                    ) {

                    }
                }
            )
    }

}

@Composable
fun dateDiv(number: Int, selectedDate: MutableState<Calendar>, cal: MutableState<Calendar>) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp)
            .clickable{ selectedDate.value = copyDate(number, cal) },
        elevation = 10.dp
    ) {
        Column(
            modifier = Modifier.padding(15.dp)
        ) {
            Text(
                buildAnnotatedString {
//                    append("welcome to ")
                    withStyle(style = SpanStyle(fontWeight = FontWeight.W900, color = Color(0xFF4552B8))
                    ) {
                        append(number.toString())
                    }
                }
            )
//            Text(
//                buildAnnotatedString {
//                    append("Now you are in the ")
//                    withStyle(style = SpanStyle(fontWeight = FontWeight.W900)) {
//                        append("Card")
//                    }
//                    append(" section")
//                }
//            )
        }
    }

}

@Composable
fun todaysDate(number: Int) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp)
            .clickable{ }

    ) {
        Column(
            modifier = Modifier.padding(15.dp)
        ) {
            Text(
                buildAnnotatedString {
//                    append("welcome to ")
                    withStyle(style = SpanStyle(fontWeight = FontWeight.W900, color = Color.Red)
                    ) {
                        append(number.toString())
                    }
                }
            )
        }
    }

}

@Composable
fun dayDiv(day: Int) {
    val days = listOf("Sun", "Mon", "Tue", "Wed", "Thur", "Fri", "Sat")
    Text(text= days[day],  style = MaterialTheme.typography.body1)
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CalendarView() {
    val cal : MutableState<Calendar> = remember { mutableStateOf(Calendar.getInstance()) }
    val today : Calendar = Calendar.getInstance()
    today.setTime(Calendar.getInstance().getTime())
    var selectedDate: MutableState<Calendar> = remember { mutableStateOf(today) }

    val monthDate = SimpleDateFormat("MMM YYYY")
    val dayDate = SimpleDateFormat("MMM d")

    val cals = remember { mutableStateOf<String>(monthDate.format(cal.value.getTime())) }
    Row(Modifier.fillMaxSize()) {
        Box(modifier = Modifier.fillMaxWidth(0.7f)) {
        Column (horizontalAlignment = Alignment.CenterHorizontally) {
            Row() {
                TextButton(onClick = {
                    cal.value.add(Calendar.MONTH, -1);
                    cals.value = monthDate.format(cal.value.getTime())
                }) {
                    Text("<")
                }
                Text(
                    text = cals.value,
                    style = MaterialTheme.typography.h3
                )
                TextButton(onClick = {
                    cal.value.add(Calendar.MONTH, 1);
                    cals.value = monthDate.format(cal.value.getTime())
                }) {
                    Text(">")
                }
            }

            val numbers = (1..cal.value.getActualMaximum(Calendar.DATE)).toList()

            val days = listOf("Sun", "Mon", "Tue", "Wed", "Thur", "Fri", "Sat")
            LazyVerticalGrid(
                    cells = GridCells.Fixed(7)
                ) {
                    items(days.size) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            dayDiv(it)
                        }
                    }
                    val firstDay: Int = getFirstDate(cal.value.get(Calendar.MONTH)) - 1
                    items(firstDay) { emptyDiv() }
                    items(numbers.size) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            if (cal.value.get(Calendar.DAY_OF_MONTH) == it + 1 && cal.value.get(Calendar.MONTH) == today.get(Calendar.MONTH) && cal.value.get(Calendar.YEAR) == today.get(Calendar.YEAR) ) {
                                    todaysDate(it + 1)
                            } else {
                                    dateDiv(it + 1, selectedDate, cal)
                            }
                        }

                    }
                }
            }
        }
        Box(modifier = Modifier.fillMaxWidth(1f).padding(5.dp)) {
            Text("Notes for " + dayDate.format(selectedDate.value.getTime()).toString(), style = MaterialTheme.typography.body1)
        }
    }
}