package co.instil.mymet.model

data class Art(
    val id: Int,
    val objectId: Int,
    val primaryImageSmall: String,
    val image: String,
    val title: String,
    val artistDisplayName: String,
    val artistDisplayBio: String,
    val medium: String,
    val objectDate: String,
    val galleryNumber: String,
    var isFavorite: Boolean = false) {


}



