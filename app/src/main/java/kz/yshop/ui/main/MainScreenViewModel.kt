package kz.yshop.ui.main

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kz.yshop.data.remote.Util.Resource
import kz.yshop.data.remote.responses.MainPageProducts.MainPageProducts
import kz.yshop.data.remote.responses.MainPageProducts.Product
import kz.yshop.data.remote.responses.ProductDetail.ProductDetail
import kz.yshop.domain.repository.ProductRepository
import kz.yshop.domain.repository.ShopRepository
import timber.log.Timber
import java.util.*
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val repository: ShopRepository,
    private val productRepository: ProductRepository
) : ViewModel() {

    init {
        getShop()
    }

    private val _shopInfo = mutableStateOf(MainScreenState())
    val shopInfo: State<MainScreenState> = _shopInfo

    private fun getShop() {
        viewModelScope.launch {
            when (val response = repository.getShop()) {
                is Resource.Success -> {
                    _shopInfo.value = MainScreenState(shop = response.data)
                    getMainPage()
                }
                is Resource.Loading -> {
                    //  _shopInfo.value = MainScreenState(isLoading = true)
                }
                else -> {
                    _shopInfo.value = MainScreenState(error = response.message ?: "Error occurred")
                }
            }
        }
    }

    fun getMainPage() {
        viewModelScope.launch {
            when (val response = productRepository.getMainPageProducts()) {
                is Resource.Success -> {
                    val oldData = _shopInfo.value
                    _shopInfo.value = MainScreenState(shop = oldData.shop, products = response.data)
                }
                is Resource.Loading -> {
                    // _shopInfo.value = MainScreenState(isLoading = true)
                }
                else -> {
                    _shopInfo.value = MainScreenState(error = response.message ?: "Error occurred")
                }
            }
        }
    }


}