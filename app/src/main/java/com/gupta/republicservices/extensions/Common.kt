package com.gupta.republicservices.extensions

fun String.extractLastName(): String {
    return this.split(" ").last()
}