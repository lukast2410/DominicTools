package com.example.dominictools.data

import com.example.dominictools.data.entities.Friend
import com.example.dominictools.data.entities.Tool

class DataGenerator {

    companion object{
        fun getInitialFriends(): List<Friend> {
            return listOf(
                Friend("Brian"),
                Friend("Luke"),
                Friend("Letty"),
                Friend("Shaw"),
                Friend("Parker"),
            )
        }

        fun getInitialTools(): List<Tool> {
            return listOf(
                Tool("Wrench", 6),
                Tool("Cutter", 15),
                Tool("Pliers", 12),
                Tool("Screwdriver", 13),
                Tool("Welding machine", 3),
                Tool("Welding glasses", 7),
                Tool("Hammer", 4),
                Tool("Measuring Tape", 9),
                Tool("Alan key set", 4),
                Tool("Air compressor", 2),
            )
        }
    }
}