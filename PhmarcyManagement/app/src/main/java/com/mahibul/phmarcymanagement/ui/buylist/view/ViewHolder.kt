package com.mahibul.phmarcymanagement.ui.buylist.view

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import kotlinx.android.synthetic.main.medicine_item_view.view.*

class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val nameTextView = itemView.nameTextView as TextView
    val priceTextView = itemView.priceTextView as TextView
    val unitTextView = itemView.unitTextView as TextView
    val btnEdit = itemView.btnEdit as MaterialButton
    val btnDelete = itemView.btnDelete as MaterialButton
}