package net.thiki.rest.sample.biz.issue

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/issue")
class IssueController(private val issueService: IssueService){

    @RequestMapping(method = [(RequestMethod.GET)])
    fun findAll(): Any {
        val result = issueService.findAll()
        return result
    }


    @RequestMapping(value = "/search", method = [(RequestMethod.GET)])
    fun search(@RequestParam("reporter") reporterKey: String): List<Issue>{

        return issueService.searchByReporter(reporterKey)
    }

//    @RequestMapping(method = [(RequestMethod.GET)])
//    fun findAll(): HttpEntity<GeneralResource>? {
//
//        return null
//    }

}