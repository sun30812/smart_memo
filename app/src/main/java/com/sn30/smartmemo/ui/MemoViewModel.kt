package com.sn30.smartmemo.ui

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.sn30.smartmemo.Memo
import com.sn30.smartmemo.MemoApp
import com.sn30.smartmemo.MemoList
import com.sn30.smartmemo.services.MemoRepository
import com.sn30.smartmemo.services.SettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

/**
 * 메모 목록을 보여주는 화면의 [ViewModel]이다.
 *
 * 메모 목록을 보여주는 화면에서의 동작을 담당하고 있다.
 *
 * @property memoRepository 메모 목록을 실제로 가져오는 저장소
 * @param settingsRepository 환경 설정을 실제로 가져오는 저장소
 * @constructor [MemoRepository]로부터 메모 목록을 가져와서 [MemoScreen]
 * 에 띄울 수 있도록 한다.
 */
class MemoViewModel(
    settingsRepository: SettingsRepository,
    private val memoRepository: MemoRepository
) : ViewModel() {
    private val _memoList = mutableStateListOf<Memo>()
    val memoList = _memoList
    val deleteConfirmFlow: Flow<Boolean> = settingsRepository.deleteConfirmFlow

    init {
        viewModelScope.launch {
            val temp = memoRepository.memoListFlow.first()
            temp.forEach {
                _memoList.add(it)
            }
        }
    }


    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val settingsRepository = (this[APPLICATION_KEY] as MemoApp).settingsRepository
                val memoRepository = (this[APPLICATION_KEY] as MemoApp).memoRepository
                MemoViewModel(settingsRepository, memoRepository)
            }
        }
    }

    /**
     * 메모 목록에서 메모를 추가한다.
     * @param memo 추가할 메모이다.
     */
    fun addMemo(memo: Memo) {
        _memoList.add(memo)
        viewModelScope.launch {
            saveMemo()
        }
    }

    /**
     * 메모 목록에서 메모를 제거한다.
     * @param memo 제거할 메모이다.
     */
    fun removeMemo(memo: Memo) {
        _memoList.remove(memo)
        viewModelScope.launch {
            saveMemo()
        }
    }

    /**
     * 현재 메모 목록을 저장한다.
     */
    private suspend fun saveMemo() {
        memoRepository.saveMemoList(
            MemoList.newBuilder()
                .addAllList(_memoList.asIterable())
                .build()
        )

    }
}