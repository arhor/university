-- #dependencies: [roles]

-- #init-table: roles  >>> START
USE [university]
GO

IF NOT EXISTS (SELECT * FROM [roles] WHERE [title] = 'USER')
    BEGIN
        INSERT INTO [roles] ([title]) VALUES ('USER')
    END
GO

IF NOT EXISTS (SELECT * FROM [roles] WHERE [title] = 'ADMIN')
    BEGIN
        INSERT INTO [roles] ([title]) VALUES ('ADMIN')
    END
GO
-- #init-table: roles  <<< END