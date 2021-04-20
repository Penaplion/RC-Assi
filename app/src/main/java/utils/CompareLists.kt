package utils

import data.FinanceFromPersonItem
import multipleroomtables.entities.Person

class CompareLists {
    fun compareAllPersons(list: List<Person>, list2: List<Person>): Boolean {
        if (list.size != list2.size) {
            return false
        }
        list.forEach {
            list2.forEach { it2 ->
                if (it.person_name != it2.person_name) {
                    return false
                }
            }
        }
        return true
    }

    fun containsDebtor(list: List<FinanceFromPersonItem>, debtor: String): Boolean {
        list.forEach {
            if (it.debtor == debtor) {
                return true
            }
        }
        return false
    }

    fun findPosition(list: List<FinanceFromPersonItem>, debtor: String): Int {
        list.forEachIndexed { index, it ->
            if (it.debtor == debtor) {
                return index
            }
        }
        return 0
    }

    fun deletePersonInList(list: MutableList<Person>, name: String): MutableList<Person> {
        list.forEach {
            if (it.person_name == name) {
                list -= it
                return list
            }
        }
        return list
    }
}