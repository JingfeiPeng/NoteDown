package presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import persistence.FileIO
import persistence.User

@Composable
fun UserSettingsView() {
    val textState = remember { mutableStateOf(TextFieldValue(User().getCurrentUserId())) }
    Column(modifier = Modifier
        .padding(10.dp)
        .fillMaxWidth()
        .fillMaxHeight()
    ) {
        Text("User ID",  style = MaterialTheme.typography.h4)
        Divider()
        TextField(
            value = textState.value,
            onValueChange = {
                textState.value = it
            }
        )
        val ID_LIMIT: Int = User().STRING_LENGTH
        if (textState.value.text.length <= 3)  {
            Text("ID must have length of atleast 4")
        }
        if (textState.value.text.length > ID_LIMIT)  {
            Text("ID must be no more then ${ID_LIMIT} characters")
        }
        TextButton(
            enabled = textState.value.text.length in 4..ID_LIMIT,
            onClick = {
            var currUser: User = User()
            currUser.userId = textState.value.text
            currUser.storedUserId(textState.value.text)
        }) {
            Text("Save")
        }
    }
}