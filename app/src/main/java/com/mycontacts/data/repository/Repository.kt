package com.mycontacts.data.repository

import com.mycontacts.data.api.ApiHelper


class Repository(private val apiHelper: ApiHelper) {
    suspend fun getContacts(source: String, query: String) = apiHelper.getContacts(source,query)
}