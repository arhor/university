-- #dependencies: [enrollees, subjects, enrollees_has_subjects]

-- #init-table: enrollees_has_subjects  >>> START
USE university
GO

DECLARE @totalSubjects  INT = (SELECT COUNT(*) FROM subjects WITH(NOLOCK))
DECLARE @totalEnrollees INT = (SELECT COUNT(*) FROM enrollees WITH(NOLOCK))

DECLARE @counter INT = 0
WHILE (@counter < @totalEnrollees)
BEGIN
    DECLARE @enrolleeId BIGINT = (
        SELECT   e.id
        FROM     enrollees e WITH(NOLOCK)
        ORDER BY e.id ASC
        OFFSET @counter ROWS
        FETCH NEXT 1 ROWS ONLY
    )

	PRINT N'Generating subjects for enrollee with ID = ' + CAST(@enrolleeId AS NVARCHAR(30))

    DECLARE @subjectsCount INT = (SELECT COUNT(*) FROM enrollees_has_subjects es WITH(NOLOCK) WHERE es.enrollee_id = @enrolleeId)

    IF (@subjectsCount < 3)
    BEGIN
		PRINT CAST((3 - @subjectsCount) AS NVARCHAR(30)) + N' subjects will be generated'

        WHILE (@subjectsCount < 3)
        BEGIN
		    DECLARE @subjectNum INT = CEILING((@totalSubjects - @subjectsCount) * RAND())

            DECLARE @subjectId BIGINT = (
                SELECT sub.id
                FROM subjects sub WITH(NOLOCK)
                WHERE sub.id NOT IN (
                    SELECT es.subject_id
                    FROM enrollees_has_subjects es WITH(NOLOCK)
                    WHERE es.enrollee_id = @enrolleeId
                )
                ORDER BY sub.id ASC
                OFFSET @subjectNum ROWS
                FETCH NEXT 1 ROWS ONLY
            )

            INSERT INTO enrollees_has_subjects (subject_id, enrollee_id, score)
            VALUES
            (
                @subjectId,
                @enrolleeId,
                CEILING(100 * RAND())
            )

            SET @subjectsCount = @subjectsCount + 1
			PRINT N'Success' + (CHAR(13) + CHAR(10)) -- line-break CR + LF
        END
    END

    ELSE
    BEGIN
        PRINT N'There are ' + CAST(@subjectsCount AS NVARCHAR(30)) + N' subjects already exists'
    END

    SET @counter = @counter + 1
END
-- #init-table: enrollees_has_subjects  <<< END