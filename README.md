## WebSocket RAT

WebSocket RAT is a highly secure Remote Access Trojan based on Java 21 and Javalin structured as a multi-module Maven project. It can remotely control both Linux and Windows computers and has persistence support.

### Warning
This project is not intended for malicious uses. Please do not commit any illegal crimes with WebSocket-RAT.
Also, this project is INCOMPLETE and not ready to be used in a production state yet.
    
### Upcoming Features
- TLS reverse proxy with Caddy and Docker-Compose
- ProGuard obfuscation and packing
- Systemd auto restart in container
- NTFY Logback appender in server container for severe errors

### Compiling a module
```
./mvnw clean package -pl <module_name>
```
(If you're on Windows, use `./mvnw.cmd`.)

This will create a normal JAR, a fat JAR, and a GraalVM Native Image executable, which is the most lightweight.

### Run server with Docker
If you have Docker on your computer, you can run the server with `sudo docker build -t rat-server .` after you've packaged it with Maven.

### Contributing
I welcome contributions to this repository. I suggest forking the repository, cloning it, making your changes, pushing it, and opening a pull request on the main repo.

Please use Allman style (putting the braces on the next line) and use hard tabs instead of spaces. If you use an IDE with auto-formatting, such as IntelliJ or Eclipse, you should set it to do that before making your changes.