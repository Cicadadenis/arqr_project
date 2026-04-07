import os

files = {
    "gradlew": """#!/bin/sh
# Gradle wrapper script
DIR="$(cd "$(dirname "$0")" && pwd)"
exec "$DIR/gradle/wrapper/gradle-wrapper.jar" "$@"
""",
    "gradlew.bat": """@echo off
set DIR=%~dp0
java -jar "%DIR%\\gradle\\wrapper\\gradle-wrapper.jar" %*
""",
    "gradle/wrapper/gradle-wrapper.properties": """distributionBase=GRADLE_USER_HOME
distributionPath=wrapper/dists
zipStoreBase=GRADLE_USER_HOME
zipStorePath=wrapper/dists
distributionUrl=https\\://services.gradle.org/distributions/gradle-8.2-bin.zip
""",
    "settings.gradle": "rootProject.name = 'arqr_project'\ninclude ':app'\n",
    "build.gradle": """plugins {
    id 'com.android.application' version '8.2.0' apply false
    id 'org.jetbrains.kotlin.android' version '1.9.0' apply false
}
""",
    "codemagic.yaml": """workflows:
  android-workflow:
    name: Android Build
    max_build_duration: 60
    environment:
      vars:
        GRADLE_OPTS: "-Xmx2048m"
    scripts:
      - name: Build APK
        script: |
          ./gradlew clean assembleDebug
    artifacts:
      - app/build/outputs/apk/debug/*.apk
"""
}

for path, content in files.items():
    dir_name = os.path.dirname(path)
    if dir_name:
        os.makedirs(dir_name, exist_ok=True)
    with open(path, "w", encoding="utf-8") as f:
        f.write(content)

print("✅ Gradle Wrapper и codemagic.yaml созданы!")
