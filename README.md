## WebSocket RAT

WebSocket RAT is a highly secure Remote Administration Tool with persistence support that can remotely control both Linux and Windows computers.

### Warning
This project is not intended for malicious uses. Please do not commit any illegal crimes with WebSocket-RAT.

### Changing configuration
1. `cp config.properties.example config.properties`
2. `emacs config.properties` (or whatever editor you like)

### Compiling a module
```
./mvnw clean compile -pl :<module_name> -am
```

(If you're on Windows, use `./mvnw.cmd` instead.)

You can also use `./mvnw clean package` which will make a GraalVM Native Image for the client.

### Start server directly
`./mvnw clean compile -pl :rat-server -am exec:java`

### Updating Dependencies
If you want to update the dependencies, you can run:
```
./mvnw versions:display-dependency-updates
```

If you see anything, you can run `./mvnw versions:use-latest-versions`. This will NOT update dependencies with a new major release.

### Notice
This project is NOT finished yet. Things that need to be implemented include:
- Admin interface
- WebSocket Endpoints
- Client features

### Upcoming Features
- Generating a self-signed SSL certificate during the build process
- Automatically copying the private and public keys into the server's resources
- Automatically copying the public key into the client's resources
- Setting up the Javalin SSL plugin
- ProGuard obfuscation and packing
- Systemd auto restart in container
- NTFY Logback appender in server container for severe errors

### License
While I usually prefer Unlicense, this repository uses MIT due to potential abuse by malicious actors.

### Contributing
I welcome contributions to this repository. I suggest forking the repository, cloning it, making your changes, pushing it, and opening a pull request on the main repo.

Please use Allman style (putting the braces on the next line) and use hard tabs instead of spaces. If you use an IDE with auto-formatting, such as IntelliJ or Eclipse, you should set it to do that before making your changes.