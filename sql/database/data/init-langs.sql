-- #dependencies: [langs]

-- #init-table: langs  >>> START
USE [university]
GO

IF NOT EXISTS (SELECT * FROM [langs] WHERE [label] = 'BY')
    BEGIN
        INSERT INTO [langs] ([label]) VALUES ('BY')
    END
GO

IF NOT EXISTS (SELECT * FROM [langs] WHERE [label] = 'RU')
    BEGIN
        INSERT INTO [langs] ([label]) VALUES ('RU')
    END
GO

IF NOT EXISTS (SELECT * FROM [langs] WHERE [label] = 'EN')
    BEGIN
        INSERT INTO [langs] ([label]) VALUES ('EN')
    END
GO
-- #init-table: langs  <<< END