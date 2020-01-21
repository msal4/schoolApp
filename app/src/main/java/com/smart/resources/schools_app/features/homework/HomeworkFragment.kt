package com.smart.resources.schools_app.features.homework

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.smart.resources.schools_app.R
import com.smart.resources.schools_app.core.helpers.SharedPrefHelper
import com.smart.resources.schools_app.core.myTypes.UserType
import com.smart.resources.schools_app.core.utils.toast
import com.smart.resources.schools_app.databinding.FragmentRecyclerLoaderBinding
import com.smart.resources.schools_app.sharedUi.ImageViewerActivity
import com.smart.resources.schools_app.sharedUi.SectionActivity
import kotlinx.android.synthetic.main.item_homework.*

class HomeworkFragment : Fragment(){
    private lateinit var binding: FragmentRecyclerLoaderBinding
    private lateinit var viewModel: HomeworkViewModel
    private lateinit var adapter: HomeworkRecyclerAdapter


    companion object {
        fun newInstance(fm:FragmentManager){
            val fragment=
                HomeworkFragment()

            fm.beginTransaction().apply {
                add(R.id.fragmentContainer, fragment)
                commit()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRecyclerLoaderBinding.inflate(inflater, container, false).apply {
            lifecycleOwner= this@HomeworkFragment
        }
        adapter = HomeworkRecyclerAdapter(::onImageClick)
        binding.recyclerView.adapter = adapter
        (activity as SectionActivity).setCustomTitle(R.string.homework)
        setHasOptionsMenu(true)

        setupViewModel()

        if(SharedPrefHelper.instance?.userType == UserType.TEACHER) {
            val mIth = setupSwipe()
            mIth.attachToRecyclerView(binding.recyclerView)
        }
        return binding.root
    }

    private fun onImageClick(imageView: ImageView, imageUrl: String){
        activity?.let { ImageViewerActivity.newInstance(it,imageView, imageUrl) }
    }

    private fun setupSwipe(): ItemTouchHelper {
        val mIth = ItemTouchHelper(
            object : ItemTouchHelper.SimpleCallback(
                0,
                ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
            ) {
                override fun onChildDraw(
                    c: Canvas,
                    recyclerView: RecyclerView,
                    viewHolder: ViewHolder,
                    dX: Float,
                    dY: Float,
                    actionState: Int,
                    isCurrentlyActive: Boolean
                ) {

                    val mBackground = ColorDrawable(Color.RED)
                    val mIcon = context?.let {
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
                    viewHolder: ViewHolder, target: ViewHolder
                ): Boolean {

                    return false
                }

                override fun onSwiped(
                    viewHolder: ViewHolder,
                    direction: Int
                ) { // remove from adapter

                        viewModel.deleteHomework(viewHolder.adapterPosition)
                }

            })
        return mIth
    }

    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(activity!!)
            .get(HomeworkViewModel::class.java).apply {
                binding.listState= listState

                getHomework.observe(this@HomeworkFragment, Observer{
                    if(it == null) return@Observer

                    binding.errorText.text= ""
                    adapter.submitList(it)
                })

            }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        if(SharedPrefHelper.instance?.userType == UserType.TEACHER) {

            inflater.inflate(R.menu.menu_add_btn, menu)
        }

        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.addMenuItem-> fragmentManager?.let { AddHomeworkFragment.newInstance(it) }
        }
        return super.onOptionsItemSelected(item)
    }
}
