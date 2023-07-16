package com.sn30.smartmemo.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sn30.smartmemo.Memo
import com.sn30.smartmemo.R
import com.sn30.smartmemo.preview.MemoCardPreviewProvider

/**
 * 메모 목록 화면에 해당된다.
 *
 * @param viewModel 메모 목록을 표시하는 화면의 `ViewModel`.
 * 따로 기입할 필요 없음.
 *
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MemoScreen(viewModel: MemoViewModel = viewModel(factory = MemoViewModel.Factory)) {
    var showDialog by remember { mutableStateOf(false) }
    Scaffold(floatingActionButton = {
        FloatingActionButton(onClick = { showDialog = showDialog.not() }) {
            Icon(
                imageVector = Icons.Outlined.Add,
                contentDescription = stringResource(id = R.string.add_memo)
            )
        }
    }) {
        if (showDialog) {
            AddMemoDialog(
                onAdd = { viewModel.addMemo(it) },
                onDismiss = { showDialog = false }
            )
        }
        if (viewModel.memoList.isEmpty()) {
            EmptyMemoScreen(it)

        } else {
            LazyColumn {
                items(viewModel.memoList) { memo ->
                    MemoCard(
                        memo = memo,
                        onTapMemo = {
                            viewModel.removeMemo(memo)
                        },
                        deleteConfirm = viewModel.deleteConfirmFlow.collectAsState(initial = false).value
                    )
                }
            }
        }
    }
}

/**
 * 빈 메모 화면
 *
 * 메모 목록이 비어있을 시 나타나는 화면이다.
 * @param it 여백값
 * @see Scaffold
 */
@Composable
private fun EmptyMemoScreen(it: PaddingValues) {
    Column(
        modifier = Modifier
            .padding(it)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Outlined.Warning,
            contentDescription = stringResource(id = R.string.empty_memo),
            tint = Color.Red
        )
        Text(text = stringResource(id = R.string.empty_memo))
    }
}

/**
 * 메모 카드
 *
 * 메모에 대한 정보를 가진 카드
 *
 * @param memo 정보를 출력할 [Memo]
 * @param onTapMemo 메모 클릭 시 수행할 동작
 * @param deleteConfirm 메모를 삭제할 시 수행할 동작
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MemoCard(memo: Memo, onTapMemo: ((Memo) -> Unit)?, deleteConfirm: Boolean) {
    var deleteDialogShow by remember {
        mutableStateOf(false)
    }
    if (deleteDialogShow) {
        RemoveMemoDialog(onRemove = {
            if (onTapMemo != null) {
                onTapMemo(memo)
            }

        }, onDismiss = { deleteDialogShow = false })
    }
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp),
        onClick = {
            if (deleteConfirm) {
                if (onTapMemo != null) {
                    onTapMemo(memo)
                }
            } else {
                deleteDialogShow = true
            }
        }
    ) {
        Text(
            text = memo.title,
            fontSize = 33.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(5.dp)
        )
        Text(text = memo.detail, modifier = Modifier.padding(5.dp))
    }
}

/**
 * 메모 삭제 알림창
 *
 * 메모를 삭제할 때 나타나는 알림창이다.
 *
 * @param onRemove 메모를 지울 시 수행할 동작
 * @param onDismiss 취소버튼 누를 시 수행할 동작
 */
@Composable
fun RemoveMemoDialog(onRemove: (() -> Unit)?, onDismiss: () -> Unit) {
    AlertDialog(
        title = { Text(text = stringResource(id = R.string.remove_memo)) },
        text = { Text(text = stringResource(id = R.string.require_remove_memo)) },
        onDismissRequest = { onDismiss() }, confirmButton = {
            TextButton(onClick = {
                if (onRemove != null) {
                    onRemove()
                }
                onDismiss()
            }) {
                Text(text = stringResource(id = R.string.remove))
            }

        }, dismissButton = {
            TextButton(onClick = { onDismiss() }) {
                Text(text = stringResource(id = R.string.cancel))
            }
        })
}

/**
 * 메모 추가 알림창
 *
 * 메모를 작성할 때 나타나는 알림창이다.
 *
 * @param onAdd 메모 추가 버튼을 누를 시 수행할 동작
 * @param onDismiss 취소 버튼 누를 시 수행할 동작
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddMemoDialog(onAdd: ((Memo) -> Unit)?, onDismiss: () -> Unit) {
    var title by remember {
        mutableStateOf("")
    }
    var detail by remember {
        mutableStateOf("")
    }
    AlertDialog(
        title = { Text(text = stringResource(id = R.string.add_memo)) },
        text = {
            Column {
                TextField(
                    modifier = Modifier.padding(8.dp),
                    label = { Text(text = stringResource(id = R.string.memo_title)) },
                    value = title,
                    onValueChange = { title = it })
                TextField(
                    modifier = Modifier.padding(8.dp),
                    label = { Text(text = stringResource(id = R.string.memo_detail)) },
                    value = detail,
                    onValueChange = { detail = it })
            }
        },
        onDismissRequest = { onDismiss() }, dismissButton = {
            TextButton(onClick = { onDismiss() }) {
                Text(text = stringResource(id = R.string.cancel))
            }
        }, confirmButton = {
            val newMemo = Memo.newBuilder()
            newMemo.title = title
            newMemo.detail = detail
            TextButton(onClick = {
                if (onAdd != null) {
                    onAdd(newMemo.build())
                    onDismiss()
                }
            }) {
                Text(text = stringResource(id = R.string.add))
            }
        })

}

@Composable
@Preview
fun AddMemoDialogPreview() {
    AddMemoDialog(onAdd = null, onDismiss = {})
}

@Composable
@Preview
fun RemoveMemoDialogPreview() {
    RemoveMemoDialog(onRemove = null, onDismiss = {})
}

@Composable
@Preview(showBackground = true)
fun BlankMemoScreenPreview(
) {
    EmptyMemoScreen(PaddingValues(0.dp))
}

@Composable
@Preview(showBackground = true)
fun MemoCardPreview(
    @PreviewParameter(MemoCardPreviewProvider::class) memo: Memo
) {
    MemoCard(memo, onTapMemo = null, deleteConfirm = false)
}