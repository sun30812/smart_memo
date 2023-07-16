package com.sn30.smartmemo.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sn30.smartmemo.R

/**
 * 설정화면에 해당된다.
 *
 * 환경 설정에 해당되는 화면이다.
 * @param viewModel 설정 화면의 `ViewModel`. 따로 기입할 필요 없음.
 * @see SettingViewModel
 * @see BooleanSettingTile
 */
@Composable
fun SettingScreen(viewModel: SettingViewModel = viewModel(factory = SettingViewModel.Factory)) {
    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        BooleanSettingTile(
            title = stringResource(id = R.string.quick_delete),
            detail = stringResource(id = R.string.quick_delete_desc),
            isChecked = viewModel.deleteConfirmFlow.collectAsState(initial = false).value,
            onCheckedChanged = { viewModel.updateDeleteConfirmSetting(it) }
        )
    }

}

/**
 * [Switch]로 변경하는 설정 타일
 *
 * 설정 화면에 사용되는 [Switch]가 포함된 환경설정 타일이다.
 *
 * @param title 설정 타일의 제목
 * @param detail 설정 타일에 대한 설명
 * @param isChecked [Switch]가 On인지에 대한 여부
 * @param onCheckedChanged [Switch]가 조작될 시 수행할 동작
 */
@Composable
fun BooleanSettingTile(
    title: String, detail: String, isChecked: Boolean,
    onCheckedChanged: (Boolean) -> Unit
) {
    Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier
        .fillMaxWidth()
        .padding(12.dp)) {
        Column {
            Text(text = title, fontWeight = FontWeight.Bold, fontSize = 28.sp)
            Text(text = detail)
        }
        Switch(checked = isChecked, onCheckedChange = { onCheckedChanged(it) })
    }
}

/**
 * [Switch]로 변경하는 설정 타일의 미리보기
 */
@Composable
@Preview(showBackground = true)
fun BooleanSettingTilePreview() {
    var temp by remember {
        mutableStateOf(false)
    }
    BooleanSettingTile(title = "Test Tile", detail = "Test for Setting Tile", isChecked = temp, onCheckedChanged = {
        temp = it
    })
}

/**
 * 설정 화면의 미리보기
 */
@Composable
@Preview(showBackground = true)
fun SettingScreenPreview() {
    var temp by remember {
        mutableStateOf(false)
    }
    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        BooleanSettingTile(title = "Test Tile", detail = "Test for Setting Tile", isChecked = temp, onCheckedChanged = {
            temp = it
        })    }
}

