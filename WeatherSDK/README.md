# WeatherSDK

A robust Android SDK for retrieving current weather and multi-day forecasts. Built on top of `CommonSDK` and `CommonLibrary` for a consistent, state-driven architecture.

## Features

- **Current Weather**: Fetch real-time weather data for any city.
- **Weather Forecast**: Retrieve multi-day/hourly forecasts.
- **State-Driven**: Uses `BaseState` to handle loading, success, and error states natively.
- **Reactive**: Built with Kotlin Coroutines and Flow.
- **Lifecycle Aware**: Integrated with `MutableLiveData` for SDK status tracking.
- **Security**: Built-in API key validation and secure local storage.

## Initialization

Initialize the SDK using the `Builder` pattern in your Application class or DI module:

```kotlin
val weatherSDK = WeatherSDK.Builder(context, "YOUR_API_KEY")
    .setupOptions(
        WeatherSDKOptions.Builder("YOUR_API_KEY")
            .setLogLevel(LogLevel.DEBUG)
            .build()
    )
    .addLogger(MyCustomLogger()) // Optional
    .build()
```

## Usage

### 1. Fetch Current Weather
```kotlin
lifecycleScope.launch {
    weatherSDK.currentWeatherUseCase("London").collect { state ->
        when (state) {
            is BaseState.Loading -> showLoading()
            is BaseState.BaseStateLoadedSuccessfully -> displayWeather(state.data)
            is BaseState.InternalServerError -> showError(state.errorMessage)
            // Handle other states...
        }
    }
}
```

### 2. Fetch Weather Forecast
```kotlin
val params = ForecastWeatherUseCaseParams(cityName = "New York", hours = 24)
lifecycleScope.launch {
    weatherSDK.forecastWeatherUseCase(params).collect { state ->
        // Handle forecast state
    }
}
```

### 3. Observe SDK Status
Monitor the SDK's internal status (e.g., when a forecast is requested via internal navigation):
```kotlin
weatherSDK.sdkStatus.observe(this) { status ->
    if (status is WeatherSdkStatus.OnLaunchForecast) {
        println("Forecast requested for: ${status.cityName}")
    }
}
```

## Data Models

- **CurrentWeatherResponse**: Contains temperature, humidity, wind speed, and condition details.
- **ForecastResponse**: Includes a list of forecast intervals with periodic weather data.

## Installation

### Local Module
```gradle
dependencies {
    implementation project(':WeatherSDK')
}
```

### Remote (JitPack)
```gradle
dependencies {
    implementation 'com.github.adhamkhwaldeh.WeatherSdk:WeatherSDK:1.0.9'
}
```

## Documentation
Generate detailed API documentation using Dokka:
```bash
./gradlew :WeatherSDK:dokkaHtml
```
The output will be available in `WeatherSDK/build/dokkaDir`.
