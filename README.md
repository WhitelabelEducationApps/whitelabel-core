# Whitelabel KMP Core

A reusable Kotlin Multiplatform core library providing generic abstractions, ViewModels, and UI components for catalog-based mobile applications with multilingual support.

## Architecture

```
core/src/
├── commonMain/kotlin/com/whitelabel/core/
│   ├── AppConfig.kt              # Feature flags per app
│   ├── domain/                   # DisplayableItem, ItemRepository, Result
│   ├── presentation/
│   │   ├── home/                 # HomeViewModel, ViewModeConfig
│   │   ├── detail/               # ItemDetailViewModel, ZoomableImageScreen
│   │   ├── language/             # LanguageSelectionScreen + ViewModel
│   │   └── components/           # SearchTopAppBar
│   └── theme/                    # AppThemeConfig
├── androidMain/                  # Android-specific implementations
└── iosMain/                      # iOS stubs (ready)
```

## Core abstractions

### `DisplayableItem`
Universal interface for any catalog item. Provides localized name/description/category getters, image URLs, coordinates, favorites, and per-item color hints.

### `ItemRepository<T : DisplayableItem>`
Generic reactive repository contract:
- `getAllItems(): Flow<Result<List<T>>>`
- `searchItems(query): Flow<Result<List<T>>>`
- `getItemCount(): Result<Long>`
- `toggleFavorite(id)`

### `HomeViewModel<T : DisplayableItem>`
Base ViewModel that wires search, language, an optional item filter, and a refresh trigger into a single `uiState: StateFlow<HomeUiState<T>>`. Pass an `itemFilter: Flow<(T) -> Boolean>` to enable reactive filtering (used by the location filter feature).

```kotlin
HomeViewModel(
    repository    = catalogRepository,
    ...
    itemFilter    = locationFilter   // Flow<(CatalogItem) -> Boolean>
)
```

`totalItemCount: StateFlow<Long>` exposes the unfiltered total for use in UI banners.

### `AppConfig`
Data class of feature flags. Pass one instance to `HomeScreen`.

```kotlin
AppConfig(
    enableMap            = true,
    enableCategories     = true,
    enableLocationFilter = false,   // default OFF
)
```

## Language support

16 languages via `SupportedLanguage` enum: English, French, Spanish, German, Italian, Portuguese, Russian, Arabic, Chinese, Japanese, Romanian, Turkish, Hindi, Hungarian, Polish, Dutch.

## Integration

1. Add as a Git submodule and include the build:
   ```kotlin
   // settings.gradle.kts
   includeBuild("whitelabel-core")
   ```

2. Implement `DisplayableItem` and `ItemRepository<T>` for your domain model.

3. Wire `HomeViewModel` in your DI module, passing your repository and any filter flows.

4. Use `AppConfig` to gate features per app without touching platform code.

## Dependencies

- Kotlin Multiplatform
- Jetpack Compose Multiplatform
- Kotlin Coroutines & Flow
- Koin (dependency injection)
- Kermit (logging)
- Material Design 3

## License

Part of the Whitelabel Educational Apps ecosystem.
