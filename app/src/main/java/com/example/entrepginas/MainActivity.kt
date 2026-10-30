package com.example.entrepginas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.entrepginas.ui.theme.EntrePáginasTheme
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TelaEstante()
            }
        }
    }

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}


@Composable
fun TelaEstante() {
    var etapa by remember { mutableIntStateOf(0) }
    var salvo by remember { mutableStateOf(false) }

    LaunchedEffect(etapa) {
        if (etapa == 1) {
            delay(2000)
            etapa = 2
        }
    }

    when (etapa) {
        0, 1 -> {
            val imagem = if (etapa == 0) {
                R.drawable.estante_normal
            } else {
                R.drawable.estante_sorteando
            }

            Image(
                painter = painterResource(id = imagem),
                contentDescription = "Estante mágica",
                modifier = Modifier
                    .fillMaxSize()
                    .clickable(enabled = etapa == 0) {
                        etapa = 1
                    },
                contentScale = ContentScale.Crop
            )
        }

        2 -> {
            TelaLivroSorteado(
                salvo = salvo,
                aoSalvar = { salvo = !salvo },
                aoSortearNovamente = {
                    salvo = false
                    etapa = 1
                }
            )
        }
    }
}





@Composable
fun TelaLivroSorteado(
    salvo: Boolean,
    aoSalvar: () -> Unit,
    aoSortearNovamente: () -> Unit
) {
    val marrom = Color(0xFF69402A)
    val bege = Color(0xFFF5E8D0)

    Box(modifier = Modifier.fillMaxSize()) {

        // Camada 1: estante de fundo
        Image(
            painter = painterResource(R.drawable.estante_normal),
            contentDescription = "Biblioteca encantada",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // Camada 2: livro flutuando
        Image(
            painter = painterResource(R.drawable.livro_hobbit),
            contentDescription = "Capa de O Hobbit",
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset(y = 65.dp)
                .fillMaxWidth(0.65f)
                .fillMaxHeight(0.55f)
                .padding(top = 100.dp),
            contentScale = ContentScale.Fit
        )

        // Camada 3: cartão de informações
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .navigationBarsPadding()
                .padding(
                    start = 22.dp,
                    end = 22.dp,
                    bottom = 90.dp
                )
                .fillMaxWidth()
                .clip(RoundedCornerShape(18.dp))
                .background(bege)
                .padding(18.dp)
        ) {

            Text(
                text = "O Hobbit",
                fontSize = 23.sp,
                fontWeight = FontWeight.Bold,
                color = marrom
            )

            Text(
                text = "J. R. R. Tolkien",
                fontSize = 12.sp,
                color = Color(0xFF8A735E)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Fantasia   •   Aventura   •   Clássico",
                fontSize = 12.sp,
                color = marrom
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Bilbo Bolseiro leva uma vida tranquila até ser convidado para uma aventura inesperada. Ao lado de um grupo de anões, ele parte em busca de um tesouro guardado por um dragão.",
                fontSize = 13.sp,
                lineHeight = 17.sp,
                color = Color(0xFF352A22)
            )

            Spacer(modifier = Modifier.height(14.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {

                Button(
                    onClick = aoSalvar,
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                        contentColor = marrom
                    )
                ) {
                    Text(
                        if (salvo) "✓ Salvo" else "Quero ler",
                        fontSize = 11.sp
                    )
                }

                Button(
                    onClick = aoSortearNovamente,
                    modifier = Modifier.weight(1.4f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = marrom
                    )
                ) {
                    Text(
                        "Sortear novamente",
                        fontSize = 10.sp,
                        maxLines = 1
                    )
                }
            }
        }
    }
}




@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    EntrePáginasTheme {
        Greeting("Android")
    }
}