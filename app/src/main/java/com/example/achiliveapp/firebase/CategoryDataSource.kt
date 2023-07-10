package com.example.achiliveapp.firebase

import com.google.firebase.firestore.DocumentSnapshot

class CategoryDataSource(collectionName: String = "categories_scheme") : FirebaseDataSource<CategoriesSchemeDTO>(
    collectionName
) {
    override fun parse(v: DocumentSnapshot): CategoriesSchemeDTO? {
        return v.toObject(CategoriesSchemeDTO::class.java)
    }
}