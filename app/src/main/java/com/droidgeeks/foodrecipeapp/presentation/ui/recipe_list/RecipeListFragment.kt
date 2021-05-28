package com.droidgeeks.foodrecipeapp.presentation.ui.recipe_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.droidgeeks.foodrecipeapp.presentation.BaseApplication
import com.droidgeeks.foodrecipeapp.presentation.components.RecipeList
import com.droidgeeks.foodrecipeapp.presentation.components.SearchAppBar
import com.droidgeeks.foodrecipeapp.presentation.components.util.SnackbarController
import com.droidgeeks.foodrecipeapp.presentation.theme.AppTheme
import com.droidgeeks.foodrecipeapp.presentation.ui.recipe_list.RecipeListEvent.NewSearchEvent
import com.droidgeeks.foodrecipeapp.presentation.ui.recipe_list.RecipeListEvent.NextPageEvent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalMaterialApi
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class RecipeListFragment : Fragment() {

    @Inject
    lateinit var application: BaseApplication

    private val snackbarController = SnackbarController(lifecycleScope)

    private val viewModel: RecipeListViewModel by viewModels()

    @ExperimentalMaterialApi
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {

                val recipes = viewModel.recipes.value

                val query = viewModel.query.value

                val selectedCategory = viewModel.selectedCategory.value

                val categoryScrollPosition = viewModel.categoryScrollPosition

                val loading = viewModel.loading.value

                val page = viewModel.page.value

                val scaffoldState = rememberScaffoldState()

                AppTheme(
                    darkTheme = application.isDark.value,
                    displayProgressBar = loading,
                    scaffoldState = scaffoldState,
                ) {


                    Scaffold(
                        topBar = {
                            SearchAppBar(
                                query = query,
                                onQueryChanged = viewModel::onQueryChanged,
                                onExecuteSearch = {
                                    if (viewModel.selectedCategory.value?.value == "Milk") {
                                        snackbarController.getScope().launch {
                                            snackbarController.showSnackbar(
                                                scaffoldState = scaffoldState,
                                                message = "Invalid category: MILK",
                                                actionLabel = "Hide"
                                            )
                                        }
                                    } else {
                                        viewModel.onTriggerEvent(NewSearchEvent)
                                    }
                                },
                                categories = getAllFoodCategories(),
                                selectedCategory = selectedCategory,
                                onSelectedCategoryChanged = viewModel::onSelectedCategoryChanged,
                                scrollPosition = categoryScrollPosition,
                                onChangeScrollPosition = viewModel::onChangeCategoryScrollPosition,
                                onToggleTheme = application::toggleLightTheme
                            )
                        },
                        scaffoldState = scaffoldState,
                        snackbarHost = {
                            scaffoldState.snackbarHostState
                        },

                        ) {
                        RecipeList(
                            loading = loading,
                            recipes = recipes,
                            onChangeScrollPosition = viewModel::onChangeRecipeScrollPosition,
                            page = page,
                            onTriggerNextPage = { viewModel.onTriggerEvent(NextPageEvent) },
                            navController = findNavController(),
                            scaffoldState = scaffoldState,
                            snackbarController = snackbarController,
                        )
                    }
                }
            }
        }
    }
}