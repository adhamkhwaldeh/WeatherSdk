# CommonSDK

A foundational library for building robust, configurable Android SDKs. This module provides base implementations for SDK lifecycle, configuration management, and global error handling.

## Features

- **Standardized SDK Architecture**: Base classes (`BaseSDK`, `BaseSDKImpl`) to maintain consistency across different SDK implementations.
- **Configurable Options**: Flexible configuration system using `BaseSDKOptionBuilder`.
- **Global Error Handling**: Integrated `BaseGlobalErrorSDK` for centralized error reporting and management.
- **Pluggable Logging System**: Robust logging proxy to bridge SDK logs to your application's logging infrastructure.
- **Listener Management**: Utilities for managing multiple SDK state and status listeners.

## Core Components

### BaseSDK
The root interface for all SDKs in the project. It defines the contract for initializing and managing the SDK's lifecycle.

### BaseConfigSDK
Provides standardized configuration management, allowing SDKs to be initialized with specific options (e.g., API keys, log levels, debug modes).

### Logging System
CommonSDK includes a proxy-based logging system. You can provide your own logger implementation to capture SDK events:

```kotlin
class MyLogger : Logger {
    override fun log(level: LogLevel, tag: String, message: String, throwable: Throwable?) {
        // Bridge to Timber, Logcat, or Firebase
    }
}

// During SDK initialization:
val sdk = MySDK.Builder(context, apiKey)
    .addLogger(MyLogger())
    .build()
```

### BaseGlobalErrorSDK
A centralized system to notify listeners about critical errors (e.g., Auth errors, API failures) regardless of where they occur in the SDK flow.

## Installation

### Local Module
Add the library as a local project dependency:
```gradle
dependencies {
    implementation project(':CommonSDK')
}
```

### Remote (JitPack)
Add the following to your `build.gradle`:
```gradle
dependencies {
    implementation 'com.github.adhamkhwaldeh.WeatherSdk:CommonSDK:1.0.7'
}
```

## Usage Example

```kotlin
class MyCustomSDK private constructor(
    context: Context,
    options: MyOptions
) : BaseSDKImpl<MyStatusListener, MyErrorListener, MyOptions>(context, options) {
    // Implement custom logic here
}
```

## Documentation
Technical API documentation can be generated using Dokka:
```bash
./gradlew :CommonSDK:dokkaHtml
```
The output will be available in `CommonSDK/build/dokkaDir`.
