package com.mycontacts.ui.activity.main

import androidx.lifecycle.liveData
import com.mycontacts.core.App
import com.mycontacts.data.repository.Repository
import com.mycontacts.ui.base.BaseViewModel
import com.mycontacts.utils.Resource
import kotlinx.coroutines.Dispatchers

class MainViewModel(private val mainRepository: Repository) : BaseViewModel(App.instance) {

    override fun subscribe() {

    }


}