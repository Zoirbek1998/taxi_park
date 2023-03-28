package dev.future.taxipark.utils.sharedPref

import io.paperdb.Paper

object SaveTarif {

    val key ="key2wd1837t43"
    val key1 ="kesfaew22f7t43"

//    fun saveTarif(tarif:ArrayList<GetTarif>){
//        Paper.book().write(key,tarif)
//    }
//    fun getTarif():ArrayList<GetTarif>{
//        return Paper.book().read(key, arrayListOf())!!
//    }
    fun saveDispetcherNumber(phone:String){
        Paper.book().write(key1,phone)
    }
    fun getDispetcherNumber():String{
        return Paper.book().read(key1,"")!!
    }

    fun saveVisit(visit:Boolean) {
        Paper.book().write("visit", visit)
    }
    fun getVisit(): Boolean {
        return Paper.book().read("visit", false)!!
    }
   fun setListenAudio(data:Boolean){
       Paper.book().write("asdasdcsfwe",data)
   }
    fun getAudio():Boolean{
          return Paper.book().read<Boolean>("asdasdcsfwe",true)!!
    }

}
