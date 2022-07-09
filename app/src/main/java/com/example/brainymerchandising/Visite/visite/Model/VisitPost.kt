package com.example.brainymerchandising.Visite.visite.Model

data class VisitPost(
    var id: Int?,
    var day : String?,
    var order : Int,
    var storeId: Int,
    var userId: Int,
    var planned: Boolean,
    var start :String?,
    var end : String?,
)