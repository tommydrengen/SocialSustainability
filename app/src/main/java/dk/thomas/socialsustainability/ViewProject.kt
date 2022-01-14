package dk.thomas.socialsustainability

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.firestore.FirebaseFirestore

class ViewProject : AppCompatActivity() {

    private lateinit var titleTv: TextView
    private lateinit var dateTv: TextView
    private lateinit var descriptionTv: TextView
    private lateinit var ownerTv: TextView
    private lateinit var emailTv: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_project)

        titleTv = findViewById(R.id.titletv)
        dateTv = findViewById(R.id.datetv)
        descriptionTv = findViewById(R.id.descriptiontv)
        ownerTv = findViewById(R.id.ownertv)
        emailTv = findViewById(R.id.emailtv)

        val id: String? = intent.getStringExtra("id")

        //val projectId = FirebaseFirestore.getInstance().collection("Project").document(id!!).get("title")
        val projectRef = FirebaseFirestore.getInstance().collection("Project").document(id!!)
        projectRef.get()
            .addOnSuccessListener {document ->
                if (document != null){
                    titleTv.text = document.get("title").toString()
                    dateTv.text = document.get("date").toString()
                    descriptionTv.text = document.get("description").toString()
                    ownerTv.text = document.get("owner").toString()
                    emailTv.text = document.get("email").toString()

                    Log.d(TAG,"Succes" )
                }
                else{
                    Log.d(TAG, "Failed")
                }
            }
            .addOnFailureListener {exception ->
                Log.d(TAG,"Failed with " + exception)
            }

        val editFab = findViewById<FloatingActionButton>(R.id.editFab)
        editFab.setOnClickListener { val intent2 = Intent(this,Edit::class.java)
            intent2.putExtra("id", id)

            startActivity(intent2)
        }




    }
}