package com.example.hw_1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.integerResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.hw_1.ui.theme.HW_1Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HW_1Theme {
                // Scaffold даёт отступы (innerPadding), чтобы контент не залезал под системные панели
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    SquaresScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun SquaresScreen(modifier: Modifier = Modifier) {
    // Количество квадратов.
    // rememberSaveable сохраняет значение при повороте экрана (пересоздании Activity),
    // при этом ничего не пишется на диск — только в Bundle состояния.
    var count by rememberSaveable { mutableIntStateOf(0) }

    // Число колонок берётся из ресурсов:
    // values/integers.xml (портрет) = 3, values-land/integers.xml (ландшафт) = 4
    val columns = integerResource(R.integer.columns_count)
    val spacing = dimensionResource(R.dimen.grid_spacing)

    Column(modifier = modifier.fillMaxSize()) {
        // Сетка занимает всё свободное место (weight = 1f), кнопка идёт под ней,
        // поэтому кнопка никогда не перекрывает квадраты.
        LazyVerticalGrid(
            columns = GridCells.Fixed(columns),
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            contentPadding = PaddingValues(spacing),
            horizontalArrangement = Arrangement.spacedBy(spacing),
            verticalArrangement = Arrangement.spacedBy(spacing)
        ) {
            // Создаём count элементов, index — номер элемента (от 0)
            items(count) { index ->
                Square(number = index)
            }
        }

        Button(
            onClick = { count++ },
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(R.dimen.button_padding))
        ) {
            Text(text = stringResource(R.string.add_button))
        }
    }
}

@Composable
fun Square(number: Int) {
    // Чётные — красные, нечётные — синие
    val color = if (number % 2 == 0) {
        colorResource(R.color.square_even)
    } else {
        colorResource(R.color.square_odd)
    }

    Box(
        modifier = Modifier
            .aspectRatio(1f) // ширина = высота, получаем квадрат
            .background(color),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = number.toString(),
            color = colorResource(R.color.square_text),
            style = MaterialTheme.typography.headlineMedium, // размер шрифта из темы
            fontWeight = FontWeight.Bold
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SquaresScreenPreview() {
    HW_1Theme {
        SquaresScreen()
    }
}
