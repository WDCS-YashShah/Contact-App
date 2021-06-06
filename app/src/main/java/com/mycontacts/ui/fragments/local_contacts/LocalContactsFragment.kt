package com.mycontacts.ui.fragments.local_contacts

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import com.mycontacts.R
import com.mycontacts.custom.recyclerview_devider.HorizontalDividerItemDecoration
import com.mycontacts.data.model.CloudContactsModel
import com.mycontacts.data.model.LocalContactsModel
import com.mycontacts.databinding.FLocalContactListBinding
import com.mycontacts.extentions.logLargeString
import com.mycontacts.extentions.obtainViewModel
import com.mycontacts.ui.base.BaseFragment
import com.mycontacts.utils.ZoomImage

class LocalContactsFragment : BaseFragment<LocalContactsViewModel>() {

    private lateinit var binding: FLocalContactListBinding
    private lateinit var contactItemAdapter: ContactItemAdapter

    override fun initializeViewModel(): LocalContactsViewModel {
        return obtainViewModel(LocalContactsViewModel::class.java)
    }

    override fun getLayoutView(inflater: LayoutInflater): View {
        binding = FLocalContactListBinding.inflate(inflater)
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
        viewModel.contactList.observe(this) {
            it.size.toString().logLargeString("it.size")
            contactItemAdapter.setItems(it.toMutableList())
        }
    }

    private fun attachListeners() {
        contactItemAdapter.onClickListener = object : ContactItemAdapter.IItemListener {
            override fun onSizeSelected(position: Int, item: LocalContactsModel) {

            }

            override fun onProfileClicked(position: Int, item: LocalContactsModel, thumbView: View, imageRes: Drawable) {
                ZoomImage.zoomImageFromThumb(thumbView, imageRes, binding.parentLayout)
            }
        }
    }
}