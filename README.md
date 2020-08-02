# PasswordManager

PasswordManager is a project I started working on because I was tired of keeping my passwords in a notebook and I don't trust the cloud for remember my data. I wanted to focus on creating an application that I could use across my Windows and Linux environments so I decided to use JavaFX. I also wanted the data to be secure from prying eyes so all the passwords use AES256 encryption. This is mostly meant as a hobby application and the UI might be rough around the edges since I am mostly concerned with usability rather than UI appearances. This whole project is done mostly for myself, I do not really expect others to use it, but I will develop it in such a way that it can be used by anyone as easily as possible. 

# Releases

You can find the latest windows installer for the software under releases. The program has not yet been validated for use on Linux or Mac Os. In theory this should not be a problem but I will need to bundle appropriate JRE instances with the software and will therefore take a look at various deployement options for Linux. The development of the software on Linux and Mac should not be a problem. 

# Development

The project uses maven and should be easy to setup in IntelliJ. The project uses JavaFX. Access the two links below for information on adding the sqlite jdbc drivers and javafx to maven. This should work crossplatform. 

* <a href=https://openjfx.io/openjfx-docs/>JavaFX documentation and setup</a> You can find information on how to setup a javafx maven project with the major java IDE. 
* <a href=https://mvnrepository.com/artifact/org.xerial/sqlite-jdbc/3.32.3.2>Maven SQLITE Xerial drivers for JDBC</a>
* Please note: For the moment for reasons of security the project excludes the Encryption class and algorithm that are used by the application. I am looking into ways of releasing the Encryption information without comprimising on security.
