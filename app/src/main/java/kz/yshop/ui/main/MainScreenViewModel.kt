package kz.yshop.ui.main

import android.content.SharedPreferences
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kz.yshop.data.remote.Util.Resource
import kz.yshop.data.remote.responses.ShopAbout.Shop
import kz.yshop.repository.ShopRepository
import kz.yshop.ui.util.Error
import kz.yshop.util.Constants.USER_HASH
import kz.yshop.util.Constants.userMainHash
import timber.log.Timber
import java.util.*
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val repository: ShopRepository,
    private val sharedPreferences: SharedPreferences,
) : ViewModel() {

    init {
        val hash = sharedPreferences.getString(USER_HASH, "")
        if (hash.isNullOrEmpty()) {
            getUser()
        } else {
            userMainHash = hash
            Timber.wtf(userMainHash)
            getShop()
        }
    }

    val error: MutableLiveData<() -> Unit> = MutableLiveData()

    var shopInfo = mutableStateOf<Shop?>(null)


    fun getUser() {
        viewModelScope.launch {
            when (val response = repository.getDemoUser("deviceToken${UUID.randomUUID()}")) {
                is Resource.Success -> {
                    userMainHash = response.data!!.data.hash
                    Timber.wtf(userMainHash)
                    sharedPreferences.edit().putString(USER_HASH, userMainHash).apply()
                    getShop()
                }
                else -> {
                    //error.postValue(Error.UserDemo.responseName)
                }
            }
        }
    }

    fun getShop() {
        viewModelScope.launch {
            when (val response = repository.getShop()) {
                is Resource.Success -> {
                    shopInfo.value = response.data
                }
                else -> {
                    Timber.wtf("error")
                    error.postValue { getShop() }
                }
            }
        }
    }
}