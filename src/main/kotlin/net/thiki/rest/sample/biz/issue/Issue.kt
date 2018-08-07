package net.thiki.rest.sample.biz.issue

class Issue(val id: Long, val key: String, var reporter: Int, var summary: String) {

    var description: String? = null
    var assignee: Int? = 0
    var status: Int? = 0

    /**
     * constructor used by mybatis
     */
    constructor(id: Long, key: String, reporter: Int, summary: String, description: String?, assignee: Int?, status: Int?): this(id, key, reporter, summary){
        this.description = description
        this.assignee = assignee
        this.status = status
    }
}