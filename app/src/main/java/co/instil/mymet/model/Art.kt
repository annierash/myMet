package co.instil.mymet.model

data class Art(
    val objectId: Int,
    val primaryImageSmall: String,
    val title: String,
    val artistDisplayName: String,
    val artistDisplayBio: String,
    val medium: String,
    val objectDate: String,
    val galleryNumber: String) {


    val uri: String
        get() = "drawable/$primaryImageSmall"

    val thumbnail: String
        get() = "drawable/thumbnail_$primaryImageSmall"

}



