-- #dependencies: [faculties, enrollees, faculties_has_enrollees, enrollees_has_subjects]

-- #init-table: faculties_has_enrollees  >>> START
DECLARE @totalEnrollees  INT = (SELECT COUNT(*) FROM enrollees WITH(NOLOCK))

DECLARE @counter INT = 0

WHILE (@counter < @totalEnrollees)
BEGIN
    DECLARE @enrolleeId BIGINT = (
        SELECT e.id
        FROM enrollees e WITH(NOLOCK)
        ORDER BY e.id ASC
        OFFSET @counter ROWS
        FETCH NEXT 1 ROWS ONLY
    )

    PRINT N'Looking for appropriate faculty for enrollee with ID = ' + CAST(@enrolleeId AS NVARCHAR(30))

    WITH enrolleeSubjects (
        id
    ) AS (
        SELECT subject_id
        FROM enrollees_has_subjects WITH(NOLOCK)
        WHERE enrollee_id = @enrolleeId
    )

    SET @counter = @counter + 1
END
-- #init-table: faculties_has_enrollees  <<< END