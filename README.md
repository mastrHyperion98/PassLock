# Description

PassLock is a local database management tool for managing services and their associated credentials. The application is designed using AES 256 encryption, jdbc, java and javafx in a Maven project. 

<strong>Please Note:</strong>
Application settings, keys and database are stored under {User}/PassLock directory. where {User} replaces the path to your user directory. 

# Motivation

In late 2019 I woke up to several emails from Netflix saying that my account had been compromised and the email and password had been changed. Netflix support was quick to return control of the account back to me. This is what motivated me to develop this application. I use a lot of various services whether it be Paypal, Netflix, Funimation, or Steam. Remembering unique passwords for everything can be difficult. Also, making up a safe password can also be a challenge. This application aims to address this. 

# Targeted Platforms
* Windows 
* Linux (SOON)

# Releases

You can find the latest windows installer for the software under releases. The application versioning is determined by the progress I make on the features I intend on shipping with the application.

# Development

The project uses maven and should be easy to setup in IntelliJ. The project uses JavaFX. Access the two links below for information on adding the sqlite jdbc drivers and javafx to maven. This should work crossplatform. 

* maven is set to use JDK 11 by default. You can change those values in the pom  document. I am leaving my project config on github for reference. You should download only the src files.
* <a href=https://openjfx.io/openjfx-docs/>JavaFX documentation and setup</a> You can find information on how to setup a javafx maven project with the major java IDE. 
* <a href=https://mvnrepository.com/artifact/org.xerial/sqlite-jdbc/3.32.3.2>Maven SQLITE Xerial drivers for JDBC</a>

# PKCS12 References
* https://ftp.gnome.org/mirror/archive/ftp.sunet.se/pub/security/docs/PCA/PKCS/ftp.rsa.com/pkcs-12/pkcs-12v1.pdf
