-- #dependencies: [faculties, subjects, faculties_has_subjects]

-- #init-table: faculties_has_subjects  >>> START
USE university
GO

DECLARE @facultyId BIGINT
DECLARE @subjectId BIGINT

--------------------------- Биологический факультет ---------------------------
SELECT @facultyId = id FROM faculties WITH(NOLOCK) WHERE default_title = N'Биологический факультет'

INSERT INTO faculties_has_subjects (faculty_id, subject_id)
VALUES
(@facultyId, SELECT id FROM subjects WITH(NOLOCK) WHERE default_title = N'Биология'),
(@facultyId, SELECT id FROM subjects WITH(NOLOCK) WHERE default_title = N'Химия'),
(@facultyId, SELECT id FROM subjects WITH(NOLOCK) WHERE default_title = N'Русский язык')

---------------------------- Исторический факультет ---------------------------
SELECT @facultyId = id FROM faculties WITH(NOLOCK) WHERE default_title = N'Исторический факультет'

INSERT INTO faculties_has_subjects (faculty_id, subject_id)
VALUES
(@facultyId, SELECT id FROM subjects WITH(NOLOCK) WHERE default_title = N'Белорусский язык'),
(@facultyId, SELECT id FROM subjects WITH(NOLOCK) WHERE default_title = N'Иностранный язык'),
(@facultyId, SELECT id FROM subjects WITH(NOLOCK) WHERE default_title = N'История')

-------------------------- Химический факультет -------------------------------
SELECT @facultyId = id FROM faculties WITH(NOLOCK) WHERE default_title = N'Химический факультет'

INSERT INTO faculties_has_subjects (faculty_id, subject_id)
VALUES
(@facultyId, SELECT id FROM subjects WITH(NOLOCK) WHERE default_title = N'Химия'),
(@facultyId, SELECT id FROM subjects WITH(NOLOCK) WHERE default_title = N'Русский язык'),
(@facultyId, SELECT id FROM subjects WITH(NOLOCK) WHERE default_title = N'Физика')

---------------- Факультет прикладной математики и информатики ----------------
SELECT @facultyId = id FROM faculties WITH(NOLOCK) WHERE default_title = N'Факультет прикладной математики и информатики'

INSERT INTO faculties_has_subjects (faculty_id, subject_id)
VALUES
(@facultyId, SELECT id FROM subjects WITH(NOLOCK) WHERE default_title = N'Математика'),
(@facultyId, SELECT id FROM subjects WITH(NOLOCK) WHERE default_title = N'Иностранный язык'),
(@facultyId, SELECT id FROM subjects WITH(NOLOCK) WHERE default_title = N'Физика')

------------- Факультет радиофизики и компьютерных технологий -----------------
SELECT @facultyId = id FROM faculties WITH(NOLOCK) WHERE default_title = N'Факультет радиофизики и компьютерных технологий'

INSERT INTO faculties_has_subjects (faculty_id, subject_id)
VALUES
(@facultyId, SELECT id FROM subjects WITH(NOLOCK) WHERE default_title = N'Физика'),
(@facultyId, SELECT id FROM subjects WITH(NOLOCK) WHERE default_title = N'Математика'),
(@facultyId, SELECT id FROM subjects WITH(NOLOCK) WHERE default_title = N'Химия')

------------------------- Экономический факультет -----------------------------
SELECT @facultyId = id FROM faculties WITH(NOLOCK) WHERE default_title = N'Экономический факультет'

INSERT INTO faculties_has_subjects (faculty_id, subject_id)
VALUES
(@facultyId, SELECT id FROM subjects WITH(NOLOCK) WHERE default_title = N'Иностранный язык'),
(@facultyId, SELECT id FROM subjects WITH(NOLOCK) WHERE default_title = N'История'),
(@facultyId, SELECT id FROM subjects WITH(NOLOCK) WHERE default_title = N'Математика')

-------------------------- Юридический факультет ------------------------------
SELECT @facultyId = id FROM faculties WITH(NOLOCK) WHERE default_title = N'Юридический факультет'

INSERT INTO faculties_has_subjects (faculty_id, subject_id)
VALUES
(@facultyId, SELECT id FROM subjects WITH(NOLOCK) WHERE default_title = N'Белорусский язык'),
(@facultyId, SELECT id FROM subjects WITH(NOLOCK) WHERE default_title = N'Иностранный язык'),
(@facultyId, SELECT id FROM subjects WITH(NOLOCK) WHERE default_title = N'История')

---------------------------- Военный факультет --------------------------------
SELECT @facultyId = id FROM faculties WITH(NOLOCK) WHERE default_title = N'Военный факультет'

INSERT INTO faculties_has_subjects (faculty_id, subject_id)
VALUES
(@facultyId, SELECT id FROM subjects WITH(NOLOCK) WHERE default_title = N'Химия'),
(@facultyId, SELECT id FROM subjects WITH(NOLOCK) WHERE default_title = N'Белорусский язык'),
(@facultyId, SELECT id FROM subjects WITH(NOLOCK) WHERE default_title = N'История')

------------------------ Филологический факультет -----------------------------
SELECT @facultyId = id FROM faculties WITH(NOLOCK) WHERE default_title = N'Филологический факультет'

INSERT INTO faculties_has_subjects (faculty_id, subject_id)
VALUES
(@facultyId, SELECT id FROM subjects WITH(NOLOCK) WHERE default_title = N'Русский язык'),
(@facultyId, SELECT id FROM subjects WITH(NOLOCK) WHERE default_title = N'Белорусский язык'),
(@facultyId, SELECT id FROM subjects WITH(NOLOCK) WHERE default_title = N'Иностранный язык')

------------ Республиканский институт китаеведения имени Конфуция -------------
SELECT @facultyId = id FROM faculties WITH(NOLOCK) WHERE default_title = N'Республиканский институт китаеведения имени Конфуция'

INSERT INTO faculties_has_subjects (faculty_id, subject_id)
VALUES
(@facultyId, SELECT id FROM subjects WITH(NOLOCK) WHERE default_title = N'Иностранный язык'),
(@facultyId, SELECT id FROM subjects WITH(NOLOCK) WHERE default_title = N'История'),
(@facultyId, SELECT id FROM subjects WITH(NOLOCK) WHERE default_title = N'Биология')
-- #init-table: faculties_has_subjects  <<< END