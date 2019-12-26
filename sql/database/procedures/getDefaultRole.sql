-- #dependencies: [roles]

-- #create-procedure: getDefaultRole >>> START
USE [university]
GO

IF (OBJECT_ID('getDefaultRole') IS NOT NULL)
BEGIN
    DROP PROCEDURE [getDefaultRole]
END
GO

CREATE PROCEDURE [dbo].[getDefaultRole]
AS
BEGIN
    DECLARE @defaultRole [NVARCHAR](10) = N'USER'
    DECLARE @id AS [BIGINT]

    SELECT @id = [roles].[id]
    FROM  [roles] WITH (NOLOCK)
    WHERE [roles].[title] = @defaultRole

    IF (@id IS NULL)
    BEGIN
        SET TRANSACTION ISOLATION LEVEL SERIALIZABLE
        BEGIN TRANSACTION
            SELECT @id = [roles].[id]
            FROM  [roles]
            WHERE [roles].[title] = @defaultRole

            IF (@id IS NULL)
            BEGIN
                INSERT INTO [roles] (title) VALUES (@defaultRole)
                SELECT @id = SCOPE_IDENTITY()
            END
        COMMIT TRANSACTION
    END

    SELECT @id
    RETURN @id
END
-- #create-procedure: getDefaultRole <<< END