package net.thiki.rest.sample.biz.issue

import org.springframework.stereotype.Service

@Service
class IssueService(private val issueRepo: IssueRepo, private val issueExtRepo: IssueExtRepo){

    fun findAll(): List<Issue> {
        return issueRepo.findAllIssue()
    }

    fun findOpenIssues(): List<Issue> {
        return issueExtRepo.findOpenIssues()
    }

    fun searchByReporter(reporterKey: String): List<Issue> {
        return issueRepo.searchByReporter(reporterKey)

    }

}