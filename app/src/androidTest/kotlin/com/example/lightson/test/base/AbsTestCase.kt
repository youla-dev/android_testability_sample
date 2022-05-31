package com.example.lightson.test.base

import com.kaspersky.components.alluresupport.interceptors.step.AllureMapperStepInterceptor
import com.kaspersky.kaspresso.enricher.MainSectionEnricher
import com.kaspersky.kaspresso.kaspresso.Kaspresso
import com.kaspersky.kaspresso.testcases.api.testcase.BaseTestCase
import org.junit.Before

abstract class AbsTestCase<InitData, Data>(
    kaspressoBuilderAdditional: (Kaspresso.Builder.() -> Unit)? = null,
    dataProducer: (((InitData.() -> Unit)?) -> Data),
    mainSectionEnrichers: List<MainSectionEnricher<Data>> = emptyList()
) : BaseTestCase<InitData, Data>(
    kaspressoBuilder = Kaspresso.Builder.simple().apply {
        kaspressoBuilderAdditional?.invoke(this)
        stepWatcherInterceptors.add(AllureMapperStepInterceptor())
    },
    dataProducer = dataProducer,
    mainSectionEnrichers = mainSectionEnrichers
) {

    abstract fun inject()

    @Before
    fun before() {
        inject()
    }

}