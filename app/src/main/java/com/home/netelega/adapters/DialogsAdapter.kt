package com.home.netelega.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.home.netelega.R
import com.home.netelega.structures.Dialog

class DialogsAdapter(private val dialogs: List<Dialog>, private val actionClicker: ActionClick): RecyclerView.Adapter<DialogsAdapter.DialogViewHolder>() {
    inner class DialogViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val dialogName: TextView = view.findViewById(R.id.contact_name_tv)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DialogViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.dialog_layout, parent, false)
        return DialogViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dialogs.size
    }

    override fun onBindViewHolder(holder: DialogViewHolder, position: Int) {
        val dialog = dialogs[position]
        holder.dialogName.text = dialog.name+" "+dialog.surname
        holder.dialogName.setOnClickListener {
            actionClicker.onDialogClick(dialog)
        }

    }

    interface ActionClick {
        fun onDialogClick(dialog: Dialog)
    }
}