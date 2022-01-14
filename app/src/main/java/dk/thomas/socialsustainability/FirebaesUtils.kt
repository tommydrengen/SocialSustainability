package dk.thomas.socialsustainability

import android.content.ContentValues
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore

class FirebaesUtils {
    val fireStoreDatabase = FirebaseFirestore.getInstance()
}

fun uploadData(collection: String, project: Project){
    FirebaesUtils().fireStoreDatabase.collection(collection)
        .add(project)
        .addOnSuccessListener {
            Log.d(ContentValues.TAG, "Upload succeded")
        }
        .addOnFailureListener { exception ->
            Log.d(ContentValues.TAG,"Upload failed")
        }
}