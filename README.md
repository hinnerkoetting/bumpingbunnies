Sourcecode for Bumping Bunnies.
See http://hinnerkoetting.github.io/ and https://play.google.com/store/apps/details?id=de.oetting.bumpingbunnies

Useful commands:
mvn install: build all projects
mvn install -P deploy: deploy on connected android devices
mvn install -P release -Dpassword=XYZ: create signed android sources

Important Projects:
bunnies-pc: PC version of this game
bunnies-level: Level-Editor (PC only)
bunnies-android: Android-Version
bunnies-network-tester: Test network. Can send fake messages and show network resource usage 