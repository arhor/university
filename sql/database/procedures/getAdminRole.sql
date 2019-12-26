-- #dependencies: [roles]

-- #create-procedure: getAdminRole >>> START
USE [university]
GO

IF (OBJECT_ID('getAdminRole') IS NOT NULL)
BEGIN
    DROP PROCEDURE [getAdminRole]
END
GO

CREATE PROCEDURE [dbo].[getAdminRole]
AS
BEGIN
    DECLARE @adminRole [NVARCHAR] = 'ADMIN'
    DECLARE @id AS [BIGINT]

    SELECT @id = [roles].[id]
    FROM  [roles] WITH (NOLOCK)
    WHERE [roles].[title] = @adminRole

    IF (@id IS NULL)
    BEGIN
        SET TRANSACTION ISOLATION LEVEL SERIALIZABLE
        BEGIN TRANSACTION
            SELECT @id = [roles].[id]
            FROM  [roles]
            WHERE [roles].[title] = @adminRole

            IF (@id IS NULL)
            BEGIN
                INSERT INTO [roles] (title) VALUES (@adminRole)
                SELECT @id = SCOPE_IDENTITY()
            END
        COMMIT TRANSACTION
    END

    SELECT @id
END
-- #create-procedure: getAdminRole <<< END