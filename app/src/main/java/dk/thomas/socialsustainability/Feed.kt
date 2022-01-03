package dk.thomas.socialsustainability

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import com.google.firebase.firestore.*
import com.google.firebase.firestore.Query


class Feed : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var projectArrayList: ArrayList<Project>
    private lateinit var adapter: MyAdapter
    private lateinit var db: FirebaseFirestore
    private lateinit var dbRef :DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        projectArrayList = arrayListOf()
        recyclerView = findViewById(R.id.feedRecycler)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        adapter = MyAdapter(projectArrayList)
        recyclerView.adapter = adapter

        val buttonCreateActivity = findViewById<ImageButton>(R.id.btnCreateActivity)
        buttonCreateActivity.setOnClickListener { startActivity(Intent(this, Create::class.java)) }
        EventChangeListener()
        //for (i in 1..10){projectArrayList.add(Project("a"+i,"a"+i, ""+1+i))}
    }

    private fun readData(projectArrayList: ArrayList<Project>) {
        for ( p in projectArrayList)
            p.date?.let { dbRef.child(it).get().addOnSuccessListener {
                if (it.exists()){
                    val date = it.child("date").value
                    val description = it.child("description").value
                    val name = it.child("name").value
                    val owner = it.child("owner").value
                    val email = it.child("email").value
                    Toast.makeText(this,"Succesfully read",Toast.LENGTH_SHORT).show()
                    projectArrayList
                }else{
                    Toast.makeText(this, "Project does not exist",Toast.LENGTH_SHORT).show()
                }
            }.addOnFailureListener { Toast.makeText(this, "Failed reading",Toast.LENGTH_SHORT).show() }
            }
    }


    private fun getProjectData() {
            dbRef = FirebaseDatabase.getInstance().getReference("Project")
            dbRef.addValueEventListener(object :ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()){
                        for (projectSnapshot in snapshot.children){
                            val project = projectSnapshot.getValue(Project::class.java)
                            projectArrayList.add(project!!)
                        }
                        recyclerView.adapter = MyAdapter(projectArrayList)
                    }
                }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
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
                    }
                }
                recyclerView.adapter?.notifyDataSetChanged()
            }
        })
    }

}