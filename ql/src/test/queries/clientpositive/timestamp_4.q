DROP TABLE IF EXISTS timestamp_4;

CREATE TABLE timestamp_4 (t TIMESTAMP);
ALTER TABLE timestamp_4 SET SERDE 'org.apache.hadoop.hive.serde2.lazy.LazySimpleSerDe';

-- timestamp_data.txt contains timestamps in below supported formats
-- 1. Integer: UNIX epoch seconds
-- 2. Floating point: UNIX epoch seconds plus nanoseconds
-- 3. Strings: JDBC compliant java.sql.Timestamp format
--             "YYYY-MM-DD HH:MM:SS.fffffffff" (9 decimal place precision)
LOAD DATA LOCAL INPATH '../data/files/timestamp_data.txt' INTO TABLE timestamp_4;

SELECT t                   FROM timestamp_4;
SELECT CAST(t AS TINYINT)  FROM timestamp_4;
SELECT CAST(t AS SMALLINT) FROM timestamp_4;
SELECT CAST(t AS INT)      FROM timestamp_4;
SELECT CAST(t AS BIGINT)   FROM timestamp_4;
SELECT CAST(t AS FLOAT)    FROM timestamp_4;
SELECT CAST(t AS DOUBLE)   FROM timestamp_4;
SELECT CAST(t AS STRING)   FROM timestamp_4;

SELECT * FROM timestamp_4 ORDER BY t;

SELECT * FROM timestamp_4 WHERE t = '2013-04-22 16:11:25';

SELECT * FROM timestamp_4 WHERE t > '2013-04-22 16:11:25';

SELECT * FROM timestamp_4 WHERE t < '2013-04-22 16:11:25';

SELECT * FROM timestamp_4 WHERE t <> '2013-04-22 16:11:25';

DROP TABLE timestamp_4;
