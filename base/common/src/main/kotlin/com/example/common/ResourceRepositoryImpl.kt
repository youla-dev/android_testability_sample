package com.example.common

import android.app.Application
import com.example.contract.domain.ResourceRepository
import javax.inject.Inject

/**
 * Again, in production code you should use context of your current activity
 * Cut it out for the sake of simplicity
 */
class ResourceRepositoryImpl @Inject constructor(private val app: Application) : ResourceRepository {

    override fun getString(id: Int, vararg formatArgs: Any) : String {
        return app.resources.getString(id, formatArgs)
    }

}