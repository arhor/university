-- #create-table: langs >>> START
USE [university];

IF (OBJECT_ID('langs') IS NULL)
BEGIN
    CREATE TABLE [langs]
    (
        [id]       [BIGINT]     NOT NULL IDENTITY(1,1),
        [label]    [CHAR](2)    NOT NULL,

        CONSTRAINT [PK_langs] PRIMARY KEY CLUSTERED ([Id] ASC),

        CONSTRAINT [CHK_langs_label] CHECK
        (
            [label] LIKE '[A-Z][A-Z]'
        )
    )
END
-- #create-table: langs <<< END
