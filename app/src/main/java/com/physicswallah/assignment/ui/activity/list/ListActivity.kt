package com.physicswallah.assignment.ui.activity.list

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import coil.compose.rememberAsyncImagePainter
import com.physicswallah.assignment.ui.activity.details.DetailActivity
import com.physicswallah.assignment.ui.base.ViewModelFactory
import com.physicswallah.assignment.ui.model.Character
import com.physicswallah.assignment.ui.model.Location
import com.physicswallah.assignment.ui.retrofit.APIService
import com.physicswallah.assignment.ui.retrofit.Resource
import com.physicswallah.assignment.ui.retrofit.RetrofitClient
import com.physicswallah.assignment.ui.theme.PhysicswallahAssignmentTheme
import com.physicswallah.assignment.ui.viewModelRepo.CharacterRepo
import com.physicswallah.assignment.ui.viewModelRepo.CharacterViewModel

class ListActivity : ComponentActivity() {
    private lateinit var listState: MutableState<List<Character>>
    private lateinit var viewModel: CharacterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        // Initialize the repository, viewmodel and the ViewModelFactory
        val retrofitClient = RetrofitClient()
        val apiService = retrofitClient.buildApi(APIService::class.java)
        val repository = CharacterRepo(apiService)
        val factory = ViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory)[CharacterViewModel::class.java]

        // Fetch the list
        viewModel.getList()

        setContent {
            PhysicswallahAssignmentTheme {
                CharacterScreen(viewModel = viewModel)
            }
        }
    }

    @Composable
    fun CharacterScreen(viewModel: CharacterViewModel) {
        listState = remember { mutableStateOf(emptyList<Character>()) }

        // Observe LiveData from the ViewModel
        viewModel.listResponse.observe(LocalLifecycleOwner.current) { it ->
            when (it) {
                is Resource.Failure -> {

                }

                is Resource.Loading -> {
                }

                is Resource.Success -> {
                    if (it.value.results.isNotEmpty()) {
                        listState.value = it.value.results
                    }
                }
            }
        }

        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            CharacterList(
                characterList = listState.value, modifier = Modifier.padding(innerPadding)
            )
        }
    }


    @Composable
    fun CharacterList(characterList: List<Character>, modifier: Modifier = Modifier) {
        Column(modifier = modifier.fillMaxSize()) {
            Text(
                text = "List of Characters",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontSize = 20.sp, fontFamily = FontFamily.Serif, fontWeight = FontWeight.Bold
                ),
                modifier = Modifier
                    .padding(vertical = 10.dp)
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally),
                textAlign = TextAlign.Center,
            )

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                contentPadding = PaddingValues(vertical = 10.dp)
            ) {
                items(characterList) { character ->
                    CharacterItem(character = character, onClick = {
                        startActivity(
                            Intent(
                                this@ListActivity,
                                DetailActivity::class.java
                            ).putExtra("data", it)
                        )
                    })
                }
            }
        }
    }

    @Composable
    fun CharacterItem(
        character: Character,
        modifier: Modifier = Modifier,
        onClick: (Character) -> Unit
    ) {
        Row(
            modifier = modifier
                .padding(15.dp)
                .fillMaxWidth()
                .clickable { onClick(character) },
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Image on the left loaded from a URL
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape)
                    .background(Color.Gray)
            ) {
                Image(
                    painter = rememberAsyncImagePainter(model = character.image),
                    contentDescription = "Character Image",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.width(10.dp))
            Column {
                Text(
                    text = "Name: ${character.name}", maxLines = 1, overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "Gender: ${character.gender}",
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "Location: ${character.location?.name}",
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }

        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )
    }

    @Preview(showBackground = true)
    @Composable
    fun CharacterItemPreview() {
        PhysicswallahAssignmentTheme {
            CharacterItem(
                character = Character(
                    name = "John Doe",
                    gender = "Male",
                    location = Location("Earth", ""),
                    image = "https://via.placeholder.com/150.jpg"
                ),
                onClick = {}
            )
        }
    }
}
