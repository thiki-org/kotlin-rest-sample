package net.thiki.rest.sample.biz.issue

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/issue")
class IssueController(private val issueService: IssueService){

    @RequestMapping(method = [(RequestMethod.GET)])
    fun findAll(
            @RequestParam(value = "status", required = false) status: Long?
    ): Any {
        return if (status == null){
            issueService.findAll()
        }else{
            issueService.findIssuesByStatus(status)
        }
    }


    @RequestMapping(value = "/search", method = [(RequestMethod.GET)])
    fun search(@RequestParam("reporter") reporterKey: String): List<Issue>{

        return issueService.searchDetailByReporter(reporterKey)
    }

}