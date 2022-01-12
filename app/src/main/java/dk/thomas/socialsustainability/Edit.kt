package dk.thomas.socialsustainability

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Edit : AppCompatActivity() {

    private lateinit var description: TextInputLayout
    private lateinit var date: TextInputLayout
    private lateinit var owner: TextInputLayout
    private lateinit var email: TextInputLayout
    private lateinit var dbRef : DatabaseReference
    private lateinit var title: TextInputLayout
    
    override fun onCreate(savedInstanceState: Bundle?) {



        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        title = findViewById<TextInputLayout>(R.id.eeditTitle)
        description = findViewById<TextInputLayout>(R.id.eeditDescription)
        date = findViewById<TextInputLayout>(R.id.eeditDate)
        owner = findViewById<TextInputLayout>(R.id.eeditOwner)
        email = findViewById<TextInputLayout>(R.id.eeditEmail)

        var upload = findViewById<Button>(R.id.saveeEditButton)
        upload.setOnClickListener {
            val titleNew = title.editText?.text.toString()
            val dateNew = date.editText?.text.toString()
            val descriptionNew = description.editText?.text.toString()
            val ownerNew = owner.editText?.text.toString()
            val emailNew = email.editText?.text.toString()
            var newProject = Project(titleNew, dateNew, descriptionNew, ownerNew, emailNew)
            updateData(titleNew, dateNew, descriptionNew, ownerNew, emailNew)//
            val i = Intent(this, Feed::class.java)
            startActivity(i)
        }
    }

    private fun updateData(titleNew: String, dateNew: String, descriptionNew: String, ownerNew: String, emailNew: String) {
        dbRef = FirebaseDatabase.getInstance().getReference("Project")
        val project = mapOf<String, String>(
            "title" to titleNew,
            "date" to dateNew,
            "description" to descriptionNew,
            "owner" to ownerNew,
            "email" to emailNew
        )

        dbRef.child(titleNew).updateChildren(project).addOnSuccessListener {
            title.editText?.setText("")
            date.editText?.setText("")
            description.editText?.setText("")
            owner.editText?.setText("")
            email.editText?.setText("")
            Toast.makeText(this, "Successfully updated", Toast.LENGTH_SHORT).show()
        }.addOnFailureListener {
            Toast.makeText(this, "Failed to update", Toast.LENGTH_SHORT).show()
        }


    }
}