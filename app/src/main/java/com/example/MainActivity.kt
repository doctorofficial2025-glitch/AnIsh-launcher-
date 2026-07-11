package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ai.GeminiService
import com.example.ui.theme.GameLauncherTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      GameLauncherTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
          GameSpaceScreen(modifier = Modifier.padding(innerPadding))
        }
      }
    }
  }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameSpaceScreen(modifier: Modifier = Modifier) {
  val sheetState = rememberModalBottomSheetState()
  val scope = rememberCoroutineScope()
  var showPanel by remember { mutableStateOf(false) }

  Scaffold(
      modifier = modifier.background(Color(0xFF121212)),
      topBar = {
        TopAppBar(title = { Text("Game Space", fontWeight = FontWeight.Bold) })
      },
      floatingActionButton = {
        FloatingActionButton(onClick = { showPanel = true }) {
          Text("🎮")
        }
      }
  ) { innerPadding ->
    LazyColumn(modifier = Modifier.padding(innerPadding).padding(16.dp)) {
      items(5) { index ->
        Card(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
          Text("Game Title ${index + 1}", modifier = Modifier.padding(16.dp))
        }
      }
    }

    if (showPanel) {
      ModalBottomSheet(
          onDismissRequest = { showPanel = false },
          sheetState = sheetState
      ) {
        GameSpacePanel(
            onClose = { showPanel = false },
            onCommand = { cmd -> 
                // Placeholder for command processing
            }
        )
      }
    }
  }
}

@Composable
fun GameSpacePanel(onClose: () -> Unit, onCommand: (String) -> Unit) {
  var prompt by remember { mutableStateOf("") }
  var response by remember { mutableStateOf("Ready for command.") }
  val scope = rememberCoroutineScope()

  Column(modifier = Modifier.padding(16.dp).fillMaxWidth()) {
    Text("Game Space Panel", style = MaterialTheme.typography.titleLarge)
    Spacer(modifier = Modifier.height(16.dp))
    
    // Performance Mock
    Text("Performance", style = MaterialTheme.typography.titleMedium)
    LinearProgressIndicator(progress = { 0.3f }, modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp))
    Text("CPU: 30% | RAM: 4GB/12GB")
    
    Spacer(modifier = Modifier.height(16.dp))
    
    // AI Control
    TextField(
        value = prompt, 
        onValueChange = { prompt = it }, 
        label = { Text("AI Command") },
        modifier = Modifier.fillMaxWidth()
    )
    Button(
        onClick = {
          scope.launch {
            response = GeminiService.generateContent(prompt)
          }
        },
        modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
    ) {
      Text("Execute")
    }
    Text(response, modifier = Modifier.padding(top = 16.dp), color = MaterialTheme.colorScheme.primary)
  }
}

@Preview
@Composable
fun Preview() {
  GameLauncherTheme { GameSpaceScreen() }
}
