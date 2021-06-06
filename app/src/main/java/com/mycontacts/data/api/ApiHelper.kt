package com.mycontacts.data.api

class ApiHelper(private val apiService: ApiService) {
    suspend fun getContacts(source: String, query: String) = apiService.getContacts(source, query)
}