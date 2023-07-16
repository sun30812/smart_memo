package com.sn30.smartmemo

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.google.protobuf.InvalidProtocolBufferException
import java.io.InputStream
import java.io.OutputStream


object MemoListSerializer : Serializer<MemoList> {

    override val defaultValue: MemoList = MemoList.getDefaultInstance()


    override suspend fun readFrom(input: InputStream): MemoList {
        try {
            return MemoList.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Can not read", exception)
        }
    }

    override suspend fun writeTo(t: MemoList, output: OutputStream) = t.writeTo(output)
}