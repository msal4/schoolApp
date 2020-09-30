package com.smart.resources.schools_app.core.callbacks

import android.graphics.Canvas
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.smart.resources.schools_app.R
import com.smart.resources.schools_app.core.extentions.toDrawable

class SwipeAdapter(
    private val fixedPositions: List<Int> = listOf(),
    private val onSwiped: (viewHolder: RecyclerView.ViewHolder) -> Unit,
) :
    ItemTouchHelper.SimpleCallback(
        0,
        ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
    ) {

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {

        viewHolder.itemView.apply {
            val mBackground =
                R.drawable.background_swipe_delete.toDrawable(context)//ColorDrawable(R.color.lightRed.toColorResource(itemView.context))
            val mIcon = R.drawable.ic_delete_white_24dp.toDrawable(context)
            if (mBackground == null || mIcon == null) return

            //so mBackground is behind the rounded corners of itemView
            val backgroundCornerOffset = 25

            val iconMargin = (height - (mIcon.intrinsicHeight)) / 2
            val iconTop =
                top + (height - (mIcon.intrinsicHeight)) / 2
            val iconBottom = iconTop + (mIcon.intrinsicHeight)

            when {
                dX > 0 -> { // Swiping to the right
                    val iconLeft = left + iconMargin
                    val iconRight: Int = iconLeft + (mIcon.intrinsicWidth)
                    mIcon.setBounds(iconLeft, iconTop, iconRight, iconBottom)
                    mBackground.setBounds(
                        left,
                        top,
                        left + dX.toInt() + backgroundCornerOffset,
                        bottom
                    )
                }
                dX < 0 -> { // Swiping to the left
                    val iconLeft =
                        right - iconMargin - (mIcon.intrinsicWidth)
                    val iconRight = right - iconMargin
                    mIcon.setBounds(iconLeft, iconTop, iconRight, iconBottom)
                    mBackground.setBounds(
                        right + dX.toInt() - backgroundCornerOffset,
                        top,
                        right,
                        bottom
                    )
                }
                else -> { // view is unSwiped
                    mIcon.setBounds(0, 0, 0, 0)
                    mBackground.setBounds(0, 0, 0, 0)
                }
            }


            mBackground.draw(c)
            mIcon.draw(c)
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

        onSwiped(viewHolder)

    }

    override fun getSwipeDirs(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        if (fixedPositions.contains(viewHolder.adapterPosition)) return 0 // disable swipe
        return super.getSwipeDirs(recyclerView, viewHolder)
    }

}