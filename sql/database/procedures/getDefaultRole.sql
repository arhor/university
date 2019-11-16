-- #dependencies: [roles]

-- #create-procedure: getDefaultRole >>> START
USE [university]
GO

IF (OBJECT_ID('getDefaultRole') IS NOT NULL)
BEGIN
    DROP PROCEDURE [getDefaultRole]
END

CREATE PROCEDURE [dbo].[getDefaultRole]
AS
BEGIN
    IF (NOT EXISTS (SELECT * FROM [roles] WHERE [roles].[title] = 'USER'))
        INSERT INTO [roles] (title)
        OUTPUT INSERTED.[id]
        VALUES ('USER');
    ELSE
        SELECT [roles].[id]
        FROM   [roles] WITH (NOLOCK)
        WHERE  [roles].[title] = 'USER';
END
GO
-- #create-procedure: getDefaultRole <<< END
