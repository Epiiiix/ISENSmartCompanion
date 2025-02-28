package fr.isen.giusiano.isensmartcompanion.api

import fr.isen.giusiano.isensmartcompanion.model.Event
import retrofit2.Response
import retrofit2.http.GET

interface EventApiService {
    @GET("events.json")
    suspend fun getEvents(): Response<List<Event>>
}
