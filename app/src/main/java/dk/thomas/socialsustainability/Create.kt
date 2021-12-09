package dk.thomas.socialsustainability

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

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
            uploadData("Project", newProject)//
            val i = Intent(this, Feed::class.java)
            startActivity(i)
        }
    }
}