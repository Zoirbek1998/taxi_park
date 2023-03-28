package dev.future.taxipark.ui.registeration.model
import com.google.gson.annotations.SerializedName

data class AuthModel(

    @field:SerializedName("updated_at")
    val updatedAt: String? = null,

    @field:SerializedName("last_name")
    val lastName: String? = null,

    @field:SerializedName("created_at")
    val createdAt: String? = null,

    @field:SerializedName("id")
    val id: Int? = null,
    @field:SerializedName("status")
    val status: Int? =null,

    @field:SerializedName("auth_key")
    val authKey: String? = null,

    @field:SerializedName("first_name")
    val firstName: String? = null,

    @field:SerializedName("username")
    val username: String? = null,

    @field:SerializedName("father_name")
    val father_name: String? = null,

    @field:SerializedName("balance")
    val balance: Int? = null,

    @field:SerializedName("permission")
    val permisson: Permisson? = null,

    @field:SerializedName("block")
    val block: BlockUser? = null
)


//data class UserBlock( )
data class Permisson(
    @field:SerializedName("map")
    val map: Int? = null,
    val taximeter: Int? = null,
)
//"permission":{"map":0,"taximeter":0}