package com.sn30.smartmemo.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.sn30.smartmemo.MemoApp
import com.sn30.smartmemo.services.SettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

/**
 * 설정 화면에 관련된 [ViewModel]이다.
 *
 * 설정 화면에서의 동작을 담당하고 있다.
 *
 * @property settingsRepository 환경 설정을 실제로 가져오는 저장소
 * @constructor [SettingsRepository]로부터 환경 설정을 가져오고 설정을 변경할
 * 수 있도록 한다.
 */
class SettingViewModel(private val settingsRepository: SettingsRepository) : ViewModel() {
    companion object {
        /**
         * [SettingViewModel]용 [Factory]
         *
         * [SettingViewModel]에서 사용하는 [SettingsRepository]를 위한 [Factory]
         *
         * @see SettingsRepository
         */
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val repository = (this[APPLICATION_KEY] as MemoApp).settingsRepository
                SettingViewModel(
                    repository
                )
            }
        }
    }

    val deleteConfirmFlow: Flow<Boolean> = settingsRepository.deleteConfirmFlow

    /**
     * 메모 바로 관련 설정을 업데이트 메서드
     *
     * 메모를 탭 할시 바로 삭제하는지에 대한 설정을 변경할 때 사용되는 메서드
     *
     * @param newValue 해당 설정에 대한 적용할 값
     */
    fun updateDeleteConfirmSetting(newValue: Boolean) {
        viewModelScope.launch {
            settingsRepository.updateDeleteConfirmSetting(newValue)
        }
    }

}