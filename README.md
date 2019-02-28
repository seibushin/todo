# todo
!!Disclaimer!!<br>
This project is a skill demonstration solved in a short time frame.

Implementation of a to-do list. The application consists of a java backend and a javascript/html frontend. The communication between the front and backend is achieved by java servlets. To persist the data h2 is used as an embedded database and tomcat is used as an embedded server.

The following features are part of the application:
- create a new todo
- change the description of a todo
- delete a todo
- mark a todo as done
- search for todos

Everything required to start the webserver is bundled inside a single jar to allow for easy usage.

## Install
<p>
Navigate to the project root and simply use
<code>./gradlew shadowJar</code>
to create the jar. The jar will be located at <code>build/libs/todo-1.0.jar</code>
</p>
<p>
The created jar can be executed using <code>java -jar todo-1.0.jar</code>
</p>
<p>
Afterwards the server will be started and the web application is available under http://localhost:8080/todo
</p>
<p>
To stop the server simply type <code>exit</code> into the command line
</p>
<p>
Note: the baseDir <code>tomcat.8080</code> and the database <code>db.mv.db</code> will be created relative to the execution directory
</p>

## Usage
<a href="http://localhost:8080/todo/">http://localhost:8080/todo/</a><br>
<p>Use the first section of the page to add a new todo. A todo needs a title and a date.</p>
<p>Use the second section to search/view your open todos and edit them. You can set the done-flag to true to show already done todos in the list.</p>

## Used frameworks/technologies
- Java
- HTML
- JS
- CSS
- Spring (can easily removed through the usage of the default java api for servlets)
- Hibernate (ORM)
- W3-Theme
- jQuery
- Tomcat
- shadowjar-plugin (for gradle)
- logback

## Challenges
Building the actual jar with all necessary dependencies and allowing the user to run the application without any hassle.
