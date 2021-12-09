package dk.thomas.socialsustainability

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MyAdapter(private val projectList:ArrayList<Project>): RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

  /*  private lateinit var mListener : onItemClickLickListener

    interface onItemClickLickListener{fun onItemClick(position: Int)}

    fun setOnItemClickListener(listener: onItemClickLickListener){
        mListener = listener

    }*/

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return MyViewHolder(itemView,/* mListener*/)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val project: Project = projectList[position]
        holder.name.text = project.name
        holder.description.text = project.description
        holder.date.text = project.date.toString()
    }

    override fun getItemCount(): Int {
        return  projectList.size
    }

    public class MyViewHolder(itemView: View,/* listener: onItemClickLickListener*/):RecyclerView.ViewHolder(itemView){
        val name: TextView = itemView.findViewById(R.id.tvNameValue)
        val description: TextView = itemView.findViewById(R.id.tvDescValue)
        val date: TextView = itemView.findViewById(R.id.tvDateValue)

        /*init {
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }

        }*/

    }
}