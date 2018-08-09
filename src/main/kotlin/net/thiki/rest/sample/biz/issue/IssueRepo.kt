package net.thiki.rest.sample.biz.issue

/**
 * any jobs that mybatis mapper can do.
 */
interface IssueMapper{

    fun findAllIssue(): List<Issue>

    fun searchByReporter(reporterKey: String): List<Issue>

    fun findOpenIssues(): List<Issue>
}

interface IssueRepo :IssueMapper{

    /**
     * do more jobs than mapper
     */
    fun searchDetailByReporter(reporterKey: String): List<Issue>
}

