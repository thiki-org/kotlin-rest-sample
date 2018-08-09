package net.thiki.rest.sample.biz.issue

import org.apache.ibatis.annotations.Arg
import org.apache.ibatis.annotations.ConstructorArgs
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Select

@Mapper
interface IssueRepo{

    @Select("select * from `tms_issue`")
    @ConstructorArgs(
            Arg(column = "id", name = "id"),
            Arg(column = "key", name = "key"),
            Arg(column = "reporter", name = "reporter"),
            Arg(column = "summary", name = "summary"),
            Arg(column = "description", name = "description"),
            Arg(column = "assignee", name = "assignee"),
            Arg(column = "status", name = "status")
    )
    fun findAllIssue(): List<Issue>

    @Select("select * from `tms_issue` where reporter = (select id from tms_user where `key` = #{reporterKey})")
    fun searchByReporter(reporterKey: String): List<Issue>

}