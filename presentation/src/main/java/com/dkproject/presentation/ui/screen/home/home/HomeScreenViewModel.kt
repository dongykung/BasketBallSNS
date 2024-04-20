package com.dkproject.presentation.ui.screen.home.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.dkproject.domain.model.home.Guest
import com.dkproject.domain.usecase.home.GetGuestUseCase
import com.dkproject.domain.usecase.home.GetNearGuestUseCase
import com.dkproject.presentation.model.ShopUiModel
import com.dkproject.presentation.util.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val getGuestUseCase: GetGuestUseCase,
    private val getNearGuestUseCase: GetNearGuestUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow(HomeUiState("모두보기", 0, false, emptyFlow()))
    val state: StateFlow<HomeUiState> = _state
    var dateText by mutableStateOf("날짜")

    fun updatedateText(text:String){
        dateText = text
    }

    fun load() {
        viewModelScope.launch {
            var homeModelFlow: Flow<PagingData<GuestUiModel>> =
                getGuestUseCase(state.value.position, state.value.date).map { pagingData ->
                    pagingData.map { guest ->
                        guest.toUiModel()
                    }
                }.cachedIn(viewModelScope)
            _state.update { it.copy(itemList = homeModelFlow) }
        }
    }

    fun updateReset(){
        _state.update { it.copy("모두보기", 0, false, emptyFlow()) }
        dateText="날짜"
        load()
    }

    fun nearload() {
        viewModelScope.launch {
            var homeNearModelFlow: Flow<PagingData<GuestUiModel>> =
                getNearGuestUseCase(state.value.position, state.value.date).map { pagingData ->
                    pagingData.map {
                        it.toUiModel()
                    }
                }.cachedIn(viewModelScope)
            _state.update { it.copy(itemList = homeNearModelFlow) }
        }
    }

    fun updatePosition(position: String) {
        _state.update { it.copy(position = position) }
        if (state.value.distacne)
            nearload()
        else
            load()
    }

    fun updateDate(date: Long) {
        _state.update { it.copy(date = date) }
        if (state.value.distacne)
            nearload()
        else
            load()
    }

    fun updateDistacne(distacne: Boolean) {
        _state.update { it.copy(distacne = distacne) }
        if (state.value.distacne)
            nearload()
        else
            load()
    }

    fun updateData() {
        if (state.value.distacne)
            nearload()
        else
            load()
    }

}

data class HomeUiState(
    val position: String,
    val date: Long,
    val distacne: Boolean,
    val itemList: Flow<PagingData<GuestUiModel>>
)

data class GuestUiModel(
    val writeUid: String,
    val uid: String,
    val title: String,
    val positionList: List<String>,
    val count: Int,
    val detailAddress: String,
    val date: Long,
    val guestSize: Int,
)


fun Guest.toUiModel(): GuestUiModel {
    return GuestUiModel(
        writeUid = writeUid,
        uid = uid,
        title = title,
        positionList = positionList,
        count = count,
        detailAddress = detailAddress,
        date = detaildate,
        guestSize = guestsUid.size
    )
}


