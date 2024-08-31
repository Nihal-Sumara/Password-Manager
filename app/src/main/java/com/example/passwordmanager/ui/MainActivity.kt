package com.example.passwordmanager.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.passwordmanager.R
import com.example.passwordmanager.model.AccountData
import com.example.passwordmanager.theme.PasswordManagerTheme
import com.example.passwordmanager.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PasswordManagerTheme {
                val viewModel: MainViewModel by viewModels<MainViewModel>()
                var showSheet by remember { mutableStateOf(false) }
                val getAllAccounts by viewModel.allData.collectAsState(initial = emptyList())
                var addNewAccount = AccountData(0, null, null, null)
                var isUpdateDelete = false


                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopAppBar(
                            title = {
                                Text(
                                    text = "Password Manager"
                                )
                            }
                        )
                    },
                    floatingActionButton = {
                        FloatingActionButton(
                            onClick = {
                                isUpdateDelete = false
                                showSheet = true
                            },
                            shape = RoundedCornerShape(10.dp),
                            containerColor = colorResource(id = R.color.blue),
                            contentColor = Color.White
                        ) {
                            Icon(imageVector = Icons.Default.Add, contentDescription = null)
                        }
                    }
                ) { innerPadding ->
                    Column(modifier = Modifier.padding(innerPadding)) {
                        LazyColumn(
                            modifier = Modifier
                        ) {
                            items(count = getAllAccounts.size) { index ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 20.dp, vertical = 10.dp)
                                        .background(
                                            color = Color.White,
                                            shape = RoundedCornerShape(50.dp)
                                        )
                                        .border(
                                            width = 1.dp,
                                            color = colorResource(id = R.color.gray_border),
                                            shape = RoundedCornerShape(50.dp)
                                        )
                                        .clickable {
                                            isUpdateDelete = true
                                            showSheet = true
                                            addNewAccount = getAllAccounts[index]
                                        },
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = getAllAccounts[index].accountName!!,
                                        modifier = Modifier.padding(20.dp),
                                        fontSize = 20.sp
                                    )
                                    Text(
                                        text = "********",
                                        color = colorResource(id = R.color.gray),
                                        modifier = Modifier.padding(20.dp)
                                    )
                                    Spacer(modifier = Modifier.weight(1F))
                                    Image(
                                        painter = painterResource(id = R.drawable.ic_forward),
                                        contentDescription = null,
                                        modifier = Modifier.padding(horizontal = 10.dp)
                                    )
                                }
                            }
                        }
                        if (showSheet) {
                            DetailBottomSheet(
                                viewModel, isUpdateDelete = isUpdateDelete, clickedData = addNewAccount
                            ) {
                                showSheet = false
                            }
                        }
                    }
                }

            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailBottomSheet(
    viewModel: MainViewModel,
    isUpdateDelete: Boolean,
    clickedData: AccountData?,
    onDismiss: () -> Unit
) {
    val modalBottomSheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()

    ModalBottomSheet(
        onDismissRequest = { onDismiss() },
        sheetState = modalBottomSheetState,
        dragHandle = { BottomSheetDefaults.DragHandle() },
        modifier = Modifier.fillMaxSize(),
    ) {
        var accountName by remember {
            mutableStateOf("")
        }
        var accountNameData by remember {
            mutableStateOf(clickedData!!.accountName)
        }
        var email by remember {
            mutableStateOf("")
        }
        var emailData by remember {
            mutableStateOf(clickedData!!.email)
        }
        var password by remember {
            mutableStateOf("")
        }
        var passwordData by remember {
            mutableStateOf(clickedData!!.password)
        }
        val context = LocalContext.current

        Column {
            TextField(
                value = if (!isUpdateDelete) {
                    accountName
                } else {
                    accountNameData!!
                },
                onValueChange = {
                    if (!isUpdateDelete) {
                        accountName = it
                    } else {
                        accountNameData = it
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 30.dp, vertical = 10.dp)
                    .border(
                        width = 1.dp,
                        color = colorResource(id = R.color.gray_border),
                        shape = RoundedCornerShape(6.dp)
                    ),
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.White,
                    focusedContainerColor = Color.White,
                    focusedIndicatorColor = Color.White,
                    unfocusedIndicatorColor = Color.White
                ),
                label = {
                    Text(
                        text = "Account Name",
                        color = colorResource(id = R.color.label_color),
                        fontSize = 13.sp
                    )
                }
            )
            TextField(
                value = if (!isUpdateDelete) {
                    email
                } else {
                    emailData!!
                },
                onValueChange = {
                    if (!isUpdateDelete) {
                        email = it
                    } else {
                        emailData = it
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 30.dp, vertical = 10.dp)
                    .border(
                        width = 1.dp,
                        color = colorResource(id = R.color.gray_border),
                        shape = RoundedCornerShape(6.dp)
                    ),
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.White,
                    focusedContainerColor = Color.White,
                    focusedIndicatorColor = Color.White,
                    unfocusedIndicatorColor = Color.White
                ),
                label = {
                    Text(
                        text = "Username/ Email",
                        color = colorResource(id = R.color.label_color),
                        fontSize = 13.sp
                    )
                }
            )
            TextField(
                value = if (!isUpdateDelete) {
                    password
                } else {
                    passwordData!!
                },
                onValueChange = {
                    if (!isUpdateDelete) {
                        password = it
                    } else {
                        passwordData = it
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 30.dp, vertical = 10.dp)
                    .border(
                        width = 1.dp,
                        color = colorResource(id = R.color.gray_border),
                        shape = RoundedCornerShape(6.dp)
                    ),
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.White,
                    focusedContainerColor = Color.White,
                    focusedIndicatorColor = Color.White,
                    unfocusedIndicatorColor = Color.White
                ),
                label = {
                    Text(
                        text = "Password",
                        color = colorResource(id = R.color.label_color),
                        fontSize = 13.sp
                    )
                }
            )
            if (!isUpdateDelete) {
                Button(
                    onClick = {
                        if (accountName.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {
                            scope.launch(Dispatchers.IO) {
                                viewModel.insertData(
                                    AccountData(
                                        null,
                                        accountName,
                                        email,
                                        password
                                    )
                                )
                                onDismiss()
                            }
                        } else {
                            Toast.makeText(context, "Please enter all fields", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }, modifier = Modifier
                        .fillMaxWidth()
                        .padding(30.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Black
                    ),
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Text(text = "Add New Account")
                }
            } else {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 30.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(
                        onClick = {
                            if (accountNameData!!.isNotEmpty() && emailData!!.isNotEmpty() && passwordData!!.isNotEmpty()) {
                                scope.launch(Dispatchers.IO) {
                                    viewModel.updateData(
                                        AccountData(
                                            clickedData!!.id,
                                            accountNameData,
                                            emailData,
                                            passwordData
                                        )
                                    )
                                    onDismiss()
                                }
                            } else {
                                Toast.makeText(
                                    context,
                                    "Please enter all fields",
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                            }
                        }, modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Black
                        ),
                        shape = RoundedCornerShape(20.dp)
                    ) {
                        Text(text = "Update")
                    }
                    Button(
                        onClick = {
                            if (accountNameData!!.isNotEmpty() && emailData!!.isNotEmpty() && passwordData!!.isNotEmpty()) {
                                scope.launch(Dispatchers.IO) {
                                    viewModel.deleteData(
                                        AccountData(
                                            clickedData!!.id,
                                            accountName,
                                            email,
                                            password
                                        )
                                    )
                                    onDismiss()
                                }
                            } else {
                                Toast.makeText(
                                    context,
                                    "Please enter all fields",
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                            }
                        }, modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Red
                        ),
                        shape = RoundedCornerShape(20.dp)
                    ) {
                        Text(text = "Delete")
                    }
                }
            }
        }
    }
}