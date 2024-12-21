-- 1
EXPLAIN ANALYZE
SELECT u.name
FROM users u
         INNER JOIN phones p ON u.id = p.user_id
GROUP BY p.user_id, u.name
HAVING COUNT(p.number) > 1;
-- 2
EXPLAIN ANALYZE
SELECT *
FROM users u
WHERE EXISTS(SELECT 1
             FROM phones p
             WHERE u.id = p.user_id
               AND p.number LIKE '8%');
-- 3
EXPLAIN ANALYZE
SELECT *
FROM users u
         INNER JOIN phones p ON u.id = p.user_id
WHERE p.number IN (SELECT p.number
                   FROM phones p
                   GROUP BY p.number
                   HAVING COUNT(p.number) > 1);
-- 4
CREATE INDEX IF NOT EXISTS users_name ON users (name);
CREATE INDEX IF NOT EXISTS phones_number ON phones (number);