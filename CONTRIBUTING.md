This project will be open to Hacktoberfest 2024.

# Guidelines for Hacktoberfest 2024 contributors

The most important requirement for Hacktoberfest contributors wanting to 
contribute to this project is to have some understanding of algebraic number 
theory, not necessarily be an expert on the subject. If you read 
[Basics.md](Basics.md) and the concepts don't make any sense to you, then this 
is not a project for you to contribute to. If you do understand the concepts but 
think they're explained poorly in that document, you just might have something 
worthwhile to contribute to this project.

Try to have your pull requests in before October 24. In my experience, pull 
requests in the final week tend to be from people who just don't understand what 
the project is about. I'm not going to do a charity merge and then undo the 
whole thing in November.

I will definitely consider pull requests associated with an issue and maybe 
consider pull requests associated with a TODO (considered an Action Item in 
NetBeans).

Issues about build tools will be closed as spam or not helpful. Just use the 
build tool your IDE provides by default, and do not do anything to impose that 
choice on anyone else. If you notice Git picking up anything build tool-specific 
besides build.xml, nbbuild.xml and manifest.mf files, then it might be a good 
idea to add some things to the Git Ignore &mdash; I welcome such a commit.

Contributors should have a proper Java IDE (most are available for free) and 
JUnit. This project started out on Java 8 and JUnit 4 as well as TestFrame from 
[testframe.org](https://testframe.org), use version 0.95 or later. However, late 
September 2024 I've very slowly started using features from later Java versions, 
nothing specifically from Java 21 yet but let's regard that as the baseline, 
since that's the runtime and compiler I'm using.

* Column width is 80.
* Prefer spaces to tabs (this is likely to be an issue only with Eclipse, a 
simple configuration change should take care of it).
* Opening curly braces should not go on lines by themselves.
* Generally use blackboard bold from LaTeX in markdown and regular bold from 
HTML in Javadoc. TeX in markdown, at least on GitHub, renders correctly.
* As much as is practical, each class in Source Packages should have a 
corresponding test class in Test Packages. That includes abstract classes as 
well as interfaces having one or more default implementations.
* Do not delete test classes nor test stubs for any reason. Leave it to me to 
delete test classes or unit tests for deprecated classes or functions.
* Do not add dependencies besides TestFrame, except Hamcrest and JUnit if your 
IDE does not have those in the project at the outset.
* No new comments in the last commit of a pull request, except Javadoc, TODO or 
FIXME. The lifetime of a comment needs to be obvious. There are some comments in 
this project of unclear expiration which I will try to address and remove before 
Hacktoberfest begins.
* Prefer small commits. But avoid making a pull request for a single commit 
(unless it's to fix a defect affecting end users).
* Pull requests should address a TODO or FIXME comment in the source, or an open 
issue on GitHub.
