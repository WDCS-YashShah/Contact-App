package com.mycontacts.ui.fragments.cloud_contacts

import android.graphics.drawable.Drawable
import android.telephony.PhoneNumberUtils
import android.view.View
import com.mycontacts.R
import com.mycontacts.data.model.CloudContactsModel
import com.mycontacts.databinding.ContactsRvItemBinding
import com.mycontacts.extentions.capitalizeWords
import com.mycontacts.extentions.loadCircleCropImage
import com.mycontacts.ui.base.BaseRecyclerViewAdapter
import com.mycontacts.ui.base.BaseViewHolder
import java.util.*

class ContactItemAdapter : BaseRecyclerViewAdapter<CloudContactsModel, ContactItemAdapter.ItemViewHolder>() {

    var onClickListener: IItemListener? = null

    override fun getRowLayoutId(viewType: Int): Int {
        return R.layout.contacts_rv_item
    }

    override fun bind(viewHolder: ItemViewHolder, position: Int, item: CloudContactsModel) {
        viewHolder.bindViewHolder(item)
    }

    override fun getViewHolder(view: View, viewType: Int): ItemViewHolder {

        return ItemViewHolder(view)
    }

    inner class ItemViewHolder(view: View) : BaseViewHolder<CloudContactsModel>(view) {

        private val bind = ContactsRvItemBinding.bind(view)

        override fun bindViewHolder(item: CloudContactsModel) {
            bind.TVContactName.text = item.name!!.capitalizeWords()
            bind.TVContactPhoneNumber.text = PhoneNumberUtils.formatNumber(item.phone, Locale.getDefault().country)
            bind.IVContact.loadCircleCropImage(item.profileImage)

            bind.IVContact.setOnClickListener {
                onClickListener?.onProfileClicked(adapterPosition, item, bind.IVContact, bind.IVContact.drawable)
            }
        }
    }

    interface IItemListener {
        fun onSizeSelected(position: Int, item: CloudContactsModel)
        fun onProfileClicked(position: Int, item: CloudContactsModel, thumbView: View, imageRes: Drawable)
    }

}