package dev.future.taxipark.ui.drawer.model.request

data class addCardRequest(
    var auth_key: String,
    var bank: String,
    var full_name: String,
    var card_number: String,


)