<div style="display: flex; flex-direction: column; align-items: center; justify-content: center; text-align: center; padding: 200px 0 20px 0;">

<div style="font-size: 24px; margin-bottom: 12px;"><strong>Amazon Q</strong></div>
<div style="background: rgba(128, 128, 128, 0.15); border: 1px solid rgba(128, 128, 128, 0.25); border-radius: 8px; padding: 8px; margin: 4px 0; text-align: center;">
<div style="font-size: 14px; margin-bottom: 4px;"><strong>Did you know?</strong></div>
<div>You can now see logs with 1-Click!</div>
</div>

Select code & ask me to explain, debug or optimize it, or type `/` for quick actions

</div>

---

Please help me fix the following errors in /home/kesi/workspace/java/yt/apiburn/rapidapi/apps/src/main/java/com/rapidapi/apps/product/ProductHandler.java:**apps/src/main/java/com/rapidapi/apps/product/ProductHandler.java**

```
[Java] Line 80: The constructor Product() is undefined
```

---

I need to check the Product class to understand why the constructor is undefined. Let me first look at the Product class structure.

---

The issue is that `Product` is defined as a `record`, not a regular class. Records in Java don't have a default no-argument constructor - they only have a constructor that takes all the record components as parameters.

I need to fix the ProductHandler to use the proper record constructor:

---

Fix the Product constructor call to use the record constructor with all required parameters

---



The error is now fixed. The `Product` is a record, so it requires all parameters in its constructor. I changed the code to use `new Product(null, name, Double.parseDouble(priceStr))` where:
- `null` for the id (will be set by the service when saved)
- `name` from the form field
- `Double.parseDouble(priceStr)` for the price

Records don't have setters, so we can't assign fields after construction like with regular classes.

---

Please help me fix the following errors in /home/kesi/workspace/java/yt/apiburn/rapidapi/apps/src/main/java/com/rapidapi/apps/product/ProductService.java:**apps/src/main/java/com/rapidapi/apps/product/ProductService.java**

```
[Java] Line 22: The constructor Product() is undefined
```

---

I need to fix the ProductService to use the proper record constructor for Product. Since Product is a record, I need to use the constructor with all parameters and handle the immutability properly.

---

Fix ProductService to use record constructor and accessor methods for the Product record

---



Fixed the ProductService to work with the Product record:

1. **Constructor calls**: Changed `new Product()` to `new Product(id, name, price)` with all required parameters
2. **Field access**: Changed direct field access (`p.name`, `p.price`) to record accessor methods (`p.name()`, `p.price()`)
3. **Immutability**: Since records are immutable, created new Product instances instead of trying to modify existing ones (e.g., in `save()` and `update()` methods)

The key changes:
- Records don't have setters, only accessor methods
- Records require all parameters in constructor
- Records are immutable, so we create new instances when we need different values

---

Please help me fix the following errors in /home/kesi/workspace/java/yt/apiburn/rapidapi/apps/src/main/java/com/rapidapi/apps/product/ProductHandler.java:**apps/src/main/java/com/rapidapi/apps/product/ProductHandler.java**

```
[Java] Line 92: The field Product.name is not visible
```

---

I need to fix the field access in the ProductHandler to use record accessor methods instead of direct field access.

---

Fix field access to use record accessor methods instead of direct field access

---



Fixed the field access issue. Since Product is a record, I changed the direct field access (`saved.name`, `saved.price`, `saved.id`) to use the record's accessor methods (`saved.name()`, `saved.price()`, `saved.id()`).

Records automatically generate accessor methods for each component, so instead of public fields, we use these methods to access the values.

---

Please help me fix the following errors in /home/kesi/workspace/java/yt/apiburn/rapidapi/apps/pom.xml:**apps/pom.xml**

```
[Java] Line 4: Missing artifact com.rapidapi:apps:jar:1.0.0-SNAPSHOT
```

---

I need to check the parent POM structure to understand the issue. Let me examine the root POM file first.

---

The issue is that the apps module is trying to inherit from a parent that doesn't exist yet. The parent POM is a multi-module project, but the child module (apps) is trying to reference itself as a dependency. I need to remove the redundant version declaration in the apps POM since it should inherit the version from the parent.

---

Remove redundant version declaration from child module since it inherits from parent

---



Fixed the Maven artifact issue by removing the redundant version declaration from the apps module. In Maven multi-module projects, child modules inherit the version from their parent, so declaring it again causes conflicts. The apps module will now properly inherit the version `1.0.0-SNAPSHOT` from the parent `rapidapi` module.

---

Please help me fix the following errors in /home/kesi/workspace/java/yt/apiburn/rapidapi/apps/src/main/java/com/rapidapi/apps/hello/HelloService.java:**apps/src/main/java/com/rapidapi/apps/hello/HelloService.java**

```
[Java] Line 6: BaseService cannot be resolved to a type
```

---

I need to add the missing import for BaseService from the core module.

---

Add missing import for BaseService from the core module

---



Fixed the import issue by adding the missing import statement for `BaseService` from the `com.rapidapi.core.common` package. The HelloService class extends BaseService, so it needs to import it from the core module.

---

Please help me fix the following errors in /home/kesi/workspace/java/yt/apiburn/rapidapi/apps/src/main/java/com/rapidapi/apps/hello/HelloService.java:**apps/src/main/java/com/rapidapi/apps/hello/HelloService.java**

```
[Java] Line 3: The import com.rapidapi.core cannot be resolved
```

---

I need to check if the BaseService class exists in the core module and verify the correct package structure.

---

The BaseService class exists in the correct location. The issue might be that the core module hasn't been compiled yet or there's a dependency issue. Let me check if the core module is properly built by trying to compile it first.



---

Compile the core module first to ensure it's available as a dependency for the apps module

---

