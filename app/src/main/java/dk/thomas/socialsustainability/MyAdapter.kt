package dk.thomas.socialsustainability

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MyAdapter(private val projectList:ArrayList<Project>): RecyclerView.Adapter<MyAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)

        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val project: Project = projectList[position]
        holder.firstName.text = project.name
        holder.lasNme.text = project.description
        holder.age.text = project.date.toString()
    }

    override fun getItemCount(): Int {
        return  projectList.size
    }

    public class MyViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val firstName: TextView = itemView.findViewById(R.id.tvFirstName)
        val lasNme: TextView = itemView.findViewById(R.id.tvLastName)
        val age: TextView = itemView.findViewById(R.id.age)

    }
}