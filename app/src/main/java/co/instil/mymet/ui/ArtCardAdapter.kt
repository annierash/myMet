package co.instil.mymet.ui

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.palette.graphics.Palette
import androidx.recyclerview.widget.RecyclerView
import co.instil.mymet.R
import co.instil.mymet.app.inflate
import co.instil.mymet.model.Art
import coil.load

class ArtCardAdapter(private val artWorks: MutableList<Art>) :
    RecyclerView.Adapter<ArtCardAdapter.ViewHolder>() {

    enum class ScrollDirection {
        UP, DOWN
    }

    var scrollDirection = ScrollDirection.DOWN
    var customSpanSize = 2

    inner class ViewHolder(itemView: View) : View.OnClickListener, RecyclerView.ViewHolder(itemView) {

        private lateinit var artwork: Art

        init{
            itemView.setOnClickListener(this)
        }

        fun bind(artwork: Art) {
            this.artwork = artwork
            val context = itemView.context

            itemView.findViewById<ImageView>(R.id.artworkCardImage).load(artwork.primaryImageSmall) {
                crossfade(true)
                placeholder(R.drawable.placeholder)
            }
            itemView.findViewById<TextView>(R.id.artworkTitle).text = artwork.title
            itemView.findViewById<TextView>(R.id.artistCardName).text = artwork.artistDisplayName
//            setBackgroundColors(context, imageResource)
        }

        override fun onClick(view:View?) {
            view?.let {
                val context = it.context
                val intent = ArtActivity.newIntent(context, artwork.id)
                context.startActivity(intent)
            }
        }

        private fun setBackgroundColors(context: Context, imageResource: Int) {
            val image = BitmapFactory.decodeResource(context.resources, imageResource)
            Palette.from(image).generate { palette ->
                palette?.let {
                    val backgroundColor = it.getDominantColor(
                        ContextCompat.getColor(
                            context,
                            R.color.primaryDarkColor
                        )
                    )
                    itemView.findViewById<CardView>(R.id.artworkCard)
                        .setBackgroundColor(backgroundColor)
                    itemView.findViewById<TextView>(R.id.nameHolder)
                        .setBackgroundColor(backgroundColor)
                    val textColor = if (isColorDark(backgroundColor)) Color.WHITE else Color.BLACK
                    itemView.findViewById<TextView>(R.id.artistCardName).setTextColor(textColor)
                    if (itemView.findViewById<TextView>(R.id.artistBio) != null) {
                        itemView.findViewById<TextView>(R.id.artistBio).setTextColor(textColor)
                    }
                }
            }
        }


        private fun isColorDark(color: Int): Boolean {
            val darkness = 1 - (0.299 * Color.red(color) +
                    0.587 * Color.green(color) +
                    0.114 * Color.blue(color)) / 255
            return darkness >= 0.5
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent.inflate(R.layout.list_item_artwork_card))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(artWorks[position])
    }

    override fun getItemCount() = artWorks.size

    fun spanSizeAtPosition(position: Int): Int {
        return 1
    }

}



