package com.example.contract.domain

interface ResourceRepository {
    fun getString(id: Int, vararg formatArgs: Any) : String
    fun getString(id: Int) : String
}