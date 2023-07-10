package com.example.achiliveapp.firebase

import com.google.firebase.firestore.DocumentSnapshot

class AwardSchemeDataSource(collectionName: String = "awards_scheme") : FirebaseDataSource<AwardSchemeDTO>(
    collectionName
) {
    override fun parse(v: DocumentSnapshot): AwardSchemeDTO? {
        return v.toObject(AwardSchemeDTO::class.java)
    }
}