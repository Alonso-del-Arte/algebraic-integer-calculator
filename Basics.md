# The Basics of Algebraic Number Theory

In this document, I will try to explain the mathematical concepts involved in 
this project as simply as I can. I'm not aiming for mathematical rigor.

## The concept of algebraic integers

When we just refer to **integers**, we're referring to the numbers that are 
obtained by the repeated addition or subtraction of $1$. Adding $1$ repeatedly 
gives the **counting numbers** $1, 2, 3, \ldots$, sometimes notated $\mathbb 
Z^+$. Subtracting $1$ from itself once gives $0$, and subtracting $1$ again and 
again gives us the additive inverses of the counting numbers, $-1, -2, -3, 
\ldots$, sometimes notated $\mathbb Z^-$.

Joining $\mathbb Z^-$ and $\mathbb Z^+$ with $0$ gives us $\mathbb Z$. Each $n 
\in \mathbb Z$ is a solution to $x$ to the rather unremarkable equation $x - n = 
0$, in which of course $x = n$.

This infinite collection of integers that is $\mathbb Z$ forms a ring that is 
"closed under" addition and "closed under" multiplication. This means that if 
$a$ and $b$ are in $\mathbb Z$, then their sum $a + b$ and their product $ab$ 
are also in $\mathbb Z$. This might seem trivial and obvious, but it is very 
helpful as we try to generalize the concept of integers to other domains.

Something much more interesting happens when, given some integer $n \gt 1$, we 
construct an equation $$x^n + \sum_{i = 0}^{n - 1} a_i x^i = 0,$$ where the 
$a_i$ are regular integers, but almost certainly the solution $x$ is not a 
regular integer or even a rational number.

Such a number $x$ is then called an **algebraic integer**, and its algebraic 
degree is $n$, provided it isn't also a solution to an equation with a smaller 
$n$ for the exponent of the leading $x$.

For example, one solution of $x^3 - 3x^2 + 3x - 19 = 0$ is $$1 + \frac{{\root 3 
\of {12}}^2}{2}.$$ Thanks to the fundamental theorem of algebra, we know that 
that equation has three solutions.

An algebraic integer belongs to infinitely many rings, at least one of degree 
$n$ and infinitely many of degrees that are nontrivial positive multiples of 
$n$. For example, the numbers of $\mathbb Z$ are also included in all other 
rings of algebraic integers.

## Arithmetic on positive, negative numbers

Obviously a positive number times a positive number is also a positive number. 
And a positive number times a negative number is a negative number. From this it 
follows that a negative number times a negative number is a positive number, but 
this is a conceptual hurdle to lots of people and requires a bit of explanation.

Multiplication is repeated addition. For example, 5 &times; 7 = 35, which we can 
verify as 0 + 5 + 5 + 5 + 5 + 5 + 5 + 5 and 0 + 7 + 7 + 7 + 7 + 7. 

Likewise, both &minus;5 &times; 7 and &minus;7 &times; 5 equal &minus;35. We can 
verify that as, for example, 0 &minus; 5 &minus; 5 &minus; 5 &minus; 5 &minus; 5 
&minus; 5 &minus; 5. But if both multiplicands are negative, this goes in the 
other direction, to positive numbers.

I felt it necessary to go over these basic facts because they are consequential 
for many rings of algebraic integers.

## Real, imaginary and complex numbers

All numbers are real in the sense that they exist and we can perform 
arithmetical operations on them. The terms "real," "imaginary" and "complex" 
arise from misunderstandings from long ago and are now too entrenched to change.

By this long-standing terminology, a **real number** is either a number from 
$\mathbb Z$ or a number that is somewhere on a straight line between two 
consecutive numbers from $\mathbb Z$. Every integer $d$ from $\mathbb Z^+$ 
figures in an equation of the form $x^n - d = 0$ where $n$ is a positive even 
integer. Each such equation has $n$ solutions. For example, $x = \root 8 \of 7$ 
is one solution to $x^8 - 7 = 0$.

But an equation $x^n + d = 0$ with $n$ a positive even integer seems to not have 
any solutions in real numbers. For example, $x^2 + 7$ (FINISH WRITING)

## Divisibility

