# 2021-10-22 (Just after midnight)

Viewed build with:

```
mvn buildplan:list
```

Published with:

```
mvn deploy
```

That got a v1 released. Note to self: No seatbelts deployments if there's no -SNAPSHOT suffix of the version!

# 2021-10-21

## Generated project

```
mvn archetype:generate -DarchetypeGroupId=org.apache.maven.archetypes -DarchetypeArtifactId=maven-archetype-simple -DarchetypeVersion=1.4
```

https://maven.apache.org/archetypes/maven-archetype-simple/

## Wrote the code

https://github.com/hiljusti/singleton

## Setup publishing

https://central.sonatype.org/publish/publish-maven/

