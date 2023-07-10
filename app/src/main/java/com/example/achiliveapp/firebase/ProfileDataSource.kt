package com.example.achiliveapp.firebase

import com.google.firebase.firestore.DocumentSnapshot

class ProfileDataSource(collectionName: String = "profiles") : FirebaseDataSource<ProfileDTO>(collectionName) {
    override fun parse(v: DocumentSnapshot): ProfileDTO? {
        return  v.toObject(ProfileDTO::class.java)
    }
}