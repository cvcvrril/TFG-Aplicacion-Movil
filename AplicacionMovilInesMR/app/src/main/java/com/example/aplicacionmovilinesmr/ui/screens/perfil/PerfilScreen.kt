package com.example.aplicacionmovilinesmr.ui.screens.perfil

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ImageSearch
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.aplicacionmovilinesmr.R

@Composable
fun PerfilScreen(
    viewModel: PerfilViewModel = hiltViewModel(),
    bottomNavigationBar: @Composable () -> Unit = {},
    toConfig: () -> Unit,
) {

    val state = viewModel.uiState.collectAsStateWithLifecycle()

    GetPerfilScreen(
        state = state.value,
        bottomNavigationBar = bottomNavigationBar,
        toConfig = toConfig,
        handleEvent = {event -> viewModel.handleEvent(event)},
    )

}

@Composable
fun GetPerfilScreen(
    state: PerfilState,
    handleEvent: (PerfilEvent) -> Unit,
    bottomNavigationBar: @Composable () -> Unit = {},
    toConfig: () -> Unit,
) {

    var imageUri: Any? by remember { mutableStateOf(R.drawable.base) }

    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        bottomBar = bottomNavigationBar
    )
    { innerPadding ->
        LaunchedEffect(state.error) {
            state.error?.let {
                snackbarHostState.showSnackbar(
                    message = state.error,
                    duration = SnackbarDuration.Short,
                )
            }
        }
        Column(
            Modifier
                .fillMaxSize()
                .padding(innerPadding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 16.dp, top = 10.dp, start = 16.dp)
                    .align(Alignment.End),
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.End
            ) {
                IconButton(onClick = { toConfig() }) {
                    Icon(
                        imageVector = Icons.Outlined.Settings,
                        contentDescription = "Settings"
                    )
                }
            }
            if (state.editmode) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 16.dp, top = 10.dp, start = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    IconButton(
                        onClick = {
                            handleEvent(PerfilEvent.OnEditModeChange(state.editmode))
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Volver"
                        )
                    }
                    Text(
                        text = "Editar el perfil",
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(100.dp))
            Box(
                Modifier
                    .padding(16.dp)
            )
            {
                Column {
                    Row {
                        CircularImage(
                            image = imageUri,
                            modifier = Modifier.size(100.dp),
                            editMode = state.editmode,
                            onImageSelected = { uri ->
                                imageUri = uri
                                handleEvent(PerfilEvent.OnImagenChange(uri))
                            }
                        )
                    }
                    Column {
                        if (state.editmode) {
                            state.perfil?.let {
                                OutlinedTextField(
                                    value = it.nombreCompleto,
                                    onValueChange = { valueCHanged ->
                                        handleEvent(
                                            PerfilEvent.OnNombreCompletoChange(
                                                valueCHanged
                                            )
                                        )
                                    },
                                    placeholder = { Text(text = "Nombre Completo") }
                                )
                            }
                        } else {
                            NombreCompletoText(
                                text = state.perfil?.nombreCompleto ?: "Nombre Completo"
                            )
                        }
                        UsernameText(text = ("@" + state.perfil?.username))
                        Spacer(modifier = Modifier.height(16.dp))
                        if (!state.editmode){
                            OutlinedButton(
                                onClick = {
                                    handleEvent(PerfilEvent.OnEditModeChange(state.editmode))
                                },
                                border = BorderStroke(1.5.dp, color = Color.Gray),
                                modifier = Modifier
                                    .width(200.dp)
                                    .align(Alignment.CenterHorizontally)
                            ) {
                                Text(text = "Editar perfil")
                            }
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        if (state.editmode) {
                            state.perfil?.let {
                                OutlinedTextField(
                                    value = it.descripcion,
                                    onValueChange = { valueCHanged ->
                                        handleEvent(
                                            PerfilEvent.OnDescripciionChange(
                                                valueCHanged
                                            )
                                        )
                                    },
                                    placeholder = { Text(text = "Descripcion") }
                                )
                            }
                        } else {
                            DescripcionText(
                                text = state.perfil?.descripcion
                                    ?: ("Hola, esto es una descripción del perfil," +
                                            " para ver cómo va la cosa, si se ve bien o no.")
                            )
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        if (state.editmode) {
                            Button(onClick = {
                                handleEvent(PerfilEvent.UpdatePerfil)
                            }) {
                                Text(text = "Actualizar perfil")
                            }
                        }
                        Spacer(modifier = Modifier.height(400.dp))
                    }
                }
            }

        }
    }

}


