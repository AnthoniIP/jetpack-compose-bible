package com.ipsoft.bibliasagrada.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.window.layout.WindowMetricsCalculator
import com.ipsoft.bibliasagrada.R
import com.ipsoft.bibliasagrada.domain.common.constants.ARG_BOOK_ABBREV
import com.ipsoft.bibliasagrada.domain.common.constants.ARG_BOOK_NAME
import com.ipsoft.bibliasagrada.domain.common.constants.ARG_CHAPTER_ID
import com.ipsoft.bibliasagrada.domain.common.constants.ARG_CHAPTER_QUANTITY
import com.ipsoft.bibliasagrada.domain.core.exception.Failure
import com.ipsoft.bibliasagrada.ui.bible.BibleViewModel
import com.ipsoft.bibliasagrada.ui.bible.books.ListBooks
import com.ipsoft.bibliasagrada.ui.bible.chapters.ListChapters
import com.ipsoft.bibliasagrada.ui.bible.reading.BibleReading
import com.ipsoft.bibliasagrada.ui.theme.BibliaSagradaTheme
import com.ipsoft.bibliasagrada.util.WindowSizeClass
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>
    private lateinit var widthWindowSizeClass: WindowSizeClass
    private lateinit var heightWindowSizeClass: WindowSizeClass

    private val viewModel: BibleViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        permissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
                viewModel.getBooks()
            }
        callRequestPermissions()

        setContent {
            BibliaSagradaTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    BibleApplication(viewModel)
                }
            }
        }
        computeWindowSizeClasses()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        viewModel.stopSpeech()
    }

    private fun callRequestPermissions() {
        permissionLauncher.launch(
            arrayOf(
                android.Manifest.permission.INTERNET
            )
        )
    }

    private fun computeWindowSizeClasses() {
        val metrics = WindowMetricsCalculator.getOrCreate().computeCurrentWindowMetrics(this)

        val widthDp = metrics.bounds.width() /
            resources.displayMetrics.density
        widthWindowSizeClass = when {
            widthDp < 600f -> WindowSizeClass.COMPACT
            widthDp < 840f -> WindowSizeClass.MEDIUM
            else -> WindowSizeClass.EXPANDED
        }

        val heightDp = metrics.bounds.height() /
            resources.displayMetrics.density
        heightWindowSizeClass = when {
            heightDp < 480f -> WindowSizeClass.COMPACT
            heightDp < 900f -> WindowSizeClass.MEDIUM
            else -> WindowSizeClass.EXPANDED
        }
    }

    @Composable
    fun BibleApplication(viewModel: BibleViewModel) {

        val failure: State<Failure?> = viewModel.failure.observeAsState(initial = null)
        val loading: State<Boolean> = viewModel.loading.observeAsState(initial = true)

        failure.value?.let {
            when (it) {
                is Failure.NetworkConnection -> {
                    Toast.makeText(
                        LocalContext.current,
                        stringResource(R.string.no_network),
                        Toast.LENGTH_LONG
                    )
                        .show()
                }
                is Failure.ServerError -> {
                    Toast.makeText(
                        LocalContext.current,
                        stringResource(R.string.server_error),
                        Toast.LENGTH_LONG
                    ).show()
                }
                else -> {
                    Toast.makeText(
                        LocalContext.current,
                        stringResource(R.string.unknown_error),
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }

        val navController = rememberNavController()
        NavHost(navController = navController, startDestination = ListBooksScreen.route) {
            composable(route = ListBooksScreen.route) {
                ListBooks(viewModel, navController, loading)
            }
            composable(
                route = ListChaptersScreen.route,
                arguments =
                listOf(
                    navArgument(ARG_BOOK_NAME) {
                        type = NavType.StringType
                    },
                    navArgument(ARG_BOOK_ABBREV) {
                        type = NavType.StringType
                    },
                    navArgument(ARG_CHAPTER_QUANTITY) {
                        type = NavType.IntType
                    }
                )
            ) { navBackStackEntry ->
                ListChapters(
                    navBackStackEntry.arguments?.getString(
                        ARG_BOOK_NAME
                    )!!,
                    navBackStackEntry.arguments?.getString(
                        ARG_BOOK_ABBREV
                    )!!,
                    navBackStackEntry.arguments?.getInt(
                        ARG_CHAPTER_QUANTITY
                    )!!,
                    navController,
                    viewModel,
                )
            }
            composable(
                route = BibleReadingScreen.route,
                arguments = listOf(
                    navArgument(ARG_BOOK_NAME) {
                        type = NavType.StringType
                    },
                    navArgument(ARG_BOOK_ABBREV) {
                        type = NavType.StringType
                    },
                    navArgument(ARG_CHAPTER_ID) {
                        type = NavType.IntType
                    },
                    navArgument(ARG_CHAPTER_QUANTITY) {
                        type = NavType.IntType
                    }
                )
            ) { navBackStackEntry ->
                BibleReading(
                    navBackStackEntry.arguments?.getString(
                        ARG_BOOK_NAME
                    )!!,
                    navBackStackEntry.arguments?.getString(
                        ARG_BOOK_ABBREV
                    )!!,
                    navBackStackEntry.arguments!!.getInt(ARG_CHAPTER_ID),
                    navBackStackEntry.arguments!!.getInt(ARG_CHAPTER_QUANTITY),
                    navController,
                    viewModel,
                    loading,
                    isLargeScreen()
                )
            }
        }
    }

    private fun isLargeScreen(): Boolean =
        widthWindowSizeClass == WindowSizeClass.EXPANDED && heightWindowSizeClass == WindowSizeClass.EXPANDED
}
