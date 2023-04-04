package me.carina.gymapp.backend.objects

data class UserFavorites(var userID: Int, var trainingsmapIDList: MutableSet<Int>)
