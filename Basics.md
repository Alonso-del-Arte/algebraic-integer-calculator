# The Basics of Algebraic Number Theory

In this document, I will try to explain the mathematical concepts involved in 
this project as simply as I can. I'm not aiming for mathematical rigor.

## The concept of algebraic integers

When we just refer to **integers**, we're referring to the numbers that are 
obtained by the repeated addition or subtraction of $1$. Adding $1$ repeatedly 
gives the **counting numbers** $1, 2, 3, \ldots$, sometimes notated $\mathbb 
Z^+$ (the notation $\mathbb N$ is also used sometimes). 

Subtracting $1$ from itself once gives $0$, and subtracting $1$ again and again 
gives us the additive inverses of the counting numbers, $-1, -2, -3, \ldots$, 
sometimes notated $\mathbb Z^-$.

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
any solutions in real numbers. For example, $x^2 + 7 = 0$ has no solutions in 
real numbers. Shifting the 7 to the right of the equals sign, we obtain the 
equation $x^2 = -7$. Clearly $x = -\sqrt 7 \approx -2.645751311$ is not a 
solution, since then $x^2 = 7$, not $-7$.

Likewise, $x^4 + 7 = 0$ has no solutions in real numbers either. The number $x = 
-\root 4 \of 7 \approx -1.626576561697786$ is not a solution, since then $x^4 = 
7$, not $-7$.

In the 16<sup>th</sup> Century, mathematicians like Girolamo Cardano resolved 
these kinds of quandaries by imagining that there exists a number $i$ such that 
$i^2 = -1$. Then, to solve an equation of the form $x^2 = d$ where $d$ is 
negative, it became a simple matter of multiplying $\sqrt{-d}$ by $i$. 

So, to solve $x^2 = -7$, we take $\sqrt 7$ and multiply it by $i$. We can notate 
this example as either $i \sqrt 7$, which might be regarded as more correct, or 
as $\sqrt{-7}$, which might not be regarded as correct but which I find more 
straightforward. However you notate this, we're talking about a purely imaginary 
number: it has 0 for a real part and not 0 for an imaginary part.

The example $x^4 + 7 = 0$ is a little more complicated. So we're going to set 
that one aside for now and instead look at the similar $x^4 + 1 = 0$. Rewriting, 
we obtain $x^4 = -1$. This suggests that $x^2 = i$ and therefore $x = \sqrt i$.

But then what is $\sqrt i$? As it turns out, it's $$\frac{1}{\sqrt 2} + 
\frac{i}{\sqrt 2},$$ which is a number with both a nonzero real part and a 
nonzero imaginary part. We don't need a different kind of number for such 
arithmetic with complex numbers. The complex numbers are complete for all 
arithmetic operations except those involving division by zero.

## Divisibility

Just as with $\mathbb Z$, all the rings that will be discussed below are closed 
under addition and closed under multiplication. But they're not closed under 
division.

We can restate this thus: given a ring $R$, if $a \in R$ and $b \in R$, then 
$a + b \in R$ and $ab \in R$, but $\frac{a}{b}$ and $\frac{b}{a}$ might be 
outside $R$.

For example, 7 and 22 are numbers in $\mathbb Z$, and we see that $7 + 22 = 29$ 
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
and vice-versa. A number is trivially its own associate.

Multiplying a nonzero number by a unit other than 1 gives a different number, 
but the equations are similar. In the previous example, 47 is the solution to 
$x - 47 = 0$, and &minus;47 is the solution to $x + 47 = 0$.

### Irreducible numbers and prime numbers

Most numbers in a ring are divisible by a few other numbers in that ring and not 
divisible by any other numbers in that ring.

If a number that is not a unit is divisible only by units and associates, then 
it is said to be **irreducible**. For example, &minus;47 is irreducible, as it's 
divisible only by itself, &minus;1, 1 and 47.

The definition of irreducible might sound familiar, and you might think that 
it's the same as the definition of prime numbers. And indeed in $\mathbb Z$, all 
irreducible numbers are also prime numbers. But there are many domains in which 
not all irreducible numbers are prime, and the set of all prime numbers in the 
domain form a proper subset of all irreducible numbers.

