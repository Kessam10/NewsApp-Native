package com.example.news_compose_c40.screens.settings

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import android.content.res.Configuration
import android.content.res.Resources
import androidx.compose.foundation.border
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.unit.dp
import java.util.Locale
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.example.news_compose_c40.R
import com.example.news_compose_c40.widgets.NewsTopAppBar
import kotlinx.coroutines.CoroutineScope
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.compose.ui.res.stringResource
import com.example.news_compose_c40.activity.HomeActivity
import kotlinx.serialization.Serializable

@Serializable
object SettingsRoute


@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    scope: CoroutineScope,
    drawerState: DrawerState,
    context: Context // Pass the context to save/retrieve preferences
) {
    val savedLanguage = getSavedLanguage(context) ?: "en" // Get saved language

    // Reapply the saved language whenever the SettingsScreen is loaded
    setAppLocale(savedLanguage, context)

    Scaffold(topBar = {
        NewsTopAppBar(
            shouldDisplaySearchIcon = true,
            shouldDisplayMenuIcon = true,
            titleString = stringResource(id = R.string.settings),
            scope = scope,
            drawerState = drawerState,
            onSearchClick = {
                // Show the search bar when the search icon is clicked
            }
        )
    }) { paddingValues ->
        DropDownMenu(
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingValues),
            context = context // Pass context to handle language switching
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropDownMenu(modifier: Modifier, context: Context) {
    val language = listOf("English", "Arabic")
    var isExpanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf(language[0]) }

    // Set the initial selection based on the saved language
    val savedLanguage = getSavedLanguage(context)
    if (savedLanguage == "ar") {
        selectedText = "Arabic"
    }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.language),
            modifier = Modifier
                .align(alignment = Alignment.Start)
                .padding(start = 30.dp, top = 30.dp, bottom = 20.dp)
        )

        ExposedDropdownMenuBox(
            expanded = isExpanded,
            onExpandedChange = { isExpanded = !isExpanded }
        ) {
            TextField(
                modifier = Modifier
                    .menuAnchor()
                    .border(1.dp, Color.Green, shape = RoundedCornerShape(4.dp)),
                value = selectedText,
                onValueChange = {},
                readOnly = true,
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded)
                }
            )
            ExposedDropdownMenu(
                expanded = isExpanded,
                onDismissRequest = { isExpanded = false },
                modifier = Modifier
                    .background(Color.White)
                    .border(1.dp, Color.Green, shape = RoundedCornerShape(4.dp))
                    .fillMaxWidth()
            ) {
                language.forEachIndexed { index, text ->
                    DropdownMenuItem(
                        text = { Text(text = text) },
                        onClick = {
                            selectedText = language[index]
                            isExpanded = false

                            // Save the language preference
                            val languageCode = if (text == "English") "en" else "ar"
                            saveLanguagePreference(context, languageCode)

                            // Set the app locale based on the selected language
                            setAppLocale(languageCode, context)
                            restartApp(context)
                        },
                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                    )
                }
            }
        }
    }
}

// Function to change the language of the app
fun setAppLocale(languageCode: String, context: Context) {
    val locale = Locale(languageCode)
    Locale.setDefault(locale)
    val resources: Resources = context.resources
    val config: Configuration = resources.configuration
    config.setLocale(locale)
    context.createConfigurationContext(config)
    resources.updateConfiguration(config, resources.displayMetrics)
}



// Function to save the language preference
fun saveLanguagePreference(context: Context, languageCode: String) {
    val sharedPref: SharedPreferences = context.getSharedPreferences("app_settings", Context.MODE_PRIVATE)
    with(sharedPref.edit()) {
        putString("language", languageCode)
        apply()
    }
}

// Function to get the saved language preference
fun getSavedLanguage(context: Context): String? {
    val sharedPref: SharedPreferences = context.getSharedPreferences("app_settings", Context.MODE_PRIVATE)
    return sharedPref.getString("language", "en") // Default to English if not set
}

fun restartApp(context: Context) {
    val intent = Intent(context, HomeActivity::class.java)
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
    context.startActivity(intent)
}
