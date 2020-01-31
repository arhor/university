-- #create-table: subjects >>> START
USE [university];

IF (OBJECT_ID('subjects') IS NULL)
BEGIN
    CREATE TABLE [subjects]
    (
        [id]               [BIGINT]          NOT NULL IDENTITY(1,1),
        [default_title]    [NVARCHAR](50)    NOT NULL UNIQUE,

        CONSTRAINT [PK_subjects] PRIMARY KEY CLUSTERED ([Id] ASC)
    )
END
-- #create-table: subjects <<< END