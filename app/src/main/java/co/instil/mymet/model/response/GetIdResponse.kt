package co.instil.mymet.model.response
import co.instil.mymet.model.Art

data class GetIdResponse(val objectIds: List<Art> = mutableListOf())