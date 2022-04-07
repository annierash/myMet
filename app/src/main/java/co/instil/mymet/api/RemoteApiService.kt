package co.instil.mymet.api

import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header


interface RemoteApiService {

    @GET("/api/objects?departmentIds=11")
    fun getArtIds(@Header("objectIDs") token: String): Call<ResponseBody>
}