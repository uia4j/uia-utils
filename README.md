UIA Utils
================

[![Download](https://api.bintray.com/packages/gazer2kanlin/maven/uia.utils4j/images/download.svg) ](https://bintray.com/gazer2kanlin/maven/uia.utils4j/_latestVersion)
[![Build Status](https://travis-ci.org/gazer2kanlin/uia.utils4j.svg?branch=master)](https://travis-ci.org/gazer2kanlin/uia.utils4j)
[![Codecov](https://img.shields.io/codecov/c/github/gazer2kanlin/uia.utils4j.svg)](https://codecov.io/gh/gazer2kanlin/uia.utils4j)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/d7210af12c1c4d4f8bbc3e84f0ad4a71)](https://www.codacy.com/app/gazer2kanlin/uia-utils4j?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=gazer2kanlin/uia.utils4j&amp;utm_campaign=Badge_Grade)
[![License](https://img.shields.io/github/license/gazer2kanlin/uia.utils4j.svg)](LICENSE)


## Utils Collection

### Common
#### Properties of Object

* PropertyUtils -
    reflect set/get/is methods to write/read data. This can works well on __Android__.

* PropertyBeanUtils -
    use `BeanInfo` to write/read data. __Android__ doesn't support `java.bean` package.

### File
#### Time Rolling
Search paths and files depending on time based naming rule.

__Example 1__

Use `PathQuery` to search sub path of __data/min/__ which folder naming matches __yyyy/MM/dd/HH__.

```java
PathQuery query = new PathQuery("data/min/", "yyyy/MM/dd/HH", TimeRollingType.HOUR);
List<FileQuery> queries = query.select(begin, end);
```

__Example 2__

Use `FileQuery` to search files with naming rule:

* __yyyyMMddHH__ - time based naming
* __HR___ - prefix
* __.json__ - postfix

```java
FileQuery fileQuery = ...;
List<FileInfo> fis = fileQuery.select("HR_", ".json", "yyyyMMddHH", TimeRollingType.HOUR);
```

## Copyright and License

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

[http://www.apache.org/licenses/LICENSE-2.0](http://www.apache.org/licenses/LICENSE-2.0)

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
