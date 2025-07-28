package com.baf1.hexmaptest

enum class Teams(val id: Int) {
    NONE(-1), BLUE(1), PURPLE(2), YELLOW(3), LIGHT_BLUE(4);

    companion object {
        fun getById(id: Int?): Teams {
            return when (id) {
                1 -> BLUE
                2 -> PURPLE
                3 -> YELLOW
                4 -> LIGHT_BLUE
                else -> NONE
            }
        }
    }
}

