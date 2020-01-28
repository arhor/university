-- #create-table: roles >>> START
USE [university]
GO

IF (OBJECT_ID('roles') IS NULL)
BEGIN
    CREATE TABLE [roles]
    (
        [id]       [BIGINT]         NOT NULL IDENTITY(1,1),
        [title]    [NVARCHAR](15)   NOT NULL,

        CONSTRAINT [PK_roles] PRIMARY KEY CLUSTERED ([Id] ASC)
    )
END
GO
-- #create-table: roles <<< END
