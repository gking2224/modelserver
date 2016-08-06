#!/bin/bash

wd=`pwd`
echo Building BuildTools plugin
cd ../BuildToolsGradlePlugin
./gradlew --daemon -q install

echo Building WebApp plugin
cd ../WebAppGradlePlugin
./gradlew --daemon -q install

echo Building Database plugin
cd ../DatabaseBuildGradlePlugin
./gradlew --daemon -q install

cd $wd
