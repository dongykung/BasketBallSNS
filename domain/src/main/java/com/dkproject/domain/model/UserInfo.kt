package com.dkproject.domain.model

data class UserInfo(
    val useruid:String,
    val nickname: String,
    val profileImageUrl: String,
    val playPosition:List<String>,
    val playStyle:List<String>,
    val userSkill:String,
)