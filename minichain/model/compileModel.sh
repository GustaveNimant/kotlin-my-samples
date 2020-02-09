kotlinc BandWidthInfo.kt -include-runtime -d BandWidthInfo.jar
kotlinc Key.kt -include-runtime -d Key.jar
kotlinc MessageWithCode.kt -include-runtime -d MessageWithCode.jar
kotlinc NameValue.kt -include-runtime -d NameValue.jar
kotlinc NamedHash.kt -include-runtime -d NamedHash.jar
kotlinc Path.kt -include-runtime -d Path.jar
kotlinc VersionInfo.kt -include-runtime -d VersionInfo.jar

kotlinc KeyV2.kt -cp ../com/squareup/moshi/moshi-1.4.0.jar -include-runtime -d KeyV2.jar


