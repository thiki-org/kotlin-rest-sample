package net.thiki.rest.sample.mybatis

import net.thiki.rest.sample.DemoApplication4Test
import org.junit.Ignore
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.TestPropertySource
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ContextConfiguration(classes = [DemoApplication4Test::class])
@MybatisTest
@Ignore
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//@TestPropertySource(locations = ["classpath:application.yaml"])
class IssueMapperImplTest {

    @Autowired lateinit var cut: IssueMapperImpl

    @Test
    fun testIT(){
        val a = "hello test!"
        assertNotNull(a, "not null!")
        assertNotNull(cut, "not null!")
    }

    @Test
    fun testSearchByReporter_found(){
        val issues = cut.searchByReporter("lisi")
        assertEquals(1, issues.size)
    }

    @Test
    fun testSearchByReporter_notFound(){
        val issues = cut.searchByReporter("unkonwn")
        assertEquals(0, issues.size)
    }
}