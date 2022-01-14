package dk.thomas.socialsustainability

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DatabaseReference
import com.google.firebase.firestore.*


class Feed : AppCompatActivity() /*, AdapterView.OnItemClickListener*/ {

    private lateinit var recyclerView: RecyclerView
    private lateinit var projectArrayList: ArrayList<Project>
    private lateinit var adapter: MyAdapter
    private lateinit var db: FirebaseFirestore
    private lateinit var dbRef :DatabaseReference
    private lateinit var idArrayList: ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        projectArrayList = arrayListOf()
        idArrayList = arrayListOf()
        recyclerView = findViewById(R.id.feedRecycler)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        adapter = MyAdapter(projectArrayList, idArrayList){
            switchScreen(it)
        }
        recyclerView.adapter = adapter

        val buttonCreateActivity = findViewById<ImageButton>(R.id.btnCreateActivity)
        buttonCreateActivity.setOnClickListener { startActivity(Intent(this, Create::class.java)) }
        EventChangeListener()
    }

    private fun EventChangeListener() {
        db = FirebaseFirestore.getInstance()
        db.collection("Project").orderBy("title", Query.Direction.ASCENDING).
        addSnapshotListener(object : EventListener<QuerySnapshot>{
            override fun onEvent(value: QuerySnapshot?,
                                 error: FirebaseFirestoreException?
            ) {
                if(error != null){
                    Log.e("Firestore error", error.message.toString())
                    return
                }
                for (dc: DocumentChange in value?.documentChanges!!){
                    if( dc.type == DocumentChange.Type.ADDED){
                        projectArrayList.add(dc.document.toObject(Project::class.java))
                        idArrayList.add(dc.document.id)
                    }
                }
                recyclerView.adapter?.notifyDataSetChanged()
            }
        })
    }


    private fun switchScreen(index: Int){
        // i stedet
        Toast.makeText(applicationContext, "index : "+index, Toast.LENGTH_SHORT).show()
        val project = projectArrayList[index]
        val intent = Intent(this, ViewProject::class.java)
        val id = idArrayList[index]
        intent.putExtra("id", id)
        startActivity(intent)
    }
}