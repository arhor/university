-- #create-table: faculties  >>> START
USE university
GO

IF (OBJECT_ID('faculties') IS NULL)
BEGIN
    CREATE TABLE faculties
    (
        id               BIGINT          NOT NULL IDENTITY(1,1),
        default_title    NVARCHAR(128)   NOT NULL UNIQUE,
        seats_paid       SMALLINT        NOT NULL,
        seats_budget     SMALLINT        NOT NULL,

        CONSTRAINT PK_faculties PRIMARY KEY CLUSTERED (id ASC)
    )
END
-- #create-table: faculties <<< END