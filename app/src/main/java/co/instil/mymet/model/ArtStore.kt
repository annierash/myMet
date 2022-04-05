package co.instil.mymet.model

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.IOException

object ArtStore {

    private const val TAG = "ArtStore"


    private lateinit var artWorks: List<Art>


    fun loadArtworks(context: Context) {
        val gson = Gson()
        val json = loadJSONFromAsset("art.json", context)
        val listType = object : TypeToken<List<Art>>() {}.type
        artWorks = gson.fromJson(json, listType)
        artWorks
            .filter { Favorites.isFavorite(it, context) }
            .forEach { it.isFavorite = true }
        Log.v(TAG, "Found ${artWorks.size} artWorks")
    }

    fun getArtworks() = artWorks

    fun getArtworksById(id: Int) = artWorks.firstOrNull { it.id == id }

    fun getFavoriteArtworks(context: Context): List<Art>? =
        Favorites.getFavorites(context)?.mapNotNull { getArtworksById(it) }

    private fun loadJSONFromAsset(filename: String, context: Context): String? {
        var json: String? = null
        try {
            val inputStream = context.assets.open(filename)
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            json = String(buffer)
        } catch (ex: IOException) {
            Log.e(TAG, "Error reading from $filename", ex)
        }
        return json
    }
}