package net.thiki.rest.sample

import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.FilterType

@EnableAutoConfiguration
@ComponentScan(
//        basePackages = ["net.thiki.rest.sample"],
        excludeFilters = [ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = [DemoApplication::class])]
)


open class DemoApplication4Test {
    companion object {
        @JvmStatic fun main(args: Array<String>) {
            runApplication<DemoApplication4Test>(*args)
        }
    }
}