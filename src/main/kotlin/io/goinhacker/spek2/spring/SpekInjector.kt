package io.goinhacker.spek2.spring

import org.spekframework.spek2.dsl.Root
import org.spekframework.spek2.lifecycle.LifecycleListener
import org.spekframework.spek2.lifecycle.TestScope
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.TestContext
import org.springframework.test.context.TestContextManager
import kotlin.reflect.KClass

class SpringContext internal constructor(testContextManager: TestContextManager, val root: Root) : LifecycleListener {

    val testContext: TestContext by lazy { testContextManager.testContext }

    inline fun <reified T : Any> inject(): T {
        val lazyInit by root.memoized { testContext.applicationContext.getBean(T::class.java) }
        return lazyInit
    }

    override fun afterExecuteTest(test: TestScope) = testContext.markApplicationContextDirty(DirtiesContext.HierarchyMode.EXHAUSTIVE)
}

fun Root.createContext(spec: KClass<*>): SpringContext = SpringContext(TestContextManager(spec.java), this@createContext)
    .apply { registerListener(this) }
