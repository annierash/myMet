package co.instil.mymet.api

import co.instil.mymet.app.App
import co.instil.mymet.model.Art
import co.instil.mymet.model.response.GetIdResponse
import com.google.gson.Gson
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.NullPointerException

const val BASE_URL = "https://collectionapi.metmuseum.org/public/collection/v1"

class RemoteApi(private val apiService: RemoteApiService) {

    private val gson = Gson()

    //TODO("add functionality to get art - art from european paintings only comes in array of id numbers, rest of data is available by searching id number")

    fun getArtIds(onIdsReceived: (List<Art>, Throwable?) -> Unit) {
        apiService.getArtIds(App.getToken()).enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                onIdsReceived(emptyList(), error("No ids received"))
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                val jsonBody = response.body()?.string()

                if (jsonBody == null) {
                    onIdsReceived(emptyList(), NullPointerException("No data available"))
                    return
                }
                val data = gson.fromJson(jsonBody, GetIdResponse::class.java)

                if (data != null && data.objectIds.isNotEmpty()) {
                    onIdsReceived(data.objectIds.filter { !it.isFavorite }, null)
                } else {
                    onIdsReceived(emptyList(), NullPointerException("No data available"))
                }
            }
        })
    }

}