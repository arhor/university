-- #dependencies: [langs]

-- #create-procedure: getDefaultLang >>> START
USE [university]
GO

IF (OBJECT_ID('getDefaultLang') IS NOT NULL)
BEGIN
    DROP PROCEDURE [getDefaultLang]
END

CREATE PROCEDURE [dbo].[getDefaultLang]
AS
BEGIN
    DECLARE @defaultLang [CHAR](2) = 'RU'
    DECLARE @id AS [BIGINT]

    SELECT @id = [langs].[id]
    FROM  [langs] WITH (NOLOCK)
    WHERE [langs].[label] = @defaultLang

    IF (@id IS NULL)
    BEGIN
        SET TRANSACTION ISOLATION LEVEL SERIALIZABLE
        BEGIN TRANSACTION
            SELECT @id = [langs].[id]
            FROM  [langs]
            WHERE [langs].[label] = @defaultLang

            IF (@id IS NULL)
            BEGIN
                INSERT INTO [langs] (label) VALUES (@defaultLang)
                SELECT @id = SCOPE_IDENTITY()
            END
        COMMIT TRANSACTION
    END

    SELECT @id
END
-- #create-procedure: getDefaultLang <<< END
