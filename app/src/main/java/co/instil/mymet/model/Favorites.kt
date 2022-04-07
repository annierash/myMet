package co.instil.mymet.model

import android.content.Context
import androidx.preference.PreferenceManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object Favorites {

    private const val KEY_FAVORITES = "KEY_FAVORITES"
    private val gson = Gson()

    private var favorites: MutableList<Int>? = null

    fun isFavorite(art: Art, context: Context): Boolean {
        return getFavorites(context)?.contains(art.id) == true
    }

    fun addFavorite(art: Art, context: Context) {
        val favorites = getFavorites(context)
        favorites?.let {
            art.isFavorite = true
            favorites.add(art.id)
            saveFavorites(KEY_FAVORITES, favorites, context)
        }
    }

    fun removeFavorite(art: Art, context: Context) {
        val favorites = getFavorites(context)
        favorites?.let {
            art.isFavorite = false
            favorites.remove(art.id)
            saveFavorites(KEY_FAVORITES, favorites, context)
        }
    }


    public fun getFavorites(context: Context): MutableList<Int>? {
        if (favorites == null) {
            val json = sharedPrefs(context).getString(KEY_FAVORITES, "")
            val type = object : TypeToken<MutableList<Int>>() {}.type
            favorites = gson.fromJson<MutableList<Int>>(json, type) ?: return mutableListOf()
        }
        return favorites
    }

    private fun saveFavorites(key: String, list: List<Int>, context: Context) {
        val json = gson.toJson(list)
        sharedPrefs(context).edit().putString(key, json).apply()
    }

    fun saveFavorites(list:List<Int>, context: Context) {
        saveFavorites(KEY_FAVORITES, list, context)
    }

    private fun sharedPrefs(context: Context) =
        PreferenceManager.getDefaultSharedPreferences(context)
}