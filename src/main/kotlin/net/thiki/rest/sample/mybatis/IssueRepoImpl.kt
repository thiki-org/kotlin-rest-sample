package net.thiki.rest.sample.mybatis

import net.thiki.rest.sample.biz.issue.Issue
import net.thiki.rest.sample.biz.issue.IssueMapper
import net.thiki.rest.sample.biz.issue.IssueRepo
import org.apache.ibatis.annotations.Arg
import org.apache.ibatis.annotations.ConstructorArgs
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Select
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class IssueRepoImpl(@Autowired private val mapper: IssueMapper): IssueMapper by mapper, IssueRepo {

    override fun searchDetailByReporter(reporterKey: String): List<Issue> {
        val mapperResult = mapper.searchByReporter(reporterKey)
        //do more jobs than mapper here

        return mapperResult
    }
}


@Mapper
interface IssueMapperImpl: IssueMapper {

    @Select("select * from `tms_issue`")
    override fun findAllIssue(): List<Issue>

    @Select("select * from `tms_issue` where reporter = (select id from tms_user where `key` = #{reporterKey})")
    override fun searchByReporter(reporterKey: String): List<Issue>

    @Select("select * from `tms_issue` where status = '10002'")
    override fun findOpenIssues(): List<Issue>

}