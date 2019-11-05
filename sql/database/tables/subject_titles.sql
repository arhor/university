-- #dependencies: [subjects, langs]

-- #create-table: subject_titles >>> START
USE [university]
GO

IF (OBJECT_ID('subject_titles') IS NULL)
BEGIN
    CREATE TABLE [dbo].[subject_titles]
    (
        [subject_id]     [BIGINT]          NOT NULL,
        [lang_id]        [BIGINT]          NOT NULL,
        [title]          [NVARCHAR](64)    NOT NULL,

        CONSTRAINT [PK_subject_titles] PRIMARY KEY CLUSTERED ([subject_id], [lang_id]),

        CONSTRAINT [FK_subject_titles_subject_id] FOREIGN KEY ([subject_id])
        REFERENCES [dbo].[subjects] (id)
            ON DELETE CASCADE
            ON UPDATE CASCADE,

        CONSTRAINT [FK_subject_titles_lang_id] FOREIGN KEY ([lang_id])
        REFERENCES [dbo].[langs] (id)
            ON DELETE CASCADE
            ON UPDATE CASCADE
    )
END
GO
-- #create-table: subject_titles <<< END