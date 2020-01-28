-- #dependencies: [subjects, enrollees]

-- #create-table: enrollees_has_subjects  >>> START
USE [university]
GO

IF (OBJECT_ID('enrollees_has_subjects') IS NULL)
BEGIN
    CREATE TABLE [enrollees_has_subjects]
    (
        [subject_id]     [BIGINT]      NOT NULL,
        [enrollee_id]    [BIGINT]      NOT NULL,
        [score]          [SMALLINT]    NOT NULL,

        CONSTRAINT [PK_enrollees_has_subjects] PRIMARY KEY CLUSTERED ([subject_id], [enrollee_id]),

        CONSTRAINT [CK_enrollees_has_subjects_score] CHECK
        (
            [score] >= 0 AND [score] <= 100
        ),

        CONSTRAINT [FK_enrollees_has_subjects_subject_id] FOREIGN KEY ([subject_id])
        REFERENCES [subjects] (id)
            ON DELETE CASCADE
            ON UPDATE CASCADE,

        CONSTRAINT [FK_enrollees_has_subjects_enrollee_id] FOREIGN KEY ([enrollee_id])
        REFERENCES [enrollees] (id)
            ON DELETE CASCADE
            ON UPDATE CASCADE
    )
END
GO
-- #create-table: enrollees_has_subjects <<< END