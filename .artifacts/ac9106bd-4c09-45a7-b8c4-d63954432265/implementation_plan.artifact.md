# Fix Gradle Sync Error: "Cannot add extension with name 'kotlin'"

The project is failing to sync because of a conflict when applying the Kotlin Android plugin. This is typically caused by inconsistent plugin versions or improper plugin management in a multi-project setup (even with a single `:app` module).

## User Review Required

> [!IMPORTANT]
> The fix involves centralizing plugin management in `libs.versions.toml` and the root `build.gradle.kts`. This is the recommended approach for modern Android projects.

## Proposed Changes

### Build Configuration

#### [MODIFY] [libs.versions.toml](file:///C:/Users/musta/AndroidStudioProjects/FinalAirMouse/gradle/libs.versions.toml)
- Add `kotlin-android` plugin definition to the `[plugins]` section, using the same Kotlin version as `kotlin-compose`.

#### [MODIFY] [build.gradle.kts (root)](file:///C:/Users/musta/AndroidStudioProjects/FinalAirMouse/build.gradle.kts)
- Add `alias(libs.plugins.kotlin.android) apply false` to the `plugins` block to manage its version centrally.

#### [MODIFY] [app/build.gradle.kts](file:///C:/Users/musta/AndroidStudioProjects/FinalAirMouse/app/build.gradle.kts)
- Update the `plugins` block to use `alias` for all plugins (`android-application`, `kotlin-android`, and `kotlin-compose`). This ensures they all use the centrally managed versions and prevents registration conflicts.

## Verification Plan

### Automated Tests
- Run `gradlew sync` (or trigger a sync in Android Studio) to verify the build configuration is valid.
- Run `gradlew :app:assembleDebug` to ensure the project builds successfully.
