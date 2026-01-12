# CommonLibrary

A shared Android library providing base architectures, state management utilities, and common UI components for the WeatherSDK project.

## Features

- **Domain Driven Design (DDD)**: Base classes for UseCases that handle boilerplate for Flow and State management.
- **Base Architecture**: Standardized `BaseRefactorViewModel` with built-in application context.
- **State Management**: Robust `BaseState` sealed class system for handling Loading, Success, and Error states.
- **Compose UI Components**: 
    - `StatesLayoutCompose`: A wrapper to automatically handle different UI states.
    - `InternalServerErrorCompose`: Standardized error screens.
- **Networking Helpers**: Retrofit extensions (`asBasState()`) and constant helpers for error code handling.

## Components

### Domain UseCases
The library provides base classes to standardize business logic execution:
- `BaseSealedUseCase<T, Params>`: Emits a `Flow<BaseState<T>>`. It ensures that every domain operation returns a consistent state object.

Example Usage:
```kotlin
class GetWeatherUseCase() : BaseSealedUseCase<Weather, Params>() {
    override suspend fun invoke(params: Params): Flow<BaseState<Weather>> = flow {
        emit(repository.getData().asBasState())
    }
}
```

### BaseState
Used to represent the status of data operations:
- `Initial`, `Loading`, `BaseStateLoadedSuccessfully<T>`.
- Error states: `NoInternetError`, `NotDataFound`, `InternalServerError`, `NoAuthorized`, `InvalidData`, `ValidationError`.

### BaseRefactorViewModel
An `AndroidViewModel` subclass that provides:
- Application context accessibility.
- Lifecycle-aware scope.

### StatesLayoutCompose
A reusable Composable that observes a `BaseState` and renders the appropriate UI:
```kotlin
StatesLayoutCompose(
    state = viewModel.uiState,
    onRetry = { viewModel.retry() }
) { data ->
    // Your Success UI here
}
```

## Installation

### Local Module
Add the library as a local project dependency:
```gradle
dependencies {
    implementation project(':CommonLibrary')
}
```

### Remote (JitPack)
Add the following to your `build.gradle`:
```gradle
dependencies {
    implementation 'com.github.adhamkhwaldeh.WeatherSdk:CommonLibrary:1.0.9'
}
```

## Best Practices

1. **Specific Exceptions**: When using `try-catch` in UseCases, prefer catching `Exception` instead of `Throwable` to avoid capturing system-level Errors.
2. **Koin DSL**: Use `viewModelOf(::YourViewModel)` from `org.koin.core.module.dsl` for modern, concise dependency injection.

## Documentation
Technical API documentation can be generated using Dokka:
```bash
./gradlew :CommonLibrary:dokkaHtml
```
The output will be available in `CommonLibrary/build/dokkaDir`.
