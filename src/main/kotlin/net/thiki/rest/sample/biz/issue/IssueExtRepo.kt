package net.thiki.rest.sample.biz.issue

import org.apache.ibatis.annotations.Mapper

@Mapper
interface IssueExtRepo{

    fun findOpenIssues(): List<Issue>
}