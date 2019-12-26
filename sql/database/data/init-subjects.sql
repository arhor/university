-- #dependencies: [subjects]

-- #init-table: subjects  >>> START
USE [university]
GO

IF NOT EXISTS (SELECT * FROM [subjects] WHERE [default_title] = N'русский язык')
BEGIN
    INSERT INTO [subjects] ([default_title]) VALUES (N'русский язык')
END
GO

IF NOT EXISTS (SELECT * FROM [subjects] WHERE [default_title] = N'белорусский язык')
    BEGIN
        INSERT INTO [subjects] ([default_title]) VALUES (N'белорусский язык')
    END
GO

IF NOT EXISTS (SELECT * FROM [subjects] WHERE [default_title] = N'иностранный язык')
    BEGIN
        INSERT INTO [subjects] ([default_title]) VALUES (N'иностранный язык')
    END
GO

IF NOT EXISTS (SELECT * FROM [subjects] WHERE [default_title] = N'математика')
    BEGIN
        INSERT INTO [subjects] ([default_title]) VALUES (N'математика')
    END
GO

IF NOT EXISTS (SELECT * FROM [subjects] WHERE [default_title] = N'физика')
    BEGIN
        INSERT INTO [subjects] ([default_title]) VALUES (N'физика')
    END
GO

IF NOT EXISTS (SELECT * FROM [subjects] WHERE [default_title] = N'химия')
    BEGIN
        INSERT INTO [subjects] ([default_title]) VALUES (N'химия')
    END
GO

IF NOT EXISTS (SELECT * FROM [subjects] WHERE [default_title] = N'история')
    BEGIN
        INSERT INTO [subjects] ([default_title]) VALUES (N'история')
    END
GO

IF NOT EXISTS (SELECT * FROM [subjects] WHERE [default_title] = N'биология')
    BEGIN
        INSERT INTO [subjects] ([default_title]) VALUES (N'биология')
    END
GO
-- #init-table: subjects  <<< END