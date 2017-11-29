# Unmaintained!
The development of Jitsi for Android has been stopped in early 2015. Issues and Pull Requests will not be addressed.

******

# Jitsi for Android

Jitsi for Android is an Android port of the [Jitsi] project: The most feature-rich communicator with support for encrypted audio/video, chat and presence over SIP and XMPP.

## Usage with IntelliJ

1. Make sure that you have [Java] and [Android SDK] installed on your system and [IntelliJ] version is up to date(13.0.2).
2. (Optional) Assuming that [Jitsi for desktop] project is in the same parent directory you can call "copy-jitsi-bundles" ant target. This will sync Jitsi bundles. Ant targets can be found in "Ant Build" tools window(View->Tool Windows->Ant Build).
3. Before building for the first time call "setup-libs" ant target. This will process jitsi bundles and place all required libraries in /libs folder.
4. Now you can use IntelliJ IDE to run/debug/test Jitsi for Android like any other application.

## Usage with ANT

After updating library bundles, when building for the first time or after clean:

    ant setup-libs
To make the project:

    ant make

To rebuild (clean and make):

    ant rebuild

To run the project (will install the apk and will run it on default test device):

    ant run

To make and run the project after modification:

    ant make run

## Sources

To obtain sources for .jar files located in lib folder checkout jitsi_android
 branch of jitsi and libjitsi projects.
 
 https://github.com/jitsi/jitsi/tree/jitsi_android
 
 https://github.com/jitsi/libjitsi/tree/jitsi_android

## Contribution

Before making any pull requests please see: https://jitsi.org/Documentation/FAQ#patch 

[Jitsi]: https://jitsi.org/
[Java]: http://www.oracle.com/technetwork/java/javase/downloads/jdk7-downloads-1880260.html
[Android SDK]: http://developer.android.com/sdk/index.html
[IntelliJ]: http://www.jetbrains.com/idea/download/
[Jitsi for desktop]: https://github.com/jitsi/jitsi


Microsoft Windows [Version 6.1.7601]
Copyright (c) 2009 Microsoft Corporation.  All rights reserved.

C:\Users\Vijen\IdeaProjects\jitsi_master_server>ant setup-libs
Buildfile: C:\Users\Vijen\IdeaProjects\jitsi_master_server\build.xml

BUILD FAILED
C:\Users\Vijen\IdeaProjects\jitsi_master_server\build.xml:46: sdk.dir is missing. Make sure to generate local.properties using 'android update project' or to inject
it through an env var

Total time: 0 seconds

C:\Users\Vijen\IdeaProjects\jitsi_master_server>android update project
Error: The parameter --path must be defined for action 'update project'

       Usage:
       android [global options] update project [action options]
       Global options:
  -s --silent     : Silent mode, shows errors only.
  -v --verbose    : Verbose mode, shows errors, warnings and all messages.
     --clear-cache: Clear the SDK Manager repository manifest cache.
  -h --help       : Help on a specific command.

                         Action "update project":
  Updates an Android project (must already have an AndroidManifest.xml).
Options:
  -s --subprojects: Also updates any projects in sub-folders, such as test
                    projects.
  -l --library    : Directory of an Android library to add, relative to this
                    project's directory.
  -p --path       : The project's directory. [required]
  -n --name       : Project name.
  -t --target     : Target ID to set for the project.

C:\Users\Vijen\IdeaProjects\jitsi_master_server>android update project -p C:\Users\Vijen\IdeaProjects\jitsi_master_server
Updated local.properties
build.xml: Found version-tag: custom. File will not be updated.
Updated file C:\Users\Vijen\IdeaProjects\jitsi_master_server\proguard-project.txt
It seems that there are sub-projects. If you want to update them
please use the --subprojects parameter.

C:\Users\Vijen\IdeaProjects\jitsi_master_server>android update project -p C:\Users\Vijen\IdeaProjects\jitsi_master_server\
Updated local.properties
build.xml: Found version-tag: custom. File will not be updated.
Updated file C:\Users\Vijen\IdeaProjects\jitsi_master_server\proguard-project.txt
It seems that there are sub-projects. If you want to update them
please use the --subprojects parameter.

