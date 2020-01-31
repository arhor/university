-- #dependencies: [subjects]

-- #init-table: subjects  >>> START
USE [university]
GO

DECLARE @TempSubjects TABLE
(
    [id]    [INT],
    [title] [NVARCHAR](64)
)

INSERT INTO @TempSubjects ([id], [title])
VALUES (0, N'Русский язык')
     , (1, N'Белорусский язык')
     , (2, N'Иностранный язык')
     , (3, N'Математика')
     , (4, N'Физика')
     , (5, N'Химия')
     , (6, N'История')
     , (7, N'Биология')

DECLARE @counter INT = 0
WHILE (@counter <= 7)
    BEGIN
        DECLARE @title NVARCHAR(64) = (SELECT [title] FROM @TempSubjects WHERE [id] = @counter)

        IF NOT EXISTS (SELECT * FROM [subjects] WHERE [default_title] = @title)
            BEGIN
                INSERT INTO [subjects] ([default_title]) VALUES (@title)
            END
        SET @counter = @counter + 1
    END
-- #init-table: subjects  <<< END