```shell
mvn clean compile -rf :core

WARNING: A terminally deprecated method in sun.misc.Unsafe has been called
WARNING: sun.misc.Unsafe::staticFieldBase has been called by com.google.inject.internal.aop.HiddenClassDefiner (file:/home/kesi/.sdkman/candidates/maven/current/lib/guice-5.1.0-classes.jar)
WARNING: Please consider reporting this to the maintainers of class com.google.inject.internal.aop.HiddenClassDefiner
WARNING: sun.misc.Unsafe::staticFieldBase will be removed in a future release

[INFO] Scanning for projects...

[INFO] 

[INFO] -------------------------< com.rapidapi:core >--------------------------
[INFO] Building Rapidapi core 1.0.0-SNAPSHOT
[INFO]   from pom.xml
[INFO] --------------------------------[ jar ]---------------------------------

[WARNING] The POM for com.rapidapi:apps:jar:1.0.0-SNAPSHOT is missing, no dependency information available

[INFO] ------------------------------------------------------------------------

[INFO] BUILD FAILURE
[INFO] ------------------------------------------------------------------------

[INFO] Total time:  1.158 s
[INFO] Finished at: 2025-08-16T00:14:43+02:00


[INFO] ------------------------------------------------------------------------
[ERROR] Failed to execute goal on project core: Could not resolve dependencies for project com.rapidapi:core:jar:1.0.0-SNAPSHOT
[ERROR] dependency: com.rapidapi:apps:jar:1.0.0-SNAPSHOT (compile)
[ERROR] 	com.rapidapi:apps:jar:1.0.0-SNAPSHOT was not found in https://repo.maven.apache.org/maven2 during a previous attempt. This failure was cached in the local repository and resolution is not reattempted until the update interval of central has elapsed or updates are forced
[ERROR] 
[ERROR] -> [Help 1]
[ERROR] 
[ERROR] To see the full stack trace of the errors, re-run Maven with the -e switch.
[ERROR] Re-run Maven using the -X switch to enable full debug logging.
[ERROR] 
[ERROR] For more information about the errors and possible solutions, please read the following articles:
[ERROR] [Help 1] http://cwiki.apache.org/confluence/display/MAVEN/DependencyResolutionException

```

---

There's a circular dependency issue. The core module is trying to depend on the apps module. Let me check the core module's POM to see what's causing this.

---

Found the issue! The core module has a dependency on the apps module, which creates a circular dependency. The core module should not depend on the apps module - it should be the other way around. I need to remove this dependency from the core POM.

---

Remove circular dependency and redundant version declaration from core module

---

Now let's try to compile the core module again:



---

Compile the core module after removing the circular dependency

---

```shell
mvn clean compile -rf :core

WARNING: A terminally deprecated method in sun.misc.Unsafe has been called
WARNING: sun.misc.Unsafe::staticFieldBase has been called by com.google.inject.internal.aop.HiddenClassDefiner (file:/home/kesi/.sdkman/candidates/maven/current/lib/guice-5.1.0-classes.jar)
WARNING: Please consider reporting this to the maintainers of class com.google.inject.internal.aop.HiddenClassDefiner
WARNING: sun.misc.Unsafe::staticFieldBase will be removed in a future release

[INFO] Scanning for projects...

[INFO] ------------------------------------------------------------------------

[INFO] Reactor Build Order:
[INFO] 

[INFO] Rapidapi core                                                      [jar]

[INFO] Rapidapi Apps                                                      [jar]

[INFO] 


[INFO] -------------------------< com.rapidapi:core >--------------------------
[INFO] Building Rapidapi core 1.0.0-SNAPSHOT                              [1/2]
[INFO]   from pom.xml
[INFO] --------------------------------[ jar ]---------------------------------

[INFO] 
[INFO] --- clean:3.5.0:clean (default-clean) @ core ---

[INFO] Deleting /home/kesi/workspace/java/yt/apiburn/rapidapi/core/target

[INFO] 

[INFO] --- resources:3.3.1:resources (default-resources) @ core ---

[INFO] Copying 8 resources from src/main/resources to target/classes



[INFO] 

[INFO] --- compiler:3.14.0:compile (default-compile) @ core ---

[INFO] Recompiling the module because of changed source code.

[INFO] Compiling 29 source files with javac [debug release 25] to target/classes

[INFO] -------------------------------------------------------------

[ERROR] COMPILATION ERROR : 
[INFO] -------------------------------------------------------------
[ERROR] /home/kesi/workspace/java/yt/apiburn/rapidapi/core/src/main/java/com/rapidapi/core/config/JacksonConfig.java:[3,38] package com.fasterxml.jackson.databind does not exist
[ERROR] /home/kesi/workspace/java/yt/apiburn/rapidapi/core/src/main/java/com/rapidapi/core/config/JacksonConfig.java:[4,38] package com.fasterxml.jackson.databind does not exist
[ERROR] /home/kesi/workspace/java/yt/apiburn/rapidapi/core/src/main/java/com/rapidapi/core/config/JacksonConfig.java:[9,55] cannot find symbol
  symbol: class ObjectMapper
[ERROR] /home/kesi/workspace/java/yt/apiburn/rapidapi/core/src/main/java/com/rapidapi/core/config/JacksonConfig.java:[11,19] cannot find symbol
  symbol:   class ObjectMapper
  location: class com.rapidapi.core.config.JacksonConfig
[ERROR] /home/kesi/workspace/java/yt/apiburn/rapidapi/core/src/main/java/com/rapidapi/core/config/JacksonConfig.java:[20,12] cannot find symbol
  symbol:   class ObjectMapper
  location: class com.rapidapi.core.config.JacksonConfig
[ERROR] /home/kesi/workspace/java/yt/apiburn/rapidapi/core/src/main/java/com/rapidapi/core/utils/HandlerMappingLoader.java:[12,38] package com.fasterxml.jackson.databind does not exist
[ERROR] /home/kesi/workspace/java/yt/apiburn/rapidapi/core/src/main/java/com/rapidapi/core/config/JacksonConfig.java:[14,22] cannot find symbol
  symbol:   class ObjectMapper
  location: class com.rapidapi.core.config.JacksonConfig
[ERROR] /home/kesi/workspace/java/yt/apiburn/rapidapi/core/src/main/java/com/rapidapi/core/config/JacksonConfig.java:[15,23] cannot find symbol
  symbol:   variable SerializationFeature
  location: class com.rapidapi.core.config.JacksonConfig
[ERROR] /home/kesi/workspace/java/yt/apiburn/rapidapi/core/src/main/java/com/rapidapi/core/utils/HandlerMappingLoader.java:[20,9] cannot find symbol
  symbol:   class ObjectMapper
  location: class com.rapidapi.core.utils.HandlerMappingLoader

[ERROR] /home/kesi/workspace/java/yt/apiburn/rapidapi/core/src/main/java/com/rapidapi/core/utils/HandlerMappingLoader.java:[20,35] cannot find symbol
  symbol:   class ObjectMapper
  location: class com.rapidapi.core.utils.HandlerMappingLoader
[INFO] 10 errors 
[INFO] -------------------------------------------------------------

[INFO] ------------------------------------------------------------------------
[INFO] Reactor Summary for Rapidapi core 1.0.0-SNAPSHOT:
[INFO] 
[INFO] Rapidapi core ...................................... FAILURE [  4.313 s]
[INFO] Rapidapi Apps ...................................... SKIPPED
[INFO] ------------------------------------------------------------------------
[INFO] BUILD FAILURE
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  4.800 s
[INFO] Finished at: 2025-08-16T00:15:10+02:00
[INFO] ------------------------------------------------------------------------
[ERROR] Failed to execute goal org.apache.maven.plugins:maven-compiler-plugin:3.14.0:compile (default-compile) on project core: Compilation failure: Compilation failure: 
[ERROR] /home/kesi/workspace/java/yt/apiburn/rapidapi/core/src/main/java/com/rapidapi/core/config/JacksonConfig.java:[3,38] package com.fasterxml.jackson.databind does not exist
[ERROR] /home/kesi/workspace/java/yt/apiburn/rapidapi/core/src/main/java/com/rapidapi/core/config/JacksonConfig.java:[4,38] package com.fasterxml.jackson.databind does not exist
[ERROR] /home/kesi/workspace/java/yt/apiburn/rapidapi/core/src/main/java/com/rapidapi/core/config/JacksonConfig.java:[9,55] cannot find symbol
[ERROR]   symbol: class ObjectMapper
[ERROR] /home/kesi/workspace/java/yt/apiburn/rapidapi/core/src/main/java/com/rapidapi/core/config/JacksonConfig.java:[11,19] cannot find symbol
[ERROR]   symbol:   class ObjectMapper
[ERROR]   location: class com.rapidapi.core.config.JacksonConfig
[ERROR] /home/kesi/workspace/java/yt/apiburn/rapidapi/core/src/main/java/com/rapidapi/core/config/JacksonConfig.java:[20,12] cannot find symbol
[ERROR]   symbol:   class ObjectMapper
[ERROR]   location: class com.rapidapi.core.config.JacksonConfig
[ERROR] /home/kesi/workspace/java/yt/apiburn/rapidapi/core/src/main/java/com/rapidapi/core/utils/HandlerMappingLoader.java:[12,38] package com.fasterxml.jackson.databind does not exist
[ERROR] /home/kesi/workspace/java/yt/apiburn/rapidapi/core/src/main/java/com/rapidapi/core/config/JacksonConfig.java:[14,22] cannot find symbol
[ERROR]   symbol:   class ObjectMapper
[ERROR]   location: class com.rapidapi.core.config.JacksonConfig
[ERROR] /home/kesi/workspace/java/yt/apiburn/rapidapi/core/src/main/java/com/rapidapi/core/config/JacksonConfig.java:[15,23] cannot find symbol
[ERROR]   symbol:   variable SerializationFeature
[ERROR]   location: class com.rapidapi.core.config.JacksonConfig
[ERROR] /home/kesi/workspace/java/yt/apiburn/rapidapi/core/src/main/java/com/rapidapi/core/utils/HandlerMappingLoader.java:[20,9] cannot find symbol
[ERROR]   symbol:   class ObjectMapper
[ERROR]   location: class com.rapidapi.core.utils.HandlerMappingLoader
[ERROR] /home/kesi/workspace/java/yt/apiburn/rapidapi/core/src/main/java/com/rapidapi/core/utils/HandlerMappingLoader.java:[20,35] cannot find symbol
[ERROR]   symbol:   class ObjectMapper
[ERROR]   location: class com.rapidapi.core.utils.HandlerMappingLoader
[ERROR] -> [Help 1]
[ERROR] 


[ERROR] To see the full stack trace of the errors, re-run Maven with the -e switch.
[ERROR] Re-run Maven using the -X switch to enable full debug logging.
[ERROR] 
[ERROR] For more information about the errors and possible solutions, please read the following articles:
[ERROR] [Help 1] http://cwiki.apache.org/confluence/display/MAVEN/MojoFailureException

```

