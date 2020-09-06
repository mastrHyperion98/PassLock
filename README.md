# Description

PassLock is a local database management tool for managing services and their associated credentials. The application is designed using jdbc, java and javafx in a Maven project using AES 256 encryption. 

<strong>Please Note:</strong>
Application settings, keys and database are stored under {User}/PassLock directory. where {User} replaces the path to your user directory. 

# Targeted Platforms
* Windows and Linux (Support coming soon I just need to figure out how to package the application into an AppImage... I still want to release the application as a self contained AppImage, but for now i'll be bundling the custom jre and jar file in a zip archive with a bash launch script that should work across linux x86_64 environments).

# Future Plans

I would like to do the following eventually, however, the application is currently in a state where I would use it myself.

* Use Material Design to improve the UI of the application.
* enhance security more by using Java KeyStore library to save the user generated secret key.
* enahnce security by using the java crypto SecretKey generator.
* add some cloud backup-sync functionality (probably with Google Drive)
* ability to change encryption key aka secret key
* ability to change master password
* ability to have mulitple different users


# Releases

You can find the latest windows installer for the software under releases. Their currently is no Linux native image of the application, and since I do not own a Mac i cannot make a Mac Os version. The windows Installer should be fixed as per the latest release. 

# Development

The project uses maven and should be easy to setup in IntelliJ. The project uses JavaFX. Access the two links below for information on adding the sqlite jdbc drivers and javafx to maven. This should work crossplatform. 

* maven is set to use JDK 11 by default. You can change those values in the pom  document. I am leaving my project config on github for reference. You should download only the src files.
* <a href=https://openjfx.io/openjfx-docs/>JavaFX documentation and setup</a> You can find information on how to setup a javafx maven project with the major java IDE. 
* <a href=https://mvnrepository.com/artifact/org.xerial/sqlite-jdbc/3.32.3.2>Maven SQLITE Xerial drivers for JDBC</a>
* Please note: For the moment for reasons of security the project excludes the Encryption class and algorithm that are used by the application. I am looking into ways of releasing the Encryption information without comprimising on security.
