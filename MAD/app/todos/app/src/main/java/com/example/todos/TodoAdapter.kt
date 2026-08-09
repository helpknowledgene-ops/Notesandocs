package com.example.todos

import android.view.*
import android.widget.*
import androidx.recyclerview.widget.RecyclerView

class TodoAdapter(
    private val items: MutableList<Todo>,
    private val onCheckChanged: (Todo) -> Unit,
    private val onDelete: (Todo) -> Unit
) : RecyclerView.Adapter<TodoAdapter.ViewHolder>() {

    class ViewHolder(v: View): RecyclerView.ViewHolder(v){
        val check: CheckBox=v.findViewById(R.id.checkTask)
        val title: TextView=v.findViewById(R.id.txtTitle)
        val edit: ImageButton=v.findViewById(R.id.btnEdit)
        val delete: ImageButton=v.findViewById(R.id.btnDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)=
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.todo_item,parent,false))

    override fun getItemCount()=items.size

    override fun onBindViewHolder(h: ViewHolder, pos: Int){
        val t=items[pos]
        h.title.text=t.title
        h.check.isChecked=t.isCompleted
        h.check.setOnCheckedChangeListener { _, b ->
            t.isCompleted = b
            onCheckChanged(t)
        }
        h.delete.setOnClickListener {
            val p = h.bindingAdapterPosition
            if (p != RecyclerView.NO_POSITION) {
                onDelete(items[p])
                items.removeAt(p)
                notifyItemRemoved(p)
            }
        }
        h.edit.setOnClickListener{
            Toast.makeText(h.itemView.context,"Edit clicked",Toast.LENGTH_SHORT).show()
        }
    }

    fun addTodo(todo: Todo){
        items.add(todo)
        notifyItemInserted(items.lastIndex)
    }

    fun setTodos(newItems: List<Todo>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }
}