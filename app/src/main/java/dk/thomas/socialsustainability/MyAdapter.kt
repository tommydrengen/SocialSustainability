package dk.thomas.socialsustainability

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MyAdapter(private val projectList:ArrayList<Project>, private val idList: ArrayList<String>, callback1: (index: Int) -> Unit): RecyclerView.Adapter<MyAdapter.MyViewHolder>() {
    var onItemClick: ((Project) -> Unit)? = null
    val callback = callback1


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return MyViewHolder(itemView, callback)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val project: Project = projectList[position]
        val id = idList[position]
        holder.title.text = project.title
        holder.description.text = project.description
        holder.date.text = project.date.toString()
        holder.id.text = id
        //holder.owner.text = project.title
        //holder.email.text = project.email
    }

    override fun getItemCount(): Int {
        return  projectList.size
    }

    inner class MyViewHolder(itemView: View, callback: (index: Int) -> Unit):RecyclerView.ViewHolder(itemView){
        val title: TextView = itemView.findViewById(R.id.titleCard)
        val description: TextView = itemView.findViewById(R.id.descriptionCard)
        val date: TextView = itemView.findViewById(R.id.dateCard)
        val id: TextView =itemView.findViewById(R.id.id)
        //val owner : TextView = itemView.findViewById(R.id.ownerCard)
        //val email: TextView = itemView.findViewById(R.id.emailCard)

        init {
            itemView.setOnClickListener {
                //onItemClick?.invoke(projectList[adapterPosition])
                //listener.onItemClick(adapterPosition)
                callback(absoluteAdapterPosition)

            }
        }


    }
}