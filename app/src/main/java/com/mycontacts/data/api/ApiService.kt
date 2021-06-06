package com.mycontacts.data.api

import com.mycontacts.data.model.CloudContactsModel
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("contacts.php")
    suspend fun getContacts(@Query("source") source: String?, @Query("search") query: String?): List<CloudContactsModel>

}