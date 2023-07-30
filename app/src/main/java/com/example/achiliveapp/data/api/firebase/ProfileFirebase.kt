package com.example.achiliveapp.data.api.firebase

import com.example.achiliveapp.data.models.dto.ProfileDTO
import com.google.firebase.firestore.DocumentSnapshot
import javax.inject.Inject

class ProfileFirebase : FirebaseDataSource<ProfileDTO>() {
    override fun parse(v: DocumentSnapshot): ProfileDTO? {
        return  v.toObject(ProfileDTO::class.java)
    }

    override fun getTableName(): String {
        return PROFILE_DATA
    }
}