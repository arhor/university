-- #dependencies: [roles, langs]

-- #create-table: users  >>> START
USE [university]
GO

IF (OBJECT_ID('users') IS NULL)
BEGIN
    CREATE TABLE [users]
    (
        [id]            [BIGINT]           NOT NULL IDENTITY(1,1),
        [email]         [NVARCHAR](255)    NOT NULL,
        [password]      [NVARCHAR](512)    NOT NULL,
        [first_name]    [NVARCHAR](50)     NOT NULL,
        [last_name]     [NVARCHAR](50)     NOT NULL,
        [role_id]       [BIGINT]           NOT NULL,
        [lang_id]       [BIGINT]           NOT NULL,

        CONSTRAINT [PK_users] PRIMARY KEY CLUSTERED ([Id] ASC),

        CONSTRAINT [FK_users_role_id] FOREIGN KEY ([role_id])
        REFERENCES [roles] (id)
            ON DELETE CASCADE
            ON UPDATE CASCADE,

        CONSTRAINT [FK_users_langs_id] FOREIGN KEY ([lang_id])
        REFERENCES [langs] (id)
            ON DELETE CASCADE
            ON UPDATE CASCADE
    )

    CREATE UNIQUE NONCLUSTERED INDEX [IDX_users_email] ON [users] ([email] ASC)
END
GO
-- #create-table: users <<< END