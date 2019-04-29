package io.goinhacker.spek2.spring

import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.test.context.ContextConfiguration

class Foo
class Bar(val foo: Foo)

@Configuration
open class MyConfiguration {

    @Bean("foo")
    open fun foo() = Foo()

    @Bean
    open fun bar(foo: Foo): Bar {
        return Bar(foo)
    }
}

@ContextConfiguration(classes = [MyConfiguration::class])
object SampleInjectSpec: Spek({
    val context = createContext(SampleInjectSpec::class)

    val foo = context.inject<Foo>()
    val bar = context.inject<Bar>()

    describe("sample test") {

        it("should be something") {
            println(foo)
            println(bar)
        }
    }
})