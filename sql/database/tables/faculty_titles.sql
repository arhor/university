-- #dependencies: [faculties, langs]

-- #create-table: faculty_titles >>> START
USE [university]
GO

IF (OBJECT_ID('faculty_titles') IS NULL)
BEGIN
    CREATE TABLE [dbo].[faculty_titles]
    (
        [faculty_id]    [BIGINT]          NOT NULL,
        [lang_id]       [BIGINT]          NOT NULL,
        [title]         [NVARCHAR](64)    NOT NULL,

        CONSTRAINT [PK_faculty_titles] PRIMARY KEY CLUSTERED ([faculty_id], [lang_id]),

        CONSTRAINT [FK_faculty_titles_faculty_id] FOREIGN KEY ([faculty_id])
        REFERENCES [dbo].[faculties] (id)
            ON DELETE CASCADE
            ON UPDATE CASCADE,

        CONSTRAINT [FK_faculty_titles_lang_id] FOREIGN KEY ([lang_id])
        REFERENCES [dbo].[langs] (id)
            ON DELETE CASCADE
            ON UPDATE CASCADE
    )
END
GO
-- #create-table: faculty_titles <<< END