A non-unit number $p$ in some ring $R$ is said to be **prime** if whenever $p$ 
divides $ab$ for any combination of $a, b \in R$, then $p$ divides either $a$ or 
$b$.

For example, &minus;47 divides 2256 = 47 &times; 48. But 48 is clearly not 
prime, since, for example, if we express 2256 as 16 &times; 141, we find that 48 
clearly does not divide 16 and it's also clear that 48 does not divide 141 
either, which is an odd number. And 48 is not irreducible either, since it's 
divisible by 2 and by 3.

In $\mathbb Z$, every nonzero, non-unit number is either irreducible and prime 
or reducible and not prime. There are no irreducible but non-prime numbers in 
$\mathbb Z$. Disregarding multiplication by units, every nonzero number in 
$\mathbb Z$ has one unique factorization into primes. A ring with these 
properties is then called a **unique factorization domain** (UFD).

In my opinion, non-UFDs are far more interesting. And for each degree higher 
than 1, there are infinitely many rings that are not UFDs.

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

Both the trace and the norm figure in the characteristic polynomial of an 
algebraic number of degree 2. If the trace and the norm are integers of degree 
1, then the algebraic number is an algebraic integer.

So, for example, $3 + \sqrt{-7}$ is an algebraic integer, having trace 6 and 
norm 16. The pertinent polynomial is $x^2 - 6x + 16$. As it turns out, the 
number $$\frac{3}{2} + \frac{\sqrt{-7}}{2}$$ is also an algebraic integer. The 
trace is 3 and the norm is 4, so the polynomial for this one is $x^2 - 3x + 4$.

Numbers such as that are sometimes misleadingly called "half-integers," but they 
are just as much algebraic integers as their doubles, as they have norms that 
are a quarter of the norm of their doubles.

Given integers $a$, $b$ and $d$, with the latter squarefree, the number 
$$\frac{a}{2} + \frac{b \sqrt d}{2}$$ is an algebraic integer only when the 
following conditions hold:

* $d \equiv 1 \pmod 4$ (such as for example &minus;7), and
* Both $a$ and $b$ are odd (such as for example 3 and 1).

These facts should have proofs in any elementary algebraic number theory book.

(FINISH WRITING)

### Degree 3

There are considerably many more kinds of cubic rings than there are quadratic 
rings.

First we'll deal with pure cubic rings. A **pure cubic ring** arises from 
joining $\root 3 \of d$ to $\mathbb Z$, where $d$ is cubefree but not 
necessarily squarefree. Pure cubic rings are purely real regardless of the sign 
of $d$. Still, there's plenty of complexity to these rings.

Given $a, b, c \in \mathbb Z$, we can rely on $a + b \root 3 \of d + c (\root 3 
\of d)^2$ to be an algebraic integer. The norm function for an algebraic number 
in a pure cubic ring is $a^3 + b^3 d + c^3 d^2 - 3abcd$. Clearly $a, b, c \in 
\mathbb Z$ will give us an integer norm. Thus, for example, $3 + 2 \root 3 
\of{19} + c (\root 3 \of{19})^2$ has norm 198, with minimal polynomial $x^3 - 
9x^2 - 87x - 198$.

But we might suspect that rational numbers with a denominator of 3 or 9 might 
also be algebraic integers in a pure cubic ring. And indeed we see for example 
that $$\frac{1}{3} + \frac{\root 3 \of{19}}{3} + \frac{(\root 3 \of{19})^2}{3}$$ 
is an algebraic integer with norm 12 and minimal polynomial $x^3 - x^2 - 6x - 
12$.

I haven't yet figured out the requirements on such fractions. From what I gather 
so far, the non-integral fractions must have denominators congruent to $\pm 1 
\bmod 9$, which suggests that 

(FINISH WRITING)

(FINISH WRITING)

The norm function is 
$N(a + b \root 3 \of d + c (\root 3 \of d)^2) = a^3 + b^3 d + c^3 d^2 - 3abcd$.

(FINISH WRITING)

(FINISH WRITING)

(FINISH WRITING)

(FINISH WRITING)

### Degree 4

(FINISH WRITING)

(FINISH WRITING)

(FINISH WRITING)

(FINISH WRITING)

(FINISH WRITING)
