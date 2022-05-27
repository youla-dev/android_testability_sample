package com.example.di

import com.example.common.ResourceRepositoryImpl
import com.example.contract.domain.ResourceRepository
import dagger.Binds
import dagger.Module

@Module
interface CommonBinds {
    @Binds
    fun bindResourceProvider(impl: ResourceRepositoryImpl): ResourceRepository
}