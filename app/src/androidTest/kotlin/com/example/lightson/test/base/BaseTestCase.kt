package com.example.lightson.test.base

import com.kaspersky.kaspresso.kaspresso.Kaspresso

abstract class BaseTestCase(
    kaspressoBuilderAdditional: (Kaspresso.Builder.() -> Unit)? = null
) : AbsTestCase<Unit, Unit>(
    kaspressoBuilderAdditional = kaspressoBuilderAdditional,
    dataProducer = { action -> action?.invoke(Unit) }
)