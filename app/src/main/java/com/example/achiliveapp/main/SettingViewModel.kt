package com.example.achiliveapp.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.achiliveapp.firebase.CategoriesSchemeDTO
import com.example.achiliveapp.firebase.CategoryDataSource
import com.example.achiliveapp.firebase.FirebaseImageCloud
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import java.net.URL

class SettingViewModel : ViewModel() {

    private val auth = Firebase.auth
    private val categoryDataSource = CategoryDataSource()
    private val imageDataSource = FirebaseImageCloud()

    fun signOut() {
        auth.signOut()
    }



}