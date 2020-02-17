-- #dependencies: [users, enrollees, getAdminRole]

-- #init-table: enrollees  >>> START
USE university
GO

DECLARE @Countries TABLE
(
    id      BIGINT,
    name    NVARCHAR(64)
)

INSERT INTO @Countries (id, name)
VALUES (1, N'Беларусь'), (2, N'Россия'), (3, N'Украина'), (4, N'Польша');

DECLARE @Cities TABLE
(
    countryId    BIGINT,
    num          BIGINT,
    name         NVARCHAR(64)
)

INSERT INTO @Cities (countryId, num, name)
VALUES (1, 1, N'Минск')  , (1, 2, N'Гродно') , (1, 3, N'Гомель') , (1, 4, N'Могилёв')
     , (2, 1, N'Москва') , (2, 2, N'Саратов'), (2, 3, N'Магадан'), (2, 4, N'Суздаль')
     , (3, 1, N'Киев')   , (3, 2, N'Львов')  , (3, 3, N'Одесса') , (3, 4, N'Харьков')
     , (4, 1, N'Варшава'), (4, 2, N'Краков') , (4, 3, N'Гданьск'), (4, 4, N'Люблин');

DECLARE @Admin BIGINT
EXEC @Admin = dbo.getAdminRole

DECLARE @counter    INT = 0
DECLARE @totalUsers INT = (SELECT COUNT(*) FROM users u WITH(NOLOCK) WHERE u.role_id != @Admin)
WHILE (@counter < @totalUsers)
BEGIN
    DECLARE @userId BIGINT = (
        SELECT   u.id
        FROM     users u WITH(NOLOCK)
        WHERE    u.role_id != @Admin
        ORDER BY u.id ASC
        OFFSET @counter ROWS
        FETCH NEXT 1 ROWS ONLY
    )

    IF NOT EXISTS (SELECT * FROM enrollees e WITH(NOLOCK) WHERE e.user_id = @userId)
    BEGIN
        DECLARE @countryId INT = CEILING (4 * RAND())
        DECLARE @cityNum   INT = CEILING (4 * RAND())

        DECLARE @countryName NVARCHAR(64) = (
            SELECT name
            FROM @Countries
            WHERE id = @countryId
        )

        DECLARE @cityName NVARCHAR(64) = (
            SELECT name
            FROM @Cities
            WHERE countryId = @countryId
            AND num = @cityNum
        )

        INSERT INTO enrollees (country, city, school_score, user_id)
        VALUES
        (
            @countryName,
            @cityName,
            CEILING(100 * RAND()),
            @userId
        )
    END
    SET @counter = @counter + 1
END
-- #init-table: enrollees  <<< END