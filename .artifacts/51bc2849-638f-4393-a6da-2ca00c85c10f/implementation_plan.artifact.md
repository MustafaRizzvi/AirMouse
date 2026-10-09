# Fix Kotlin Plugin Conflict

The project is failing to sync with the error: `Cannot add extension with name 'kotlin', as there is an extension already registered with that name.` This occurs because the `kotlin-android` and `kotlin-compose` plugins are being applied in a way that causes a version or configuration conflict. Specifically, `kotlin-android` is missing a version definition in the root project and is not managed via the Version Catalog, leading to inconsistencies when applied alongside the Compose compiler plugin.

## Proposed Changes

### Build Configuration

#### [MODIFY] [libs.versions.toml](file:///C:/Users/musta/AndroidStudioProjects/FinalAirMouse/gradle/libs.versions.toml)
- Add the `kotlin-android` plugin definition to the `[plugins]` section, using the same Kotlin version as the Compose plugin.

#### [MODIFY] [build.gradle.kts (root)](file:///C:/Users/musta/AndroidStudioProjects/FinalAirMouse/build.gradle.kts)
- Declare the `kotlin-android` plugin in the root `plugins` block with `apply false` to ensure a consistent version is used across all modules.

#### [MODIFY] [app/build.gradle.kts](file:///C:/Users/musta/AndroidStudioProjects/FinalAirMouse/app/build.gradle.kts)
- Update the `plugins` block to use `alias()` for all plugins (Android Application, Kotlin Android, and Kotlin Compose) for consistency and to ensure they use the versions defined in the catalog.

## Verification Plan

### Automated Tests
- Perform a Gradle Sync to ensure the "extension already registered" error is resolved.
- Run `./gradlew :app:assembleDebug` to verify the build completes successfully.

### Manual Verification
- Check that the IDE no longer reports sync errors in the Build tool window.
