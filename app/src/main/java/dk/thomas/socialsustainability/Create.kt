package dk.thomas.socialsustainability

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class Create : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create)

        var name = findViewById<EditText>(R.id.name)
        var description = findViewById<EditText>(R.id.description)
        var date = findViewById<EditText>(R.id.date)

        val upload = findViewById<Button>(R.id.btnUploadData)
        upload.setOnClickListener {
            var newProject = Project(name.text.toString() ,description.text.toString(), date.text.toString())
            uploadData("Project", newProject)
            val i = Intent(this, Create::class.java)
            startActivity(i)
        }
    }
}