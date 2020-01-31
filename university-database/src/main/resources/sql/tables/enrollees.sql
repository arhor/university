--#dependencies: [users]

-- #create-table: enrollees >>> START
USE [university];

IF (OBJECT_ID('enrollees') IS NULL)
BEGIN
    CREATE TABLE [enrollees]
    (
        [id]              [BIGINT]          NOT NULL IDENTITY(1,1),
        [country]         [NVARCHAR](64)    NOT NULL,
        [city]            [NVARCHAR](64)    NOT NULL,
        [school_score]    [SMALLINT]        NOT NULL,
        [user_id]         [BIGINT]          NOT NULL UNIQUE,

        CONSTRAINT [PK_enrollees] PRIMARY KEY CLUSTERED ([Id] ASC),

        CONSTRAINT [CK_enrollees_school_score] CHECK
        (
            [school_score] >= 0 AND [school_score] <= 100
        ),

        CONSTRAINT [FK_enrollees_user_id] FOREIGN KEY ([user_id])
        REFERENCES [users] (id)
            ON DELETE CASCADE
            ON UPDATE CASCADE
    )

    CREATE NONCLUSTERED INDEX [IDX_enrollees_country] ON [enrollees] ([country] ASC)

    CREATE NONCLUSTERED INDEX [IDX_enrollees_city] ON [enrollees] ([city] ASC)
END
-- #create-table: enrollees <<< END