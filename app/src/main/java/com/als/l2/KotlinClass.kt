package com.als.l2

class KotlinClass {
    fun some(){
        var notNullable = ""

        var nullable: String? = ""

        var i: Int = 1
        val d: Double = 1.0

        i = d.toInt()

        val t : Any

        if (nullable !=null){
            notNullable = nullable
        }

        val lenghtnn = notNullable.length


        val lenght = nullable?.length ?: 0

        val arrlst = mutableListOf<Int>(1,5,4,5)
        arrlst.add(1)
        val constList = ArrayList<Int>(1)

        val map: MutableMap<Int, Int> = mutableMapOf()
        map.put(1,2)

        val persons: MutableList<String?> = arrayListOf(null, "per1", "per2","per3", "")
        val notEmpty = persons.filter {
                it.isNullOrBlank()
        }
        val ints: MutableList<Int> = mutableListOf(1, 5, 7,5)

        val c = ints.find { it == 5 }
        ints.count{ it == 5}
        ints.any { it <0 }

        val square = ints
            .filter { it>3 }
            .map { it*it }
            .map { it.toString() }
            .filter { it.length > 2 }

        val str = "hi " + ints.count().toString()
        val str1 = "hi ${ints.count()} \$ $c ${ints.size} "

        val vs = BaseViewSate<String>("hi", null)

    }
}