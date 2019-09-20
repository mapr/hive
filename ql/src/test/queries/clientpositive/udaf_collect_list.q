with t2 as (
  select array(1,2) as c1
  union all
  select array(2,3) as c1
)
select collect_list(c1) from t2;

create table t1 as (
  select array(1,2) as c1
  union all
  select array(2,3) as c1
);

select collect_list(c1) from t1;
