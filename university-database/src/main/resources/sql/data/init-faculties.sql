-- #dependencies: [faculties]

-- #init-table: faculties  >>> START
USE [university]
GO

DECLARE @TempFaculties TABLE
(
    [id]    [INT],
    [title] [NVARCHAR](128)
)

INSERT INTO @TempFaculties ([id], [title])
VALUES (0, N'Биологический факультет')
     , (1, N'Исторический факультет')
     , (2, N'Химический факультет')
     , (3, N'Факультет прикладной математики и информатики')
     , (4, N'Факультет радиофизики и компьютерных технологий')
     , (5, N'Экономический факультет')
     , (6, N'Юридический факультет')
     , (7, N'Военный факультет')
     , (8, N'Филологический факультет')
     , (9, N'Республиканский институт китаеведения имени Конфуция')

DECLARE @counter INT = 0
WHILE (@counter <= 9)
    BEGIN
        DECLARE @title NVARCHAR(64) = (SELECT [title] FROM @TempFaculties WHERE [id] = @counter)

        IF NOT EXISTS (SELECT * FROM [faculties] WHERE [default_title] = @title)
            BEGIN
                INSERT INTO [faculties] ([default_title], [seats_budget], [seats_paid])
                VALUES
                (
                    @title,
                    CEILING((10 * RAND()) + 5),
                    CEILING((25 * RAND()) + 15)
                )
            END
        SET @counter = @counter + 1
    END
-- #init-table: faculties  <<< END