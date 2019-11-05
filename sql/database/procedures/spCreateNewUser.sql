-- #dependencies: [roles, langs, users]

-- #create-procedure: spCreateNewUser >>> START
USE [university]
GO

IF (OBJECT_ID('spCreateNewUser') IS NOT NULL)
BEGIN
    DROP PROCEDURE [spCreateNewUser]
END

CREATE PROCEDURE [dbo].[spCreateNewUser]
    @email         [NVARCHAR](255),
    @password      [NVARCHAR](512),
    @first_name    [NVARCHAR](50),
    @last_name     [NVARCHAR](50),
    @role_id       [BIGINT],
    @lang_id       [BIGINT]
AS
BEGIN
    DECLARE @default_role_id [BIGINT]
    DECLARE @default_lang_id [BIGINT]

    SELECT @default_role_id = [roles].[id] FROM [roles] WHERE [roles].[title] = 'USER';
    SELECT @default_lang_id = [langs].[id] FROM [langs] WHERE [langs].[label] = 'RU';

    INSERT INTO [users] (email, password, first_name, last_name, role_id, lang_id)
    VALUES
    (
        @email,
        @password,
        @first_name,
        @last_name,
        COALESCE(@role_id, @default_role_id),
        COALESCE(@lang_id, @default_lang_id)
    )
END
GO
-- #create-procedure: spCreateNewUser <<< END
