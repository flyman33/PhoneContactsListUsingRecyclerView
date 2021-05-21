package com.example.contactslistusingrecyclerview

import android.content.DialogInterface
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.util.*
import kotlin.collections.ArrayList

class PhoneBookAdapter(): RecyclerView.Adapter<PhoneBookAdapter.PersonViewHolder>() {

    var people = mutableListOf<Person>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
//    var copyList = mutableListOf<Person>()

//    fun deletePersonListItems(list: List<Person>) {
//        val callback = ContactDiffUtilCallback(people, list)
//        val diffResult: DiffUtil.DiffResult = DiffUtil.calculateDiff(callback)
//
//        people.clear()
//        people.addAll(list)
//        diffResult.dispatchUpdatesTo(this)
//    }

    fun deleteElement(position: Int) {
//        copyList.addAll(people)
//        Log.d("diff", copyList.size.toString())
        people.removeAt(position)
        notifyItemRemoved(position)
//        deletePersonListItems(copyList)
    }

    override fun getItemCount() = people.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.person_item, parent, false)
        return PersonViewHolder(view)
    }

    override fun onBindViewHolder(holder: PersonViewHolder, position: Int) {
        holder.bind(people[position])

        Glide
            .with(holder.itemView.context)
            .load(people[position].image)
            .centerCrop()
            .into(holder.image)

        holder.itemView.setOnClickListener {

            val name = holder.itemView.findViewById<TextView>(R.id.name_text).text.toString()
            val lastname = holder.itemView.findViewById<TextView>(R.id.lastname_text).text.toString()
            val phoneNumber = holder.itemView.findViewById<TextView>(R.id.phone_number_text).text.toString()

            (holder.itemView.context as FragmentActivity).supportFragmentManager.apply {

                val contactFragment = ContactFragment.newInstance(name, lastname, phoneNumber, position)
                val transaction = beginTransaction()

                transaction.replace(R.id.frame_layout, contactFragment)
                transaction.addToBackStack(null)
                transaction.commit()
            }
        }

        holder.itemView.setOnLongClickListener {
            MaterialAlertDialogBuilder(holder.itemView.context)
                .setTitle("Deleting")
                .setMessage("Do you want to delete it?")
                .setPositiveButton("Yes") {dialog, _ ->
                deleteElement(holder.adapterPosition)
                    dialog.dismiss()
                }
                .setNegativeButton("No") {dialog, _ ->
                    dialog.dismiss()
                }
                .show()
            Log.d("asd", position.toString())
true
        }
    }

    class PersonViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        val name = itemView.findViewById<TextView>(R.id.name_text)
        val lastname = itemView.findViewById<TextView>(R.id.lastname_text)
        val phoneNumber = itemView.findViewById<TextView>(R.id.phone_number_text)
        val image = itemView.findViewById<ImageView>(R.id.contact_image_view)

        fun bind(person: Person) {
            name.text = person.name
            lastname.text = person.lastname
            phoneNumber.text = "${person.phoneNumber}"
        }
    }

    override fun onViewRecycled(holder: PersonViewHolder) {
        super.onViewRecycled(holder)
        Glide.with(holder.image.context).clear(holder.image)
    }
}