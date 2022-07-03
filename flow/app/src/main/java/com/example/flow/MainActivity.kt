package com.example.flow

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.flow.ui.theme.FlowTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewModel: MainViewModel by viewModels()

        setContent {
            FlowTheme {
                SecondScreen(viewModel = viewModel)
            }
        }
    }
}

@Composable
fun FirstScreen(viewModel: MainViewModel) {

    Surface(color = MaterialTheme.colors.background) {
        val counter = viewModel.countDownTimerFlow.collectAsState(initial = 20)

        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                Text(
                    text = "${counter.value}",
                    fontSize = 30.sp,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}

@Composable
fun SecondScreen(viewModel: MainViewModel) {

    val liveDataValue = viewModel.liveData.observeAsState()
    val stateFlowValue = viewModel.stateFlow.collectAsState()
    val sharedFlowValue = viewModel.sharedFlow.collectAsState("")


    Surface(color = MaterialTheme.colors.background) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = "${liveDataValue.value}")
                Button(onClick = { viewModel.changeLiveData() }) {
                    Text(text = "Change LiveData")
                }
                Text(text = stateFlowValue.value)
                Button(onClick = { viewModel.changeStateFlowValue() }) {
                    Text(text = "Change State Flow")
                }
                Text(text = sharedFlowValue.value)
                Button(onClick = { viewModel.changeSharedFlowValue() }) {
                    Text(text = "Change Shared Flow")
                }
            }
        }
    }
}