package com.example.achiliveapp.data.api.firebase


import com.example.achiliveapp.data.api.RemoteSource
import com.example.achiliveapp.data.models.dto.DTO
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

abstract class FirebaseDataSource<T : DTO<String>>(
    private val collectionName: String,
    private val db: FirebaseFirestore = Firebase.firestore
) : RemoteSource<T, String>() {

    companion object {
        const val CATEGORIES_SCHEME_DATA = "categories_scheme"
        const val AWARD_SCHEME_DATA = "awards_scheme"
        const val RATING_DATA = "rating"
        const val PROFILE_DATA = "profiles"
    }

    override suspend fun where(fieldName: String, value: Any) = withContext(ioDispatcher) {
        try {
            val list = db.collection(collectionName).whereEqualTo(fieldName, value).get().await()
            Result.success(list.map { q -> parse(q)!! })
        } catch (e: Exception) {
            Result.failure(e)
        }
    }


    override suspend fun getAll() = withContext(ioDispatcher) {
        try {
            val list = db.collection(collectionName).get().await()
            Result.success(list.map { q -> parse(q)!! })
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun insert(t: T) = withContext(ioDispatcher) {
        try {
            val doc: DocumentReference
            if (t.id.isEmpty()) {
                doc = db.collection(collectionName).document()
                t.id = doc.id
            } else {
                doc = db.collection(collectionName).document(t.id)
            }
            doc.set(t).await()
            Result.success(t)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun delete(t: T) = withContext(ioDispatcher) {
        try {
            db.collection(collectionName).document(t.id).delete().await()
            Result.success(t)
        } catch (e: Exception) {
            Result.failure(e)
        }

    }

    override suspend fun update(t: T) = withContext(ioDispatcher) {
        try {
            val doc = db.collection(collectionName).document(t.id)
            doc.set(t).await()
            Result.success(t)
        } catch (e: Exception) {
            Result.failure(e)
        }

    }


    override suspend fun getById(id: String) = withContext(ioDispatcher) {
        try {
            val task = db.collection(collectionName).document(id).get().await()
            Result.success(parse(task)!!)
        } catch (e: Exception) {
            Result.failure(e)
        }


    }


    abstract fun parse(v: DocumentSnapshot): T?

}
