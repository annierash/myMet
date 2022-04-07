package co.instil.mymet.ui.utils

import androidx.recyclerview.widget.RecyclerView

interface ItemDragListener {
    fun onItemDrag(viewHolder: RecyclerView.ViewHolder)
}