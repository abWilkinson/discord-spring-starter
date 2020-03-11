[ ![version][] ][download]
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/0f34e92024384457b594b87aa1db7ea3)](https://www.codacy.com/manual/ab.wilkinson/discord-spring-starter?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=abWilkinson/discord-spring-starter&amp;utm_campaign=Badge_Grade)
[![Codacy Badge](https://api.codacy.com/project/badge/Coverage/0f34e92024384457b594b87aa1db7ea3)](https://www.codacy.com/manual/ab.wilkinson/discord-spring-starter?utm_source=github.com&utm_medium=referral&utm_content=abWilkinson/discord-spring-starter&utm_campaign=Badge_Coverage)
# Discord Spring Starter
This project is a library to quickly get started creating a discord bot using the [JDA](https://github.com/DV8FromTheWorld/JDA) library and Spring. By wrapping up JDA with Spring
we can easily use the JDA library while cleanly separating each command into individual components.

To get started with a bot all you need is to add discord-spring-starter dependency, set your bot's secret key and implement a provided interface.

## Usage

### Add the library to your Java project
Latest Version:
[ ![version][] ][download]
#### Gradle
```
dependencies {
	compile 'abwilkinson:discord-spring-starter:VERSION'
}

repositories {
	jcenter()
}

```

#### Maven
```
<dependency>
	<groupId>abwilkinson</groupId>
	<artifactId>discord-spring-starter</artifactId>
	<version>VERSION</version>
</dependency>
```

```
<repository>
    <id>jcenter</id>
    <name>jcenter-bintray</name>
    <url>https://jcenter.bintray.com</url>
</repository>
```

### Add Discord bot secret
Add your secret from discord to your properties file
e.g. in application.yml:

```
jda:
  secret: <Secret here>
```

### Create your component

If you want to listen only to commands (Starting with !) then implement ```CommandEventHandler```. To listen
to all messages on the channel to for example implement a state machine, implement ```StatefulEventHandler```

The ```match``` method should return true if this handler should be actioned for a given command.

For example for a bot which listens for !ping and responds with the message 'pong':

```
@Component
public class PingHandler implements CommandEventHandler {

    @Override
    public boolean messageReceived(MessageReceivedEvent event, InputValues message) {
        event.getChannel().sendMessage("pong").queue();
        return true;
    }

    @Override
    public boolean matches(InputValues inputValues) {
        //We only want to use this if the message is "!ping"
        return "ping".equals(inputValues.getCommand());
    }
```

### Using JDA

As this is just a wrapper around JDA, other than implementing an EventHandler interface JDA can be used as documented at 
[JDA Documentation](https://github.com/DV8FromTheWorld/JDA).

JDA can be accessed directly be accessing the JDA bean directly
```
@Autowired
private JDA jda;
```

[version]: https://api.bintray.com/packages/abwilkinson/maven/discord-spring-starter/images/download.svg
[download]: https://bintray.com/abwilkinson/maven/discord-spring-starter/_latestVersion
