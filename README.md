
<p align="center">
  <img src="docs/screenshots/rustore_logo.png" width="800">
</p>

<h1 align="center">
    RuStore Publishing Gradle Plugin
</h1>

The plugin allows publishing Android release build files (`*.apk` and `*.aab`) to the **RuStore** using the official [RuStore Publish API](https://console.rustore.ru).

:construction: _This is an unofficial plugin. I made it for internal use and decided to share it with the community._

:exclamation Make sure before using it:
1) You have at least 1 published version of the application.
2) The package matches the version of the application that was published.

# Table of contents
<!-- TOC -->
- [Features](#features)
<!-- /TOC -->

# Features

The following features are available:

- :white_check_mark: Publish APK or AAB build files to RuStore
- :white_check_mark: Automatically submit builds for store review
- :white_check_mark: Publish updates to all users after approval
- :white_check_mark: Configure settings for different build types and flavors
- :white_check_mark: Fully supports Gradle Plugin Portal and Gradle DSL
- :white_check_mark: Supports Gradle 8.+
