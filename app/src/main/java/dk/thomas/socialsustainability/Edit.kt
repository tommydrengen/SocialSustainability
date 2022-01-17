package dk.thomas.socialsustainability

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.firestore.FirebaseFirestore

class Edit : AppCompatActivity() {

    private lateinit var description: TextInputEditText
    private lateinit var date: TextInputEditText
    private lateinit var owner: TextInputEditText
    private lateinit var email: TextInputEditText
    //private lateinit var dbRef : DatabaseReference
    private lateinit var title: TextInputEditText
    
    override fun onCreate(savedInstanceState: Bundle?) {



        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        title = findViewById<TextInputEditText>(R.id.eeditTitle)
        description = findViewById<TextInputEditText>(R.id.eeditDescription)
        date = findViewById<TextInputEditText>(R.id.eeditDate)
        owner = findViewById<TextInputEditText>(R.id.eeditOwner)
        email = findViewById<TextInputEditText>(R.id.eeditEmail)

        val id = intent.getStringExtra("id")
        val projectRef = FirebaseFirestore.getInstance().collection("Project").document(id!!)
        projectRef.get()
            .addOnSuccessListener {document ->
                if (document != null){
                    title.setText(document.get("title").toString())
                    description.setText(document.get("date").toString())
                    date.setText(document.get("description").toString())
                    owner.setText(document.get("owner").toString())
                    email.setText(document.get("email").toString())

                    Log.d(ContentValues.TAG,"Succes" )
                }
                else{
                    Log.d(ContentValues.TAG, "Failed")
                }
            }
            .addOnFailureListener {exception ->
                Log.d(ContentValues.TAG,"Failed with " + exception)
            }

        var upload = findViewById<Button>(R.id.saveeEditButton)
        upload.setOnClickListener {
            val titleNew = title.text.toString()
            val dateNew = date.text.toString()
            val descriptionNew = description.text.toString()
            val ownerNew = owner.text.toString()
            val emailNew = email.text.toString()
            var newProject = Project(titleNew, dateNew, descriptionNew, ownerNew, emailNew)
            updateData(newProject, id)//
        }
    }

    private fun updateData(project: Project, id: String) { // laant fra https://www.youtube.com/redirect?event=video_description&redir_token=QUFFLUhqbTA3VjdTeUtZLWtuc3RiUk5xV3c1bkRwbmN5QXxBQ3Jtc0ttX2E4N3hXNXR5UzVTR0FvVmh6OVJVMkpCNWlHRjJEZjBWM0F4bTRMTnY4a1FCLVpLQ1A1Nk9kaUpXMXgwNFViU2tFQ0hwaXdLaWRsVWVQMEJyUldYamZVRVc0Wktla25uSEg3a3FEUi0yRkpZRmwzWQ&q=https%3A%2F%2Fgithub.com%2Ffoxandroid%2FRealtimedatabaseKotlin
        val fireStoreDatabase = FirebaseFirestore.getInstance()
        var dbRef = fireStoreDatabase.collection("Project")
        //dbRef = FirebaseDatabase.getInstance().getReference("Project")


        var update = hashMapOf<String,Any>(
            "title" to project.title!!,
            "description" to project.description!!,
            "date" to project.date!!,
            "owner" to project.owner!!,
            "email" to project.email!!
        )
        dbRef.document(id!!).update(update)
            .addOnSuccessListener {
                val intent = Intent(this, Feed::class.java)
                startActivity(intent)
            }
        //dbRef.document(id!!).set(project)
        /*dbRef.document(id!!).update(mapOf(
            "date" to project.date,
            "description" to project.description,
            "email" to project.email,
            "owner" to project.owner,
            "title" to project.title,
        ))*/
      /*      .addOnSuccessListener {
            Toast.makeText(this, "Successfully updated", Toast.LENGTH_SHORT).show()
            val i = Intent(this, Feed::class.java)
            startActivity(i)
        }.addOnFailureListener { exception ->
            Toast.makeText(this, "Failed to update " + exception, Toast.LENGTH_SHORT).show()
            Log.d(ContentValues.TAG,"Failed with " + exception)
        }*/


    }
}