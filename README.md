<head>
  <meta name="Momento Kotlin Client Library Documentation" content="Kotlin client software development kit for Momento Cache">
</head>
<img src="https://docs.momentohq.com/img/logo.svg" alt="logo" width="400"/>

[![project status](https://momentohq.github.io/standards-and-practices/badges/project-status-incubating.svg)](https://github.com/momentohq/standards-and-practices/blob/main/docs/momento-on-github.md)
[![project stability](https://momentohq.github.io/standards-and-practices/badges/project-stability-alpha.svg)](https://github.com/momentohq/standards-and-practices/blob/main/docs/momento-on-github.md)

# Momento Kotlin Client Library

:warning: Experimental SDK :warning:

This is an incubating project; it may or may not achieve official supported status, and the APIs are subject to
backward incompatible changes.  For more info, click on the incubating badge above.


Momento Cache is a fast, simple, pay-as-you-go caching solution without any of the operational overhead
required by traditional caching solutions.  This repo contains the source code for the Momento Kotlin client library.

To get started with Momento you will need a Momento Auth Token. You can get one from the [Momento Console](https://console.gomomento.com).

* Website: [https://www.gomomento.com/](https://www.gomomento.com/)
* Momento Documentation: [https://docs.momentohq.com/](https://docs.momentohq.com/)
* Getting Started: [https://docs.momentohq.com/getting-started](https://docs.momentohq.com/getting-started)
* Kotlin SDK Documentation: [https://docs.momentohq.com/develop/sdks/kotlin](https://docs.momentohq.com/develop/sdks/kotlin)
* Discuss: [Momento Discord](https://discord.gg/3HkAKjUZGq)

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

```kotlin
package software.momento.example.doc_examples

import kotlinx.coroutines.runBlocking
import software.momento.kotlin.sdk.CacheClient
import software.momento.kotlin.sdk.auth.CredentialProvider
import software.momento.kotlin.sdk.config.Configurations
import software.momento.kotlin.sdk.responses.cache.GetResponse
import kotlin.time.Duration.Companion.seconds

fun main() = runBlocking {
    CacheClient(
        CredentialProvider.fromEnvVar("MOMENTO_API_KEY"),
        Configurations.Laptop.latest,
        60.seconds
    ).use { client ->
        val cacheName = "cache"

        client.createCache(cacheName)

        client.set(cacheName, "key", "value")

        when (val response = client.get(cacheName, "key")) {
            is GetResponse.Hit -> println("Hit: ${response.value}")
            is GetResponse.Miss -> println("Miss")
            is GetResponse.Error -> throw response
        }
    }
}

```

## Getting Started and Documentation

Documentation coming soon on the [Momento Docs website](https://docs.momentohq.com).

## Examples

Examples coming soon.

## Developing

If you are interested in contributing to the SDK, please see the [CONTRIBUTING](./CONTRIBUTING.md) docs.

----------------------------------------------------------------------------------------
For more info, visit our website at [https://gomomento.com](https://gomomento.com)!
