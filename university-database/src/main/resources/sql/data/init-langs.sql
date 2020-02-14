-- #dependencies: [langs]

-- #init-table: langs  >>> START
USE [university]
GO

DECLARE @TempLangs TABLE
(
    [id]    [INT],
    [label] [CHAR](2)
)

INSERT INTO @TempLangs ([id], [label])
VALUES (0, 'BY')
     , (1, 'RU')
     , (2, 'EN')

DECLARE @counter INT = 0
WHILE (@counter <= 2)
BEGIN
   DECLARE @label CHAR(2) = (SELECT [label] FROM @TempLangs WHERE [id] = @counter)

   IF NOT EXISTS (SELECT * FROM [langs] WHERE [label] = @label)
   BEGIN
       INSERT INTO [langs] ([label]) VALUES (@label)
   END
   SET @counter = @counter + 1
END
-- #init-table: langs  <<< END