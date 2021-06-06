package com.mycontacts.extentions

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mycontacts.data.api.ApiHelper
import com.mycontacts.data.api.RetrofitBuilder
import com.mycontacts.ui.base.ViewModelFactory


fun <T : ViewModel> AppCompatActivity.obtainViewModel(viewModelClass: Class<T>) =
    ViewModelProvider(this, ViewModelFactory(ApiHelper(RetrofitBuilder.apiService))).get(
        viewModelClass
    )

fun <T : ViewModel> Fragment.obtainViewModel(viewModelClass: Class<T>) =
    ViewModelProvider(this, ViewModelFactory(ApiHelper(RetrofitBuilder.apiService))).get(
        viewModelClass
    )

