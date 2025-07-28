package com.baf1.hexmaptest

data class CellDTO(
    val id: String,
    val teamId: Int? = null,
    val roundId: Int? = null,
) {
    override fun equals(other: Any?): Boolean {
        return other is CellDTO && this.id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}