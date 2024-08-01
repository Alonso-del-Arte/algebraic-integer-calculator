# The Basics of Algebraic Number Theory

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

Something more interesting happens when, given some integer $n \gt 1$, we 
construct an equation $$x^n + \sum_{i = 0}^{n - 1} a_i x^i = 0,$$ where the 
$a_i$ are regular integers, but almost certainly the solution $x$ is not a 
regular integer or even a rational number.

Such a number $x$ is then called an **algebraic integer**, and its algebraic 
degree is $n$, provided it isn't also a solution to an equation with a smaller 
$n$ for the exponent of the leading $x$.

For example, one solution of $x^3 - 3x^2 + 3x - 19 = 0$ is $$1 + \frac{{\root 3 
\of {12}}^2}{2}.$$ Thanks to the fundamental theorem of algebra, we know that 
that equation has three solutions.

**Quadratic integers** are algebraic integers of degree 2, and they're much 
easier to understand and reckon than algebraic integers of degree 3 or higher. 
For the time being, this algebraic integer calculator will be limited to unary 
and quadratic integers.

(FINISH WRITING)
