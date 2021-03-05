# Java study

Toy project for learning Java language & DB

## Goal
 * Parser
 * Join clause
 * Graph statistics

## Example

```
table test1
insert 1234 hjkl
insert 12 asdf
insert 234 sdfg
insert 345 qwert
insert 32 a
table test2
insert 12 ni
insert 23 nm
insert 2345 sd
insert 234 s
insert 32 a
select * from test1 inner_join test2

Node@3d075dc0	12	asdf
Node@7cca494b	32	a
Node@7ba4f24f	234	sdfg
```