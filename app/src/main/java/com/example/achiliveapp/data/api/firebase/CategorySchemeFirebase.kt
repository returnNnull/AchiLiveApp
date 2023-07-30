package com.example.achiliveapp.data.api.firebase

import com.example.achiliveapp.data.models.dto.CategoriesSchemeDTO
import com.google.firebase.firestore.DocumentSnapshot
import javax.inject.Inject

class CategorySchemeFirebase : FirebaseDataSource<CategoriesSchemeDTO>(
) {

    override fun parse(v: DocumentSnapshot): CategoriesSchemeDTO? {
        return v.toObject(CategoriesSchemeDTO::class.java)
    }

    override fun getTableName(): String {
        return CATEGORIES_SCHEME_DATA
    }
}