package com.dkproject.data.model

import com.dkproject.domain.model.UserInfo

data class UserDTO(
    val userid:String,
    val username:String,
    val profileImageUrl:String,
    val first:Boolean
){

}