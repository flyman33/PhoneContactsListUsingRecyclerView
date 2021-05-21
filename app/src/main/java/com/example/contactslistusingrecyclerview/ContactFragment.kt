package com.example.contactslistusingrecyclerview

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide

class ContactFragment: Fragment() {

    private var name: String? = null
    private var lastname: String? = null
    private var phoneNumber: String? = null
    private var idImage: Int = 0

    override fun onAttach(context: Context) {
        super.onAttach(context)

        name = requireArguments().getString(NAME_EXTRA)
        lastname = requireArguments().getString(LASTNAME_EXTRA)
        phoneNumber = requireArguments().getString(PHONE_NUMBER_EXTRA)
        idImage = requireArguments().getInt(ID_EXTRA)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.contact_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<TextView>(R.id.contact_name).text = name
        view.findViewById<TextView>(R.id.contact_lastname).text = lastname
        view.findViewById<TextView>(R.id.contact_phone_number).text = phoneNumber

        Glide
            .with(view.context)
            .load("https://source.unsplash.com/random/300x200?sig=${idImage}")
            .centerCrop()
            .into(view.findViewById(R.id.contact_image))

        view.findViewById<Button>(R.id.edit_button).setOnClickListener() {

            val editContactDialogFragment = EditContactDialogFragment.newInstance(name, lastname, phoneNumber, idImage)

            editContactDialogFragment.setTargetFragment(this, REQUEST)
            editContactDialogFragment.show(requireFragmentManager(), "editContact")
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                REQUEST -> {
                    val name = data?.getStringExtra(EditContactDialogFragment.NAME_EXTRA).toString()
                    val lastname = data?.getStringExtra(EditContactDialogFragment.LASTNAME_EXTRA).toString()
                    val phoneNumber = data?.getStringExtra(EditContactDialogFragment.PHONE_NUMBER_EXTRA).toString()
                    val position = data?.getIntExtra(EditContactDialogFragment.ID_EXTRA, -1)

                    view?.findViewById<TextView>(R.id.contact_name)?.text = name
                    view?.findViewById<TextView>(R.id.contact_lastname)?.text = lastname
                    view?.findViewById<TextView>(R.id.contact_phone_number)?.text = phoneNumber

                    val image = view?.findViewById<ImageView>(R.id.contact_image).toString()
                    Log.d("asd", image)

                    fragmentManager?.apply {
                        val bundle = Bundle()
                        bundle.putString(NAME_EXTRA, name)
                        bundle.putString(LASTNAME_EXTRA, lastname)
                        bundle.putString(PHONE_NUMBER_EXTRA, phoneNumber)
                        bundle.putInt(ID_EXTRA, position!!)
                        MainActivity.isOld = true
                        setFragmentResult("KEY", bundle)
                    }
                }
            }
        }
    }

    companion object {

        const val NAME_EXTRA = "NAME_EXTRA"
        const val LASTNAME_EXTRA = "LASTNAME_EXTRA"
        const val PHONE_NUMBER_EXTRA = "PHONE_NUMBER_EXTRA"
        const val ID_EXTRA = "ID_EXTRA"
        const val REQUEST = 1

        fun newInstance(name:String, lastName: String, phoneNumber: String, idImage: Int) = ContactFragment().apply {
            arguments = Bundle().also {
                it.putString(NAME_EXTRA, name)
                it.putString(LASTNAME_EXTRA, lastName)
                it.putString(PHONE_NUMBER_EXTRA, phoneNumber)
                it.putInt(ID_EXTRA, idImage)
            }
        }
    }
}