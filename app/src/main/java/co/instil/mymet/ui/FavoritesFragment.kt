package co.instil.mymet.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.instil.mymet.R
import co.instil.mymet.model.ArtStore
import co.instil.mymet.ui.utils.ItemDragListener
import co.instil.mymet.ui.utils.ItemTouchHelperCallback

class FavoritesFragment : Fragment(), ItemDragListener {

    private val adapter = ArtAdapter(mutableListOf(), this)
    private lateinit var itemTouchHelper: ItemTouchHelper

    companion object {
        fun newInstance(): FavoritesFragment {
            return FavoritesFragment()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var favoriteRecyclerView = view.findViewById<RecyclerView>(R.id.favoriteRecyclerView)
        favoriteRecyclerView.layoutManager = LinearLayoutManager(activity)
        favoriteRecyclerView.adapter = adapter
        val heightInPixels = resources.getDimensionPixelSize(R.dimen.list_item_divider_height)
        context?.let {
            favoriteRecyclerView.addItemDecoration(
                co.instil.mymet.ui.utils.DividerItemDecoration(
                    ContextCompat.getColor(it, R.color.black), heightInPixels
                )
            )
        }
        setupItemTouchHelper()
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favorites, container, false)
    }

    override fun onResume() {
        super.onResume()
        activity?.let {
            ArtStore.getFavoriteArtworks(it)?.let {
                favorites ->
                adapter.updateArtworks(favorites)
            }
        }
    }

    override fun onItemDrag(viewHolder: RecyclerView.ViewHolder) {
        itemTouchHelper.startDrag(viewHolder)
    }

    private fun setupItemTouchHelper() {
        itemTouchHelper = ItemTouchHelper(ItemTouchHelperCallback(adapter))
        itemTouchHelper.attachToRecyclerView(view?.findViewById(R.id.favoriteRecyclerView))
    }

}

