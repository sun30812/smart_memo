package com.sn30.smartmemo.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.sn30.smartmemo.Memo

class MemoCardPreviewProvider: PreviewParameterProvider<Memo> {
    override val values: Sequence<Memo>
        get() = sequenceOf(
            Memo.newBuilder()
                .setTitle("메모1")
                .setDetail("메모 세부내용1")
                .build(),
            Memo.newBuilder()
                .setTitle("메모2")
                .setDetail("메모 세부내용2")
                .build()

        )

}