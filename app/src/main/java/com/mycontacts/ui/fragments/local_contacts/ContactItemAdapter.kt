package com.mycontacts.ui.fragments.local_contacts

import android.graphics.drawable.Drawable
import android.telephony.PhoneNumberUtils
import android.view.View
import com.mycontacts.R
import com.mycontacts.data.model.CloudContactsModel
import com.mycontacts.data.model.LocalContactsModel
import com.mycontacts.databinding.ContactsRvItemBinding
import com.mycontacts.extentions.buildRoundDrawable
import com.mycontacts.extentions.capitalizeWords
import com.mycontacts.ui.base.BaseRecyclerViewAdapter
import com.mycontacts.ui.base.BaseViewHolder
import java.util.*

class ContactItemAdapter : BaseRecyclerViewAdapter<LocalContactsModel, ContactItemAdapter.ItemViewHolder>() {

    var onClickListener: IItemListener? = null

    override fun getRowLayoutId(viewType: Int): Int {
        return R.layout.contacts_rv_item
    }

    override fun bind(viewHolder: ItemViewHolder, position: Int, item: LocalContactsModel) {
        viewHolder.bindViewHolder(item)
    }

    override fun getViewHolder(view: View, viewType: Int): ItemViewHolder {

        return ItemViewHolder(view)
    }

    inner class ItemViewHolder(view: View) : BaseViewHolder<LocalContactsModel>(view) {

        private val bind = ContactsRvItemBinding.bind(view)

        override fun bindViewHolder(item: LocalContactsModel) {
            bind.TVContactName.text = item.contactName!!.capitalizeWords()
            bind.TVContactPhoneNumber.text = PhoneNumberUtils.formatNumber(item.contactPhoneNumber, Locale.getDefault().country)
            bind.IVContact.buildRoundDrawable(item.contactName!!.substring(0, 1))
            bind.IVContact.setOnClickListener {
                onClickListener?.onProfileClicked(adapterPosition, item, bind.IVContact, bind.IVContact.drawable)
            }
        }
    }

    interface IItemListener {
        fun onSizeSelected(position: Int, item: LocalContactsModel)
        fun onProfileClicked(position: Int, item: LocalContactsModel, thumbView: View, imageRes: Drawable)
    }

}