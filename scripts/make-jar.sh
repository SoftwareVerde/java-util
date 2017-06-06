#!/bin/bash

rm -rf out/bin 2>/dev/null
mkdir -p out/bin

version='0.1.0'

./gradlew makeJar && cp build/libs/java-util-${version}.jar out/bin/java-util-${version}.jar && chmod 770 out/bin/java-util-${version}.jar

