package com.example.brainymerchandising.Utils.resources.Server_date.ViewModel

 class Date_time_provider() {

    fun getDatee (link : String): String {
        return (link.substring(0,link.indexOf('T')))

    }
    fun getTime ( link : String): String {
        return (link.substring(link.indexOf('T')+1,link.indexOf('.')-1))
    }
}


