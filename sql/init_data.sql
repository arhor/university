USE [university]
GO

-- #insert: roles >>> START
INSERT INTO [roles] (title)
VALUES ('USER')
     , ('ADMIN')
GO
-- #insert: roles <<< END

-- #insert: langs >>> START
INSERT INTO [langs] (label)
VALUES ('BY')
     , ('RU')
	 , ('EN')
GO
-- #insert: langs <<< END