# Algebraic Integer Calculator

NOTE: This project uses JUnit, Hamcrest and one or two assertions from the 
testing framework from testframe.org. This project has no other external 
dependencies.

WORK IN PROGRESS: This grew out of the visualization of imaginary quadratic 
integer rings project.

When I started this project, I didn't really understand Git or GitHub, so I was 
operating under various misconceptions. It wasn't until August 24, 2023 that I 
started correcting the deficiencies caused by certain unnecessary idiosyncracies 
that hopefully now only appear in the commits prior to that date.

This is a Java project. At one point it had a couple of Scala classes, but this 
was really quite unnecessary, as it is quite easy to use the project's compiled 
files in a local Scala REPL, making the REPL very vaguely similar to a Wolfram 
Mathematica notebook.

This project started out in Java 8 with JUnit 4. I have since upgraded to Java 
21, but because I'm using NetBeans rather than IntelliJ or Eclipse, I can't 
upgrade JUnit to JUnit 5.

Regardless, I'm in no rush to use Java 9 or later features.

## The concept of algebraic integers

When we just refer to **integers**, we're referring to the numbers that are 
obtained by the repeated addition or subtraction of $1$. Adding $1$ repeatedly 
gives the **counting numbers** $1, 2, 3, \ldots$, sometimes notated $\mathbb 
Z^+$. Subtracting $1$ from itself once gives $0$, and subtracting $1$ again and 
again gives us the additive inverses of the counting numbers, $-1, -2, -3, 
\ldots$, sometimes notated $\mathbb Z^-$.

(FINISH WRITING)
