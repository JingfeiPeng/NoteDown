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
import database.Document
import persistence.DocumentMetaCRUDJson.readAllMetaData
import java.text.SimpleDateFormat
import java.util.*


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
                    withStyle(style = SpanStyle(fontWeight = FontWeight.W900, color = Color(0xFF4552B8))
                    ) {

                    }
                }
            )
    }

}

@Composable
fun dateDiv(
    number: Int,
    selectedDate: MutableState<Calendar>,
    cal: MutableState<Calendar>,
    calMap: HashMap<Calendar, MutableList<Document>>
) {
//    Color(0xFF4552B8)
    val stripedCal: Calendar = cal.value
    stripedCal[Calendar.DAY_OF_MONTH] = number
    stripedCal[Calendar.HOUR_OF_DAY] = 0
    stripedCal[Calendar.MINUTE] = 0
    stripedCal[Calendar.SECOND] = 0
    stripedCal[Calendar.MILLISECOND] = 0
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
                    if (calMap[stripedCal] != null) {
                        withStyle(style = SpanStyle(fontWeight = FontWeight.W900, color = Color(0xFF4552B8))
                        ) {
                            append(number.toString() + " (" + calMap[stripedCal]?.size.toString() + ")")
                        }
                    } else {
                        withStyle(style = SpanStyle(fontWeight = FontWeight.W900, color = Color.Gray)
                        ) {
                            append("$number   ")
                        }
                    }

                }
            )
        }
    }

}

@Composable
fun todaysDate(
    number: Int,
    selectedDate: MutableState<Calendar>,
    cal: MutableState<Calendar>,
    calMap: HashMap<Calendar, MutableList<Document>>
) {
    val stripedCal: Calendar = cal.value
    stripedCal[Calendar.DAY_OF_MONTH] = number
    stripedCal[Calendar.HOUR_OF_DAY] = 0
    stripedCal[Calendar.MINUTE] = 0
    stripedCal[Calendar.SECOND] = 0
    stripedCal[Calendar.MILLISECOND] = 0
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp)
            .clickable{ selectedDate.value = copyDate(number, cal) }

    ) {
        Column(
            modifier = Modifier.padding(15.dp)
        ) {
            Text(
                buildAnnotatedString {
                    if (calMap[stripedCal] != null) {
                        withStyle(style = SpanStyle(fontWeight = FontWeight.W900, color = Color.Red)
                        ) {
                            append(number.toString() + " (" + calMap[stripedCal]?.size.toString() + ")")
                        }
                    } else {
                        withStyle(style = SpanStyle(fontWeight = FontWeight.W900, color = Color.Red)
                        ) {
                            append("$number   ")
                        }
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
fun CalendarView(calendarView: MutableState<Boolean>) {
    val cal : MutableState<Calendar> = remember { mutableStateOf(Calendar.getInstance()) }
    val today : Calendar = Calendar.getInstance()
    today.setTime(Calendar.getInstance().getTime())
    var selectedDate: MutableState<Calendar> = remember { mutableStateOf(today) }
    val calMap:HashMap<Calendar, MutableList<Document>> = HashMap<Calendar, MutableList<Document>>()
    val allMetaData = readAllMetaData();

    for (metaData in allMetaData) {
//        val parsedCreatedOn = Date()
        val cal: Calendar = Calendar.getInstance()
        cal.time = metaData.createdOn
        cal[Calendar.HOUR_OF_DAY] = 0
        cal[Calendar.MINUTE] = 0
        cal[Calendar.SECOND] = 0
        cal[Calendar.MILLISECOND] = 0

        if (calMap[cal] == null) {
            calMap[cal] = mutableListOf(metaData)
        } else {
            calMap[cal]?.add(metaData)
        }
    }


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
                            if (today.get(Calendar.DAY_OF_MONTH) == it + 1 && cal.value.get(Calendar.MONTH) == today.get(Calendar.MONTH) && cal.value.get(Calendar.YEAR) == today.get(Calendar.YEAR) ) {

                                todaysDate(it + 1, selectedDate, cal, calMap)
                            } else {
                                    dateDiv(it + 1, selectedDate, cal, calMap)
                            }
                        }

                    }
                }
            }
        }
        Box(modifier = Modifier.fillMaxWidth(1f).padding(5.dp)) {
            Column() {
                Text(
                    "Notes for " + dayDate.format(selectedDate.value.getTime()).toString() + "\n",
                    style = MaterialTheme.typography.h6
                )


                val stripedSelCal: Calendar = selectedDate.value
                stripedSelCal[Calendar.HOUR_OF_DAY] = 0
                stripedSelCal[Calendar.MINUTE] = 0
                stripedSelCal[Calendar.SECOND] = 0
                stripedSelCal[Calendar.MILLISECOND] = 0

                if (calMap[stripedSelCal] == null) {

                    Text("No notes on this day")

                } else {
                    LazyVerticalGrid(
                        cells = GridCells.Fixed(1)
                    ) {
                        var x: Int = 0
                        if (calMap[stripedSelCal] != null) {
                            x = calMap[stripedSelCal]?.size!!
                        }

                        items(x) {
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(15.dp)
                                    .clickable{ calendarView.value = !calendarView.value }

                            ) {
                                Column(
                                    modifier = Modifier.padding(15.dp)
                                ) {
                                    Text(
                                        buildAnnotatedString {
                                                withStyle(style = SpanStyle(fontWeight = FontWeight.W900, color = Color(0xFF4552B8))
                                                ) {
                                                    calMap[stripedSelCal]?.get(it)?.let { it1 -> append(it1.path) }
                                                }
                                        }
                                    )
                                }
                            }

                        }
                    }
                }
            }
        }
    }
}