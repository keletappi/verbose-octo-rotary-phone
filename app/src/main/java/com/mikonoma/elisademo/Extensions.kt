package com.mikonoma.elisademo

fun ByteArray.asUTF8String() = String(this, Charsets.UTF_8)