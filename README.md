# command-man
terminal command manager


## build.gradle 
1. Set repositories
    ```gradle
    allprojects {
      repositories {
        ...
        maven { url 'https://jitpack.io' }
      }
    }
    ```
2. Set dependencies
    ```gradle
    dependencies {
      implementation 'com.github.avaj-java:command-man:0.5.6'
    }
    ```
