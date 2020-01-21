package com.smart.resources.schools_app.core.adapters

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.smart.resources.schools_app.R

class SwipeAdapter (val onSwipe:(viewHolder: RecyclerView.ViewHolder)->Unit) : ItemTouchHelper.SimpleCallback(0,
    ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {

        val mBackground = ColorDrawable(Color.RED)
        val mIcon = viewHolder.itemView.context?.let {
            ContextCompat.getDrawable(
                it,
                R.drawable.ic_delete_white_24dp
            )
        }
        val itemView = viewHolder.itemView
        val backgroundCornerOffset =
            25 //so mBackground is behind the rounded corners of itemView


        val iconMargin: Int = (itemView.height - (mIcon?.getIntrinsicHeight() ?: 0)) / 2
        val iconTop: Int =
            itemView.top + (itemView.height - (mIcon?.getIntrinsicHeight() ?: 0)) / 2
        val iconBottom: Int = iconTop + (mIcon?.getIntrinsicHeight() ?: 0)

        if (dX > 0) { // Swiping to the right
            val iconLeft = itemView.left + iconMargin
            val iconRight: Int = iconLeft + (mIcon?.getIntrinsicWidth() ?: 0)
            mIcon?.setBounds(iconLeft, iconTop, iconRight, iconBottom)
            mBackground.setBounds(
                itemView.left,
                itemView.top,
                itemView.left + dX.toInt() + backgroundCornerOffset,
                itemView.bottom
            )
        } else if (dX < 0) { // Swiping to the left
            val iconLeft: Int =
                itemView.right - iconMargin - (mIcon?.getIntrinsicWidth() ?: 0)
            val iconRight = itemView.right - iconMargin
            mIcon?.setBounds(iconLeft, iconTop, iconRight, iconBottom)
            mBackground.setBounds(
                itemView.right + dX.toInt() - backgroundCornerOffset,
                itemView.top, itemView.right, itemView.bottom
            )
        } else { // view is unSwiped
            mIcon?.setBounds(0, 0, 0, 0)
            mBackground.setBounds(0, 0, 0, 0)
        }


        mBackground.draw(c)
        mIcon?.draw(c)
        super.onChildDraw(
            c,
            recyclerView,
            viewHolder,
            dX,
            dY,
            actionState,
            isCurrentlyActive
        )

    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder
    ): Boolean {

        return false
    }

    override fun onSwiped(
        viewHolder: RecyclerView.ViewHolder,
        direction: Int
    ) { // remove from adapter

        onSwipe(viewHolder)

    }



}