Just as with $\mathbb Z$, all the rings that will be discussed below are closed 
under addition and closed under multiplication. But they're not closed under 
division.

We can restate this thus: given a ring $R$, if $a \in R$ and $b \in R$, then $a 
+ b \in R$ and $ab \in R$, but $\frac{a}{b}$ and $\frac{b}{a}$ might be outside 
$R$.

For example, 7 and 22 are numbers in $\mathbb Z$, and we see that 7 + 22 = 29 
and 7 &times; 22 = 164 are also in $\mathbb Z$. Clearly 164 is divisible by both 
7 and 22.

But neither $\frac{7}{22}$ nor $\frac{22}{7}$ are in $\mathbb Z$. Then we say 22 
is not divisible by 7 and 7 is not divisible by 22.

### Zero and units

Every ring we will be discussing in this document has the number 0. No number is 
divisible by zero, but zero is divisible by all numbers other than itself. Zero 
is the additive identity. Given any number $x$, we see that $x + 0 = x$.

And then there are the units, which are numbers that every number in the ring is 
divisible by. In $\mathbb Z$, the units are &minus;1 and 1, which correspond to 
the equations $x + 1 = 0$ and $x - 1 = 0$.

The number 1 is the multiplicative identity. Given any number $x$, we see that 
$x \times 1 = x$.

A number multiplied by a unit is an **associate** of that number. For example, 
47 multiplied by &minus;1 is &minus;47, and so &minus;47 is an associate of 47, 
and vice-versa.

Multiplying a nonzero number by a unit other than 1 gives a different number, 
but the equations are similar. In the previous example, 47 is the solution to $x 
- 47 = 0$, and &minus;47 is the solution to $x + 47 = 0$.

### Irreducible numbers and prime numbers

(FINISH WRITING)

## Rings of higher degree

### Degree 2

**Quadratic integers** are algebraic integers of degree 2, they're solutions to 
equations of the form $x^2 - Tx + N = 0$. The numbers $T$ and $N$ are plain but 
important integers that will be explained later on. Quadratic integers are much 
easier to understand and reckon than algebraic integers of degree 3 or higher.

Given a squarefree integer $d$, a number that is not divisible by any of the 
perfect squares 4, 9, 16, 25, 36, 49, etc., the number $\sqrt d$ is an algebraic 
integer that is a solution to the equation $x^2 - d = 0$, and it belongs to a 
ring of algebraic integers that includes lots of numbers besides the numbers of 
$\mathbb Z$. For example, $\sqrt{14}$ is one solution to $x^2 - 14 = 0$ (the 
other is $-\sqrt{14}$).

By adding $\sqrt d$ to itself and to the integers of $\mathbb Z$, we obtain all 
or some of the numbers of a ring sometimes notated as $\mathbb Z[\sqrt d]$. 
These are numbers of the form $a + b \sqrt d$, where $a$ and $b$ come from 
$\mathbb Z$.

An algebraic integer of degree $n$ has $n - 1$ conjugates. This means that each 
quadratic integer has precisely one conjugate.

The **trace** of an algebraic integer is the sum of all the roots of the 
characteristic polynomial, the **characteristic polynomial** being the equation, 
without the "= 0" part, of lowest degree for which the algebraic integer is a 
solution. In the case of quadratic integers of the form $a + b \sqrt d$, the 
trace $T = 2a$.

The **norm** of an algebraic integer is the product of itself times its 
conjugates, which is just two numbers in the case of a quadratic integer. The 
norm is useful for comparing numbers that might not all be on the same straight 
line. The norm of a quadratic integer $a + b \sqrt d$ is $a^2 - db^2$.

Both the trace and the norm figure in the (FINISH WRITING)

### Degree 3

There are considerably many more kinds of cubic rings than there are quadratic 
rings.

First we'll deal with pure cubic rings. A **pure cubic ring** arises from 
joining $\root 3 \of d$ to $\mathbb Z$, where $d$ is cubefree but not 
necessarily squarefree.

(FINISH WRITING)

(FINISH WRITING)

### Degree 4

(FINISH WRITING)

(FINISH WRITING)

(FINISH WRITING)

(FINISH WRITING)

(FINISH WRITING)
