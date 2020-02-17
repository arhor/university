-- #dependencies: [faculties, subjects, faculties_has_subjects]

-- #init-table: faculties_has_subjects  >>> START
USE [university]
GO

DECLARE @facultyId BIGINT
DECLARE @subjectId BIGINT

SELECT @facultyId = [id] FROM [faculties] WITH(NOLOCK) WHERE [default_title] = N'Биологический факультет'

INSERT INTO [faculties_has_subjects] ([faculty_id],[subject_id])

-- FIXME: implement
-- #init-table: faculties_has_subjects  <<< END