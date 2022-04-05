package co.instil.mymet.ui

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.instil.mymet.R
import co.instil.mymet.model.ArtStore
import co.instil.mymet.ui.utils.SpacingItemDecoration

class AllFragment : Fragment() {

    private val adapter = ArtCardAdapter(ArtStore.getArtworks().toMutableList())
    private lateinit var layoutManager: GridLayoutManager
    private lateinit var listItemDecoration: RecyclerView.ItemDecoration
    private lateinit var gridItemDecoration: RecyclerView.ItemDecoration
    private lateinit var listMenuItem: MenuItem
    private lateinit var gridMenuItem: MenuItem
    private var gridState = GridState.GRID

    private enum class GridState {
        LIST, GRID
    }

    companion object {
        fun newInstance(): AllFragment {
            return AllFragment()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_all, menu)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_all, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return (adapter.spanSizeAtPosition(position))
            }
        }
        val artworkRecyclerView = view.findViewById<RecyclerView>(R.id.artworkRecyclerView)
        artworkRecyclerView.layoutManager = layoutManager
        artworkRecyclerView.adapter = adapter
        val spacingInPixels = 0
        listItemDecoration = SpacingItemDecoration(1, spacingInPixels)
        gridItemDecoration = SpacingItemDecoration(2, spacingInPixels)
        artworkRecyclerView.addItemDecoration(gridItemDecoration)
        artworkRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                adapter.scrollDirection = if (dy > 0) {
                    ArtCardAdapter.ScrollDirection.DOWN
                } else {
                    ArtCardAdapter.ScrollDirection.UP
                }
            }
        })
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        listMenuItem = menu.findItem(R.id.action_span_1)
        gridMenuItem = menu.findItem(R.id.action_span_2)
        when (gridState) {
            GridState.LIST -> {
                listMenuItem.isEnabled = false
                gridMenuItem.isEnabled = true
            }
            GridState.GRID -> {
                listMenuItem.isEnabled = true
                gridMenuItem.isEnabled = false
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        when (id) {
            R.id.action_span_1 -> {
                gridState = GridState.LIST
                updateRecyclerView(1, listItemDecoration, gridItemDecoration)
                return true
            }
            R.id.action_span_2 -> {
                gridState = GridState.GRID
                updateRecyclerView(2, gridItemDecoration, listItemDecoration)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun updateRecyclerView(spanCount: Int, addItemDecoration: RecyclerView.ItemDecoration, removeItemDecoration: RecyclerView.ItemDecoration) {
        layoutManager.spanCount = spanCount
        adapter.customSpanSize = spanCount
        view?.findViewById<RecyclerView>(R.id.artworkRecyclerView)?.removeItemDecoration(removeItemDecoration)
        view?.findViewById<RecyclerView>(R.id.artworkRecyclerView)?.addItemDecoration(addItemDecoration)
    }


}