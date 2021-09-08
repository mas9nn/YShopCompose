package kz.yshop.ui.activity

import kz.yshop.data.remote.responses.Demo.UserDemo

data class MainActivityState(
    val isLoading:Boolean = false,
    val user: UserDemo? = null,
    val error:String = ""
)