C:\Users\Vijen\IdeaProjects\jitsi_master_server>android update project -p C:\Users\Vijen\IdeaProjects\jitsi_master_server\
Updated local.properties
build.xml: Found version-tag: custom. File will not be updated.
Updated file C:\Users\Vijen\IdeaProjects\jitsi_master_server\proguard-project.txt
It seems that there are sub-projects. If you want to update them
please use the --subprojects parameter.

C:\Users\Vijen\IdeaProjects\jitsi_master_server>android update project C:\Users\Vijen\IdeaProjects\jitsi_master_server
Error: Argument 'C:\Users\Vijen\IdeaProjects\jitsi_master_server' is not recognized.

       Usage:
       android [global options] update project [action options]
       Global options:
  -s --silent     : Silent mode, shows errors only.
  -v --verbose    : Verbose mode, shows errors, warnings and all messages.
     --clear-cache: Clear the SDK Manager repository manifest cache.
  -h --help       : Help on a specific command.

                         Action "update project":
  Updates an Android project (must already have an AndroidManifest.xml).
Options:
  -s --subprojects: Also updates any projects in sub-folders, such as test
                    projects.
  -l --library    : Directory of an Android library to add, relative to this
                    project's directory.
  -p --path       : The project's directory. [required]
  -n --name       : Project name.
  -t --target     : Target ID to set for the project.

C:\Users\Vijen\IdeaProjects\jitsi_master_server>ant setup-libs
Buildfile: C:\Users\Vijen\IdeaProjects\jitsi_master_server\build.xml

define-jarjar:

create-asset-dex:
[getbuildtools] Using latest Build Tools: 27.0.0
     [copy] Copying 20 files to C:\Users\Vijen\IdeaProjects\jitsi_master_server\bin\asset-libs
   [jarjar] Building jar: C:\Users\Vijen\IdeaProjects\jitsi_master_server\bin\asset-libs\protocol-sip.jar
   [jarjar] Building jar: C:\Users\Vijen\IdeaProjects\jitsi_master_server\bin\asset-libs\smacklib.jar
   [jarjar] Building jar: C:\Users\Vijen\IdeaProjects\jitsi_master_server\bin\asset-libs\protocol-jabber.jar
    [mkdir] Created dir: C:\Users\Vijen\IdeaProjects\jitsi_master_server\bin\jitsi_bundles_dex_dir
      [dex] input: C:\Users\Vijen\IdeaProjects\jitsi_master_server\bin\asset-libs
      [dex] Converting compiled files and external libraries into C:\Users\Vijen\IdeaProjects\jitsi_master_server\bin\jitsi_bundles_dex_dir\classes.dex...
      [jar] Building jar: C:\Users\Vijen\IdeaProjects\jitsi_master_server\assets\jitsi-bundles-dex.jar
   [delete] Deleting directory C:\Users\Vijen\IdeaProjects\jitsi_master_server\bin\asset-libs

setup-libs:
    [mkdir] Created dir: C:\Users\Vijen\IdeaProjects\jitsi_master_server\libs
   [jarjar] Building jar: C:\Users\Vijen\IdeaProjects\jitsi_master_server\libs\ui-service.jar
   [jarjar] Building jar: C:\Users\Vijen\IdeaProjects\jitsi_master_server\libs\protocol.jar
   [jarjar] Building jar: C:\Users\Vijen\IdeaProjects\jitsi_master_server\libs\libjitsi.jar
   [jarjar] Building jar: C:\Users\Vijen\IdeaProjects\jitsi_master_server\libs\bouncycastle.jar
   [jarjar] Building jar: C:\Users\Vijen\IdeaProjects\jitsi_master_server\libs\bccontrib.jar
   [jarjar] Building jar: C:\Users\Vijen\IdeaProjects\jitsi_master_server\libs\certificate.jar
   [jarjar] Building jar: C:\Users\Vijen\IdeaProjects\jitsi_master_server\libs\zrtp4j.jar
   [jarjar] Building jar: C:\Users\Vijen\IdeaProjects\jitsi_master_server\libs\otr.jar
   [jarjar] Building jar: C:\Users\Vijen\IdeaProjects\jitsi_master_server\libs\commons-codec.jar
   [jarjar] Building jar: C:\Users\Vijen\IdeaProjects\jitsi_master_server\libs\sdes4j.jar
   [jarjar] Building jar: C:\Users\Vijen\IdeaProjects\jitsi_master_server\libs\httpcore.jar
   [jarjar] Building jar: C:\Users\Vijen\IdeaProjects\jitsi_master_server\libs\httpclient.jar
   [jarjar] Building jar: C:\Users\Vijen\IdeaProjects\jitsi_master_server\libs\httputil.jar
   [jarjar] Building jar: C:\Users\Vijen\IdeaProjects\jitsi_master_server\libs\java-stubs.jar
   [jarjar] Building jar: C:\Users\Vijen\IdeaProjects\jitsi_master_server\libs\resourcemanager.jar
     [copy] Copying 32 files to C:\Users\Vijen\IdeaProjects\jitsi_master_server\libs
     [copy] Copying C:\Users\Vijen\IdeaProjects\jitsi_master_server\lib\bundles\browserlauncher.jar to C:\Users\Vijen\IdeaProjects\jitsi_master_server\libs\browserla
