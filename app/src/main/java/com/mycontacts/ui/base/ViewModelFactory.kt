package com.mycontacts.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mycontacts.data.api.ApiHelper
import com.mycontacts.data.repository.Repository
import com.mycontacts.ui.activity.main.MainViewModel
import com.mycontacts.ui.fragments.add_contact.AddContactViewModel
import com.mycontacts.ui.fragments.cloud_contacts.CloudContactsViewModel
import com.mycontacts.ui.fragments.local_contacts.LocalContactsViewModel

class ViewModelFactory(private val apiHelper: ApiHelper) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T = when {
        modelClass.isAssignableFrom(MainViewModel::class.java) -> {
            MainViewModel(Repository(apiHelper)) as T
        }
        modelClass.isAssignableFrom(LocalContactsViewModel::class.java) -> {
            LocalContactsViewModel(Repository(apiHelper)) as T
        }
        modelClass.isAssignableFrom(CloudContactsViewModel::class.java) -> {
            CloudContactsViewModel(Repository(apiHelper)) as T
        }
        modelClass.isAssignableFrom(AddContactViewModel::class.java) -> {
            AddContactViewModel(Repository(apiHelper)) as T
        }
        else -> throw IllegalArgumentException("Unknown class name")
    }

}

