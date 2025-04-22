# Algebraic Integer Calculator

This is going to be a calculator for algebraic integers of low degrees, 
operating for the most part symbolically rather than numerically.

NOTE: This project uses JUnit, Hamcrest and one or two assertions from the 
testing framework from testframe.org. This project has no other external 
dependencies.

WORK IN PROGRESS: This grew out of the visualization of imaginary quadratic 
integer rings project.

This is a Java project. At one point it had a couple of Scala classes, but this 
was really quite unnecessary, as it is quite easy to use the project's compiled 
files in a local Scala REPL, making the REPL very vaguely similar to a Wolfram 
Mathematica notebook.

This project started out in Java 8 with JUnit 4. I have since upgraded to Java 
21, but because I'm using NetBeans rather than IntelliJ or Eclipse for this 
project, I can't upgrade JUnit to JUnit 5. But this is now a Java 21 project, 
though Java 12 might work.

This is still a JUnit 4 project. But rather than be limited by JUnit 4 not 
having the things from JUnit 5 that I sometimes need, like `assertThrows()`, I 
just use the equivalents from [TestFrame](https://testframe.org). Preferably use 
TestFrame 1.0, but for now 0.95 should also work (note that 0.9 used the 
`testframe` namespace rather than `org.testframe`).

When I started this project, I didn't really understand Git or GitHub, so I was 
operating under various misconceptions. It wasn't until August 24, 2023 that I 
started correcting the deficiencies caused by certain unnecessary idiosyncracies 
that hopefully now only appear in the commits prior to that date.

I've split off information on the basics of algebraic number theory to 
[a new page](Basics.md).
