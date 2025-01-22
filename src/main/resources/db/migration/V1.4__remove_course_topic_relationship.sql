ALTER TABLE topico
DROP FOREIGN KEY fk_topico_curso_id;

ALTER TABLE topico
DROP COLUMN curso_id;

ALTER TABLE topico ADD COLUMN curso VARCHAR(255);
