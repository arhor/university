-- #create-table: langs >>> START
USE [university]
GO

IF (OBJECT_ID('langs') IS NULL)
BEGIN
    CREATE TABLE [dbo].[langs]
    (
        [id]       [BIGINT]     NOT NULL IDENTITY(1,1),
        [label]    [CHAR](2)    NOT NULL,

        CONSTRAINT [PK_langs] PRIMARY KEY CLUSTERED ([Id] ASC),

        CONSTRAINT [CK_langs_label] CHECK
        (
            [label] LIKE '[A-Z][A-Z]'
        )
    )
END
GO
-- #create-table: langs <<< END

-- #init-table: langs >>> START
INSERT INTO [langs] (label)
VALUES ('RU')
     , ('BY')
	 , ('EN')
GO
-- #init-table: langs <<< END