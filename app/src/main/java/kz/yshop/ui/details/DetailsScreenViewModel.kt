package kz.yshop.ui.details

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kz.yshop.data.remote.Util.Resource
import kz.yshop.data.remote.responses.MainPageProducts.Product
import kz.yshop.domain.repository.ProductRepository
import kz.yshop.util.Constants
import javax.inject.Inject


@HiltViewModel
class DetailsScreenViewModel @Inject constructor(
    private val productRepository: ProductRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    init {
        savedStateHandle.get<String>(Constants.PARAM_PRODUCT_ID)?.let { product ->
            getProductDetail(product)
        }
    }

    private val _detailState = mutableStateOf(DetailsScreenState())
    val detailState: State<DetailsScreenState> = _detailState

    fun getProductDetail(id: String) {
        viewModelScope.launch {
            when (val response = productRepository.getProductDetail(id)) {
                is Resource.Success<*> -> {
                    _detailState.value = DetailsScreenState(productDetail = response.data)
                }
                is Resource.Loading -> {

                }
                else -> {
                    _detailState.value =
                        DetailsScreenState(error = response.message ?: "Error occurred")
                }
            }
        }
    }

}