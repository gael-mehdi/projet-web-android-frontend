package com.ismin.android


class MonumentManagement {
    private val storage = HashMap<String, Monument>()

    fun addMonument(monument: Monument) {
        storage[monument.ref] = monument
    }

    fun getMonument(ref: String): Monument {
        return storage[ref] ?: throw Exception("Monument not found")
    }

    fun getAllMonuments(): ArrayList<Monument> {
        return ArrayList(storage.values
            .sortedWith(compareByDescending<Monument> { it.favorite } // Trier d'abord par le statut 'favorite' (les favoris d'abord)
                .thenBy { it.dep_current_code } // Puis trier par 'dep_current_code'
            ))
    }

    fun getMonumentsOf(reg_name: String): List<Monument> {
        return storage.filterValues { monument -> monument.reg_name.equals(reg_name) }
            .values
            .sortedBy { monument -> monument.edif }
    }

    fun getTotalNumberOfMonuments(): Int {
        return storage.size
    }

    fun clear() {
        storage.clear()
    }
}
