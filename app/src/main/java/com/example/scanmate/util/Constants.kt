package com.example.scanmate.util

object Constants {

    object Logs{
        const val vmSuccess = "ViewModelSuccess"
        const val vmLoading = "ViewModelLoading"
        const val vmError = "ViewModelError"
    }

    object LogMessages{

        const val responseFound = "Response Found"
        const val responseFailed = "Response Failed"
        const val loading = "Response Loading"
        const val success = "Observer Success"
        const val error = "Observer Error"
        const val BUSLOC_OBSERVER = "BUSLOC_OBSERVER"
        const val WRH_OBSERVER = "WRH_OBSERVER"
        const val RACK_OBSERVER = "RACK_OBSERVER"
        const val SHELF_OBSERVER = "SHELF_OBSERVER"
        const val PALLET_OBSERVER = "PALLET_OBSERVER"

    }

    object WMSStructure{
        const val warehouse = "warehouse"
        const val racks = "racks"
        const val shelf = "shelf"
        const val pallets = "pallets"
    }

}