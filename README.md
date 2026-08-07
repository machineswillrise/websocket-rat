## WebSocket RAT

WebSocket RAT is a highly secure Remote Access Trojan based on Java 21 and Javalin structured as a multi-module Maven project. It can remotely control both Linux and Windows computers and has persistence support.

### Compiling a module
```
./mvnw clean compile -pl <module_name>
```
(If you're on Windows, use `./mvnw.cmd`.)

If you run `mvn clean package` on a module, it'll generate a GraalVM Native Image that you can use too, which uses less resources and doesn't require a host JRE. That makes it harder for it to be detected and reduces the friction required to run it.
### Contributing
I welcome contributions to this repository. I suggest forking the repository, cloning it, making your changes, pushing it, and opening a pull request on the main repo.

Please use Allman style (putting the braces on the next line) and use hard tabs instead of spaces. If you use an IDE with auto-formatting, such as IntelliJ or Eclipse, you should set it to do that before making your changes.