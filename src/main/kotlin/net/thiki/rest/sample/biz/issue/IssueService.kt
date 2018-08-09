package net.thiki.rest.sample.biz.issue

import org.springframework.stereotype.Service

@Service
class IssueService(private val issueRepo: IssueRepo){

    fun findAll(): List<Issue> {
        return issueRepo.findAllIssue()
    }

    fun findOpenIssues(): List<Issue> {
        return issueRepo.findOpenIssues()
    }

    fun searchByReporter(reporterKey: String): List<Issue> {
        return issueRepo.searchByReporter(reporterKey)

    }

}