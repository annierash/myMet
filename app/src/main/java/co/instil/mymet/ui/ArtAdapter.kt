package co.instil.mymet.ui


import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import co.instil.mymet.model.Art
import co.instil.mymet.R
import co.instil.mymet.app.inflate
import co.instil.mymet.model.Favorites
import co.instil.mymet.ui.utils.ItemDragListener
import co.instil.mymet.ui.utils.ItemTouchHelperListener
import coil.load
import java.util.*


class ArtAdapter(
    private val artWorks: MutableList<Art>,
    private val itemDragListener: ItemDragListener
) :
    RecyclerView.Adapter<ArtAdapter.ViewHolder>(), ItemTouchHelperListener {
    inner class ViewHolder(itemView: View) : View.OnClickListener,
        RecyclerView.ViewHolder(itemView) {
        private lateinit var artwork: Art

        fun bind(artwork: Art) {
            this.artwork = artwork
            val context = itemView.context
            val artworkImage = itemView.findViewById<ImageView>(R.id.artworkImage)
            artworkImage.load(artwork.primaryImageSmall) {
                crossfade(true)
                placeholder(R.drawable.placeholder)
            }
            itemView.findViewById<TextView>(R.id.title).text = artwork.title
            itemView.findViewById<TextView>(R.id.artist).text = artwork.artistDisplayName
            itemView.findViewById<ImageView>(R.id.handle).setOnTouchListener { _, event ->
                if (event.action == MotionEvent.ACTION_DOWN) {
                    itemDragListener.onItemDrag(this)
                }
                false
            }
            animateView(itemView)
        }

        override fun onClick(view: View) {
            view?.let {
                val context = it.context
                val intent = ArtActivity.newIntent(context, artwork.id)
                context.startActivity(intent)
            }
        }

    }

    private fun animateView(viewToAnimate: View) {
        if (viewToAnimate.animation == null) {
            val animation = AnimationUtils.loadAnimation(viewToAnimate.context, R.anim.scale_xy)
            viewToAnimate.animation = animation
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent.inflate(R.layout.list_item_artwork))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(artWorks[position])
    }

    override fun getItemCount() = artWorks.size

    fun updateArtworks(artworks: List<Art>) {
        this.artWorks.clear()
        this.artWorks.addAll(artworks)
        notifyDataSetChanged()
    }

    override fun onItemMove(
        recyclerView: RecyclerView,
        fromPosition: Int,
        toPosition: Int
    ): Boolean {
        if(fromPosition < toPosition) {
            for(i in fromPosition until toPosition){
                Collections.swap(artWorks, i, i + 1)
            }
        }else {
            for(i in fromPosition downTo toPosition + 1) {
                Collections.swap(artWorks, i, i - 1)
            }
        }
        Favorites.saveFavorites(artWorks.map { it.id }, recyclerView.context)
        notifyItemMoved(fromPosition, toPosition)
        return true
    }


}