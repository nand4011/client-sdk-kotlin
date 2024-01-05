{{ ossHeader }}

## Packages

The Kotlin SDK is available on the Sonatype Central snapshots repo:

### Gradle

```kotlin
repositories {
    maven("https://s01.oss.sonatype.org/content/repositories/snapshots/")
}

dependencies {
    implementation("software.momento.kotlin:sdk:0.1.0-SNAPSHOT")
}
```

### Maven

```xml
<repository>
    <id>sonatype-snapshots</id>
    <name>Sonatype Snapshots</name>
    <url>https://s01.oss.sonatype.org/content/repositories/snapshots/</url>
</repository>

<dependency>
    <groupId>software.momento.kotlin</groupId>
    <artifactId>sdk</artifactId>
    <version>0.1.0-SNAPSHOT</version>
</dependency>
```

## Usage

Examples coming soon.

## Getting Started and Documentation

Documentation coming soon on the [Momento Docs website](https://docs.momentohq.com).

## Examples

Examples coming soon.

## Developing

If you are interested in contributing to the SDK, please see the [CONTRIBUTING](./CONTRIBUTING.md) docs.

{{ ossFooter }}
