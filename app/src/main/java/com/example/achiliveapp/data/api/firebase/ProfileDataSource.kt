package com.example.achiliveapp.data.api.firebase

import com.example.achiliveapp.data.models.dto.ProfileDTO
import com.google.firebase.firestore.DocumentSnapshot

class ProfileDataSource(collectionName: String = PROFILE_DATA) : FirebaseDataSource<ProfileDTO>(collectionName) {
    override fun parse(v: DocumentSnapshot): ProfileDTO? {
        return  v.toObject(ProfileDTO::class.java)
    }
}