@Composable
fun NombreCompletoText(
    text: String,
) {
    Text(
        text = text,
        fontSize = 40.sp,
        textAlign = TextAlign.Left,
        fontWeight = FontWeight.Black,
        lineHeight = 46.sp,
        modifier = Modifier
            .padding(start = 16.dp)
    )
}

@Composable
fun UsernameText(
    text: String,
) {
    Text(
        text = text,
        fontSize = 26.sp,
        textAlign = TextAlign.Left,
        fontWeight = FontWeight.Medium,
        lineHeight = 46.sp,
        modifier = Modifier
            .padding(start = 16.dp)
    )
}

@Composable
fun DescripcionText(
    text: String,
) {
    Text(
        text = text,
        fontSize = 13.sp,
        textAlign = TextAlign.Left,
        fontWeight = FontWeight.Normal,
        lineHeight = 20.sp,
        modifier = Modifier
            .padding(start = 16.dp)
    )
}

@Composable
fun CircularImage(
    image: Any?,
    modifier: Modifier = Modifier,
    editMode: Boolean,
    onImageSelected: (Uri) -> Unit
) {
    val context = LocalContext.current
    val photoPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) {
        if (it != null) {
            onImageSelected(it)
        }
    }

    Box(
        modifier = modifier
            .aspectRatio(1f)
            .clickable(enabled = editMode, onClick = {
                photoPicker.launch(
                    PickVisualMediaRequest(
                        ActivityResultContracts.PickVisualMedia.ImageOnly
                    )
                )
            })
    ) {
        AsyncImage(
            model = ImageRequest.Builder(context)
                .data(image)
                .crossfade(true)
                .build(),
            contentDescription = "profile image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .clip(CircleShape)
                .alpha(if (editMode) 0.5f else 1f)
        )

        if (editMode) {
            Icon(
                imageVector = Icons.Default.ImageSearch,
                contentDescription = "Change Image Icon",
                modifier = Modifier
                    .size(40.dp)
                    .alpha(0.8f)
                    .align(Alignment.Center)
            )
        }
    }
}

@Preview
@Composable
fun PreviewScreen(
) {
    Column(
        Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(end = 16.dp, top = 10.dp, start = 16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 16.dp, top = 10.dp, start = 16.dp)
                .align(Alignment.End),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.End
        ) {
            IconButton(
                onClick = {
                    /*TODO*/
                }
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack ,
                    contentDescription = "Volver"
                )
            }
            Text(
                text = "Editar el perfil"
            )
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    imageVector = Icons.Outlined.Settings,
                    contentDescription = "Settings"
                )
            }

        }
        Spacer(modifier = Modifier.height(100.dp))
        Box(
            Modifier
                .padding(16.dp)
        )
        {
            Column {
                Row {
                    Column {
                        NombreCompletoText(text = "Martina Salinas")
                        UsernameText(text = "@martinasalinas")
                        Spacer(modifier = Modifier.height(16.dp))
                        OutlinedButton(
                            onClick = { /*TODO*/ },
                            border = BorderStroke(1.5.dp, color = Color.Gray),
                            modifier = Modifier
                                .width(200.dp)
                                .align(Alignment.CenterHorizontally)
                        ) {
                            Text(text = "Editar perfil")
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                        DescripcionText(
                            text = "Hola, esto es una descripción del perfil," +
                                    " para ver cómo va la cosa, si se ve bien o no."
                        )
                        Spacer(modifier = Modifier.height(400.dp))
                    }
                }
            }

        }
    }
}