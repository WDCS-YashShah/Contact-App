package com.mycontacts.ui.fragments.cloud_contacts

import androidx.lifecycle.liveData
import com.mycontacts.core.App
import com.mycontacts.data.repository.Repository
import com.mycontacts.ui.base.BaseViewModel
import com.mycontacts.utils.Resource
import kotlinx.coroutines.Dispatchers

class CloudContactsViewModel(private val mainRepository: Repository) : BaseViewModel(App.instance) {

    override fun subscribe() {
        getContacts()
    }

    fun getContacts(source: String = "gmail", query: String = "", ) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = mainRepository.getContacts(source, query)))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }

}