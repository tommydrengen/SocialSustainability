package dk.thomas.socialsustainability

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputLayout

class Create : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_post)

        var title = findViewById<TextInputLayout>(R.id.editTitle)
        var description = findViewById<TextInputLayout>(R.id.editDescription)
        var date = findViewById<TextInputLayout>(R.id.editDate)
        var owner = findViewById<TextInputLayout>(R.id.editOwner)
        var email = findViewById<TextInputLayout>(R.id.editEmail)

        val upload = findViewById<Button>(R.id.saveeEditButton)
        upload.setOnClickListener {
            var newProject = Project(title.editText?.text.toString(), date.editText?.text.toString(), description.editText?.text.toString(), owner.editText?.text.toString(), email.editText?.text.toString())
            uploadData("Project", newProject)//
            val i = Intent(this, Feed::class.java)
            startActivity(i)
        }
    }
}