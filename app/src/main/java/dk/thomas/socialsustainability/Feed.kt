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


class Feed : AppCompatActivity() /*, AdapterView.OnItemClickListener*/ {

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
        adapter = MyAdapter(projectArrayList){
            switchScreen(it)
        }
        recyclerView.adapter = adapter

        val buttonCreateActivity = findViewById<ImageButton>(R.id.btnCreateActivity)
        buttonCreateActivity.setOnClickListener { startActivity(Intent(this, Create::class.java)) }
        EventChangeListener()
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
                        recyclerView.adapter = MyAdapter(projectArrayList){
                            switchScreen(it)
                        }
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


    private fun switchScreen(index: Int){
        // i stedet
        Toast.makeText(applicationContext, "index : "+index, Toast.LENGTH_SHORT).show()
        val project = projectArrayList[index]
        val value: String = project.toString()
        val intent = Intent(this, ViewProject::class.java)
        val title = project.title
        val date = project.date
        val description = project.description
        val owner = project.owner
        val email = project.email
        intent.putExtra("title", project.title)
        intent.putExtra("date", project.date)
        intent.putExtra("description", project.description)
        intent.putExtra("owner", project.owner)
        intent.putExtra("email", project.email)
        //intent.putParcelableArrayListExtra(project.title, null)

        //val bundle: Bundle? = intent.extras
        //val string: String? = intent.getStringExtra(title)
        //val myArray: ArrayList<String>? = intent.getStringArrayListExtra("projectArrayList")
        startActivity(intent)
    }
}