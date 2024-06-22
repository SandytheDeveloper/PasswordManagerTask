package com.passwordmanager.bottomsheet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.passwordmanager.R
import com.passwordmanager.model.UserModel
import com.passwordmanager.util.Util

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsBottomSheet(
    isBottomSheetVisible: Boolean,
    userModel: UserModel,
    onDismiss: () -> Unit,
    onValueChange: (String, String, String) -> Unit,
    onValueDelete: (Boolean) -> Unit
) {
    val accountName = remember { mutableStateOf("") }
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }

    accountName.value = userModel.accountType
    email.value = userModel.email
    password.value = userModel.password

    val screenType = remember { mutableIntStateOf(1) }
    if (!isBottomSheetVisible)
        screenType.intValue = 1

    if (isBottomSheetVisible) {

        ModalBottomSheet(
            onDismissRequest = onDismiss,
            sheetState = rememberModalBottomSheetState(),
            windowInsets = WindowInsets.ime,
            content = {

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(PaddingValues(horizontal = 16.dp))
                ) {


                    if (screenType.intValue == 1) {

                        Text(
                            text = "Account Details",
                            style = TextStyle(
                                fontFamily = FontFamily(Font(R.font.sf_pro_display_bold)),
                                fontSize = 19.sp,
                            ),
                            modifier = Modifier.padding(4.dp),
                            color = colorResource(id = R.color.blue), textAlign = TextAlign.Center
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        Text(
                            text = "Account Type",
                            style = TextStyle(
                                fontWeight = FontWeight.Medium,
                                fontSize = 11.sp,
                            ),
                            modifier = Modifier.padding(4.dp),
                            color = colorResource(id = R.color.label_text_grey),
                            textAlign = TextAlign.Center
                        )

                        Text(
                            text = accountName.value,
                            style = TextStyle(
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp,
                            ),
                            modifier = Modifier.padding(4.dp),
                            color = colorResource(id = R.color.black), textAlign = TextAlign.Center
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        Text(
                            text = "Username/ Email",
                            style = TextStyle(
                                fontWeight = FontWeight.Medium,
                                fontSize = 11.sp,
                            ),
                            modifier = Modifier.padding(4.dp),
                            color = colorResource(id = R.color.label_text_grey),
                            textAlign = TextAlign.Center
                        )

                        Text(
                            text = email.value,
                            style = TextStyle(
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp,
                            ),
                            modifier = Modifier.padding(4.dp),
                            color = colorResource(id = R.color.black), textAlign = TextAlign.Center
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        Text(
                            text = "Password",
                            style = TextStyle(
                                fontWeight = FontWeight.Medium,
                                fontSize = 11.sp,
                            ),
                            modifier = Modifier.padding(4.dp),
                            color = colorResource(id = R.color.label_text_grey),
                            textAlign = TextAlign.Center
                        )

                        Text(
                            text = "********",
                            style = TextStyle(
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp,
                            ),
                            modifier = Modifier.padding(4.dp),
                            color = colorResource(id = R.color.black), textAlign = TextAlign.Center
                        )


                    } else {

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

                    }

                    if (screenType.intValue == 2) {

                        Spacer(modifier = Modifier.height(8.dp))

                        var text = ""
                        var color = 0
                        Util.checkPasswordStrength(password.value) { t, c ->
                            text = t
                            color = c
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {

                            Text(
                                text,
                                modifier = Modifier.padding(horizontal = 10.dp),
                                color = colorResource(id = color),
                                fontWeight = FontWeight.Medium
                            )

                            ClickableText(
                                text = AnnotatedString("Auto-generate password"),
                                modifier = Modifier.padding(horizontal = 10.dp),
                                style = TextStyle(
                                    colorResource(id = R.color.blue),
                                    textAlign = TextAlign.End,
                                    fontWeight = FontWeight.Medium
                                ),
                                onClick = {
                                    password.value = Util.generatePassword()
                                }
                            )
                        }

                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround
                    )
                    {

                        Button(
                            onClick = {

                                if (screenType.intValue == 2) {
                                    onValueChange(
                                        accountName.value,
                                        email.value,
                                        password.value
                                    )
                                }

                                screenType.intValue = if (screenType.intValue == 1) 2 else 1

                            },
                            modifier = Modifier
                                .height(50.dp)
                                .fillMaxWidth()
                                .weight(1f),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = colorResource(id = R.color.button_black)
                            )
                        ) {
                            Text(
                                if (screenType.intValue == 1) "Edit" else "Save",
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Spacer(modifier = Modifier.width(15.dp))

                        Button(
                            onClick = {

                                onValueDelete(true)

                            },
                            modifier = Modifier
                                .height(50.dp)
                                .fillMaxWidth()
                                .weight(1f),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = colorResource(id = R.color.button_red)
                            )
                        ) {
                            Text(
                                "Delete",
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }



                    Spacer(modifier = Modifier.height(60.dp))
                }

            }
        )
    }
}