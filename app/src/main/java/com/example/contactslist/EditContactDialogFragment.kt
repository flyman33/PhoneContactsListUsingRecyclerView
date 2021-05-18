package com.example.contactslist

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment

class EditContactDialogFragment: DialogFragment() {

    private var name: String? = null
    private var lastname: String? = null
    private var phoneNumber: String? = null
    private var idLayout: Int? = null

    private var nameEdit: EditText? = null
    private var lastnameEdit: EditText? = null
    private var phoneNumberEdit: EditText? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)

        name = requireArguments().getString(NAME_EXTRA)
        lastname = requireArguments().getString(LASTNAME_EXTRA)
        phoneNumber = requireArguments().getString(PHONE_NUMBER_EXTRA)
        idLayout = requireArguments().getInt(ID_EXTRA)
    }

    override fun onResume() {
        super.onResume()

        nameEdit = dialog?.findViewById(R.id.name_edit)
        nameEdit?.setText(name)

        lastnameEdit = dialog?.findViewById(R.id.lastname_edit)
        lastnameEdit?.setText(lastname)

        phoneNumberEdit = dialog?.findViewById(R.id.phone_number_edit)
        phoneNumberEdit?.setText(phoneNumber)
    }

    companion object {
        const val NAME_EXTRA = "NAME_EXTRA"
        const val LASTNAME_EXTRA = "LASTNAME_EXTRA"
        const val PHONE_NUMBER_EXTRA = "PHONE_NUMBER_EXTRA"
        const val ID_EXTRA = "ID_EXTRA"

        fun newInstance(name: String?, lastName: String?, phoneNumber: String?, id: Int) = EditContactDialogFragment().apply {
            arguments = Bundle().also {
                it.putString(NAME_EXTRA, name)
                it.putString(LASTNAME_EXTRA, lastName)
                it.putString(PHONE_NUMBER_EXTRA, phoneNumber)
                it.putInt(ID_EXTRA, id)
            }
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)

            val inflater = requireActivity().layoutInflater;

            builder.setView(inflater.inflate(R.layout.edit_contact_dialog_fragment, null))
                .setPositiveButton("Сохранить",
                    DialogInterface.OnClickListener { dialog, id ->

                        val intent = Intent()
                        intent.putExtra(NAME_EXTRA, nameEdit?.text.toString())
                        intent.putExtra(LASTNAME_EXTRA, lastnameEdit?.text.toString())
                        intent.putExtra(PHONE_NUMBER_EXTRA, phoneNumberEdit?.text.toString())
                        intent.putExtra(ID_EXTRA, idLayout)
                        targetFragment?.onActivityResult(targetRequestCode, Activity.RESULT_OK, intent)

                        dialog.dismiss()

                    })
                .setNegativeButton("Отмена",
                    DialogInterface.OnClickListener { dialog, id ->
                        getDialog()?.cancel()
                    })

            builder.create()
        } ?: throw IllegalStateException()
    }
}