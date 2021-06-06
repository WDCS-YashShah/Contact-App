package com.mycontacts.ui.fragments.cloud_contacts

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import com.mycontacts.R
import com.mycontacts.custom.recyclerview_devider.HorizontalDividerItemDecoration
import com.mycontacts.data.model.CloudContactsModel
import com.mycontacts.databinding.FCloudContactListBinding
import com.mycontacts.extentions.logLargeString
import com.mycontacts.extentions.obtainViewModel
import com.mycontacts.ui.base.BaseFragment
import com.mycontacts.utils.Status
import com.mycontacts.utils.ZoomImage

class CloudContactsFragment : BaseFragment<CloudContactsViewModel>() {

    private lateinit var binding: FCloudContactListBinding
    private lateinit var contactItemAdapter: ContactItemAdapter

    override fun initializeViewModel(): CloudContactsViewModel {
        return obtainViewModel(CloudContactsViewModel::class.java)
    }

    override fun getLayoutView(inflater: LayoutInflater): View {
        binding = FCloudContactListBinding.inflate(inflater)
        return binding.root
    }

    override fun setUpUI() {

        contactItemAdapter = ContactItemAdapter()
        binding.RVContacts.adapter = contactItemAdapter
        binding.RVContacts.addItemDecoration(
            HorizontalDividerItemDecoration.Builder(requireContext()).drawable(R.drawable.divider).sizeResId(R.dimen.size_1).build()
        )

        setUpObservers()
        attachListeners()

        viewModel.subscribe()
    }

    private fun setUpObservers() {
        viewModel.getContacts().observe(this) {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        viewModel.hideProgressDialog()
                        resource.data?.let { response ->
                            contactItemAdapter.setItems(response.toMutableList())
                        }
                    }
                    Status.ERROR -> {
                        viewModel.hideProgressDialog()
                        resource.message!!.logLargeString("message")
                    }
                    Status.LOADING -> {
                        viewModel.showProgressDialog()
                    }
                }
            }
        }
    }

    private fun attachListeners() {
        contactItemAdapter.onClickListener = object : ContactItemAdapter.IItemListener {
            override fun onSizeSelected(position: Int, item: CloudContactsModel) {

            }

            override fun onProfileClicked(position: Int, item: CloudContactsModel, thumbView: View, imageRes: Drawable) {
                ZoomImage.zoomImageFromThumb(thumbView, imageRes, binding.parentLayout)
            }
        }
    }
}