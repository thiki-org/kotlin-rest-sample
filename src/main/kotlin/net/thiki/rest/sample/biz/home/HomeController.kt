package net.thiki.rest.sample.biz.home

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.support.ServletUriComponentsBuilder
import javax.servlet.http.HttpServletRequest

@RestController
@RequestMapping("/api/home")
class HomeController {

    @RequestMapping(value = "greeting", method = [(RequestMethod.GET)])
    fun greet() = "Hello world!"


    @RequestMapping(method = [(RequestMethod.GET)])
    fun home(req: HttpServletRequest): Any {

        return mapOf(
                "home" to buildUri(req, "/api/home"),
                "activities" to buildUri(req, "/api/activities")
        )
    }

    private fun buildUri(req: HttpServletRequest, path: String) =
            ServletUriComponentsBuilder.fromContextPath(req).path(path).build().toUri()


}