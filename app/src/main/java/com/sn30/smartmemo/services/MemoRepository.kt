package com.sn30.smartmemo.services

import androidx.datastore.core.DataStore
import com.sn30.smartmemo.MemoList
import kotlinx.coroutines.flow.map

/**
 * 메모 목록을 담당하는 저장소
 *
 * 메모 목록을 가져오거나 저장하는 역할은 담당한다.
 * @property dataStore 메모를 저장하는 DB에 해당된다.
 * @see DataStore
 */
class MemoRepository(private val dataStore: DataStore<MemoList>) {
    val memoListFlow = dataStore.data.map {
        it.listList
    }

    /**
     * 메모를 저장하는 메서드
     *
     * [newValue]에 해당되는 메모 목록을 DB에 저장한다.
     *
     * @param newValue 저장할 메모 목록이다.
     * @see MemoList
     */
    suspend fun saveMemoList(newValue: MemoList) {
        dataStore.updateData {
            newValue
        }
    }
}