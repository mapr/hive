-- kv1.* : 238, 86, 311, 27, ...

-- text file
create table skip_nrow1(key string, value string) stored as textfile TBLPROPERTIES ('skip.first.rownum'='1');
load data local inpath '../../data/files/kv1.txt' overwrite into table skip_nrow1;

-- fetch task
explain
select * from skip_nrow1 limit 3;
select * from skip_nrow1 limit 3;

-- mr task
explain
select key,value from skip_nrow1 limit 3;
select key,value from skip_nrow1 limit 3;

-- sequence file
create table skip_nrow2(key string, value string) stored as sequencefile TBLPROPERTIES ('skip.first.rownum'='1');
load data local inpath '../../data/files/kv1.seq' overwrite into table skip_nrow2;

-- fetch task
explain
select * from skip_nrow2 limit 3;
select * from skip_nrow2 limit 3;

-- mr task
explain
select key,value from skip_nrow2 limit 3;
select key,value from skip_nrow2 limit 3;