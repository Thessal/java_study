# Java study

Toy project for learning Java language & DB

## Goal
 * Parser
 * Join clause
 * Graph statistics

## Example

```
default > insert 1 a
default > insert 2 b
default > select
default
Node@682a0b20	1	a
Node@448139f0	2	b
default > table table2
table2 > tables
[default, table2]
table2 > insert 3 c
table2 > insert 4 d
table2 > table default
default > select
default
Node@682a0b20	1	a
Node@448139f0	2	b
default > table table2
table2 > select
table2
Node@7cca494b	3	c
Node@7ba4f24f	4	d
table2 > exit
```