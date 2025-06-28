package eu.tutorials.myrecipeapp

sealed class Screen(val route : String) {
    object RecipeScreen : Screen("recipe_screen")
    object CategoryDetailScreen : Screen("category_detail_screen")
}