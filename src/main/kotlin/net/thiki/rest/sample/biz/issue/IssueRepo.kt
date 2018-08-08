package net.thiki.rest.sample.biz.issue

import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Select

@Mapper
interface IssueRepo{

    @Select("select * from `tms_issue`")
    fun findAllIssue(): List<Issue>

    @Select("select * from `tms_issue` where reporter = (select id from tms_user where `key` = #{reporterKey})")
    fun searchByReporter(reporterKey: String): List<Issue>

}