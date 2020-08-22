# PasswordManager

PasswordManager is a local database management tool for managing services and their associated credentials. The application is designed using jdbc, java and javafx in a Maven project. 

The passwords are protected by a AES256 encryption using a user defined secret key on first startup. At the moment there is no way to change the encryption on current passwords without overwritting them. Replacing the stored encryption key (which is itself encrypted) will cause the application to display the passwords as empty strings as the values cannot be properly decrypted. Values in any cells of the table can be copied to the clipboard. 

<strong>Please Note:</strong>
It is strongly suggested to use the auto-generate button to generate your secret key. It will generate a 256 character string of mismatched characters, numbers and symbols that will offer the strongest security. However, entering a manual key would make recovering data easier in the case the secret key local file is loss or corrupted. <strong>It is strongly recommended to backup the PasswordManager folder created in your user folder</strong>.

# Targeted Platforms
* Windows and Linux (Support coming soon I just need to figure out how to package the application into an AppImage).

# Future Plans

I would like to do the following eventually, however, the application is currently in a state where I would use it myself.

* polish the user interface
* add some cloud backup-sync functionality (probably with Google Drive)
* ability to change encryption key aka secret key
* ability to change master password
* ability to have mulitple different users
* migrate the backend stuff to a hosted server API (either in NodeJS or Python). 
* connect to the server API rather than local database.


# Releases

You can find the latest windows installer for the software under releases. Their currently is no Linux native image of the application, and since I do not own a Mac i cannot make a Mac Os version. The windows Installer should be fixed as per the latest release. 

# Development

The project uses maven and should be easy to setup in IntelliJ. The project uses JavaFX. Access the two links below for information on adding the sqlite jdbc drivers and javafx to maven. This should work crossplatform. 

* maven is set to use JDK 11 by default. You can change those values in the pom  document. I am leaving my project config on github for reference. You should download only the src files.
* <a href=https://openjfx.io/openjfx-docs/>JavaFX documentation and setup</a> You can find information on how to setup a javafx maven project with the major java IDE. 
* <a href=https://mvnrepository.com/artifact/org.xerial/sqlite-jdbc/3.32.3.2>Maven SQLITE Xerial drivers for JDBC</a>
* Please note: For the moment for reasons of security the project excludes the Encryption class and algorithm that are used by the application. I am looking into ways of releasing the Encryption information without comprimising on security.
