package net.thiki.rest.sample.biz.issue

class Issue(val id: Long, val key: String, var reporter: Long, var summary: String) {

    var description: String? = null
    var assignee: Long? = 0
    var status: Long? = 0

    /**
     * constructor used by mybatis
     */
    constructor(id: Long, key: String, reporter: Long, summary: String, description: String?, assignee: Long?, status: Long?): this(id, key, reporter, summary){
        this.description = description
        this.assignee = assignee
        this.status = status
    }
}