package com.example.achiliveapp.main

import androidx.lifecycle.ViewModel
import com.example.achiliveapp.data.api.firebase.CategorySchemeFirebase
import com.example.achiliveapp.data.api.firebase.FirebaseImageCloud
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SettingViewModel : ViewModel() {

    private val auth = Firebase.auth
    private val __categorySchemeFirebase = CategorySchemeFirebase()
    private val imageDataSource = FirebaseImageCloud()

    fun signOut() {
        auth.signOut()
    }



}