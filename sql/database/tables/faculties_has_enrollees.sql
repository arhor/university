-- #dependencies: [faculties, enrollees]

-- #create-table: faculties_has_enrollees  >>> START
USE [university]
GO

IF (OBJECT_ID('faculties_has_enrollees'))
BEGIN
    CREATE TABLE [dbo].[faculties_has_enrollees]
    (
        [faculty_id]     [BIGINT]      NOT NULL,
        [enrollee_id]    [BIGINT]      NOT NULL,
        [filing_date]    [DATETIME]    NOT NULL,

        CONSTRAINT [PK_faculties_has_enrollees] PRIMARY KEY CLUSTERED ([faculty_id], [enrollee_id]),

        CONSTRAINT [FK_faculties_has_enrollees_faculty_id] FOREIGN KEY ([faculty_id] ASC)
        REFERENCES [dbo].[faculties] (id)
            ON DELETE CASCADE
            ON UPDATE CASCADE,

        CONSTRAINT [FK_faculties_has_enrollees_enrollee_id] FOREIGN KEY ([enrollee_id] ASC)
        REFERENCES [dbo].[enrollees] (id)
            ON DELETE CASCADE
            ON UPDATE CASCADE
    )
END
GO
-- #create-table: faculties_has_enrollees <<< END