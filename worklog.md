
# work log

## TODO

* [ ] refactoring: segregate the Repository and Mapper
* [ ] include the user detail information of reporter and assignee into the ``issue`` entity response


# 2018-08-09

* [x] trying to use both xml and annotation in one mapper.

## idea: using Kotlin ``Delegation`` for ``Repository`` to wrap the mybatis mapper interface and add some factory jobs needed.

[Kotlin Delegation](https://kotlinlang.org/docs/reference/delegation.html)


## spring mvc @RequestParam usage

I follow [this sage](https://www.logicbig.com/tutorials/spring-framework/spring-web-mvc/spring-mvc-request-param.html)

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



