package com.example.achiliveapp.data.api.firebase

import com.example.achiliveapp.data.models.dto.CategoriesSchemeDTO
import com.google.firebase.firestore.DocumentSnapshot

class CategoryDataSource(collectionName: String = CATEGORIES_SCHEME_DATA) : FirebaseDataSource<CategoriesSchemeDTO>(
    collectionName
) {
    override fun parse(v: DocumentSnapshot): CategoriesSchemeDTO? {
        return v.toObject(CategoriesSchemeDTO::class.java)
    }
}