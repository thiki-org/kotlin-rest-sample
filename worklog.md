
# work log

## TODO

* [ ] unit testing for sqlmap


# 2018-08-14

* [x] include the user detail information of reporter and assignee into the ``issue`` entity response
  - [x] try to use constructor args to associate reporter
  - [x] make other mapper methods work.

# 2018-08-13

## the implementation for 'association' in sqlmap

* solution A
  - ``<association/>`` element in sqlmap xml
  - using ``lateinit var xxx`` and property injection in Kotlin code

* solution B
  - ``<arg resultMap="XXX"></arg>`` in ``<constructor/>`` element in sqlmap xml
  - using constructor arg injection in Kotlin code

I prefer solution B because it can use `val` instead of `lateinit var` to make the "reporter" property immutable.

Remember "Don't talk to a stranger" Principle? If needed, I can even use `private val` to prevent client codes from calling ``issue.reporter.doA()``. Of cause, reporter's information should be known by client, so I just make it `val`.


# 2018-08-09

* [x] trying to use both xml and annotation in one mapper.
* [x] refactoring: segregate the Repository and Mapper

## my best practice: mybatis annotation vs xml

* simple sql, use annotation
* resultMap and complex sql, especially joined queries, use xml

## my best practice: use constructor instead of using field getter and setter.

Java Bean specification is the worst thing to break the encapsulation. Some infrastructure like mybatis or protobuf needs Java Bean to get and set the data of an object, but if we can, avoid relying on the public getters and setters.

```xml
    <resultMap id="issueMap" type="net.thiki.rest.sample.biz.issue.Issue">
        <constructor>
            <idArg column="id" name="id"/>
            <arg column="key" name="key"/>
            <arg column="reporter" name="reporter"/>
            <arg column="summary" name="summary"/>
            <arg column="description" name="description"/>
            <arg column="assignee" name="assignee"/>
            <arg column="status" name="status"/>
        </constructor>

        <id column="id" property="id" jdbcType="BIGINT" javaType="long"/>
        <!--<result column="nick_name" property="nickName" jdbcType="VARCHAR" />-->
    </resultMap>
```

```Kotlin
    constructor(id: Long, key: String, reporter: Long, summary: String, description: String?, assignee: Long?, status: Long?): this(id, key, reporter, summary){
        this.description = description
        this.assignee = assignee
        this.status = status
    }

```

The codes above are worthy.


## idea: using Kotlin ``Delegation`` for ``Repository`` to wrap the mybatis mapper interface and add some factory jobs if needed.

[Kotlin Delegation](https://kotlinlang.org/docs/reference/delegation.html)

Theoretically, the Repository interface should extend Mapper **interface**, but mybatis use this interface(say IssueMapper) as the placeholder for implementation of sqlmap. As a result of that, the domain package 'net.thiki.rest.sample.biz.issue' will depend on the infrastructure package 'net.thiki.rest.sample.mybatis', which is unacceptable to me.

So, I write 2 interfaces in one file(IssueRepo.kt) in domain package, and 2 implementations in other file(IssueRepoImpl.kt) in infrastructure package. As a domain client calling the interfaces will not have to "know" the detail of implementations, which is in infrastructure package.

Now I can implement the Repository using Delegation "by Mapper".

```Kotlin
@Component
class IssueRepoImpl(@Autowired private val mapper: IssueMapper): IssueMapper by mapper, IssueRepo {

    override fun searchDetailByReporter(reporterKey: String): List<Issue> {
        val mapperResult = mapper.searchByReporter(reporterKey)
        //do more jobs than mapper here

        return mapperResult
    }
}
```

## spring mvc @RequestParam usage

I follow [this usage](https://www.logicbig.com/tutorials/spring-framework/spring-web-mvc/spring-mvc-request-param.html)

but it throw an IllegalStateException:
```
Optional long parameter 'status' is present but cannot be translated into a null value due to being declared as a primitive type. Consider declaring it as object wrapper for the corresponding primitive type.
```

then I check the Kotlin Ref [Null Safety](https://kotlinlang.org/docs/reference/null-safety.html) and [java interop](https://kotlinlang.org/docs/reference/java-interop.html).
and found the reason:
Kotlin's ``Long`` will be mapped to java's ``long`` and ``Long?`` well be mapped to ``Long``.
So, ``status: Long`` should change to ``status: Long?``.

It works.

## using spring-boot-starter
[spring boot mybatis sample, in Chinese](http://www.ityouknow.com/springboot/2016/11/06/spring-boo-mybatis.html)

## Using annotation of Mybatis

* Official suggestion of using annotations
[mybatis getting started](http://www.mybatis.org/mybatis-3/getting-started.html)

> Mapper classes are Java classes that contain SQL Mapping Annotations that avoid the need for XML. However, due to some limitations of Java Annotations and the complexity of some MyBatis mappings, XML mapping is still required for the most advanced mappings (e.g. Nested Join Mapping). For this reason, MyBatis will automatically look for and load a peer XML file if it exists (in this case, BlogMapper.xml would be loaded based on the classpath and name of BlogMapper.class). More on this later.

# 2018-08-08

* [x] search issue with reporter

## How jira JQL was implemented

[google it](https://stackoverflow.com/questions/4208558/did-atlassion-build-jira-query-language-jql-from-scratch)
* Using ANTLR for parsing the language
* tranlate to Lucene and database queries
* execute the queires



