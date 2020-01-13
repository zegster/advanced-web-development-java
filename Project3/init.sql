-- Database: [mathprobdb]
DROP DATABASE IF EXISTS mathprobdb;
CREATE DATABASE mathprobdb;
USE mathprobdb;


-- Table structure for table [category]
CREATE TABLE IF NOT EXISTS category
(
	cid int(10) unsigned NOT NULL AUTO_INCREMENT,
	name varchar(1024) NOT NULL,
	PRIMARY KEY (cid)
);


-- Table structure for table [problem]
CREATE TABLE IF NOT EXISTS problem
(
	pid int(10) unsigned NOT NULL AUTO_INCREMENT,
	content varchar(5000) NOT NULL,
	category_id int(10) unsigned NOT NULL,
	PRIMARY KEY (pid),
	FOREIGN KEY (category_id) REFERENCES category(cid)
);


-- Dumping data for table [category]
INSERT INTO category (cid, name) VALUES
(NULL, 'algebra'),
(NULL, 'calculus'),
(NULL, 'discrete mathematics'),
(NULL, 'geometry'),
(NULL, 'graph theory'),
(NULL, 'number theory'),
(NULL, 'probability and statistics'),
(NULL, 'topology');


-- Dumping data for table [problem]
INSERT INTO problem (pid, content, category_id) VALUES
(NULL, 'Let \\(a, b, c\\) be the side lengths, and \\(h_a, h_b, h_c\\) be the altitudes, respectively, and \\(r\\) be the inradius\r\nof a triangle. Prove the inequality\r\n\\[ \\frac{1}{h_a - 2 r} + \\frac{1}{h_b - 2 r} + \\frac{1}{h_c - 2 r} \\ge \\frac{3}{r}.\r\n\\]', 3),
(NULL, 'Let \\(a, b, c; l_{\\alpha}, l_{\\beta}, l_{\\gamma}\\) be the lengths of the sides and the bisectors of respective\r\nangles. Let \\(s\\) be the semi-perimeter and \\(r\\) denote the inradius of a given triangle. Prove the inequality\r\n\\[ \\frac{l_{\\alpha}}{a} + \\frac{l_{\\beta}}{b} + \\frac{l_{\\gamma}}{c} \\le \\frac{s}{2 r}.\r\n\\]', 3),
(NULL, 'Algebra is the metaphysics of arithmetic.', 1),
(NULL, 'Geometry is the archetype of the beauty of the world.', 4),
(NULL, 'Topology is precisely the mathematical discipline that allows the passage from local to global.', 8),
(NULL, 'Geometry enlightens the intellect and sets one''s mind right. All of its proofs are very clear and orderly. It is hardly possible for errors to enter into geometrical reasoning, because it is well arranged and orderly. Thus, the mind that constantly applies itself to geometry is not likely to fall into error. In this convenient way, the person who knows geometry acquires intelligence.', 4),
(NULL, 'Some numbers, even large ones, have no factors - except themselves, of course, and 1. These are called prime numbers, because everything they are starts with themselves. They are original, gnarled, unpredictable, the freaks of the number world.', 6),
(NULL, 'If nature has taught us anything it is that the impossible is probable', 7),
(NULL, 'Facts are stubborn things, but statistics are pliable.', 7),
(NULL, 'Calculus, the electrical battery, the telephone, the steam engine, the radio - all these groundbreaking innovations were hit upon by multiple inventors working in parallel with no knowledge of one another.', 2);