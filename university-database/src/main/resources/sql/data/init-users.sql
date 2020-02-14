-- #dependencies: [langs, users, getAdminRole, createNewUser]

-- #init-table: users  >>> START
USE [university]
GO

-- password to use: `password` encrypted with BCrypt (strength 5)
DECLARE @Password NVARCHAR(512) = N'$2y$05$wc9f6o/gGJyoagNZfHkHJerFc0tIJAmdCQmabJCtXs0uOJhUAGICa'

DECLARE @RU BIGINT = (SELECT [id] FROM [langs] WITH(NOLOCK) WHERE [label] = 'RU')
DECLARE @BY BIGINT = (SELECT [id] FROM [langs] WITH(NOLOCK) WHERE [label] = 'BY')
DECLARE @EN BIGINT = (SELECT [id] FROM [langs] WITH(NOLOCK) WHERE [label] = 'EN')

IF NOT EXISTS (SELECT * FROM [users] WHERE [email] = N'admin@gmail.com')
BEGIN
    DECLARE @Admin BIGINT
    EXECUTE @Admin = [dbo].[getAdminRole]
    EXECUTE [dbo].[createNewUser] N'admin@gmail.com', @Password, N'Максим', N'Буришинец', @Admin,  @RU
END

DECLARE @counter INT = 1
WHILE (@counter <= 100)
BEGIN
    DECLARE @Email NVARCHAR(50) = N'user.test' + CONVERT(NVARCHAR(3), @counter)  + N'@gmail.com'

    DECLARE @Lang INT = ROUND((3 * RAND()), 0)
    DECLARE @LangToUse BIGINT = (
        CASE
            WHEN @Lang = 0 THEN @RU
            WHEN @Lang = 1 THEN @BY
            WHEN @Lang = 2 THEN @EN
            ELSE @RU
        END
    )

    DECLARE @FN INT = ROUND((10 * RAND()), 0)
    DECLARE @FirstName NVARCHAR(30) = (
        CASE
            WHEN @FN = 0 THEN N'Максим'
            WHEN @FN = 1 THEN N'Иван'
            WHEN @FN = 2 THEN N'Семён'
            WHEN @FN = 3 THEN N'Александр'
            WHEN @FN = 4 THEN N'Валерий'
            WHEN @FN = 5 THEN N'Николай'
            WHEN @FN = 6 THEN N'Алексей'
            WHEN @FN = 7 THEN N'Виктор'
            WHEN @FN = 8 THEN N'Геннадий'
            WHEN @FN = 9 THEN N'Евгений'
            ELSE N'Клим'
        END
    )

    DECLARE @LN INT = ROUND((10 * RAND()), 0)
    DECLARE @LastName NVARCHAR(30) = (
        CASE
            WHEN @LN = 0 THEN N'Вадимович'
            WHEN @LN = 1 THEN N'Александрович'
            WHEN @LN = 2 THEN N'Иванович'
            WHEN @LN = 3 THEN N'Степанович'
            WHEN @LN = 4 THEN N'Григорьевич'
            WHEN @LN = 5 THEN N'Викторович'
            WHEN @LN = 6 THEN N'Валерьевич'
            WHEN @LN = 7 THEN N'Генрихович'
            WHEN @LN = 8 THEN N'Антонович'
            WHEN @LN = 9 THEN N'Алексеевич'
            ELSE N'Олимпиевич'
        END
    )

    IF NOT EXISTS (SELECT * FROM [users] WHERE [email] = @Email)
    BEGIN
        EXECUTE [dbo].[createNewUser] @Email, @Password, @FirstName, @LastName, null, @LangToUse
    END
    SET @counter = @counter + 1
END
-- #init-table: users  <<< END