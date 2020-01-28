-- #dependencies: [faculties, subjects]

-- #create-table: faculties_has_subjects  >>> START
USE [university]
GO

IF (OBJECT_ID('faculties_has_subjects') IS NULL)
BEGIN
    CREATE TABLE [faculties_has_subjects]
    (
        [faculty_id]     [BIGINT]      NOT NULL,
        [subject_id]     [BIGINT]      NOT NULL,

        CONSTRAINT [PK_faculties_has_subjects] PRIMARY KEY CLUSTERED ([faculty_id], [subject_id]),

        CONSTRAINT [FK_faculties_has_subjects_faculty_id] FOREIGN KEY ([faculty_id])
        REFERENCES [faculties] (id)
            ON DELETE CASCADE
            ON UPDATE CASCADE,

        CONSTRAINT [FK_faculties_has_subjects_subject_id] FOREIGN KEY ([subject_id])
        REFERENCES [subjects] (id)
            ON DELETE CASCADE
            ON UPDATE CASCADE
    )
END
GO
-- #create-table: faculties_has_subjects <<< END