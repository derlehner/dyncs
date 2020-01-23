# Readme

## 0. TODO
https://gitlab.com/0xCAF3BAB3/ame/issues

## 1. About
SOMQM ....

## 2. Content
Contains ...

## 3. Disclosure
 * Content in <project directory>/server/src/main/resources/example-data is from http://www.omgwiki.org/model-interchange/doku.php?id=start .
 * Content in <project directory>/server/src/main/resources/local-maven-repo is from https://www.eclipse.org/modeling/mdt/downloads/?project=uml2 .

## 4. Build

### 4.1. Preconditions
 * Java 1.8
 * Maven 3.5.0
 * PostgreSQL 10.3 (install with username: 'postgres', password: '1234', port: 5432)
 * IntelliJ IDEA

### 4.2. Clone project repository

### 4.3. Setup database
 1. Start pgAdmin 4 (part of the PostgreSQL installation).
    Connect to server 'localhost' (using password: '1234').
    Create new database named 'somqm'.

### 4.4. Build project
 1. Browse to <project directory> and perform ´mvn clean install´.

### 4.5. Setup IntelliJ
 1. Set Java coding style according to Google style-guide:
    Download style-file: https://github.com/google/styleguide/blob/gh-pages/intellij-java-google-style.xml
    File --> Settings... --> Editor --> Code Style --> Java --> Gear icon --> Import Scheme --> Intellij IDEA code style XML --> <select style-file> --> OK.
    Verify that "GoogleStyle" is selected in the dropdown-field next to 'Scheme:' (at least for this project).
    Auto-format the code before committing: click on a package or class and click on "Reformat code".
 2. Import project as Maven project.
 3. Start the project
    * Server: Run ServerApplication.java

Helpful notes:
 * The IntelliJ tool-window 'Database' allows to directly interact with the PostgreSQL database.
 * The IntelliJ tool-window 'Persistence' allows to show the JPA mapping entities (e.g. as ER diagram).
   To enable it: File --> Project Structure... --> Modules --> Right-click on Maven module 'server' --> Add --> JPA --> OK.
   Now open tool-window 'Persistence' --> expand 'server' --> right-click on 'entityManagerFactory' --> ER Diagram.

## 5. Development

### 5.1 Server
#### 5.1.1 REST API Documentation
The REST API of the server is documented via Swagger and is located under http://localhost:8080/somqm/swagger-ui.html.

### 5.4 Known issues / future improvements
  * server: Create a Maven profile to start the server from Maven console.
  * server: EMF and MDT/UML2 dependencies are added via a deprecated Maven feature (as their current versions are not available in Maven's central repository). Maybe upload them to a custom Maven repository or wait until they become available in the central repository.
