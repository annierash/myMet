package co.instil.mymet.ui


import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import co.instil.mymet.model.Art
import co.instil.mymet.R
import co.instil.mymet.app.inflate
import coil.load


class ArtAdapter(private val artWorks: MutableList<Art>) :
    RecyclerView.Adapter<ArtAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View) : View.OnClickListener, RecyclerView.ViewHolder(itemView) {
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
        }

    override fun onClick(view: View) {
        view?.let {
            val context = it.context
            val intent = ArtActivity.newIntent (context, artwork.id)
            context.startActivity(intent)
        }
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


}