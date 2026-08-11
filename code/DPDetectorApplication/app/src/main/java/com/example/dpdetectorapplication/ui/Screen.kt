package com.example.dpdetectorapplication.ui

sealed class Screen(val route: String) {

    data object Home : Screen("home")

    data object Detail : Screen("detail/{id}") {
        fun createRoute(id: String): String {
            return "detail/$id"
        }
    }
}