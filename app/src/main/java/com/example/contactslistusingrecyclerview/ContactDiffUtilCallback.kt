package com.example.contactslistusingrecyclerview

import androidx.recyclerview.widget.DiffUtil

class ContactDiffUtilCallback(var oldList: List<Person>, var newList: List<Person>): DiffUtil.Callback() {

    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldContacts = oldList[oldItemPosition]
        val newContacts = newList[newItemPosition]
        return oldContacts.name == newContacts.name
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldContacts = oldList[oldItemPosition]
        val newContacts = newList[newItemPosition]
        return oldContacts.lastname == newContacts.lastname &&
                oldContacts.phoneNumber == newContacts.phoneNumber
    }
}