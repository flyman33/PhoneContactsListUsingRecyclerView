package com.example.contactslist

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment

class ListContactsFragment: Fragment() {

    var nameSave: String? = null
    var lastnameSave: String? = null
    var phoneNumberSave: String? = null
    var idSave: Int = 0

    companion object {
        fun newInstance() = ListContactsFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.list_contacts_fragment, container, false)

    private fun goToContactDetails(view: View, id: Int) {
        val layout = view.findViewById<LinearLayout>(id)

        layout.setOnClickListener() {
            val name = view.findViewById<TextView>(layout.getChildAt(0).id).text.toString()
            val lastName = view.findViewById<TextView>(layout.getChildAt(1).id).text.toString()
            val phoneNumber = view.findViewById<TextView>(layout.getChildAt(2).id).text.toString()

            val isTablet = resources.getBoolean(R.bool.isTablet)

            fragmentManager?.apply {
                val contactFragment = ContactFragment.newInstance(name, lastName, phoneNumber, id)
                val transaction = beginTransaction()

                if(isTablet && resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    transaction.replace(R.id.frame_layout_detail, contactFragment)
                } else {
                    transaction.replace(R.id.frame_layout, contactFragment)
                }
                transaction.addToBackStack("contacts")
                transaction.commit()
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bundle: Bundle? = this.arguments
        if(bundle != null) {
            val name = bundle.getString(ContactFragment.NAME_EXTRA)
            val lastname = bundle.getString(ContactFragment.LASTNAME_EXTRA)
            val phoneNumber = bundle.getString(ContactFragment.PHONE_NUMBER_EXTRA)
            val idLayout = bundle.getInt(ContactFragment.ID_EXTRA)
            val layout = view.findViewById<LinearLayout>(idLayout)

            nameSave = name
            lastnameSave = lastname
            phoneNumberSave = phoneNumber
            idSave = idLayout

            view.findViewById<TextView>(layout.getChildAt(0).id).text = name
            view.findViewById<TextView>(layout.getChildAt(1).id).text = lastname
            view.findViewById<TextView>(layout.getChildAt(2).id).text = phoneNumber
        }
            goToContactDetails(view, R.id.contact1)
            goToContactDetails(view, R.id.contact2)
            goToContactDetails(view, R.id.contact3)
            goToContactDetails(view, R.id.contact4)
            goToContactDetails(view, R.id.contact5)
    }
}