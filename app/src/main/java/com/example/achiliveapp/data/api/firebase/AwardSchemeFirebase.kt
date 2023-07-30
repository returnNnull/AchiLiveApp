package com.example.achiliveapp.data.api.firebase

import com.example.achiliveapp.data.models.dto.AwardSchemeDTO
import com.google.firebase.firestore.DocumentSnapshot
import javax.inject.Inject

class AwardSchemeFirebase : FirebaseDataSource<AwardSchemeDTO>(
) {

    override fun parse(v: DocumentSnapshot): AwardSchemeDTO? {
        return v.toObject(AwardSchemeDTO::class.java)
    }

    override fun getTableName(): String {
        return AWARD_SCHEME_DATA
    }
}