uncher.jar
     [copy] Copying C:\Users\Vijen\IdeaProjects\jitsi_master_server\lib\bundles\callhistory.jar to C:\Users\Vijen\IdeaProjects\jitsi_master_server\libs\callhistory.j
ar
     [copy] Copying C:\Users\Vijen\IdeaProjects\jitsi_master_server\lib\bundles\contactlist.jar to C:\Users\Vijen\IdeaProjects\jitsi_master_server\libs\contactlist.j
ar
     [copy] Copying C:\Users\Vijen\IdeaProjects\jitsi_master_server\lib\bundles\contactsource.jar to C:\Users\Vijen\IdeaProjects\jitsi_master_server\libs\contactsour
ce.jar
     [copy] Copying C:\Users\Vijen\IdeaProjects\jitsi_master_server\lib\bundles\credentialsstorage.jar to C:\Users\Vijen\IdeaProjects\jitsi_master_server\libs\creden
tialsstorage.jar
     [copy] Copying C:\Users\Vijen\IdeaProjects\jitsi_master_server\lib\bundles\defaultresources.jar to C:\Users\Vijen\IdeaProjects\jitsi_master_server\libs\defaultr
esources.jar
     [copy] Copying C:\Users\Vijen\IdeaProjects\jitsi_master_server\lib\bundles\dnsservice.jar to C:\Users\Vijen\IdeaProjects\jitsi_master_server\libs\dnsservice.jar

     [copy] Copying C:\Users\Vijen\IdeaProjects\jitsi_master_server\lib\bundles\filehistory.jar to C:\Users\Vijen\IdeaProjects\jitsi_master_server\libs\filehistory.j
ar
     [copy] Copying C:\Users\Vijen\IdeaProjects\jitsi_master_server\lib\bundles\globaldisplaydetails.jar to C:\Users\Vijen\IdeaProjects\jitsi_master_server\libs\glob
aldisplaydetails.jar
     [copy] Copying C:\Users\Vijen\IdeaProjects\jitsi_master_server\lib\bundles\history.jar to C:\Users\Vijen\IdeaProjects\jitsi_master_server\libs\history.jar
     [copy] Copying C:\Users\Vijen\IdeaProjects\jitsi_master_server\lib\bundles\metahistory.jar to C:\Users\Vijen\IdeaProjects\jitsi_master_server\libs\metahistory.j
ar
     [copy] Copying C:\Users\Vijen\IdeaProjects\jitsi_master_server\lib\bundles\msghistory.jar to C:\Users\Vijen\IdeaProjects\jitsi_master_server\libs\msghistory.jar

     [copy] Copying C:\Users\Vijen\IdeaProjects\jitsi_master_server\lib\bundles\muc.jar to C:\Users\Vijen\IdeaProjects\jitsi_master_server\libs\muc.jar
     [copy] Copying C:\Users\Vijen\IdeaProjects\jitsi_master_server\lib\bundles\netaddr.jar to C:\Users\Vijen\IdeaProjects\jitsi_master_server\libs\netaddr.jar
     [copy] Copying C:\Users\Vijen\IdeaProjects\jitsi_master_server\lib\bundles\notification-service.jar to C:\Users\Vijen\IdeaProjects\jitsi_master_server\libs\noti
