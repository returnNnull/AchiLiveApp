package com.example.achiliveapp.data.api.firebase

import com.example.achiliveapp.data.models.dto.AwardSchemeDTO
import com.google.firebase.firestore.DocumentSnapshot

class AwardSchemeDataSource(collectionName: String = AWARD_SCHEME_DATA) : FirebaseDataSource<AwardSchemeDTO>(
    collectionName
) {
    override fun parse(v: DocumentSnapshot): AwardSchemeDTO? {
        return v.toObject(AwardSchemeDTO::class.java)
    }
}