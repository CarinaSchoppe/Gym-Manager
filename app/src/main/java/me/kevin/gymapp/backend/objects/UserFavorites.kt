package me.kevin.gymapp.backend.objects

data class UserFavorites(var userID: Int, var trainingsmapIDList: MutableSet<Int>)
