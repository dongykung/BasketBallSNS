package com.dkproject.data.model

import com.dkproject.domain.model.UserInfo

data class UserDTO(
    val useruid:String="",
    val nickname: String="",
    val profileImageUrl: String="",
    val playPosition:List<String> = emptyList(),
    val playStyle:List<String> = emptyList(),
    val userSkill:String = "",
){
    fun toDomainModel():UserInfo{
        return UserInfo(
            useruid=useruid,
         nickname=nickname,
         profileImageUrl=profileImageUrl,
         playPosition=playPosition,
         playStyle=playStyle,
         userSkill=userSkill
        )
    }
}