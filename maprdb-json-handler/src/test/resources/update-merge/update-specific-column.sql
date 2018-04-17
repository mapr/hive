merge into customer_db_json_target trg
using customer_db_json_source src on src.id = trg.id
when matched then update set age = src.age
when not matched then insert values (src.id, src.first_name, src.last_name, src.age)
