package co.instil.mymet.ui


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import co.instil.mymet.R
import co.instil.mymet.model.Art
import co.instil.mymet.model.ArtStore
import co.instil.mymet.model.Favorites
import org.w3c.dom.Text


class ArtActivity: AppCompatActivity() {

    private lateinit var artwork: Art

    companion object {
       private const val EXTRA_ART_ID = "EXTRA_ART_ID"


        fun newIntent(context: Context, artworkId: Int): Intent {
        val intent = Intent(context, ArtActivity::class.java)
        intent.putExtra(EXTRA_ART_ID, artworkId)
        return intent
    }

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_art)

        setupArtwork()
        setupTitle()
        setupViews()
        setupFavoriteButton()
    }

    private fun setupArtwork() {
        val artworkById = ArtStore.getArtworksById(intent.getIntExtra(EXTRA_ART_ID, 1))
        if(artworkById == null) {
            Toast.makeText(this, "Invalid artwork", Toast.LENGTH_SHORT).show()
            finish()
        } else {
            artwork = artworkById
        }
    }

    private fun setupTitle() {
        title = String.format(getString(R.string.detail_title_format), artwork.title)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setupViews() {
        val headerImage = findViewById<ImageView>(R.id.headerImage)
        val artistName = findViewById<TextView>(R.id.artistName)
        val title = findViewById<TextView>(R.id.artTitle)
        val medium = findViewById<TextView>(R.id.medium)
        val artistBio = findViewById<TextView>(R.id.artistBio)
        val date = findViewById<TextView>(R.id.date)
        val gallery = findViewById<TextView>(R.id.gallery)
        headerImage.setImageResource(resources.getIdentifier(artwork.uri, null, packageName))
        artistName.text = artwork.artistDisplayName
        title.text = artwork.title
        medium.text = artwork.medium
        artistBio.text = artwork.artistDisplayBio
        date.text = artwork.objectDate
        gallery.text = artwork.galleryNumber
    }

    private fun setupFavoriteButton() {
        setupFavoriteButtonImage(artwork)
        setupFavoriteButtonClickListener(artwork)
    }


    private fun setupFavoriteButtonImage(artwork: Art) {
        if(artwork.isFavorite) {
            findViewById<ImageButton>(R.id.favoriteButton).setImageDrawable(getDrawable(R.drawable.ic_baseline_favorite_24))
        } else {
            findViewById<ImageButton>(R.id.favoriteButton).setImageDrawable(getDrawable(R.drawable.ic_baseline_favorite_border_24))
        }
    }


    private fun setupFavoriteButtonClickListener(artwork: Art) {
        findViewById<ImageButton>(R.id.favoriteButton).setOnClickListener {
            if(artwork.isFavorite) {
                findViewById<ImageButton>(R.id.favoriteButton).setImageDrawable(getDrawable(R.drawable.ic_baseline_favorite_border_24))
                Favorites.removeFavorite(artwork, this)
            } else {
                findViewById<ImageButton>(R.id.favoriteButton).setImageDrawable(getDrawable(R.drawable.ic_baseline_favorite_24))
                Favorites.addFavorite(artwork, this)
            }

        }
    }
}