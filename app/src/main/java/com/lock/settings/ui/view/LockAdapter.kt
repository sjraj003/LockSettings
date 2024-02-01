package com.lock.settings.ui.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.lock.settings.R
import com.lock.settings.model.LockEntity


class LockAdapter(
    private val context: Context,
    private var dataList: List<LockEntity>?,
    private var listener: LockItemListener
) :
    RecyclerView.Adapter<LockAdapter.MyViewHolder>() {

    private var filteredList: List<LockEntity>? = dataList

    interface LockItemListener {
        fun onItemClick(entity: LockEntity?)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        filteredList?.let {
            val data = filteredList!![position]
            holder.bind(data)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.item_value, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return filteredList?.size ?: 0
    }

    fun setLock(data: List<LockEntity>?) {
        this.filteredList = data
        notifyDataSetChanged()
    }

    // Filter the list based on search query
    fun filter(query: String) {
        filteredList = filteredList?.filter { it.name?.contains(query, true)!! }
        notifyDataSetChanged()
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var primaryValueTextView: TextView
        private var secondaryValueTextView: TextView
        private var titleTextView: TextView

        init {
            primaryValueTextView = itemView.findViewById(R.id.primaryValue)
            secondaryValueTextView = itemView.findViewById(R.id.secondaryValue)
            titleTextView = itemView.findViewById(R.id.titleText)
        }

        fun bind(entity: LockEntity) {
            titleTextView.text = entity.name
            primaryValueTextView.text =
                (entity.details.primary?.ifEmpty { entity.details.defaults })
            secondaryValueTextView.text =
                (entity.details.secondary?.ifEmpty { entity.details.defaults })
            itemView.setOnClickListener {
                listener.onItemClick(entity)
            }
        }
    }
}

