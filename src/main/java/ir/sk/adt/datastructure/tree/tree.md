
# Data Structures: Trees

Trees are hierarchical data structures that consist of nodes connected by edges. Each tree has a root node, and every node can have zero or more child nodes. Trees are widely used in computer science for various applications, including representing hierarchical data, parsing expressions, and implementing search algorithms.

## Types of Binary Trees:
Full Binary Tree: A full binary tree is a binary tree where every node has exactly 0 or 2 children.\
* Internal nodes always have exactly two children.
* Leaf nodes may be at different depths.
* Not necessarily balanced.
Real-world Use Cases:
* Represent arithmetic expressions like (a + b) * (c - d).
Internal nodes = operators (+, -, *, /), leaf nodes = operands (a, b, c, d).
Always full, since every operator needs exactly two operands.
* Parsing Trees: Used in compilers to represent syntax structures where binary grammar rules apply.

```
        A          A
      /   \       / \
     B     C     B   C
    / \             / \
   D   E           F   G
```
Complete Binary Tree: A complete binary tree is a binary tree in which every level, except possibly the last, is completely filled, and all nodes are as far left as possible.
* All leaf nodes are as far left as possible.
* Very efficient memory representation using arrays (no gaps).
* Not necessarily full or perfect.
Real-world Use Cases:

```
        A
      /   \
     B     C
    / \   /
   D   E F
```
Perfect Binary Tree: A binary tree is perfect binary Tree if all internal nodes have two children and all leaves are at the same level.
```
        A
      /   \
     B     C
    / \   / \
   D   E F   G
```

in Tree, in each level the size become double
