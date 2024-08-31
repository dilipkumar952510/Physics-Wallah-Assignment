package com.physicswallah.assignment.ui.activity.details

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import coil.compose.rememberAsyncImagePainter
import com.physicswallah.assignment.R
import com.physicswallah.assignment.ui.model.Character
import com.physicswallah.assignment.ui.model.Location
import com.physicswallah.assignment.ui.model.Origin
import com.physicswallah.assignment.ui.theme.PhysicswallahAssignmentTheme

class DetailActivity : ComponentActivity() {
    private lateinit var character: Character
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        character = intent.getSerializableExtra("data") as Character
        setContent {
            PhysicswallahAssignmentTheme {
                DetailsScreen(character, onBackClick = {
                    finish()
                })
            }
        }
    }
}

@Composable
fun DetailsScreen(character: Character,onBackClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        // Box with background color
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .size(250.dp)
                .background(color = colorResource(id = R.color.teal_200))
                .align(Alignment.CenterHorizontally)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.align(Alignment.Center)
            ) {
                Image(
                    painter = rememberAsyncImagePainter(model = character.image),
                    contentDescription = "Character Image",
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                        .background(Color.Blue),
                    contentScale = ContentScale.Crop
                )

                Text(
                    text = character.name.toString(),
                    modifier = Modifier.padding(top = 8.dp),
                    fontFamily = FontFamily.Serif,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp)
                .offset(y = (-30).dp)
                .zIndex(1f)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .shadow(10.dp)
                    .background(color = colorResource(id = R.color.purple_200)),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Gender: \n${character.gender}",
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .weight(1f)
                        .padding(10.dp),
                    fontWeight = FontWeight.Medium,
                    fontFamily = FontFamily.SansSerif,
                    fontSize = 14.sp
                )
                Text(
                    text = "Status: \n${character.status}",
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .weight(1f)
                        .padding(10.dp),
                    fontWeight = FontWeight.Medium,
                    fontFamily = FontFamily.SansSerif,
                    fontSize = 14.sp
                )
                Text(
                    text = "Species: \n${character.species}",
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .weight(1f)
                        .padding(10.dp),
                    fontWeight = FontWeight.Medium,
                    fontFamily = FontFamily.SansSerif,
                    fontSize = 14.sp
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp)
        ) {
            Text(
                "Location : ",
                modifier = Modifier.weight(1f),
                fontWeight = FontWeight.Medium,
                fontFamily = FontFamily.SansSerif,
                fontSize = 15.sp
            )

            Text(
                character.location?.name.toString(),
                modifier = Modifier.weight(1f),
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.SansSerif,
                fontSize = 15.sp
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp, vertical = 15.dp)
        ) {
            Text(
                "Origin : ",
                modifier = Modifier.weight(1f),
                fontWeight = FontWeight.Medium,
                fontFamily = FontFamily.SansSerif,
                fontSize = 15.sp
            )

            Text(
                character.origin?.name.toString(),
                modifier = Modifier.weight(1f),
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.SansSerif,
                fontSize = 15.sp
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 100.dp, top = 0.dp, start = 30.dp, end = 30.dp)
        ) {
            Text(
                "No. of Episodes : ",
                modifier = Modifier.weight(1f),
                fontWeight = FontWeight.Medium,
                fontFamily = FontFamily.SansSerif,
                fontSize = 15.sp
            )

            Text(
                character.episode?.size.toString(),
                modifier = Modifier.weight(1f),
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.SansSerif,
                fontSize = 15.sp
            )
        }

        Button(
            onClick = { onBackClick()},
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp, vertical = 16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
            shape = RoundedCornerShape(10.dp),
            elevation = ButtonDefaults.elevatedButtonElevation(10.dp)
        ) {
            Text(
                text = "Go back",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DetailsScreenPreview() {
    PhysicswallahAssignmentTheme {
        // Sample character data for preview
        val sampleCharacter = Character(
            id = 1,
            name = "John Doe",
            image = "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
            gender = "Male",
            location = Location(name = "Earth", ""),
            status = "Alive",
            species = "Human",
            origin = Origin("Citadel of Ricks", "")
        )
        DetailsScreen(character = sampleCharacter, onBackClick = {})
    }
}
