package net.thiki.rest.sample.biz.issue

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/issues")
class IssueController(private val issueService: IssueService){

    @RequestMapping(method = [(RequestMethod.GET)])
    fun findAll(): Any {
        val result = issueService.findAll()
        return result
    }



//    @RequestMapping(method = [(RequestMethod.GET)])
//    fun findAll(): HttpEntity<GeneralResource>? {
//
//        return null
//    }

}