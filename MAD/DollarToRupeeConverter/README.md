# Dollar to Rupee Converter (Kotlin, Android Studio)

A minimal single-screen Android app: enter a USD amount, tap Convert, see the INR equivalent.
Built to teach event-driven programming and core Kotlin syntax on Android.

## How to open in Android Studio

1. Unzip this project anywhere on your computer.
2. Open Android Studio -> File -> Open -> select the `DollarToRupeeConverter` folder (the one
   containing this README and `settings.gradle.kts`) -> OK.
3. Android Studio will start a Gradle sync automatically. This project does not ship the
   `gradlew` wrapper scripts or the wrapper jar (to keep the download small) -- if Android
   Studio prompts about the Gradle wrapper, click "OK" / "Use default Gradle wrapper" and let
   it regenerate them, or simply allow the sync to use Android Studio's bundled Gradle.
4. If prompted to upgrade the Android Gradle Plugin or Kotlin version, accepting the upgrade
   is safe -- the project will still work the same way.
5. Once sync finishes, pick a device or emulator from the toolbar dropdown and press Run (v).

## Note on build file format

This project uses Kotlin DSL build files (`build.gradle.kts`, `settings.gradle.kts`) rather
than Groovy (`build.gradle`). This is also what current Android Studio uses by default for new
projects. If you ever hand-edit these files, keep using Kotlin DSL syntax (e.g.
`isMinifyEnabled = false`, `compileSdk = 34`) -- mixing in Groovy-style syntax
(e.g. `minifyEnabled false`) in a `.kts` file, or vice versa in a `.gradle` file, is a common
source of a "Could not set unknown property" sync error.

## Project structure

```
DollarToRupeeConverter/
|-- settings.gradle.kts         declares this project has one module: app
|-- build.gradle.kts            project-level Gradle config (plugin versions)
|-- gradle.properties           build environment settings
|-- gradle/wrapper/              Gradle wrapper version pin
`-- app/
    |-- build.gradle.kts         module-level config: SDK versions, dependencies
    `-- src/main/
        |-- AndroidManifest.xml  declares MainActivity as the app's entry point
        |-- java/com/example/dollartorupee/
        |   `-- MainActivity.kt  all the app's logic
        `-- res/
            |-- layout/activity_main.xml   the screen's UI layout
            `-- values/           strings.xml, colors.xml, themes.xml
```

## Changing the exchange rate

Open `MainActivity.kt` and change the `exchangeRate` value near the top of the class.
The app displays whatever rate is set there directly on screen, so students can see exactly
what number is being used.

## Suggested student exercises

- Add a Rupees -> Dollars reverse-conversion button.
- Replace the hardcoded rate with a live rate fetched from a currency API (a natural bridge
  to Unit II's networking topic: Retrofit, JSON parsing).
- Persist the last-used amount with SharedPreferences so it survives an app restart.
- Add a Spinner to let the user pick between multiple currencies, not just USD/INR.
