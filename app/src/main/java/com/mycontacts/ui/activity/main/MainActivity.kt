@file:Suppress("LocalVariableName", "PrivatePropertyName")

package com.mycontacts.ui.activity.main

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LiveData
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import com.mycontacts.R
import com.mycontacts.databinding.AMainBinding
import com.mycontacts.extentions.obtainViewModel
import com.mycontacts.extentions.setupWithNavController
import com.mycontacts.ui.base.BaseActivity
import java.util.*


class MainActivity : BaseActivity<MainViewModel>() {

    private val REQUEST_ID_MULTIPLE_PERMISSIONS = 1

    private lateinit var binding: AMainBinding
    private lateinit var navController: LiveData<NavController>

    override fun initializeViewModel(): MainViewModel {
        return obtainViewModel(MainViewModel::class.java)
    }

    override fun getLayoutView(): View {
        binding = AMainBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun setUpChildUI(savedInstanceState: Bundle?) {

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        if (checkPermissions()) {
            updateUI()
        }
    }

    private fun updateUI() {
        val navGraphIds = listOf(
            R.navigation.navigation_local_contacts,
            R.navigation.navigation_cloud_contacts,
            R.navigation.navigation_add_contact
        )

        navController = binding.navView.setupWithNavController(
            navGraphIds,
            supportFragmentManager,
            R.id.nav_host_fragment,
            intent
        )

        viewModel.subscribe()

        setUpObservers()
    }

    private fun setUpObservers() {
        val value = FragmentManager.OnBackStackChangedListener {
            updateTitle(findNavController(R.id.nav_host_fragment).currentDestination)
        }

        navController.observe(this) {
            updateTitle(it.currentDestination)
        }
    }

    fun updateTitle(destination: NavDestination?) {

        if (destination?.label?.isNotEmpty() == true) {
            binding.txtTitle.text = destination.label
        }
    }

    override fun onNetworkStatusChanged(isConnected: Boolean) {

    }

    private fun checkPermissions(): Boolean {

        val READ_CONTACTS = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)
        val WRITE_CONTACTS = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_CONTACTS)
        val CALL_PHONE = ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)

        val listPermissionsNeeded: MutableList<String> = ArrayList()

        if (READ_CONTACTS != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_CONTACTS)
        }
        if (WRITE_CONTACTS != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.WRITE_CONTACTS)
        }
        if (CALL_PHONE != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CALL_PHONE)
        }

        if (listPermissionsNeeded.isNotEmpty()) {
            requestPermissions(listPermissionsNeeded)
            return false
        }
        return true
    }

    private fun requestPermissions(listPermissionsNeeded: MutableList<String>) {
        ActivityCompat.requestPermissions(this, listPermissionsNeeded.toTypedArray(), REQUEST_ID_MULTIPLE_PERMISSIONS)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {

        when (requestCode) {
            REQUEST_ID_MULTIPLE_PERMISSIONS -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    if (checkPermissions()) {
                        updateUI()
                    } else {
                        checkPermissions()
                    }
                } else {
                    checkPermissions()
                }
                return
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        super.onActivityResult(requestCode, resultCode, intent)
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(this, "Added Contact", Toast.LENGTH_SHORT).show()
            }
            if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "Cancelled Added Contact", Toast.LENGTH_SHORT).show()
            }
        }
    }

}
