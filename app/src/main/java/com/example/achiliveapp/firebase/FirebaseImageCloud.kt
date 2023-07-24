package com.example.achiliveapp.firebase

import android.net.Uri
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.io.File

class FirebaseImageCloud {

    private val server = Firebase.storage
    suspend fun save(uri: Uri, folder: Folder): Result<Uri> = withContext(Dispatchers.IO) {
        try {
            if (uri != Uri.EMPTY){
                val ref = server.reference
                val riversRef = ref.child("${folder.name.lowercase()}/${uri.lastPathSegment}")
                riversRef.putFile(uri).await()
                val downloadUri = riversRef.downloadUrl.await()
                Result.success(downloadUri)
            }
            else{
             Result.success(uri)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    enum class Folder() {
        CATEGORIES,
        AWARDS,
        AVATARS
    }

}