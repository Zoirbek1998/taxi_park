package dev.future.taxipark.utils.sharedPref


import dev.future.taxipark.ui.registeration.model.AuthModel
import dev.future.taxipark.ui.registeration.model.RegisterTokenSave
import dev.future.taxipark.ui.registeration.model.Status
import io.paperdb.Paper

object SaveUserInformation {
    val key ="sfekjg"
    val tokenkey ="wew232wjkh2dd"


    fun savePassword(password:String){
        Paper.book().write(key,password)
    }
    fun getPassword():String{
        return Paper.book().read<String>(key, "").toString()
    }

    fun saveAuthInfo(token: AuthModel){
      Paper.book().write(tokenkey,token)
    }

    fun getAuthInfo(): AuthModel {
        return Paper.book().read<AuthModel>(tokenkey,AuthModel("","","",0, 0,"","","","",0))!!
    }
    fun delete(){
        Paper.book().delete(tokenkey)
    }

    fun deletePassword(){
        Paper.book().delete(key)
    }


}