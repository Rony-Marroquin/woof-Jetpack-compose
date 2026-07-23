package com.example.woof

import android.os.Bundle
import android.provider.CalendarContract
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.StringRes
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContent
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Shapes
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TooltipBox
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.woof.data.DataPerro
import com.example.woof.model.ModeloPerros
import com.example.woof.ui.theme.WoofTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WoofTheme {
                       Scaffold(
                           topBar = {
                                 WoofTopBar()
                           },
                           containerColor = MaterialTheme.colorScheme.background
                       ) { inerpadding ->
                           WoofApp(modifier = Modifier.padding(inerpadding))
                       }
                }
            }
        }
    }


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WoofTopBar(modifier: Modifier= Modifier){



    CenterAlignedTopAppBar(title = {
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Image(painter = painterResource(R.drawable.ic_woof_logo),
                contentDescription = null,
                modifier = Modifier.size(35.dp))
            Text(text = stringResource(R.string.app_name),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold)
        }
    },
        )

}
@Preview(showBackground = true)
@Composable
fun PrevieWoofTopBar(){
    WoofTheme() {

    WoofTopBar()
}


}

@Composable
fun WoofApp( modifier: Modifier = Modifier) {

    listaPuppy(DataPerro.PerroWoof)


}

@Composable
fun listaPuppy(lista: List<ModeloPerros>){

    LazyColumn() {
        items(lista){puppy -> CardWoofApp(puppy= puppy,
            modifier = Modifier.padding(8.dp))}
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IconoBotonPuppy(

     modifier: Modifier= Modifier,
    Expandir: Boolean,
    onclick: ()-> Unit,

    )
{
       IconButton(
            onClick = onclick,
           modifier = Modifier.animateContentSize()
       ){
           Icon(
               imageVector = Icons.Filled.ExpandMore,
               contentDescription = "Expandir",
               modifier = Modifier.rotate(if (Expandir) 180f
               else 0f)

           )
       }



}



@Composable
fun CardWoofApp(puppy: ModeloPerros, modifier: Modifier = Modifier) {

    var Expandir by remember { mutableStateOf(false) }

    val color by animateColorAsState(
        targetValue = if(Expandir) MaterialTheme.colorScheme.tertiaryContainer
        else MaterialTheme.colorScheme.primaryContainer
    )

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(40.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Column(modifier = Modifier.animateContentSize()
            .background(color = color)) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(top = 10.dp, start = 8.dp, end = 8.dp, bottom = 8.dp)
            ) {

                Image(
                    painter = painterResource(puppy.imagenPerro),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(45.dp)
                        .clip(shape = CircleShape)
                )
                Column() {
                    Text(
                        text = stringResource(puppy.namePerro),
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = stringResource(R.string.years_old),
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.Light
                    )

                }
                Spacer(modifier = Modifier.weight(1f))

                IconoBotonPuppy(
                    modifier = Modifier,
                    Expandir = Expandir,
                    onclick = {
                       Expandir = !Expandir
                    }



                )
            }

            if (Expandir){
                hobbies(hobbie = puppy.hobbbiePerro)
            }
        }
    }
}

@Composable
fun hobbies(

    @StringRes hobbie : Int,
    modifier: Modifier = Modifier
){
Column( modifier = Modifier.padding(top = 10.dp, start = 25.dp, end = 16.dp, bottom = 8.dp)) {
    Text(text = stringResource(R.string.about),
        style = MaterialTheme.typography.labelSmall)

    Text(text = stringResource(hobbie),
    style = MaterialTheme.typography.bodyLarge)
}



}



    @Preview(showBackground = true)
    @Composable
    fun GreetingPreview() {
        WoofTheme() {
            WoofApp()
        }
    }



