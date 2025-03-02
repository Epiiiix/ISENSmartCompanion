package fr.isen.giusiano.isensmartcompanion.api

import fr.isen.giusiano.isensmartcompanion.model.Event
import retrofit2.Call
import retrofit2.http.GET

interface EventApiService {
    @GET("events.json")
    fun getEvents(): Call<List<Event>>
}
