-- #dependencies: [faculties]

-- #init-table: faculties  >>> START
USE [university]
GO

IF NOT EXISTS (SELECT * FROM [faculties] WHERE [default_title] = N'Биологический факультет')
    BEGIN
        INSERT INTO [faculties] ([default_title], [seats_budget], [seats_paid])
        VALUES (N'Биологический факультет', 10, 25)
    END

IF NOT EXISTS (SELECT * FROM [faculties] WHERE [default_title] = N'Исторический факультет')
    BEGIN
        INSERT INTO [faculties] ([default_title], [seats_budget], [seats_paid])
        VALUES (N'Исторический факультет', 13, 21);
    END
GO

IF NOT EXISTS (SELECT * FROM [faculties] WHERE [default_title] = N'Химический факультет')
    BEGIN
        INSERT INTO [faculties] ([default_title], [seats_budget], [seats_paid])
        VALUES (N'Химический факультет', 16, 33);
    END
GO

IF NOT EXISTS (SELECT * FROM [faculties] WHERE [default_title] = N'Факультет прикладной математики и информатики')
    BEGIN
        INSERT INTO [faculties] ([default_title], [seats_budget], [seats_paid])
        VALUES (N'Факультет прикладной математики и информатики', 7, 35);
    END
GO

IF NOT EXISTS (SELECT * FROM [faculties] WHERE [default_title] = N'Факультет радиофизики и компьютерных технологий')
    BEGIN
        INSERT INTO [faculties] ([default_title], [seats_budget], [seats_paid])
        VALUES (N'Факультет радиофизики и компьютерных технологий', 11, 19);
    END
GO

IF NOT EXISTS (SELECT * FROM [faculties] WHERE [default_title] = N'Экономический факультет')
    BEGIN
        INSERT INTO [faculties] ([default_title], [seats_budget], [seats_paid])
        VALUES (N'Экономический факультет', 19, 37);
    END
GO

IF NOT EXISTS (SELECT * FROM [faculties] WHERE [default_title] = N'Юридический факультет')
    BEGIN
        INSERT INTO [faculties] ([default_title], [seats_budget], [seats_paid])
        VALUES (N'Юридический факультет', 5, 23);
    END
GO

IF NOT EXISTS (SELECT * FROM [faculties] WHERE [default_title] = N'Военный факультет')
    BEGIN
        INSERT INTO [faculties] ([default_title], [seats_budget], [seats_paid])
        VALUES (N'Военный факультет', 15, 40);
    END
GO

IF NOT EXISTS (SELECT * FROM [faculties] WHERE [default_title] = N'Филологический факультет')
    BEGIN
        INSERT INTO [faculties] ([default_title], [seats_budget], [seats_paid])
        VALUES (N'Филологический факультет', 14, 28);
    END
GO

IF NOT EXISTS (SELECT * FROM [faculties] WHERE [default_title] = N'Республиканский институт китаеведения имени Конфуция')
    BEGIN
        INSERT INTO [faculties] ([default_title], [seats_budget], [seats_paid])
        VALUES (N'Республиканский институт китаеведения имени Конфуция', 9, 30);
    END
GO
-- #init-table: faculties  <<< END