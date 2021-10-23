package com.example.hackerearthchallenge.data.models

data class DataResponse(
    val has_more: Boolean,
    val items: ArrayList<Item>,
    val quota_max: Int,
    val quota_remaining: Int
)