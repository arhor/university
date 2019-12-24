-- #create-table: roles >>> START
IF (OBJECT_ID('roles') IS NULL)
BEGIN
    CREATE TABLE [dbo].[roles]
    (
        [id]       [BIGINT]         NOT NULL IDENTITY(1,1),
        [title]    [NVARCHAR](15)   NOT NULL,

        CONSTRAINT [PK_roles] PRIMARY KEY CLUSTERED ([Id] ASC)
    )
END
GO
-- #create-table: roles <<< END

-- #create-table: langs >>> START
IF (OBJECT_ID('langs') IS NULL)
BEGIN
    CREATE TABLE [dbo].[langs]
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
GO
-- #create-table: langs <<< END

INSERT INTO roles (title) VALUES ('USER'), ('ADMIN');

INSERT INTO langs (label) VALUES ('BY'), ('RU'), ('EN');
