package com.mycontacts.ui.fragments.add_contact

import android.view.LayoutInflater
import android.view.View
import com.mycontacts.R
import com.mycontacts.databinding.FAddContactBinding
import com.mycontacts.extentions.obtainViewModel
import com.mycontacts.ui.base.BaseFragment

class AddContactFragment : BaseFragment<AddContactViewModel>() {

    private lateinit var binding: FAddContactBinding


    override fun initializeViewModel(): AddContactViewModel {
        return obtainViewModel(AddContactViewModel::class.java)
    }

    override fun getLayoutView(inflater: LayoutInflater): View {
        binding = FAddContactBinding.inflate(inflater)
        return binding.root
    }

    override fun setUpUI() {

        attachListeners()

        viewModel.subscribe()
    }

    private fun attachListeners() {

        binding.addUserContact.setOnClickListener {
            if (validateInputs()) {
                viewModel.addContact(requireActivity(), binding.userName.text.toString(), binding.userNumber.text.toString())
            }
        }
    }

    private fun validateInputs(): Boolean {
        var valid = true

        if (binding.userName.text.isNullOrEmpty()) {
            binding.userNameInputLayout.error = resources.getString(R.string.error_required)
            valid = false
        }

        if (binding.userNumber.text.isNullOrEmpty()) {
            binding.userNumberInputLayout.error = resources.getString(R.string.error_required)
            valid = false
        }

        if (!binding.userNumber.text.isNullOrEmpty()) {
            if (binding.userNumber.text!!.length < 8 || binding.userNumber.text!!.length > 13) {
                binding.userNumberInputLayout.error = resources.getString(R.string.error_invalid_number)
                valid = false
            }
        }

        return valid
    }
}