fication-service.jar
     [copy] Copying C:\Users\Vijen\IdeaProjects\jitsi_master_server\lib\bundles\notification-wiring.jar to C:\Users\Vijen\IdeaProjects\jitsi_master_server\libs\notif
ication-wiring.jar
     [copy] Copying C:\Users\Vijen\IdeaProjects\jitsi_master_server\lib\bundles\plugin-loggingutils.jar to C:\Users\Vijen\IdeaProjects\jitsi_master_server\libs\plugi
n-loggingutils.jar
     [copy] Copying C:\Users\Vijen\IdeaProjects\jitsi_master_server\lib\bundles\replacement.jar to C:\Users\Vijen\IdeaProjects\jitsi_master_server\libs\replacement.j
ar
     [copy] Copying C:\Users\Vijen\IdeaProjects\jitsi_master_server\lib\bundles\sysactivitynotifications.jar to C:\Users\Vijen\IdeaProjects\jitsi_master_server\libs\
sysactivitynotifications.jar
     [copy] Copying C:\Users\Vijen\IdeaProjects\jitsi_master_server\lib\bundles\systray-service.jar to C:\Users\Vijen\IdeaProjects\jitsi_master_server\libs\systray-s
ervice.jar
     [copy] Copying C:\Users\Vijen\IdeaProjects\jitsi_master_server\lib\bundles\updateservice.jar to C:\Users\Vijen\IdeaProjects\jitsi_master_server\libs\updateservi
ce.jar
     [copy] Copying C:\Users\Vijen\IdeaProjects\jitsi_master_server\lib\bundles\util.jar to C:\Users\Vijen\IdeaProjects\jitsi_master_server\libs\util.jar
     [copy] Copying C:\Users\Vijen\IdeaProjects\jitsi_master_server\lib\native\armeabi\libjnawtrenderer.so to C:\Users\Vijen\IdeaProjects\jitsi_master_server\libs\ar
meabi\libjnawtrenderer.so
     [copy] Copying C:\Users\Vijen\IdeaProjects\jitsi_master_server\lib\native\armeabi\libjnffmpeg.so to C:\Users\Vijen\IdeaProjects\jitsi_master_server\libs\armeabi
\libjnffmpeg.so
     [copy] Copying C:\Users\Vijen\IdeaProjects\jitsi_master_server\lib\native\armeabi\libjng722.so to C:\Users\Vijen\IdeaProjects\jitsi_master_server\libs\armeabi\l
ibjng722.so
     [copy] Copying C:\Users\Vijen\IdeaProjects\jitsi_master_server\lib\native\armeabi\libjnopensles.so to C:\Users\Vijen\IdeaProjects\jitsi_master_server\libs\armea
bi\libjnopensles.so
     [copy] Copying C:\Users\Vijen\IdeaProjects\jitsi_master_server\lib\native\armeabi\libjnopus.so to C:\Users\Vijen\IdeaProjects\jitsi_master_server\libs\armeabi\l
ibjnopus.so
     [copy] Copying C:\Users\Vijen\IdeaProjects\jitsi_master_server\lib\native\armeabi\libjnspeex.so to C:\Users\Vijen\IdeaProjects\jitsi_master_server\libs\armeabi\
libjnspeex.so
     [copy] Copying C:\Users\Vijen\IdeaProjects\jitsi_master_server\lib\android-support-v4.jar to C:\Users\Vijen\IdeaProjects\jitsi_master_server\libs\android-suppor
t-v4.jar
     [copy] Copying C:\Users\Vijen\IdeaProjects\jitsi_master_server\lib\log4j-1.2.8.jar to C:\Users\Vijen\IdeaProjects\jitsi_master_server\libs\log4j-1.2.8.jar
     [copy] Copying C:\Users\Vijen\IdeaProjects\jitsi_master_server\lib\osgi.core.jar to C:\Users\Vijen\IdeaProjects\jitsi_master_server\libs\osgi.core.jar
     [copy] Copying C:\Users\Vijen\IdeaProjects\jitsi_master_server\lib\smack-sasl.jar to C:\Users\Vijen\IdeaProjects\jitsi_master_server\libs\smack-sasl.jar

BUILD SUCCESSFUL
Total time: 15 seconds

C:\Users\Vijen\IdeaProjects\jitsi_master_server>^A

