package dev.future.taxipark.utils.sharedPref

import io.paperdb.Paper

object SaveTextSize {
    const val key="fmowerefkfoif"

    fun saveTextSize(size:Int){
        Paper.book().write(key,size)
    }

    fun getTextSize():Int{
        return Paper.book().read<Int>(key,0)!!
    }
}