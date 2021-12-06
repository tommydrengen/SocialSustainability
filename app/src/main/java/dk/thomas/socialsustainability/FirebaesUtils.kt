package dk.thomas.socialsustainability

import android.content.ContentValues
import android.content.ContentValues.TAG
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

fun readData(fireStoreCallback: FireStoreCallback){
    FirebaesUtils().fireStoreDatabase.collection("Test")
        .get()
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val list: ArrayList<String> = ArrayList()
                for (word in task.result!!) {
                    val wordHej: String = word.data["hej"].toString()
                    list.add(wordHej)
                }
                fireStoreCallback.onCallBack(list)
            } else {
                Log.d(TAG,"Error getting data")
            }
        }

}

interface FireStoreCallback{
    fun onCallBack(value: List<String>)
}