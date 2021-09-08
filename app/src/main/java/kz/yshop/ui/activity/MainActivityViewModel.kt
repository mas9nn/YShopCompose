package kz.yshop.ui.activity

import android.content.SharedPreferences
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kz.yshop.data.remote.Util.Resource
import kz.yshop.data.remote.responses.Demo.Data
import kz.yshop.data.remote.responses.Demo.UserDemo
import kz.yshop.data.remote.responses.MainPageProducts.Product
import kz.yshop.domain.repository.UserRepository
import kz.yshop.util.Constants
import timber.log.Timber
import java.util.*
import javax.inject.Inject


@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val sharedPreferences: SharedPreferences,
) : ViewModel() {


    private val _user = mutableStateOf(MainActivityState())
    val user: State<MainActivityState> = _user

    val scrollState = mutableStateOf(false)
    val openDrawer = mutableStateOf(false)


    val accentColor = mutableStateOf(Color.Black)
    val secondColor = mutableStateOf(Color.Black)

    var product: Product? = null

    init {
        val hash = sharedPreferences.getString(Constants.USER_HASH, "")
        if (hash.isNullOrEmpty()) {
            getUser()
        } else {
            Constants.userMainHash = hash
            _user.value = MainActivityState(user = UserDemo(success = true, data = Data(hash)))
        }
    }

    fun getUser() {
        viewModelScope.launch {
            when (val response = userRepository.getDemoUser("deviceToken${UUID.randomUUID()}")) {
                is Resource.Success -> {
                    Constants.userMainHash = response.data!!.data.hash
                    Timber.wtf(Constants.userMainHash)
                    sharedPreferences.edit().putString(Constants.USER_HASH, Constants.userMainHash)
                        .apply()
                    _user.value = MainActivityState(user = response.data)
                }
                is Resource.Loading -> {
                    _user.value = MainActivityState(isLoading = true)
                }
                else -> {
                    _user.value = MainActivityState(error = response.message ?: "Error occurred")
                }
            }
        }
    }
}