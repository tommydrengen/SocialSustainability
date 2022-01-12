package dk.thomas.socialsustainability

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton

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

        val title: String? = intent.getStringExtra("title")
        val date: String? = intent.getStringExtra("date")
        val description: String? = intent.getStringExtra("description")
        val owner: String? = intent.getStringExtra("owner")
        val email: String? = intent.getStringExtra("email")

        titleTv.text = title
        dateTv.text = date
        descriptionTv.text = description
        ownerTv.text = owner
        emailTv.text = email

        val editFab = findViewById<FloatingActionButton>(R.id.editFab)
        editFab.setOnClickListener { val intent2 = Intent(this,Edit::class.java)
            intent2.putExtra("title", title)
            intent2.putExtra("date", date)
            intent2.putExtra("description", description)
            intent2.putExtra("owner", owner)
            intent2.putExtra("email", email)
            startActivity(intent2)
        }




    }
}