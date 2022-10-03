package com.example.brainymerchandising.Visite.suivie.model.display

import com.example.brainymerchandising.Visite.suivie.model.User
import com.google.gson.annotations.SerializedName

data class Displays (

    @SerializedName("id"                       ) var id                       : Int?                                = null,
    @SerializedName("createdAt"                ) var createdAt                : String?                             = null,
    @SerializedName("updatedAt"                ) var updatedAt                : String?                             = null,
    @SerializedName("visitId"                  ) var visitId                  : Int?                                = null,
    @SerializedName("categoryId"               ) var categoryId               : String?                             = null,
    @SerializedName("brandId"                  ) var brandId                  : String?                             = null,
    @SerializedName("displayTypeId"            ) var displayTypeId            : Int?                                = null,
    @SerializedName("storeId"                  ) var storeId                  : Int?                                = null,
    @SerializedName("userId"                   ) var userId                   : Int?                                = null,
    @SerializedName("displayData"              ) var displayData              : ArrayList<String>                   = arrayListOf(),
    @SerializedName("user"                     ) var user                     : User?                               ,
    @SerializedName("category"                 ) var category                 : String?                             = null,
    @SerializedName("brand"                    ) var brand                    : String?                             = null,
    @SerializedName("displayCustomFieldValues" ) var displayCustomFieldValues : ArrayList<DisplayCustomFieldValues> = arrayListOf(),
    @SerializedName("displayType"              ) var displayType              : DisplayType?                        = DisplayType()

)