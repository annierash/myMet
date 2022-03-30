package co.instil.mymet.api

import co.instil.mymet.model.Art

const val BASE_URL = "https://collectionapi.metmuseum.org/public/collection/v1"

class RemoteApi (private val apiService: RemoteApiService){

    fun getArtIds(onIdsRecieved: (List<Art>, Throwable?) -> Unit) {
        TODO("add functionality to get art - art from european paintings only comes in array of id numbers, rest of data is available by searching id number")
    }
}