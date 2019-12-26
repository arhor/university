-- #dependencies: [users]

-- #init-table: users  >>> START
USE [university]
GO

-- password to use: `password` encrypted with BCrypt (strength 5)
DECLARE @Password NVARCHAR = N'$2y$05$wc9f6o/gGJyoagNZfHkHJerFc0tIJAmdCQmabJCtXs0uOJhUAGICa'

DECLARE @RU BIGINT = (SELECT [id] FROM [langs] WITH(NOLOCK) WHERE [label] = 'RU')
DECLARE @BY BIGINT = (SELECT [id] FROM [langs] WITH(NOLOCK) WHERE [label] = 'BY')
DECLARE @EN BIGINT = (SELECT [id] FROM [langs] WITH(NOLOCK) WHERE [label] = 'EN')

DECLARE @LangsNumber INT = 3

DECLARE @counter INT = 0
WHILE (@counter < 100)
    BEGIN
        DECLARE @Email NVARCHAR = N'user.test' + CONVERT(NVARCHAR, @counter)  + N'@gmail.com'
        DECLARE @Lang INT = ROUND((@LangsNumber * RAND() + 1), 0)

        DECLARE @LangToUse BIGINT = (
            CASE
                WHEN @Lang = 1 THEN @RU
                WHEN @Lang = 2 THEN @BY
                WHEN @Lang = 3 THEN @EN
            END
        )

        IF NOT EXISTS (SELECT * FROM [users] WHERE [email] = @Email)
            BEGIN
                EXECUTE [dbo].[createNewUser]
                    @Email,
                    @Password,
                    N'',
                    N'',
                    null,
                    @LangToUse
            END
    END
GO
-- #init-table: users  <<< END