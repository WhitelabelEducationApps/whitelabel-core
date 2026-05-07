# Whitelabel KMP Core

A reusable Kotlin Multiplatform core library providing generic abstractions, ViewModels, and UI components for catalog-based mobile applications with multilingual support.

## Used By

- [MuseumKMP](https://github.com/WhitelabelEducationApps/MuseumKMP) - UNESCO World Heritage Sites
- [HerbalRedo](https://github.com/WhitelabelEducationApps/HerbalRedo) - Medicinal Plants Encyclopedia

## Architecture

```n
core/src/
commonMain/kotlin/com/whitelabel/core/
  AppConfig.kt              # Feature flags per app
  domain/                   # DisplayableItem, ItemRepository, Result
  presentation/
    home/                 # HomeViewModel, ItemGrouper, ViewModeConfig
    detail/               # ItemDetailViewModel, ZoomableImageScreen
    language/             # LanguageSelectionScreen + ViewModel
    components/           # SearchTopAppBar
  theme/                    # AppThemeConfig
androidMain/                  # Android-specific implementations
iosMain/                      # iOS stubs (ready)

```n
## Core Abstractions

### DisplayableItem
Universal interface for any catalog item. Provides localized name/description/category getters, image URLs, coordinates, favorites, and per-item color hints.

### ItemRepository<T : DisplayableItem>
Generic reactive repository contract:
- getAllItems(): Flow<Result<List<T>>>
- searchItems(query): Flow<Result<List<T>>>
- getItemCount(): Result<Long>
- toggleFavorite(id)

### HomeViewModel<T : DisplayableItem>
Base ViewModel that wires search, language, an optional item filter, and a refresh trigger into a single uiState: StateFlow<HomeUiState<T>>. Pass an itemFilter: Flow<(T) -> Boolean> to enable reactive filtering (used by the location filter feature).

totalItemCount: StateFlow<Long> exposes the unfiltered total for use in UI banners.

### ItemGrouper<T : DisplayableItem>
Strategy interface for grouping items. Each app can provide its own implementation via Koin override (e.g. group by category, by country, alphabetically).

### AppConfig
Data class of feature flags:
- enableMap - show map view toggle
- enableCategories - show category labels on cards
- enableLocationFilter - show location-based filtering toggle

## Language Support

16 languages via SupportedLanguage enum: English, French, Spanish, German, Italian, Portuguese, Russian, Arabic, Chinese, Japanese, Romanian, Turkish, Hindi, Hungarian, Polish, Dutch.

## Integration

1. Add as a Git submodule and include the build:
   includeBuild("whitelabel-core")

2. Implement DisplayableItem and ItemRepository<T> for your domain model (or use whitelabel-platform's CatalogItem).

3. Wire HomeViewModel in your DI module, passing your repository and any filter flows.

4. Use AppConfig to gate features per app without touching platform code.

## Dependencies

- Kotlin Multiplatform
- Jetpack Compose Multiplatform
- Kotlin Coroutines and Flow
- Koin (dependency injection)
- Kermit (logging)
- Material Design 3

## License

Part of the Whitelabel Educational Apps ecosystem.
