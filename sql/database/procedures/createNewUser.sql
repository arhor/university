-- #dependencies: [roles, langs, users, getDefaultRole, getDefaultLang]

-- #create-procedure: createNewUser >>> START
USE [university]
GO

IF (OBJECT_ID('createNewUser') IS NOT NULL)
BEGIN
    DROP PROCEDURE [createNewUser]
END

CREATE PROCEDURE [dbo].[createNewUser]
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

    EXECUTE @default_role_id = [dbo].[getDefaultRole];
    EXECUTE @default_lang_id = [dbo].[getDefaultLang];

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

    SELECT [u].[email]
         , [u].[password]
         , [u].[first_name]
         , [u].[last_name]
         , [u].[role_id]
         , [u].[lang_id]
    FROM [users] [u] WITH (NOLOCK)
    WHERE [u].[email] = @email
END
GO
-- #create-procedure: createNewUser <<< END
