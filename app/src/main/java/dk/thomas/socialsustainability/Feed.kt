package dk.thomas.socialsustainability

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.*

class Feed : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var projectArrayList: ArrayList<Project>
    private lateinit var myAdapter: MyAdapter
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView = findViewById(R.id.feedRecycler)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        projectArrayList = arrayListOf()

        val buttonCreateActivity = findViewById<ImageButton>(R.id.btnCreateActivity)
        buttonCreateActivity.setOnClickListener { startActivity(Intent(this, Create::class.java)) }

        //dummy data:
        for (i in 1..10){projectArrayList.add(Project("a"+i,"a"+i, ""+1+i))}
        myAdapter = MyAdapter(projectArrayList)
        recyclerView.adapter = myAdapter
        EventChangeListener()
    }
    private fun EventChangeListener() {
        db = FirebaseFirestore.getInstance()
        db.collection("Projects").orderBy("name", Query.Direction.ASCENDING).
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
                        createProjectElement(dc.document)
                    }
                }
                myAdapter.notifyDataSetChanged()
            }
        })
    }

    fun createProjectElement(project: QueryDocumentSnapshot){
        //var element: View = layoutInflater.inflate(com.google.firebase.firestore.R.layout.list_item,null,false)
        var element: View = layoutInflater.inflate(R.layout.list_item,null,false)
        //var first = element.findViewById<TextView>(com.google.firebase.firestore.R.id.tvFirstNameValue)
        var first = element.findViewById<TextView>(/*com.google.firebase.firestore.*/R.id.tvFirstName)

        var last = element.findViewById<TextView>(/*com.google.firebase.firestore.*/R.id.tvLastNameValue)
        var age = element.findViewById<TextView>(/*com.google.firebase.firestore.*/R.id.ageVal)

        first.text = project["name"].toString()
        last.text = project["description"].toString()
        age.text = project["date"].toString()

        recyclerView.addView(element)

    }


}