# iGuardApp
This project is intended to perform Psycho-Analysis on the captured image. It uses the [Clarifai Java Client](https://github.com/Clarifai/clarifai-java) to perform Concept recognition.

## Building and Running
To set your environment up for Android development, you'll need to install the
[Java SE Development Kit (JDK)](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
and [Android Studio](https://developer.android.com/studio/index.html).

Replace `YOUR_API_KEY_HERE` with your [Clarifai API Key](http://blog.clarifai.com/introducing-api-keys-a-safer-way-to-authenticate-your-applications/) in [`strings.xml`](app/src/main/res/values/strings.xml).

This project will compile in the standard manner through Android Studio or `./gradlew clean build` in your terminal.

# Dependencies
- ClarifAI API
- Firebase API

