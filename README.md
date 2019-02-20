# todo
Powerfolder | skill demonstration

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