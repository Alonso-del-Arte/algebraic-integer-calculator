This project might be open to Hacktoberfest 2024, I haven't decided yet.

# Guidelines for Hacktoberfest 2024 Contributors

Try to have your pull requests in before October 24. In my experience, pull 
requests in the final week tend to be from people who just don't understand what 
the project is about. I'm not going to do a charity merge and then undo the 
whole thing in November.

I will definitely consider pull requests associated with an issue and maybe 
consider pull requests associated with a TODO (considered an Action Item in 
NetBeans).

Issues about build tools will be closed as spam or not helpful. Just use the 
build tool your IDE provides by default.

Contributors should have a proper Java IDE (most are available for free) and 
JUnit. This project uses Java 8 and JUnit 4. However, I might upgrade to Java 21 
and JUnit 5.

* Column width is 80.
* Prefer spaces to tabs (this is likely to be an issue only with Eclipse, a 
simple configuration change should take care of it).
* Opening curly braces should not go on lines by themselves.
* Generally use blackboard bold from LaTeX in markdown and regular bold from 
HTML in Javadoc. TeX in markdown, at least on GitHub, renders correctly.
* As much as is practical, each class in Source Packages should have a 
corresponding test class in Test Packages. That includes abstract classes as 
well as interfaces having one or more default implementations.
* Do not delete test classes nor test stubs without explanation.
* Do not add dependencies, except Hamcrest and JUnit if your IDE does not have 
those in the project at the outset.
* No comments except Javadoc, TODO or FIXME.
* Prefer small commits. But avoid making a pull request for a single commit 
(unless it's to fix a defect affecting end users).
* Pull requests should address a TODO or FIXME comment in the source, or an open 
issue on GitHub.
