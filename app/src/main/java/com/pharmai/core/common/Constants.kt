package com.pharmai.core.common

object Constants {

    //Api endpoints
    const val  BASE_URL = "https://api.fda.gov/"

    const val  RXNAV_BASE_URL = "https://rxnav.nlm.nih.gov/REST/"

    //Database
    const val DATABASE_NAME = "pharmai_database"
    const val DRUG_TABLE = "drugs"
    const val SEARCH_HISTORY_TABLE = "search_history"
    const val FAVORITES_TABLE = "favorites"

    //API Timeouts
    const val CONNECT_TIMEOUT = 30L
    const val READ_TIMEOUT = 30L
    const val WRITE_TIMEOUT = 30L

    //Pagination
    const val PAGE_SIZE = 20

    //cache
    const val CACHE_SIZE = 10 * 1024 * 1024L // 10 mb'
    const val CACHE_MAX_AGE = 60
    const val CACHE_MAX_STALE = 60 * 60 * 24 * 7

    // OpenFDA Query limits
    const val MAX_SEARCH_RESULTS = 50
}