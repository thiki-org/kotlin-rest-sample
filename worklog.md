
# work log

## TODO

* [ ] add user Resource, GET
* [ ] add `self` ref to json response(HATEOAS)

## buckrake(low priority tasks)

* [ ] support db version control and migration among multiple type DBs(mysql and h2) automatically.

# 2018-08-17

* [x] change the wrong author of commits before
  - [use git rebase to change the author](https://gist.github.com/trey/9588090)
  - vim substitution command: ``:[range]s/pattern/string/[c,e,g,i]``



# 2018-08-17

* [x] use h2 db for integration unit test

## use h2 db for integration unit test

### digging

```
  java.lang.IllegalStateException: Failed to replace DataSource with an embedded database for tests. If you want an embedded database please put a supported one on the classpath or tune the replace attribute of @AutoConfigureTestDatabase.
```

[configure the data source for testing](https://www.baeldung.com/spring-testing-separate-data-source)

[Initialize a Database](https://docs.spring.io/spring-boot/docs/current/reference/html/howto-database-initialization.html#howto-initialize-a-database-using-spring-jdbc)

Default file name: schema-${platform}.sql and data-${platform}.sql.
Here ${platform} should be the value of the property "spring.datasource.platform", like hsqldb, h2, oracle, mysql, postgresql, and so on.

comments in ddl is invalid for h2.

### solution

* add the data source configurations
  - spring.datasource.initialization-mode: 'always'
  - spring.datasource.platform: 'h2'
  - add schema-h2.sql(DDL) and data-h2.sql(DML) in src/test/resources
* change the DDL and DML sql to be compatible to h2 DB
  - delete the `COMMENT` in `create table`
  - delete the `engine` and `default charset` setting in `create table`
* change the sqlmap(h2 do not support reporter.id in resultset)

  ```diff
  -            <idArg column="reporter.id" name="id"/>
  +            <idArg column="reporter_id" name="id"/>

   <sql id="userColumns">
  -        ${alias}.id,
  +        ${alias}.id as ${alias}_id,
   ```


# 2018-08-15


* [x] unit testing for sqlmap

## [Resolve POJO constructor arguments by name rather than position](https://github.com/mybatis/mybatis-3/issues/721)

> To reference constructor parameters by name, you can either 1) annotate parameters with @Param annotation or 2) compile with -parameters option.

1. add in build.gradle:

  ```
  tasks.withType(JavaCompile) {
      options.compilerArgs << '-parameters'
  }
  ```

2. add in IntelliJ IDEA:

  preference->Build,Execution,Deployment->Compiler->java Compiler:
  Add an item in "Override compiler parameters per-module", here is: `` service --> "-parameters"``


## gradle proxy settings

* problem

  I encounter the following message when using gradle:
  ```
   > Connect to d29vzk4ow07wi7.cloudfront.net:443 [d29vzk4ow07wi7.cloudfront.net/54.230.86.77, d29vzk4ow07wi7.cloudfront.net/54.230.86.206, d29vzk4ow07wi7.cloudfront.net/54.230.86.33, d29vzk4ow07wi7.cloudfront.net/54.230.86.64] failed: Read timed out
  ```

* solution

  I think I probably need a HTTP proxy to circumvent the network blocking in my area of the internet.

  See:

  [Accessing the web through a HTTP proxy](https://docs.gradle.org/current/userguide/build_environment.html#sec:accessing_the_web_via_a_proxy)

  and

  [Gradle properties](https://docs.gradle.org/current/userguide/build_environment.html#sec:gradle_configuration_properties)

  > The configuration is applied in following order (if an option is configured in multiple locations the last one wins):
  > * gradle.properties in project root directory.
  > * gradle.properties in GRADLE_USER_HOME directory.
  > * system properties, e.g. when -Dgradle.user.home is set on the command line.

  I added the proxy setting in ~/.gradle/gradle.properties.


## add log4j2 dependency for test

build.gradle:
```
test {
    useJUnitPlatform()
    systemProperty 'java.util.logging.manager', 'org.apache.logging.log4j.jul.LogManager'
}
dependencies {
    //...
    compile('org.apache.logging.log4j:log4j-jul:2.11.1')
}
```

## db version control:

[flyway: a database migration tool](https://flywaydb.org/)
vs
[liquibase](https://www.liquibase.org/index.html)

# 2018-08-14

* [x] include the user detail information of reporter and assignee into the ``issue`` entity response
  - [x] try to use constructor args to associate reporter
  - [x] make other mapper methods work.

## unit testing for sqlmap

* [Best Practices for Unit Testing in Kotlin](https://blog.philipphauer.de/best-practices-unit-testing-kotlin/)
* [Junit 5 configuration in gradle](https://junit.org/junit5/docs/current/user-guide/#running-tests-build-gradle)
* [AssertJ](http://joel-costigliola.github.io/assertj/index.html)
* [Mockk](https://mockk.io/) vs mokito?

### `@Configuration` needs `open class` and `open methods`

The blog "[spring-boot-configuration-in-kotlin](http://engineering.pivotal.io/post/spring-boot-configuration-in-kotlin/)" said:
> The Configuration class must be declared open. This is because Spring Boot subclasses your configuration class but Kotlin makes them final by default.

### JunitTestRunner have to do all things that `mybatis-spring-boot-autoconfigure` do

see [mybatis-spring-boot-autoconfigure](http://www.mybatis.org/spring-boot-starter/mybatis-spring-boot-autoconfigure/)

found [mybatis-spring-boot-test-autoconfigure/](http://www.mybatis.org/spring-boot-starter/mybatis-spring-boot-test-autoconfigure/)

> It's the essential complex of the mybatis configurations which can't be avoid. -- joeaniu


# 2018-08-13

## the implementation for 'association' in sqlmap

* solution A
  - ``<association/>`` element in sqlmap xml
  - using ``lateinit var xxx`` and property injection in Kotlin code

* solution B
  - ``<arg resultMap="XXX"></arg>`` in ``<constructor/>`` element in sqlmap xml
  - using constructor arg injection in Kotlin code

I prefer solution B because it can use `val` instead of `lateinit var` to make the "reporter" property immutable.

Remember "Don't talk to a stranger" Principle? If needed, I can even use `private val` to prevent client codes from calling ``issue.reporter.doA()``. But here, reporter's information should be known by client, so I just make it `val`.


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



