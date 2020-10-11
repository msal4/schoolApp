package com.smart.resources.schools_app.core.callbacks

import android.graphics.Canvas
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.haytham.coder.extensions.toColor
import com.smart.resources.schools_app.R
import com.haytham.coder.extensions.toDrawable

class SwipeAdapter(
    private val fixedPositions: List<Int> = listOf(),
    private val swipedRightForOptions:Boolean= false,
    private val onSwiped: (swipeDirection: Int, viewHolder: RecyclerView.ViewHolder) -> Unit
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
            val background = R.drawable.bg_swipe_delete.toDrawable(context)
            background?.setTint(R.color.swipeToDeleteColor.toColor(context))
            var icon = R.drawable.ic_delete_white_with_spacing.toDrawable(context)
            if (background == null || icon == null) return

            //so mBackground is behind the rounded corners of itemView
            val backgroundCornerOffset = 25

            val iconMargin = (height - (icon.intrinsicHeight)) / 2
            val iconTop =
                top + (height - (icon.intrinsicHeight)) / 2
            val iconBottom = iconTop + (icon.intrinsicHeight)

            when {
                dX > 0 -> { // Swiping to the right

                    val iconLeft = left + iconMargin
                    val iconRight: Int = iconLeft + (icon.intrinsicWidth)
                    icon.setBounds(iconLeft, iconTop, iconRight, iconBottom)
                    background.setBounds(
                        left,
                        top,
                        left + dX.toInt() + backgroundCornerOffset,
                        bottom
                    )
                }
                dX < 0 -> { // Swiping to the left
                    if(swipedRightForOptions){
                        background.setTint(R.color.swipeForOptionsColor.toColor(context))
                        icon= R.drawable.ic_round_menu_white_24.toDrawable(context)
                        if(icon == null) return
                    }

                    val iconLeft =
                        right - iconMargin - (icon.intrinsicWidth)
                    val iconRight = right - iconMargin
                    icon.setBounds(iconLeft, iconTop, iconRight, iconBottom)
                    background.setBounds(
                        right + dX.toInt() - backgroundCornerOffset,
                        top,
                        right,
                        bottom
                    )
                }
                else -> { // view is unSwiped
                    icon.setBounds(0, 0, 0, 0)
                    background.setBounds(0, 0, 0, 0)
                }
            }


            background.draw(c)
            icon.draw(c)
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

        onSwiped(direction, viewHolder)

    }

    override fun getSwipeDirs(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        if (fixedPositions.contains(viewHolder.adapterPosition)) return 0 // disable swipe
        return super.getSwipeDirs(recyclerView, viewHolder)
    }

}