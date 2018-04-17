MERGE INTO customer_db_json_all_target USING
  (SELECT id,
          first_name,
          last_name,
          age,
          department
   FROM
     (SELECT c.id,
             first_name,
             last_name,
             age,
             d.name AS department
      FROM customer_west c
      JOIN department d ON (c.department_id = d.id)
      UNION ALL SELECT c.id,
                       first_name,
                       last_name,
                       age,
                       d.name AS department
      FROM customer_east c
      JOIN department d ON (c.department_id = d.id)) all_data) sub ON sub.id = customer_db_json_all_target.id WHEN matched THEN
UPDATE
SET first_name = sub.first_name,
    last_name = sub.last_name,
    age = sub.age WHEN NOT matched THEN
INSERT
VALUES (sub.id,
        sub.first_name,
        sub.last_name,
        sub.age,
        sub.department)
