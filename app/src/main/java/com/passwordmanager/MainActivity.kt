package com.passwordmanager

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.passwordmanager.bottomsheet.DetailsBottomSheet
import com.passwordmanager.model.UserModel
import com.passwordmanager.util.BiometricPromptManager
import com.passwordmanager.util.DatabaseManager
import com.passwordmanager.util.StoreData
import com.passwordmanager.util.Util

class MainActivity : AppCompatActivity() {

    private val promptManager by lazy {
        BiometricPromptManager(this)
    }
    private var firstTime = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            val biometricResult by promptManager.promptResults.collectAsState(
                initial = null
            )

            if (firstTime) {
                firstTime = false
                promptManager.showBiometricPrompt(
                    title = "Fingerprint",
                    description = "Authentication"
                )
            }

            Util.authenticationCheck(this,biometricResult)

            PasswordListView(context = this)
        }
    }
}


@SuppressLint("MutableCollectionMutableState")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordListView(context: Context) {

    var isSheetOpen by remember { // BottomSheet to add Data
        mutableStateOf(false)
    }

    var selectedModel by remember {
        mutableStateOf(UserModel("", "", ""))
    }

    var selectedIndex by remember {
        mutableIntStateOf(0)
    }

    var isDetailsSheetOpen by remember { // BottomSheet to Edit or Show data
        mutableStateOf(false)
    }

    val passwordList = ArrayList<UserModel>()

    val dbManager = DatabaseManager(context)
    val fetchData = dbManager.getStringData()
    if (fetchData.isNotEmpty()) {
        val decryptData = StoreData.decrypt(fetchData)

        if (decryptData.isNotEmpty()) {
            passwordList.clear()
            passwordList.addAll(decryptData)
        }

    }


    Column(
        modifier = Modifier
            .padding(PaddingValues(top = 40.dp))
            .fillMaxSize()
            .background(colorResource(id = R.color.white))
    ) {

        Text(
            text = "Password Manager",
            style = TextStyle(
                fontFamily = FontFamily(Font(R.font.sf_pro_display_bold)),
                fontSize = 18.sp
            ),
            modifier = Modifier.padding(16.dp),
            color = Color.Black, textAlign = TextAlign.Center
        )

        Divider(
            color = colorResource(id = R.color.border_grey),
            thickness = 1.dp,
            modifier = Modifier.padding(
                PaddingValues(bottom = 20.dp)
            )
        )

        LazyColumn {

            itemsIndexed(passwordList) { index, item ->

                Card(
                    modifier = Modifier.padding(8.dp),
                    shape = CircleShape,
                    border = BorderStroke(1.dp, colorResource(R.color.border_grey)),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    onClick = {

                        isDetailsSheetOpen = true
                        selectedModel = passwordList[index]
                        selectedIndex = index

                    },
                )
                {
                    Row(
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth()
                            .height(55.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Spacer(modifier = Modifier.width(8.dp))

                        Text(
                            text = passwordList[index].accountType,
                            style = TextStyle(
                                fontFamily = FontFamily(Font(R.font.sf_pro_display_bold)),
                                fontSize = 20.sp,
                            ),
                            maxLines = 1,
                            modifier = Modifier
                                .padding(PaddingValues(horizontal = 10.dp))
                                .widthIn(max = 150.dp),
                            color = Color.Black, textAlign = TextAlign.Center
                        )

                        Spacer(modifier = Modifier.width(5.dp))

                        Text(
                            text = "*******",
                            style = TextStyle(
                                fontFamily = FontFamily(Font(R.font.sf_pro_display_bold)),
                                fontSize = 20.sp
                            ),
                            modifier = Modifier.padding(PaddingValues(top = 10.dp)),
                            color = colorResource(id = R.color.hint_text_grey),
                            textAlign = TextAlign.Center
                        )

                        Spacer(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxWidth()
                        )

                        Image(
                            painter = painterResource(id = R.drawable.dr_img_forward),

                            contentDescription = "Javascript",

                            modifier = Modifier
                                .height(21.dp)
                                .width(21.dp)
                                .padding(PaddingValues(end = 10.dp))
                        )

                    }
                }
            }

        }
    }

    Column(
        Modifier
            .fillMaxSize()
            .padding(30.dp)
            .padding(PaddingValues(bottom = 20.dp)),
        horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.Bottom,
    ) {


        FloatingActionButton(
            onClick = {
                isSheetOpen = true
            },
            containerColor = colorResource(id = R.color.blue),
            shape = RoundedCornerShape(16.dp),
        ) {
            Icon(
                imageVector = Icons.Rounded.Add,
                contentDescription = "Add",
                tint = Color.White,
            )
        }

    }


    MyBottomSheet(
        isBottomSheetVisible = isSheetOpen,
        onDismiss = { isSheetOpen = false },
        onValueChange = { accountName, email, password ->

            if (Util.checkCondition(context, accountName, email, password)) {

                passwordList.add(UserModel(accountName, email, password))

                val value = StoreData.encrypt(passwordList)

                dbManager.insertData(value)

                isSheetOpen = false

                Util.showToastMessage(context, "Data Added Successfully", true)
            }

        }
    )


    DetailsBottomSheet(
        isBottomSheetVisible = isDetailsSheetOpen,
        userModel = selectedModel,
        onDismiss = { isDetailsSheetOpen = false },
        onValueChange = { editedAccountName, editedEmail, editedPassword ->

            if (Util.checkCondition(context, editedAccountName, editedEmail, editedPassword)) {

                passwordList[selectedIndex] =
                    UserModel(editedAccountName, editedEmail, editedPassword)

                val value = StoreData.encrypt(passwordList)

                dbManager.insertData(value)

            }

        },
        onValueDelete = {

            passwordList.removeAt(selectedIndex)

            val value = StoreData.encrypt(passwordList)

            dbManager.insertData(value)

            isDetailsSheetOpen = false

            Util.showToastMessage(context, "Data Deleted Successfully", true)

        }
    )


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyBottomSheet(
    isBottomSheetVisible: Boolean,
    onDismiss: () -> Unit,
    onValueChange: (String, String, String) -> Unit
) {
    val accountName = remember { mutableStateOf("") }
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }

    if (!isBottomSheetVisible) {
        accountName.value = ""
        email.value = ""
        password.value = ""
    }

    if (isBottomSheetVisible) {
        ModalBottomSheet(
            onDismissRequest = onDismiss,
            sheetState = rememberModalBottomSheetState(),
            windowInsets = WindowInsets.ime,
            content = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    OutlinedTextField(
                        value = accountName.value,
                        onValueChange = { accountName.value = it },
                        label = {
                            Text(
                                "Account Name",
                                color = colorResource(id = R.color.label_text_grey),
                                fontWeight = FontWeight.Medium
                            )
                        },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = colorResource(id = R.color.label_text_grey),
                            unfocusedBorderColor = colorResource(id = R.color.label_text_grey),
                        )
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    OutlinedTextField(
                        value = email.value,
                        onValueChange = { email.value = it },
                        label = {
                            Text(
                                "Username/ Email",
                                color = colorResource(id = R.color.label_text_grey),
                                fontWeight = FontWeight.Medium
                            )
                        },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = colorResource(id = R.color.label_text_grey),
                            unfocusedBorderColor = colorResource(id = R.color.label_text_grey),
                        )
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    OutlinedTextField(
                        value = password.value,
                        onValueChange = { password.value = it },
                        label = {
                            Text(
                                "Password",
                                color = colorResource(id = R.color.label_text_grey),
                                fontWeight = FontWeight.Medium
                            )
                        },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = colorResource(id = R.color.label_text_grey),
                            unfocusedBorderColor = colorResource(id = R.color.label_text_grey),
                        )
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = {
                            onValueChange(accountName.value, email.value, password.value)
                        },
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .fillMaxWidth()
                            .height(50.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorResource(id = R.color.button_black)
                        )
                    ) {
                        Text("Add New Account", color = Color.White, fontWeight = FontWeight.Bold)
                    }

                    Spacer(modifier = Modifier.height(40.dp))

                }
            }
        )
    }

}