---

The core module is missing Jackson dependencies. I need to add them to the core POM:

---

Add missing Jackson dependency to core module

---

Now let's try compiling the core module again:



---

Compile the core module after adding Jackson dependency

---

```shell
mvn clean compile -rf :core

WARNING: A terminally deprecated method in sun.misc.Unsafe has been called
WARNING: sun.misc.Unsafe::staticFieldBase has been called by com.google.inject.internal.aop.HiddenClassDefiner (file:/home/kesi/.sdkman/candidates/maven/current/lib/guice-5.1.0-classes.jar)
WARNING: Please consider reporting this to the maintainers of class com.google.inject.internal.aop.HiddenClassDefiner
WARNING: sun.misc.Unsafe::staticFieldBase will be removed in a future release

[INFO] Scanning for projects...

[INFO] ------------------------------------------------------------------------
[INFO] Reactor Build Order:


[INFO] 

[INFO] Rapidapi core                                                      [jar]
[INFO] Rapidapi Apps                                                      [jar]

[INFO] 

[INFO] -------------------------< com.rapidapi:core >--------------------------
[INFO] Building Rapidapi core 1.0.0-SNAPSHOT                              [1/2]
[INFO]   from pom.xml
[INFO] --------------------------------[ jar ]---------------------------------

[INFO] 
[INFO] --- clean:3.5.0:clean (default-clean) @ core ---

[INFO] Deleting /home/kesi/workspace/java/yt/apiburn/rapidapi/core/target

[INFO] 

[INFO] --- resources:3.3.1:resources (default-resources) @ core ---

[INFO] Copying 8 resources from src/main/resources to target/classes

[INFO] 

[INFO] --- compiler:3.14.0:compile (default-compile) @ core ---

[INFO] Recompiling the module because of changed source code.

[INFO] Compiling 29 source files with javac [debug release 25] to target/classes

[INFO] 


[INFO] -------------------------< com.rapidapi:apps >--------------------------
[INFO] Building Rapidapi Apps 1.0.0-SNAPSHOT                              [2/2]
[INFO]   from /home/kesi/workspace/java/yt/apiburn/rapidapi/apps/pom.xml
[INFO] --------------------------------[ jar ]---------------------------------

[INFO] 
[INFO] --- clean:3.5.0:clean (default-clean) @ apps ---

[INFO] Deleting /home/kesi/workspace/java/yt/apiburn/rapidapi/apps/target

[INFO] 
[INFO] --- resources:3.3.1:resources (default-resources) @ apps ---

[INFO] Copying 6 resources from src/main/resources to target/classes

[INFO] 

[INFO] --- compiler:3.14.0:compile (default-compile) @ apps ---

[INFO] Recompiling the module because of changed source code.

[INFO] Compiling 8 source files with javac [debug release 25] to target/classes

[INFO] -------------------------------------------------------------
[WARNING] COMPILATION WARNING : 
[INFO] -------------------------------------------------------------
[WARNING] /home/kesi/workspace/java/yt/apiburn/rapidapi/apps/src/main/java/com/rapidapi/apps/product/ProductNotFoundException.java:[3,8] serializable class com.rapidapi.apps.product.ProductNotFoundException has no definition of serialVersionUID
[INFO] 1 warning

[INFO] -------------------------------------------------------------
[INFO] -------------------------------------------------------------
[ERROR] COMPILATION ERROR : 
[INFO] -------------------------------------------------------------
[ERROR] /home/kesi/workspace/java/yt/apiburn/rapidapi/apps/src/main/java/com/rapidapi/apps/hello/HelloHandler.java:[5,30] package org.eclipse.jetty.http does not exist
[ERROR] /home/kesi/workspace/java/yt/apiburn/rapidapi/apps/src/main/java/com/rapidapi/apps/hello/HelloHandler.java:[6,32] package org.eclipse.jetty.server does not exist
[ERROR] /home/kesi/workspace/java/yt/apiburn/rapidapi/apps/src/main/java/com/rapidapi/apps/hello/HelloHandler.java:[7,32] package org.eclipse.jetty.server does not exist
[ERROR] /home/kesi/workspace/java/yt/apiburn/rapidapi/apps/src/main/java/com/rapidapi/apps/hello/HelloHandler.java:[8,32] package org.eclipse.jetty.server does not exist
[ERROR] /home/kesi/workspace/java/yt/apiburn/rapidapi/apps/src/main/java/com/rapidapi/apps/hello/HelloHandler.java:[9,30] package org.eclipse.jetty.util does not exist
[ERROR] /home/kesi/workspace/java/yt/apiburn/rapidapi/apps/src/main/java/com/rapidapi/apps/hello/HelloHandler.java:[10,30] package org.eclipse.jetty.util does not exist
[ERROR] /home/kesi/workspace/java/yt/apiburn/rapidapi/apps/src/main/java/com/rapidapi/apps/hello/HelloHandler.java:[12,42] package Handler does not exist
[ERROR] /home/kesi/workspace/java/yt/apiburn/rapidapi/apps/src/main/java/com/rapidapi/apps/hello/HelloHandler.java:[22,27] cannot find symbol
  symbol:   class Request
  location: class com.rapidapi.apps.hello.HelloHandler
[ERROR] /home/kesi/workspace/java/yt/apiburn/rapidapi/apps/src/main/java/com/rapidapi/apps/hello/HelloHandler.java:[22,44] cannot find symbol
  symbol:   class Response
  location: class com.rapidapi.apps.hello.HelloHandler
[ERROR] /home/kesi/workspace/java/yt/apiburn/rapidapi/apps/src/main/java/com/rapidapi/apps/hello/HelloHandler.java:[22,63] cannot find symbol
  symbol:   class Callback
  location: class com.rapidapi.apps.hello.HelloHandler
[ERROR] /home/kesi/workspace/java/yt/apiburn/rapidapi/apps/src/main/java/com/rapidapi/apps/hello/HelloResource.java:[15,13] cannot find symbol
  symbol:   class HelloWorldService
  location: class com.rapidapi.apps.hello.HelloResource
[ERROR] /home/kesi/workspace/java/yt/apiburn/rapidapi/apps/src/main/java/com/rapidapi/apps/hello/HelloService.java:[3,32] package com.rapidapi.core.common does not exist
[ERROR] /home/kesi/workspace/java/yt/apiburn/rapidapi/apps/src/main/java/com/rapidapi/apps/hello/HelloService.java:[7,35] cannot find symbol
  symbol: class BaseService
[ERROR] /home/kesi/workspace/java/yt/apiburn/rapidapi/apps/src/main/java/com/rapidapi/apps/product/ProductHandler.java:[7,30] package org.eclipse.jetty.http does not exist
[ERROR] /home/kesi/workspace/java/yt/apiburn/rapidapi/apps/src/main/java/com/rapidapi/apps/product/ProductHandler.java:[8,32] package org.eclipse.jetty.server does not exist
[ERROR] /home/kesi/workspace/java/yt/apiburn/rapidapi/apps/src/main/java/com/rapidapi/apps/product/ProductHandler.java:[9,32] package org.eclipse.jetty.server does not exist
[ERROR] /home/kesi/workspace/java/yt/apiburn/rapidapi/apps/src/main/java/com/rapidapi/apps/product/ProductHandler.java:[10,32] package org.eclipse.jetty.server does not exist
[ERROR] /home/kesi/workspace/java/yt/apiburn/rapidapi/apps/src/main/java/com/rapidapi/apps/product/ProductHandler.java:[11,30] package org.eclipse.jetty.util does not exist
[ERROR] /home/kesi/workspace/java/yt/apiburn/rapidapi/apps/src/main/java/com/rapidapi/apps/product/ProductHandler.java:[12,30] package org.eclipse.jetty.util does not exist
[ERROR] /home/kesi/workspace/java/yt/apiburn/rapidapi/apps/src/main/java/com/rapidapi/apps/product/ProductHandler.java:[14,32] package com.rapidapi.core.common does not exist
[ERROR] /home/kesi/workspace/java/yt/apiburn/rapidapi/apps/src/main/java/com/rapidapi/apps/product/ProductHandler.java:[15,32] package com.rapidapi.core.common does not exist
[ERROR] /home/kesi/workspace/java/yt/apiburn/rapidapi/apps/src/main/java/com/rapidapi/apps/product/ProductHandler.java:[20,44] package Handler does not exist
[ERROR] /home/kesi/workspace/java/yt/apiburn/rapidapi/apps/src/main/java/com/rapidapi/apps/product/ProductHandler.java:[20,65] cannot find symbol
  symbol: class ValidatedHandler
[ERROR] /home/kesi/workspace/java/yt/apiburn/rapidapi/apps/src/main/java/com/rapidapi/apps/product/ProductService.java:[8,32] package com.rapidapi.core.common does not exist
[ERROR] /home/kesi/workspace/java/yt/apiburn/rapidapi/apps/src/main/java/com/rapidapi/apps/product/ProductService.java:[13,37] cannot find symbol
  symbol: class BaseService
[ERROR] /home/kesi/workspace/java/yt/apiburn/rapidapi/apps/src/main/java/com/rapidapi/apps/product/ProductHandler.java:[26,27] cannot find symbol
  symbol:   class Request
  location: class com.rapidapi.apps.product.ProductHandler
[ERROR] /home/kesi/workspace/java/yt/apiburn/rapidapi/apps/src/main/java/com/rapidapi/apps/product/ProductHandler.java:[26,44] cannot find symbol
  symbol:   class Response
  location: class com.rapidapi.apps.product.ProductHandler
[ERROR] /home/kesi/workspace/java/yt/apiburn/rapidapi/apps/src/main/java/com/rapidapi/apps/product/ProductHandler.java:[26,63] cannot find symbol
  symbol:   class Callback
  location: class com.rapidapi.apps.product.ProductHandler
[ERROR] /home/kesi/workspace/java/yt/apiburn/rapidapi/apps/src/main/java/com/rapidapi/apps/product/ProductHandler.java:[39,31] cannot find symbol
  symbol:   class Request
  location: class com.rapidapi.apps.product.ProductHandler
[ERROR] /home/kesi/workspace/java/yt/apiburn/rapidapi/apps/src/main/java/com/rapidapi/apps/product/ProductHandler.java:[39,48] cannot find symbol
  symbol:   class Response
  location: class com.rapidapi.apps.product.ProductHandler
[ERROR] /home/kesi/workspace/java/yt/apiburn/rapidapi/apps/src/main/java/com/rapidapi/apps/product/ProductHandler.java:[39,67] cannot find symbol
  symbol:   class Callback
  location: class com.rapidapi.apps.product.ProductHandler
[ERROR] /home/kesi/workspace/java/yt/apiburn/rapidapi/apps/src/main/java/com/rapidapi/apps/product/ProductHandler.java:[43,32] cannot find symbol
  symbol:   class Request
  location: class com.rapidapi.apps.product.ProductHandler
[ERROR] /home/kesi/workspace/java/yt/apiburn/rapidapi/apps/src/main/java/com/rapidapi/apps/product/ProductHandler.java:[43,49] cannot find symbol
  symbol:   class Response
  location: class com.rapidapi.apps.product.ProductHandler
[ERROR] /home/kesi/workspace/java/yt/apiburn/rapidapi/apps/src/main/java/com/rapidapi/apps/product/ProductHandler.java:[43,68] cannot find symbol
  symbol:   class Callback
  location: class com.rapidapi.apps.product.ProductHandler

[ERROR] /home/kesi/workspace/java/yt/apiburn/rapidapi/apps/src/main/java/com/rapidapi/apps/product/ProductHandler.java:[110,41] cannot find symbol
  symbol:   class Response
  location: class com.rapidapi.apps.product.ProductHandler
[ERROR] /home/kesi/workspace/java/yt/apiburn/rapidapi/apps/src/main/java/com/rapidapi/apps/product/ProductHandler.java:[110,60] cannot find symbol
  symbol:   class Callback
  location: class com.rapidapi.apps.product.ProductHandler
[ERROR] /home/kesi/workspace/java/yt/apiburn/rapidapi/apps/src/main/java/com/rapidapi/apps/product/ProductResource.java:[6,32] package com.rapidapi.core.common does not exist
[ERROR] /home/kesi/workspace/java/yt/apiburn/rapidapi/apps/src/main/java/com/rapidapi/apps/hello/HelloHandler.java:[21,5] method does not override or implement a method from a supertype
[ERROR] /home/kesi/workspace/java/yt/apiburn/rapidapi/apps/src/main/java/com/rapidapi/apps/hello/HelloHandler.java:[24,35] cannot find symbol
  symbol:   variable HttpHeader
  location: class com.rapidapi.apps.hello.HelloHandler
[ERROR] /home/kesi/workspace/java/yt/apiburn/rapidapi/apps/src/main/java/com/rapidapi/apps/hello/HelloHandler.java:[25,30] cannot find symbol
  symbol:   variable BufferUtil
  location: class com.rapidapi.apps.hello.HelloHandler
[ERROR] /home/kesi/workspace/java/yt/apiburn/rapidapi/apps/src/main/java/com/rapidapi/apps/product/ProductHandler.java:[25,5] method does not override or implement a method from a supertype
[ERROR] /home/kesi/workspace/java/yt/apiburn/rapidapi/apps/src/main/java/com/rapidapi/apps/product/ProductHandler.java:[30,27] package HttpMethod does not exist
[ERROR] /home/kesi/workspace/java/yt/apiburn/rapidapi/apps/src/main/java/com/rapidapi/apps/product/ProductHandler.java:[32,34] package HttpMethod does not exist
[ERROR] /home/kesi/workspace/java/yt/apiburn/rapidapi/apps/src/main/java/com/rapidapi/apps/product/ProductHandler.java:[45,9] cannot find symbol
  symbol:   class Fields
  location: class com.rapidapi.apps.product.ProductHandler
[ERROR] /home/kesi/workspace/java/yt/apiburn/rapidapi/apps/src/main/java/com/rapidapi/apps/product/ProductHandler.java:[45,29] cannot find symbol
  symbol:   variable Request
  location: class com.rapidapi.apps.product.ProductHandler
[ERROR] /home/kesi/workspace/java/yt/apiburn/rapidapi/apps/src/main/java/com/rapidapi/apps/product/ProductHandler.java:[62,35] cannot find symbol
  symbol:   variable ErrorHandler
  location: class com.rapidapi.apps.product.ProductHandler
[ERROR] /home/kesi/workspace/java/yt/apiburn/rapidapi/apps/src/main/java/com/rapidapi/apps/product/ProductHandler.java:[83,64] cannot find symbol
  symbol:   method getValidator()
  location: class com.rapidapi.apps.product.ProductHandler
[ERROR] /home/kesi/workspace/java/yt/apiburn/rapidapi/apps/src/main/java/com/rapidapi/apps/product/ProductHandler.java:[98,32] cannot find symbol
  symbol:   variable ErrorHandler
  location: class com.rapidapi.apps.product.ProductHandler
[ERROR] /home/kesi/workspace/java/yt/apiburn/rapidapi/apps/src/main/java/com/rapidapi/apps/product/ProductHandler.java:[114,31] cannot find symbol
  symbol:   method getClass()
  location: class com.rapidapi.apps.product.ProductHandler
[ERROR] /home/kesi/workspace/java/yt/apiburn/rapidapi/apps/src/main/java/com/rapidapi/apps/product/ProductService.java:[17,25] cannot find symbol
  symbol:   method getConnection()
  location: class com.rapidapi.apps.product.ProductService
[ERROR] /home/kesi/workspace/java/yt/apiburn/rapidapi/apps/src/main/java/com/rapidapi/apps/product/ProductService.java:[32,25] cannot find symbol
  symbol:   method getConnection()
  location: class com.rapidapi.apps.product.ProductService
[ERROR] /home/kesi/workspace/java/yt/apiburn/rapidapi/apps/src/main/java/com/rapidapi/apps/product/ProductService.java:[49,25] cannot find symbol
  symbol:   method getConnection()
  location: class com.rapidapi.apps.product.ProductService
[ERROR] /home/kesi/workspace/java/yt/apiburn/rapidapi/apps/src/main/java/com/rapidapi/apps/product/ProductService.java:[69,25] cannot find symbol
  symbol:   method getConnection()
  location: class com.rapidapi.apps.product.ProductService
[ERROR] /home/kesi/workspace/java/yt/apiburn/rapidapi/apps/src/main/java/com/rapidapi/apps/product/ProductService.java:[87,25] cannot find symbol
  symbol:   method getConnection()
  location: class com.rapidapi.apps.product.ProductService
[ERROR] /home/kesi/workspace/java/yt/apiburn/rapidapi/apps/src/main/java/com/rapidapi/apps/product/ProductResource.java:[33,41] cannot find symbol
  symbol:   class ErrorResponse
  location: class com.rapidapi.apps.product.ProductResource
[ERROR] /home/kesi/workspace/java/yt/apiburn/rapidapi/apps/src/main/java/com/rapidapi/apps/product/ProductResource.java:[48,41] cannot find symbol
  symbol:   class ErrorResponse
  location: class com.rapidapi.apps.product.ProductResource
[ERROR] /home/kesi/workspace/java/yt/apiburn/rapidapi/apps/src/main/java/com/rapidapi/apps/product/ProductResource.java:[59,29] cannot find symbol
  symbol:   class ErrorResponse
  location: class com.rapidapi.apps.product.ProductResource
[INFO] 57 errors 
[INFO] -------------------------------------------------------------
[INFO] ------------------------------------------------------------------------
[INFO] Reactor Summary for Rapidapi core 1.0.0-SNAPSHOT:


[INFO] 
[INFO] Rapidapi core ...................................... SUCCESS [  2.911 s]
[INFO] Rapidapi Apps ...................................... FAILURE [  0.636 s]
[INFO] ------------------------------------------------------------------------
[INFO] BUILD FAILURE
[INFO] ------------------------------------------------------------------------

[INFO] Total time:  3.966 s
[INFO] Finished at: 2025-08-16T00:15:25+02:00
[INFO] ------------------------------------------------------------------------

[ERROR] Failed to execute goal org.apache.maven.plugins:maven-compiler-plugin:3.14.0:compile (default-compile) on project apps: Compilation failure: Compilation failure: 
[ERROR] /home/kesi/workspace/java/yt/apiburn/rapidapi/apps/src/main/java/com/rapidapi/apps/hello/HelloHandler.java:[5,30] package org.eclipse.jetty.http does not exist
[ERROR] /home/kesi/workspace/java/yt/apiburn/rapidapi/apps/src/main/java/com/rapidapi/apps/hello/HelloHandler.java:[6,32] package org.eclipse.jetty.server does not exist
[ERROR] /home/kesi/workspace/java/yt/apiburn/rapidapi/apps/src/main/java/com/rapidapi/apps/hello/HelloHandler.java:[7,32] package org.eclipse.jetty.server does not exist
[ERROR] /home/kesi/workspace/java/yt/apiburn/rapidapi/apps/src/main/java/com/rapidapi/apps/hello/HelloHandler.java:[8,32] package org.eclipse.jetty.server does not exist
[ERROR] /home/kesi/workspace/java/yt/apiburn/rapidapi/apps/src/main/java/com/rapidapi/apps/hello/HelloHandler.java:[9,30] package org.eclipse.jetty.util does not exist
[ERROR] /home/kesi/workspace/java/yt/apiburn/rapidapi/apps/src/main/java/com/rapidapi/apps/hello/HelloHandler.java:[10,30] package org.eclipse.jetty.util does not exist
[ERROR] /home/kesi/workspace/java/yt/apiburn/rapidapi/apps/src/main/java/com/rapidapi/apps/hello/HelloHandler.java:[12,42] package Handler does not exist
[ERROR] /home/kesi/workspace/java/yt/apiburn/rapidapi/apps/src/main/java/com/rapidapi/apps/hello/HelloHandler.java:[22,27] cannot find symbol
[ERROR]   symbol:   class Request
[ERROR]   location: class com.rapidapi.apps.hello.HelloHandler
[ERROR] /home/kesi/workspace/java/yt/apiburn/rapidapi/apps/src/main/java/com/rapidapi/apps/hello/HelloHandler.java:[22,44] cannot find symbol
[ERROR]   symbol:   class Response
[ERROR]   location: class com.rapidapi.apps.hello.HelloHandler
[ERROR] /home/kesi/workspace/java/yt/apiburn/rapidapi/apps/src/main/java/com/rapidapi/apps/hello/HelloHandler.java:[22,63] cannot find symbol


[ERROR]   symbol:   class Callback
[ERROR]   location: class com.rapidapi.apps.hello.HelloHandler
[ERROR] /home/kesi/workspace/java/yt/apiburn/rapidapi/apps/src/main/java/com/rapidapi/apps/hello/HelloResource.java:[15,13] cannot find symbol
[ERROR]   symbol:   class HelloWorldService
[ERROR]   location: class com.rapidapi.apps.hello.HelloResource
[ERROR] /home/kesi/workspace/java/yt/apiburn/rapidapi/apps/src/main/java/com/rapidapi/apps/hello/HelloService.java:[3,32] package com.rapidapi.core.common does not exist
[ERROR] /home/kesi/workspace/java/yt/apiburn/rapidapi/apps/src/main/java/com/rapidapi/apps/hello/HelloService.java:[7,35] cannot find symbol
[ERROR]   symbol: class BaseService
[ERROR] /home/kesi/workspace/java/yt/apiburn/rapidapi/apps/src/main/java/com/rapidapi/apps/product/ProductHandler.java:[7,30] package org.eclipse.jetty.http does not exist
[ERROR] /home/kesi/workspace/java/yt/apiburn/rapidapi/apps/src/main/java/com/rapidapi/apps/product/ProductHandler.java:[8,32] package org.eclipse.jetty.server does not exist
[ERROR] /home/kesi/workspace/java/yt/apiburn/rapidapi/apps/src/main/java/com/rapidapi/apps/product/ProductHandler.java:[9,32] package org.eclipse.jetty.server does not exist
[ERROR] /home/kesi/workspace/java/yt/apiburn/rapidapi/apps/src/main/java/com/rapidapi/apps/product/ProductHandler.java:[10,32] package org.eclipse.jetty.server does not exist
[ERROR] /home/kesi/workspace/java/yt/apiburn/rapidapi/apps/src/main/java/com/rapidapi/apps/product/ProductHandler.java:[11,30] package org.eclipse.jetty.util does not exist
[ERROR] /home/kesi/workspace/java/yt/apiburn/rapidapi/apps/src/main/java/com/rapidapi/apps/product/ProductHandler.java:[12,30] package org.eclipse.jetty.util does not exist
[ERROR] /home/kesi/workspace/java/yt/apiburn/rapidapi/apps/src/main/java/com/rapidapi/apps/product/ProductHandler.java:[14,32] package com.rapidapi.core.common does not exist
[ERROR] /home/kesi/workspace/java/yt/apiburn/rapidapi/apps/src/main/java/com/rapidapi/apps/product/ProductHandler.java:[15,32] package com.rapidapi.core.common does not exist
[ERROR] /home/kesi/workspace/java/yt/apiburn/rapidapi/apps/src/main/java/com/rapidapi/apps/product/ProductHandler.java:[20,44] package Handler does not exist
[ERROR] /home/kesi/workspace/java/yt/apiburn/rapidapi/apps/src/main/java/com/rapidapi/apps/product/ProductHandler.java:[20,65] cannot find symbol
[ERROR]   symbol: class ValidatedHandler
[ERROR] /home/kesi/workspace/java/yt/apiburn/rapidapi/apps/src/main/java/com/rapidapi/apps/product/ProductService.java:[8,32] package com.rapidapi.core.common does not exist
[ERROR] /home/kesi/workspace/java/yt/apiburn/rapidapi/apps/src/main/java/com/rapidapi/apps/product/ProductService.java:[13,37] cannot find symbol
[ERROR]   symbol: class BaseService
[ERROR] /home/kesi/workspace/java/yt/apiburn/rapidapi/apps/src/main/java/com/rapidapi/apps/product/ProductHandler.java:[26,27] cannot find symbol
[ERROR]   symbol:   class Request
[ERROR]   location: class com.rapidapi.apps.product.ProductHandler
[ERROR] /home/kesi/workspace/java/yt/apiburn/rapidapi/apps/src/main/java/com/rapidapi/apps/product/ProductHandler.java:[26,44] cannot find symbol
[ERROR]   symbol:   class Response
[ERROR]   location: class com.rapidapi.apps.product.ProductHandler
[ERROR] /home/kesi/workspace/java/yt/apiburn/rapidapi/apps/src/main/java/com/rapidapi/apps/product/ProductHandler.java:[26,63] cannot find symbol
[ERROR]   symbol:   class Callback
[ERROR]   location: class com.rapidapi.apps.product.ProductHandler
[ERROR] /home/kesi/workspace/java/yt/apiburn/rapidapi/apps/src/main/java/com/rapidapi/apps/product/ProductHandler.java:[39,31] cannot find symbol
[ERROR]   symbol:   class Request
[ERROR]   location: class com.rapidapi.apps.product.ProductHandler
[ERROR] /home/kesi/workspace/java/yt/apiburn/rapidapi/apps/src/main/java/com/rapidapi/apps/product/ProductHandler.java:[39,48] cannot find symbol
[ERROR]   symbol:   class Response
[ERROR]   location: class com.rapidapi.apps.product.ProductHandler
[ERROR] /home/kesi/workspace/java/yt/apiburn/rapidapi/apps/src/main/java/com/rapidapi/apps/product/ProductHandler.java:[39,67] cannot find symbol
[ERROR]   symbol:   class Callback
[ERROR]   location: class com.rapidapi.apps.product.ProductHandler
[ERROR] /home/kesi/workspace/java/yt/apiburn/rapidapi/apps/src/main/java/com/rapidapi/apps/product/ProductHandler.java:[43,32] cannot find symbol
[ERROR]   symbol:   class Request
[ERROR]   location: class com.rapidapi.apps.product.ProductHandler
[ERROR] /home/kesi/workspace/java/yt/apiburn/rapidapi/apps/src/main/java/com/rapidapi/apps/product/ProductHandler.java:[43,49] cannot find symbol
[ERROR]   symbol:   class Response
[ERROR]   location: class com.rapidapi.apps.product.ProductHandler
[ERROR] /home/kesi/workspace/java/yt/apiburn/rapidapi/apps/src/main/java/com/rapidapi/apps/product/ProductHandler.java:[43,68] cannot find symbol
[ERROR]   symbol:   class Callback
[ERROR]   location: class com.rapidapi.apps.product.ProductHandler
[ERROR] /home/kesi/workspace/java/yt/apiburn/rapidapi/apps/src/main/java/com/rapidapi/apps/product/ProductHandler.java:[110,41] cannot find symbol
[ERROR]   symbol:   class Response
[ERROR]   location: class com.rapidapi.apps.product.ProductHandler
[ERROR] /home/kesi/workspace/java/yt/apiburn/rapidapi/apps/src/main/java/com/rapidapi/apps/product/ProductHandler.java:[110,60] cannot find symbol
[ERROR]   symbol:   class Callback
[ERROR]   location: class com.rapidapi.apps.product.ProductHandler
[ERROR] /home/kesi/workspace/java/yt/apiburn/rapidapi/apps/src/main/java/com/rapidapi/apps/product/ProductResource.java:[6,32] package com.rapidapi.core.common does not exist
[ERROR] /home/kesi/workspace/java/yt/apiburn/rapidapi/apps/src/main/java/com/rapidapi/apps/hello/HelloHandler.java:[21,5] method does not override or implement a method from a supertype
[ERROR] /home/kesi/workspace/java/yt/apiburn/rapidapi/apps/src/main/java/com/rapidapi/apps/hello/HelloHandler.java:[24,35] cannot find symbol
[ERROR]   symbol:   variable HttpHeader
[ERROR]   location: class com.rapidapi.apps.hello.HelloHandler
[ERROR] /home/kesi/workspace/java/yt/apiburn/rapidapi/apps/src/main/java/com/rapidapi/apps/hello/HelloHandler.java:[25,30] cannot find symbol
[ERROR]   symbol:   variable BufferUtil
[ERROR]   location: class com.rapidapi.apps.hello.HelloHandler
[ERROR] /home/kesi/workspace/java/yt/apiburn/rapidapi/apps/src/main/java/com/rapidapi/apps/product/ProductHandler.java:[25,5] method does not override or implement a method from a supertype
[ERROR] /home/kesi/workspace/java/yt/apiburn/rapidapi/apps/src/main/java/com/rapidapi/apps/product/ProductHandler.java:[30,27] package HttpMethod does not exist
[ERROR] /home/kesi/workspace/java/yt/apiburn/rapidapi/apps/src/main/java/com/rapidapi/apps/product/ProductHandler.java:[32,34] package HttpMethod does not exist
[ERROR] /home/kesi/workspace/java/yt/apiburn/rapidapi/apps/src/main/java/com/rapidapi/apps/product/ProductHandler.java:[45,9] cannot find symbol
[ERROR]   symbol:   class Fields

[ERROR]   location: class com.rapidapi.apps.product.ProductHandler
[ERROR] /home/kesi/workspace/java/yt/apiburn/rapidapi/apps/src/main/java/com/rapidapi/apps/product/ProductHandler.java:[45,29] cannot find symbol
[ERROR]   symbol:   variable Request
[ERROR]   location: class com.rapidapi.apps.product.ProductHandler
[ERROR] /home/kesi/workspace/java/yt/apiburn/rapidapi/apps/src/main/java/com/rapidapi/apps/product/ProductHandler.java:[62,35] cannot find symbol
[ERROR]   symbol:   variable ErrorHandler
[ERROR]   location: class com.rapidapi.apps.product.ProductHandler
[ERROR] /home/kesi/workspace/java/yt/apiburn/rapidapi/apps/src/main/java/com/rapidapi/apps/product/ProductHandler.java:[83,64] cannot find symbol
[ERROR]   symbol:   method getValidator()
[ERROR]   location: class com.rapidapi.apps.product.ProductHandler
[ERROR] /home/kesi/workspace/java/yt/apiburn/rapidapi/apps/src/main/java/com/rapidapi/apps/product/ProductHandler.java:[98,32] cannot find symbol
[ERROR]   symbol:   variable ErrorHandler
[ERROR]   location: class com.rapidapi.apps.product.ProductHandler
[ERROR] /home/kesi/workspace/java/yt/apiburn/rapidapi/apps/src/main/java/com/rapidapi/apps/product/ProductHandler.java:[114,31] cannot find symbol
[ERROR]   symbol:   method getClass()
[ERROR]   location: class com.rapidapi.apps.product.ProductHandler
[ERROR] /home/kesi/workspace/java/yt/apiburn/rapidapi/apps/src/main/java/com/rapidapi/apps/product/ProductService.java:[17,25] cannot find symbol
[ERROR]   symbol:   method getConnection()
[ERROR]   location: class com.rapidapi.apps.product.ProductService
[ERROR] /home/kesi/workspace/java/yt/apiburn/rapidapi/apps/src/main/java/com/rapidapi/apps/product/ProductService.java:[32,25] cannot find symbol
[ERROR]   symbol:   method getConnection()
[ERROR]   location: class com.rapidapi.apps.product.ProductService
[ERROR] /home/kesi/workspace/java/yt/apiburn/rapidapi/apps/src/main/java/com/rapidapi/apps/product/ProductService.java:[49,25] cannot find symbol
[ERROR]   symbol:   method getConnection()
[ERROR]   location: class com.rapidapi.apps.product.ProductService
[ERROR] /home/kesi/workspace/java/yt/apiburn/rapidapi/apps/src/main/java/com/rapidapi/apps/product/ProductService.java:[69,25] cannot find symbol
[ERROR]   symbol:   method getConnection()
[ERROR]   location: class com.rapidapi.apps.product.ProductService
[ERROR] /home/kesi/workspace/java/yt/apiburn/rapidapi/apps/src/main/java/com/rapidapi/apps/product/ProductService.java:[87,25] cannot find symbol
[ERROR]   symbol:   method getConnection()
[ERROR]   location: class com.rapidapi.apps.product.ProductService
[ERROR] /home/kesi/workspace/java/yt/apiburn/rapidapi/apps/src/main/java/com/rapidapi/apps/product/ProductResource.java:[33,41] cannot find symbol
[ERROR]   symbol:   class ErrorResponse
[ERROR]   location: class com.rapidapi.apps.product.ProductResource
[ERROR] /home/kesi/workspace/java/yt/apiburn/rapidapi/apps/src/main/java/com/rapidapi/apps/product/ProductResource.java:[48,41] cannot find symbol
[ERROR]   symbol:   class ErrorResponse
[ERROR]   location: class com.rapidapi.apps.product.ProductResource
[ERROR] /home/kesi/workspace/java/yt/apiburn/rapidapi/apps/src/main/java/com/rapidapi/apps/product/ProductResource.java:[59,29] cannot find symbol
[ERROR]   symbol:   class ErrorResponse
[ERROR]   location: class com.rapidapi.apps.product.ProductResource
[ERROR] -> [Help 1]
[ERROR] 
[ERROR] To see the full stack trace of the errors, re-run Maven with the -e switch.
[ERROR] Re-run Maven using the -X switch to enable full debug logging.
[ERROR] 
[ERROR] For more information about the errors and possible solutions, please read the following articles:
[ERROR] [Help 1] http://cwiki.apache.org/confluence/display/MAVEN/MojoFailureException
[ERROR] 
[ERROR] After correcting the problems, you can resume the build with the command
[ERROR]   mvn <args> -rf :apps

```

---

Great! The core module compiled successfully, but now the apps module is missing Jetty dependencies. I need to add the missing Jetty dependencies to the apps module:

---

Add missing Jetty dependencies to apps module

---

Add core dependency and Jetty dependencies to apps module, remove redundant version

---



Now the HelloService should be able to import BaseService from the core module since we've added the core dependency and fixed the circular dependency issue.