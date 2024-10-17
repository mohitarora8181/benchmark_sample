package com.mohit.benchmarking_sample_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.compose.ui.unit.dp
import androidx.navigation.NavArgument
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.mohit.benchmarking_sample_app.ui.theme.Benchmarking_Sample_AppTheme

@OptIn(ExperimentalComposeUiApi::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Benchmarking_Sample_AppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    var navController = rememberNavController()
                    NavHost(navController = navController,
                        startDestination = "home" ,
                        modifier = Modifier
                            .semantics {
                                testTagsAsResourceId = true
                            }
                    ){
                        composable("home"){
                            Home(modifier = Modifier
                                .padding(innerPadding)
                                .fillMaxSize(),navController)
                        }
                        composable("screen/{item}"
                            , arguments = listOf(
                                navArgument("item"){
                                    type = NavType.StringType
                                }
                            )
                        ){
                            Screen(modifier = Modifier
                                .padding(innerPadding)
                                .fillMaxSize(),it.arguments?.getString("item")!!)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Home(modifier: Modifier,navController: NavController){
    var count by rememberSaveable { mutableStateOf(0) }
    Column (
        modifier
            .fillMaxSize()
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
        ) {
        Button(onClick = { count++ }) {
            Text(text = "Click me")
        }

        LazyColumn (modifier = Modifier
            .fillMaxSize()
            .padding(top = 10.dp).testTag("items"),
                verticalArrangement = Arrangement.spacedBy(10.dp)
        ){
            items(count){
                Text(text = "Item $it",
                    Modifier
                        .border(1.dp, Color.Black)
                        .padding(20.dp)
                        .fillMaxWidth()
                        .clickable(onClick = {
                            navController.navigate("screen/$it")
                        })
                )
            }
        }
    }
}

@Composable
fun Screen(modifier: Modifier = Modifier,item:String) {
    Column(
        modifier=modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(text = "Details : Item $item")
    }
}