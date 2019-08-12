MERGE INTO tgt
USING src_seq ON tgt.id=src_seq.id
WHEN MATCHED THEN DELETE
WHEN NOT MATCHED THEN INSERT VALUES (src_seq.id, src_seq.value)
