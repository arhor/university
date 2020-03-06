-- #module: university >>> START
USE master
GO

IF (DB_ID('university') IS NULL)
BEGIN
    CREATE DATABASE university
    ON
    (
        NAME = university_db,
        FILENAME = 'C:\database\university_db.mdf',
        SIZE = 128MB,
        MAXSIZE = UNLIMITED,
        FILEGROWTH = 15%
    )
    LOG ON
    (
        NAME = university_log,
        FILENAME = 'C:\database\university_db.ldf',
        SIZE = 128MB,
        MAXSIZE = UNLIMITED,
        FILEGROWTH = 15%
    )
END
GO

USE university
GO

IF (DATABASE_PRINCIPAL_ID('UniversitySA') IS NULL)
BEGIN
    CREATE LOGIN UniversitySA WITH PASSWORD = '1university@SECRET!', DEFAULT_DATABASE = university
    CREATE USER UniversitySA FOR LOGIN UniversitySA
END
GO

GRANT CONTROL ON DATABASE::university TO UniversitySA
GO
-- #module: university <<< END

-- #module: subjects >>> START
IF (OBJECT_ID('subjects') IS NULL)
BEGIN
    CREATE TABLE subjects
    (
        id               BIGINT          NOT NULL IDENTITY(1,1),
        default_title    NVARCHAR(50)    NOT NULL UNIQUE,
        CONSTRAINT PK_subjects PRIMARY KEY CLUSTERED (id ASC)
    )
END
GO
-- #module: subjects <<< END

-- #module: langs >>> START
IF (OBJECT_ID('langs') IS NULL)
BEGIN
    CREATE TABLE langs
    (
        id       BIGINT     NOT NULL IDENTITY(1,1),
        label    CHAR(2)    NOT NULL CHECK (label LIKE '[A-Z][A-Z]'),
        CONSTRAINT PK_langs PRIMARY KEY CLUSTERED (id ASC)
    )
END
GO
-- #module: langs <<< END

-- #module: faculties >>> START
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
GO
-- #module: faculties <<< END

-- #module: roles >>> START
IF (OBJECT_ID('roles') IS NULL)
BEGIN
    CREATE TABLE roles
    (
        id       BIGINT         NOT NULL IDENTITY(1,1),
        title    NVARCHAR(15)   NOT NULL,
        CONSTRAINT PK_roles PRIMARY KEY CLUSTERED (id ASC)
    )
END
GO
-- #module: roles <<< END

-- #module: labels >>> START
-- #dependencies: [langs]

IF (OBJECT_ID('labels') IS NULL)
BEGIN
    CREATE TABLE labels
    (
        lang_id       BIGINT           NOT NULL,
        label         NVARCHAR(64)     NOT NULL,
        value         NVARCHAR(500)    NOT NULL,
        CONSTRAINT PK_labels PRIMARY KEY CLUSTERED (lang_id, label),
        CONSTRAINT FK_faculty_titles_lang_id FOREIGN KEY (lang_id)
        REFERENCES langs (id)
            ON DELETE CASCADE
            ON UPDATE CASCADE
    )
    CREATE NONCLUSTERED INDEX IDX_labels_lang_id ON labels (lang_id ASC)
END
GO
-- #module: labels <<< END

-- #module: users >>> START
-- #dependencies: [roles, langs]

IF (OBJECT_ID('users') IS NULL)
BEGIN
    CREATE TABLE users
    (
        id            BIGINT           NOT NULL IDENTITY(1,1),
        email         NVARCHAR(255)    NOT NULL UNIQUE,
        password      NVARCHAR(512)    NOT NULL,
        first_name    NVARCHAR(50)     NOT NULL,
        last_name     NVARCHAR(50)     NOT NULL,
        role_id       BIGINT           NOT NULL,
        lang_id       BIGINT           NOT NULL,
        CONSTRAINT PK_users PRIMARY KEY CLUSTERED (id ASC),
        CONSTRAINT FK_users_role_id FOREIGN KEY (role_id)
        REFERENCES roles (id)
            ON DELETE CASCADE
            ON UPDATE CASCADE,
        CONSTRAINT FK_users_langs_id FOREIGN KEY (lang_id)
        REFERENCES langs (id)
            ON DELETE CASCADE
            ON UPDATE CASCADE
    )
    CREATE NONCLUSTERED INDEX IDX_users_email ON users (email ASC)
    CREATE NONCLUSTERED INDEX IDX_users_lang_id ON users (lang_id ASC)
    CREATE NONCLUSTERED INDEX IDX_users_role_id ON users (role_id ASC)
END
GO
-- #module: users <<< END

-- #module: faculties_has_subjects >>> START
-- #dependencies: [faculties, subjects]

IF (OBJECT_ID('faculties_has_subjects') IS NULL)
BEGIN
    CREATE TABLE faculties_has_subjects
    (
        faculty_id     BIGINT      NOT NULL,
        subject_id     BIGINT      NOT NULL,
        CONSTRAINT PK_faculties_has_subjects PRIMARY KEY CLUSTERED (faculty_id, subject_id),
        CONSTRAINT FK_faculties_has_subjects_faculty_id FOREIGN KEY (faculty_id)
        REFERENCES faculties (id)
            ON DELETE CASCADE
            ON UPDATE CASCADE,
        CONSTRAINT FK_faculties_has_subjects_subject_id FOREIGN KEY (subject_id)
        REFERENCES subjects (id)
            ON DELETE CASCADE
            ON UPDATE CASCADE
    )
    CREATE NONCLUSTERED INDEX IDX_faculties_has_subjects_faculty_id ON faculties_has_subjects (faculty_id ASC)
    CREATE NONCLUSTERED INDEX IDX_faculties_has_subjects_subject_id ON faculties_has_subjects (subject_id ASC)
END
GO
-- #module: faculties_has_subjects <<< END

-- #module: users_audit >>> START
-- #dependencies: [users]

IF (OBJECT_ID('users_audit') IS NULL)
BEGIN
    CREATE TABLE users_audit
    (
        id            BIGINT        NOT NULL IDENTITY(1, 1),
        user_id       BIGINT        NOT NULL,
        email         NVARCHAR(255) NOT NULL,
        password      NVARCHAR(512) NOT NULL,
        first_name    NVARCHAR(50)  NOT NULL,
        last_name     NVARCHAR(50)  NOT NULL,
        role_id       BIGINT        NOT NULL,
        modified_by   VARCHAR(128)  NOT NULL,
        modified_date DATETIME      NOT NULL DEFAULT GETDATE(),
        operation     VARCHAR(20)   NOT NULL CHECK (operation in ('CREATED', 'DELETED')),
        CONSTRAINT PK_users_audit PRIMARY KEY CLUSTERED (id ASC)
    )
END
GO
-- #module: users_audit <<< END

-- #module: users_audit_modification >>> START
-- #dependencies: [users]

IF (OBJECT_ID('users_audit_modification') IS NULL)
BEGIN
    CREATE TABLE users_audit_modification
    (
        id            BIGINT        NOT NULL IDENTITY(1, 1),
        user_id       BIGINT        NOT NULL,
        field_name    VARCHAR(64)   NOT NULL,
        old_value     NVARCHAR(512) NULL,
        new_value     NVARCHAR(512) NULL,
        modified_by   VARCHAR(128)  NOT NULL,
        modified_date DATETIME      NOT NULL DEFAULT GETDATE(),
        CONSTRAINT PK_users_audit_modification PRIMARY KEY CLUSTERED (id ASC)
    )
END
GO
-- #module: users_audit_modification <<< END

-- #module: enrollees >>> START
-- #dependencies: [users]

IF (OBJECT_ID('enrollees') IS NULL)
BEGIN
    CREATE TABLE enrollees
    (
        id              BIGINT          NOT NULL IDENTITY(1,1),
        country         NVARCHAR(64)    NOT NULL,
        city            NVARCHAR(64)    NOT NULL,
        school_score    SMALLINT        NOT NULL CHECK (school_score >= 0 AND school_score <= 100),
        user_id         BIGINT          NOT NULL UNIQUE,
        CONSTRAINT PK_enrollees PRIMARY KEY CLUSTERED (id ASC),
        CONSTRAINT FK_enrollees_user_id FOREIGN KEY (user_id)
        REFERENCES users (id)
            ON DELETE CASCADE
            ON UPDATE CASCADE
    )
    CREATE NONCLUSTERED INDEX IDX_enrollees_user_id ON enrollees (user_id ASC)
    CREATE NONCLUSTERED INDEX IDX_enrollees_country ON enrollees (country ASC)
    CREATE NONCLUSTERED INDEX IDX_enrollees_city ON enrollees (city ASC)
END
GO
-- #module: enrollees <<< END

-- #module: TR_users_audit >>> START
-- #dependencies: [users_audit]

IF OBJECT_ID('TR_users_audit','TR') IS NOT NULL
BEGIN
    DROP TRIGGER TR_users_audit
END
GO

CREATE TRIGGER TR_users_audit ON users
    FOR INSERT, DELETE
AS
BEGIN
    DECLARE @login_name VARCHAR(128) = (
        SELECT login_name
        FROM sys.dm_exec_sessions
        WHERE session_id = @@SPID
    )
    IF EXISTS (SELECT * FROM deleted)
    BEGIN
        INSERT INTO users_audit (user_id, email, password, first_name, last_name, role_id, modified_by, operation)
        SELECT id, email, password, first_name, last_name, role_id, @login_name, 'DELETED' FROM deleted
    END
    ELSE
    BEGIN
        INSERT INTO users_audit (user_id, email, password, first_name, last_name, role_id, modified_by, operation)
        SELECT id, email, password, first_name, last_name, role_id, @login_name, 'CREATED' FROM inserted
    END
END
GO
-- #module: TR_users_audit <<< END

-- #module: faculties_has_enrollees >>> START
-- #dependencies: [faculties, enrollees]

IF (OBJECT_ID('faculties_has_enrollees') IS NULL)
BEGIN
    CREATE TABLE faculties_has_enrollees
    (
        faculty_id     BIGINT      NOT NULL,
        enrollee_id    BIGINT      NOT NULL,
        filing_date    DATETIME    NOT NULL DEFAULT GETDATE(),
        CONSTRAINT PK_faculties_has_enrollees PRIMARY KEY CLUSTERED (faculty_id, enrollee_id),
        CONSTRAINT FK_faculties_has_enrollees_faculty_id FOREIGN KEY (faculty_id)
        REFERENCES faculties (id)
            ON DELETE CASCADE
            ON UPDATE CASCADE,
        CONSTRAINT FK_faculties_has_enrollees_enrollee_id FOREIGN KEY (enrollee_id)
        REFERENCES enrollees (id)
            ON DELETE CASCADE
            ON UPDATE CASCADE
    )
    CREATE NONCLUSTERED INDEX IDX_faculties_has_enrollees_faculty_id ON faculties_has_enrollees (faculty_id ASC)
    CREATE NONCLUSTERED INDEX IDX_faculties_has_enrollees_enrollee_id ON faculties_has_enrollees (enrollee_id ASC)
END
GO
-- #module: faculties_has_enrollees <<< END

-- #module: enrollees_has_subjects >>> START
-- #dependencies: [subjects, enrollees]

IF (OBJECT_ID('enrollees_has_subjects') IS NULL)
BEGIN
    CREATE TABLE enrollees_has_subjects
    (
        subject_id     BIGINT      NOT NULL,
        enrollee_id    BIGINT      NOT NULL,
        score          SMALLINT    NOT NULL CHECK (score >= 0 AND score <= 100),
        CONSTRAINT PK_enrollees_has_subjects PRIMARY KEY CLUSTERED (subject_id, enrollee_id),
        CONSTRAINT FK_enrollees_has_subjects_subject_id FOREIGN KEY (subject_id)
        REFERENCES subjects (id)
            ON DELETE CASCADE
            ON UPDATE CASCADE,
        CONSTRAINT FK_enrollees_has_subjects_enrollee_id FOREIGN KEY (enrollee_id)
        REFERENCES enrollees (id)
            ON DELETE CASCADE
            ON UPDATE CASCADE
    )
    CREATE NONCLUSTERED INDEX IDX_enrollees_has_subjects_subject_id ON enrollees_has_subjects (subject_id ASC)
    CREATE NONCLUSTERED INDEX IDX_enrollees_has_subjects_enrollee_id ON enrollees_has_subjects (enrollee_id ASC)
END
GO
-- #module: enrollees_has_subjects <<< END

-- #module: getAdminRole >>> START
-- #dependencies: [roles]

IF (OBJECT_ID('getAdminRole') IS NOT NULL)
BEGIN
    DROP PROCEDURE getAdminRole
END
GO

CREATE PROCEDURE dbo.getAdminRole
AS
BEGIN
    DECLARE @adminRole NVARCHAR(10) = N'ADMIN'
    DECLARE @id AS BIGINT
    SELECT @id = roles.id
    FROM  roles WITH(NOLOCK)
    WHERE roles.title = @adminRole
    IF (@id IS NULL)
    BEGIN
        SET TRANSACTION ISOLATION LEVEL SERIALIZABLE
        BEGIN TRANSACTION
            SELECT @id = roles.id
            FROM  roles
            WHERE roles.title = @adminRole
            IF (@id IS NULL)
            BEGIN
                INSERT INTO roles (title) VALUES (@adminRole)
                SELECT @id = SCOPE_IDENTITY()
            END
        COMMIT TRANSACTION
    END
    SELECT @id
    RETURN @id
END
GO
-- #module: getAdminRole <<< END

-- #module: getDefaultRole >>> START
-- #dependencies: [roles]

IF (OBJECT_ID('getDefaultRole') IS NOT NULL)
BEGIN
    DROP PROCEDURE getDefaultRole
END
GO

CREATE PROCEDURE dbo.getDefaultRole
AS
BEGIN
    DECLARE @defaultRole NVARCHAR(10) = N'USER'
    DECLARE @id AS BIGINT
    SELECT @id = roles.id
    FROM  roles WITH(NOLOCK)
    WHERE roles.title = @defaultRole
    IF (@id IS NULL)
    BEGIN
        SET TRANSACTION ISOLATION LEVEL SERIALIZABLE
        BEGIN TRANSACTION
            SELECT @id = roles.id
            FROM  roles
            WHERE roles.title = @defaultRole
            IF (@id IS NULL)
            BEGIN
                INSERT INTO roles (title) VALUES (@defaultRole)
                SELECT @id = SCOPE_IDENTITY()
            END
        COMMIT TRANSACTION
    END
    SELECT @id
    RETURN @id
END
GO
-- #module: getDefaultRole <<< END

-- #module: getDefaultLang >>> START
-- #dependencies: [langs]

IF (OBJECT_ID('getDefaultLang') IS NOT NULL)
BEGIN
    DROP PROCEDURE getDefaultLang
END
GO

CREATE PROCEDURE dbo.getDefaultLang
AS
BEGIN
    DECLARE @defaultLang CHAR(2) = 'RU'
    DECLARE @id AS BIGINT
    SELECT @id = langs.id
    FROM  langs WITH(NOLOCK)
    WHERE langs.label = @defaultLang
    IF (@id IS NULL)
    BEGIN
        SET TRANSACTION ISOLATION LEVEL SERIALIZABLE
        BEGIN TRANSACTION
            SELECT @id = langs.id
            FROM  langs
            WHERE langs.label = @defaultLang
            IF (@id IS NULL)
            BEGIN
                INSERT INTO langs (label) VALUES (@defaultLang)
                SELECT @id = SCOPE_IDENTITY()
            END
        COMMIT TRANSACTION
    END
    SELECT @id
    RETURN @id
END
GO
-- #module: getDefaultLang <<< END

-- #module: logChanges >>> START
-- #dependencies: [users_audit_modification]

IF (OBJECT_ID('logChanges') IS NOT NULL)
BEGIN
    DROP PROCEDURE logChanges
END
GO

CREATE PROCEDURE dbo.logChanges
    @field      VARCHAR(64),
    @old_val    VARCHAR(512),
    @new_val    VARCHAR(512),
    @id         BIGINT
AS
BEGIN
    IF (@old_val != @new_val)
    BEGIN
        DECLARE @login_name VARCHAR(128) = (
            SELECT login_name
            FROM sys.dm_exec_sessions
            WHERE session_id = @@SPID
        )
        INSERT INTO users_audit_modification (user_id, field_name, old_value, new_value, modified_by)
        VALUES (@id, @field, @old_val, @new_val, @login_name)
    END
END
GO
-- #module: logChanges <<< END

-- #module: TR_users_audit_modification >>> START
-- #dependencies: [users_audit_modification, logChanges]

IF OBJECT_ID('TR_users_audit_modification','TR') IS NOT NULL
BEGIN
    DROP TRIGGER TR_users_audit_modification
END
GO

CREATE TRIGGER TR_users_audit_modification ON users
    FOR UPDATE
AS
BEGIN
    DECLARE @id BIGINT = (SELECT id FROM inserted)
    DECLARE @field_name VARCHAR(64)
          , @old_value  NVARCHAR(512)
          , @new_value  NVARCHAR(512)
    IF UPDATE(email)
    BEGIN
        SET @field_name = 'email'
        SELECT @old_value = CAST(email AS NVARCHAR(512)) FROM deleted
        SELECT @new_value = CAST(email AS NVARCHAR(512)) FROM inserted
        EXECUTE dbo.logChanges @field_name, @old_value, @new_value, @id
    END
    IF UPDATE(password)
    BEGIN
        SET @field_name = 'password'
        SELECT @old_value = CAST(password AS NVARCHAR(512)) FROM deleted
        SELECT @new_value = CAST(password AS NVARCHAR(512)) FROM inserted
        EXECUTE dbo.logChanges @field_name, @old_value, @new_value, @id
    END
    IF UPDATE(first_name)
    BEGIN
        SET @field_name = 'first_name'
        SELECT @old_value = CAST(first_name AS NVARCHAR(512)) FROM deleted
        SELECT @new_value = CAST(first_name AS NVARCHAR(512)) FROM inserted
        EXECUTE dbo.logChanges @field_name, @old_value, @new_value, @id
    END
    IF UPDATE(last_name)
    BEGIN
        SET @field_name = 'last_name'
        SELECT @old_value = CAST(last_name AS NVARCHAR(512)) FROM deleted
        SELECT @new_value = CAST(last_name AS NVARCHAR(512)) FROM inserted
        EXECUTE dbo.logChanges @field_name, @old_value, @new_value, @id
    END
    IF UPDATE(role_id)
    BEGIN
        SET @field_name = 'role_id'
        SELECT @old_value = CAST(role_id AS NVARCHAR(512)) FROM deleted
        SELECT @new_value = CAST(role_id AS NVARCHAR(512)) FROM inserted
        EXECUTE dbo.logChanges @field_name, @old_value, @new_value, @id
    END
    IF UPDATE(lang_id)
    BEGIN
        SET @field_name = 'lang_id'
        SELECT @old_value = CAST(lang_id AS NVARCHAR(512)) FROM deleted
        SELECT @new_value = CAST(lang_id AS NVARCHAR(512)) FROM inserted
        EXECUTE dbo.logChanges @field_name, @old_value, @new_value, @id
    END
END
GO
-- #module: TR_users_audit_modification <<< END

-- #module: calcEnrollmentPosition >>> START
-- #dependencies: [enrollees, enrollees_has_subjects, faculties_has_enrollees]

IF OBJECT_ID('calcEnrollmentPosition') IS NOT NULL
BEGIN
    DROP FUNCTION calcEnrollmentPosition
END
GO

CREATE FUNCTION dbo.calcEnrollmentPosition (
    @facultyId  BIGINT,
    @enrolleeId BIGINT
)
RETURNS INT
AS
BEGIN
    DECLARE @position INT
    ;WITH dashboard AS (
        SELECT e.id, ROW_NUMBER() OVER(ORDER BY e.school_score + SUM(ehs.score) DESC) AS 'num'
        FROM faculties_has_enrollees fhe
        JOIN enrollees e ON e.id = fhe.enrollee_id
        JOIN enrollees_has_subjects ehs  ON e.id = ehs.enrollee_id
        WHERE fhe.faculty_id = @facultyId
        GROUP BY e.id, e.school_score
    )
    SELECT @position = num FROM dashboard WHERE id = @enrolleeId
    RETURN COALESCE(@position, 0)
END
GO
-- #module: calcEnrollmentPosition <<< END

-- #module: createNewUser >>> START
-- #dependencies: [roles, langs, users, getDefaultRole, getDefaultLang]

IF (OBJECT_ID('createNewUser') IS NOT NULL)
BEGIN
    DROP PROCEDURE createNewUser
END
GO

CREATE PROCEDURE dbo.createNewUser
    @email         NVARCHAR(255),
    @password      NVARCHAR(512),
    @first_name    NVARCHAR(50),
    @last_name     NVARCHAR(50),
    @role_id       BIGINT,
    @lang_id       BIGINT
AS
BEGIN
    DECLARE @default_role_id BIGINT
    DECLARE @default_lang_id BIGINT
    EXECUTE @default_role_id = dbo.getDefaultRole;
    EXECUTE @default_lang_id = dbo.getDefaultLang;
    INSERT INTO users (email, password, first_name, last_name, role_id, lang_id)
    VALUES
    (
        @email,
        @password,
        @first_name,
        @last_name,
        COALESCE(@role_id, @default_role_id),
        COALESCE(@lang_id, @default_lang_id)
    )
    SELECT u.id
         , u.email
         , u.password
         , u.first_name
         , u.last_name
         , u.role_id
         , u.lang_id
    FROM users u WITH(NOLOCK)
    WHERE u.email = @email
END
GO
-- #module: createNewUser <<< END

-- #module: enrollment_campaign >>> START
-- #dependencies: [langs, roles, users, enrollees, faculties, enrollees_has_subjects, faculties_has_enrollees, calcEnrollmentPosition]

IF OBJECT_ID('enrollment_campaign') IS NOT NULL
BEGIN
    DROP VIEW enrollment_campaign
END
GO

CREATE VIEW enrollment_campaign
AS
SELECT r.title AS 'Role'
     , l.label AS 'Language'
     , u.first_name AS 'First Name'
     , u.last_name AS 'Last Name'
     , e.country AS 'Country'
     , e.city AS 'City'
     , e.school_score + SUM(ehs.score) AS 'Total Score'
     , f.default_title AS 'Faculty'
     , (CASE
            WHEN dbo.calcEnrollmentPosition(f.id, e.id) < f.seats_budget THEN 'budget'
            WHEN dbo.calcEnrollmentPosition(f.id, e.id) < f.seats_paid THEN 'paid'
            ELSE 'does not pass'
        END) AS 'Status'
FROM users u
JOIN langs l ON l.id = u.lang_id
JOIN roles r ON r.id = u.role_id
JOIN enrollees e ON u.id = e.user_id
JOIN enrollees_has_subjects ehs ON e.id = ehs.enrollee_id
JOIN faculties_has_enrollees fhe ON e.id = fhe.enrollee_id
JOIN faculties f ON f.id = fhe.faculty_id
GROUP BY r.title
       , l.label
       , u.id
       , u.first_name
       , u.last_name
       , e.id
       , e.country
       , e.city
       , e.school_score
       , f.id
       , f.default_title
       , f.seats_budget
eats_paid
ORDER BY f.id ASC
       , 'Total Score' DESC
GO
-- #module: enrollment_campaign <<< END

-- #module: init-faculties >>> START
-- #dependencies: [faculties]

DECLARE @TempFaculties TABLE
(
    id       INT,
    title    NVARCHAR(128)
)
INSERT INTO @TempFaculties (id, title)
VALUES (0, N'Биологический факультет')
     , (1, N'Исторический факультет')
     , (2, N'Химический факультет')
     , (3, N'Факультет прикладной математики и информатики')
     , (4, N'Факультет радиофизики и компьютерных технологий')
     , (5, N'Экономический факультет')
     , (6, N'Юридический факультет')
     , (7, N'Военный факультет')
     , (8, N'Филологический факультет')
     , (9, N'Республиканский институт китаеведения имени Конфуция')
DECLARE @counter INT = 0
WHILE (@counter <= 9)
BEGIN
    DECLARE @title NVARCHAR(64) = (SELECT title FROM @TempFaculties WHERE id = @counter)
    IF NOT EXISTS (SELECT * FROM faculties WHERE default_title = @title)
    BEGIN
        INSERT INTO faculties (default_title, seats_budget, seats_paid)
        VALUES
        (
            @title,
            CEILING((10 * RAND()) + 5),
            CEILING((25 * RAND()) + 15)
        )
    END
    SET @counter = @counter + 1
END
GO
-- #module: init-faculties <<< END

-- #module: init-langs >>> START
-- #dependencies: [langs]

DECLARE @TempLangs TABLE
(
    id       INT,
    label    CHAR(2)
)
INSERT INTO @TempLangs (id, label)
VALUES (1, 'RU')
     , (2, 'EN')
DECLARE @counter INT = 0
WHILE (@counter < 2)
BEGIN
    DECLARE @label CHAR(2) = (SELECT label FROM @TempLangs WHERE id = @counter)
    IF NOT EXISTS (SELECT * FROM langs WHERE label = @label)
    BEGIN
        INSERT INTO langs (label) VALUES (@label)
    END
    SET @counter = @counter + 1
END
GO
-- #module: init-langs <<< END

-- #module: init-subjects >>> START
-- #dependencies: [subjects]

DECLARE @TempSubjects TABLE
(
    id       INT,
    title    NVARCHAR(64)
)
INSERT INTO @TempSubjects (id, title)
VALUES (0, N'Русский язык')
     , (1, N'Белорусский язык')
     , (2, N'Иностранный язык')
     , (3, N'Математика')
     , (4, N'Физика')
     , (5, N'Химия')
     , (6, N'История')
     , (7, N'Биология')
DECLARE @counter INT = 0
WHILE (@counter <= 7)
BEGIN
    DECLARE @title NVARCHAR(64) = (SELECT title FROM @TempSubjects WHERE id = @counter)
    IF NOT EXISTS (SELECT * FROM subjects WHERE default_title = @title)
    BEGIN
        INSERT INTO subjects (default_title) VALUES (@title)
    END
    SET @counter = @counter + 1
END
GO
-- #module: init-subjects <<< END

-- #module: init-roles >>> START
-- #dependencies: [roles]

IF NOT EXISTS (SELECT * FROM roles WHERE title = 'USER')
BEGIN
    INSERT INTO roles (title) VALUES ('USER')
END
GO

IF NOT EXISTS (SELECT * FROM roles WHERE title = 'ADMIN')
BEGIN
    INSERT INTO roles (title) VALUES ('ADMIN')
END
GO
-- #module: init-roles <<< END

-- #module: init-faculties_has_subjects >>> START
-- #dependencies: [faculties, subjects, faculties_has_subjects, init-faculties, init-subjects]

DECLARE @facultyId BIGINT
DECLARE @subjectId BIGINT
--------------------------- Биологический факультет ---------------------------
SELECT @facultyId = id FROM faculties WITH(NOLOCK) WHERE default_title = N'Биологический факультет'
INSERT INTO faculties_has_subjects (faculty_id, subject_id)
VALUES
(@facultyId, (SELECT id FROM subjects WITH(NOLOCK) WHERE default_title = N'Биология')),
(@facultyId, (SELECT id FROM subjects WITH(NOLOCK) WHERE default_title = N'Химия')),
(@facultyId, (SELECT id FROM subjects WITH(NOLOCK) WHERE default_title = N'Русский язык'))
---------------------------- Исторический факультет ---------------------------
SELECT @facultyId = id FROM faculties WITH(NOLOCK) WHERE default_title = N'Исторический факультет'
INSERT INTO faculties_has_subjects (faculty_id, subject_id)
VALUES
(@facultyId, (SELECT id FROM subjects WITH(NOLOCK) WHERE default_title = N'Белорусский язык')),
(@facultyId, (SELECT id FROM subjects WITH(NOLOCK) WHERE default_title = N'Иностранный язык')),
(@facultyId, (SELECT id FROM subjects WITH(NOLOCK) WHERE default_title = N'История'))
-------------------------- Химический факультет -------------------------------
SELECT @facultyId = id FROM faculties WITH(NOLOCK) WHERE default_title = N'Химический факультет'
INSERT INTO faculties_has_subjects (faculty_id, subject_id)
VALUES
(@facultyId, (SELECT id FROM subjects WITH(NOLOCK) WHERE default_title = N'Химия')),
(@facultyId, (SELECT id FROM subjects WITH(NOLOCK) WHERE default_title = N'Русский язык')),
(@facultyId, (SELECT id FROM subjects WITH(NOLOCK) WHERE default_title = N'Физика'))
---------------- Факультет прикладной математики и информатики ----------------
SELECT @facultyId = id FROM faculties WITH(NOLOCK) WHERE default_title = N'Факультет прикладной математики и информатики'
INSERT INTO faculties_has_subjects (faculty_id, subject_id)
VALUES
(@facultyId, (SELECT id FROM subjects WITH(NOLOCK) WHERE default_title = N'Математика')),
(@facultyId, (SELECT id FROM subjects WITH(NOLOCK) WHERE default_title = N'Иностранный язык')),
(@facultyId, (SELECT id FROM subjects WITH(NOLOCK) WHERE default_title = N'Физика'))
------------- Факультет радиофизики и компьютерных технологий -----------------
SELECT @facultyId = id FROM faculties WITH(NOLOCK) WHERE default_title = N'Факультет радиофизики и компьютерных технологий'
INSERT INTO faculties_has_subjects (faculty_id, subject_id)
VALUES
(@facultyId, (SELECT id FROM subjects WITH(NOLOCK) WHERE default_title = N'Физика')),
(@facultyId, (SELECT id FROM subjects WITH(NOLOCK) WHERE default_title = N'Математика')),
(@facultyId, (SELECT id FROM subjects WITH(NOLOCK) WHERE default_title = N'Химия'))
------------------------- Экономический факультет -----------------------------
SELECT @facultyId = id FROM faculties WITH(NOLOCK) WHERE default_title = N'Экономический факультет'
INSERT INTO faculties_has_subjects (faculty_id, subject_id)
VALUES
(@facultyId, (SELECT id FROM subjects WITH(NOLOCK) WHERE default_title = N'Иностранный язык')),
(@facultyId, (SELECT id FROM subjects WITH(NOLOCK) WHERE default_title = N'История')),
(@facultyId, (SELECT id FROM subjects WITH(NOLOCK) WHERE default_title = N'Математика'))
-------------------------- Юридический факультет ------------------------------
SELECT @facultyId = id FROM faculties WITH(NOLOCK) WHERE default_title = N'Юридический факультет'
INSERT INTO faculties_has_subjects (faculty_id, subject_id)
VALUES
(@facultyId, (SELECT id FROM subjects WITH(NOLOCK) WHERE default_title = N'Белорусский язык')),
(@facultyId, (SELECT id FROM subjects WITH(NOLOCK) WHERE default_title = N'Иностранный язык')),
(@facultyId, (SELECT id FROM subjects WITH(NOLOCK) WHERE default_title = N'История'))
---------------------------- Военный факультет --------------------------------
SELECT @facultyId = id FROM faculties WITH(NOLOCK) WHERE default_title = N'Военный факультет'
INSERT INTO faculties_has_subjects (faculty_id, subject_id)
VALUES
(@facultyId, (SELECT id FROM subjects WITH(NOLOCK) WHERE default_title = N'Химия')),
(@facultyId, (SELECT id FROM subjects WITH(NOLOCK) WHERE default_title = N'Белорусский язык')),
(@facultyId, (SELECT id FROM subjects WITH(NOLOCK) WHERE default_title = N'История'))
------------------------ Филологический факультет -----------------------------
SELECT @facultyId = id FROM faculties WITH(NOLOCK) WHERE default_title = N'Филологический факультет'
INSERT INTO faculties_has_subjects (faculty_id, subject_id)
VALUES
(@facultyId, (SELECT id FROM subjects WITH(NOLOCK) WHERE default_title = N'Русский язык')),
(@facultyId, (SELECT id FROM subjects WITH(NOLOCK) WHERE default_title = N'Белорусский язык')),
(@facultyId, (SELECT id FROM subjects WITH(NOLOCK) WHERE default_title = N'Иностранный язык'))
------------ Республиканский институт китаеведения имени Конфуция -------------
SELECT @facultyId = id FROM faculties WITH(NOLOCK) WHERE default_title = N'Республиканский институт китаеведения имени Конфуция'
INSERT INTO faculties_has_subjects (faculty_id, subject_id)
VALUES
(@facultyId, (SELECT id FROM subjects WITH(NOLOCK) WHERE default_title = N'Иностранный язык')),
(@facultyId, (SELECT id FROM subjects WITH(NOLOCK) WHERE default_title = N'История')),
(@facultyId, (SELECT id FROM subjects WITH(NOLOCK) WHERE default_title = N'Биология'))
GO
-- #module: init-faculties_has_subjects <<< END

-- #module: init-users >>> START
-- #dependencies: [langs, users, getAdminRole, createNewUser, init-roles, init-langs]

-- password to use: `password` encrypted with BCrypt (strength 5)
DECLARE @Password NVARCHAR(512) = N'$2y$05$wc9f6o/gGJyoagNZfHkHJerFc0tIJAmdCQmabJCtXs0uOJhUAGICa'
DECLARE @RU BIGINT = (SELECT id FROM langs WITH(NOLOCK) WHERE label = 'RU')
DECLARE @EN BIGINT = (SELECT id FROM langs WITH(NOLOCK) WHERE label = 'EN')
IF NOT EXISTS (SELECT * FROM users WHERE email = N'admin@gmail.com')
BEGIN
    DECLARE @Admin BIGINT
    EXECUTE @Admin = dbo.getAdminRole
    EXECUTE dbo.createNewUser N'admin@gmail.com', @Password, N'Максим', N'Буришинец', @Admin,  @RU
END
DECLARE @counter INT = 1
WHILE (@counter <= 10000)
BEGIN
    DECLARE @Email NVARCHAR(50) = N'user.test' + CONVERT(NVARCHAR(3), @counter)  + N'@gmail.com'
    DECLARE @Lang INT = ROUND((3 * RAND()), 0)
    DECLARE @LangToUse BIGINT = (
        CASE
            WHEN @Lang = 0 THEN @RU
            WHEN @Lang = 2 THEN @EN
            ELSE @RU
        END
    )
    DECLARE @FN INT = ROUND((10 * RAND()), 0)
    DECLARE @FirstName NVARCHAR(30) = (
        CASE
            WHEN @FN = 0 THEN N'Максим'
            WHEN @FN = 1 THEN N'Иван'
            WHEN @FN = 2 THEN N'Семён'
            WHEN @FN = 3 THEN N'Александр'
            WHEN @FN = 4 THEN N'Валерий'
            WHEN @FN = 5 THEN N'Николай'
            WHEN @FN = 6 THEN N'Алексей'
            WHEN @FN = 7 THEN N'Виктор'
            WHEN @FN = 8 THEN N'Геннадий'
            WHEN @FN = 9 THEN N'Евгений'
            ELSE N'Клим'
        END
    )
    DECLARE @LN INT = ROUND((10 * RAND()), 0)
    DECLARE @LastName NVARCHAR(30) = (
        CASE
            WHEN @LN = 0 THEN N'Вадимович'
            WHEN @LN = 1 THEN N'Александрович'
            WHEN @LN = 2 THEN N'Иванович'
            WHEN @LN = 3 THEN N'Степанович'
            WHEN @LN = 4 THEN N'Григорьевич'
            WHEN @LN = 5 THEN N'Викторович'
            WHEN @LN = 6 THEN N'Валерьевич'
            WHEN @LN = 7 THEN N'Генрихович'
            WHEN @LN = 8 THEN N'Антонович'
            WHEN @LN = 9 THEN N'Алексеевич'
            ELSE N'Олимпиевич'
        END
    )
    IF NOT EXISTS (SELECT * FROM users WHERE email = @Email)
    BEGIN
        EXECUTE dbo.createNewUser @Email, @Password, @FirstName, @LastName, null, @LangToUse
    END
    SET @counter = @counter + 1
END
GO
-- #module: init-users <<< END

-- #module: init-enrollees >>> START
-- #dependencies: [users, enrollees, getAdminRole, init-users]

DECLARE @Countries TABLE
(
    id      BIGINT,
    name    NVARCHAR(64)
)
INSERT INTO @Countries (id, name)
VALUES (1, N'Беларусь'), (2, N'Россия'), (3, N'Украина'), (4, N'Польша');
DECLARE @Cities TABLE
(
    countryId    BIGINT,
    num          BIGINT,
    name         NVARCHAR(64)
)
INSERT INTO @Cities (countryId, num, name)
VALUES (1, 1, N'Минск')  , (1, 2, N'Гродно') , (1, 3, N'Гомель') , (1, 4, N'Могилёв')
     , (2, 1, N'Москва') , (2, 2, N'Саратов'), (2, 3, N'Магадан'), (2, 4, N'Суздаль')
     , (3, 1, N'Киев')   , (3, 2, N'Львов')  , (3, 3, N'Одесса') , (3, 4, N'Харьков')
     , (4, 1, N'Варшава'), (4, 2, N'Краков') , (4, 3, N'Гданьск'), (4, 4, N'Люблин');
DECLARE @Admin BIGINT
EXEC @Admin = dbo.getAdminRole
DECLARE @totalUsers INT = (SELECT COUNT(*) FROM users u WITH(NOLOCK) WHERE u.role_id != @Admin)
DECLARE @counter    INT = 0
WHILE (@counter < @totalUsers)
BEGIN
    DECLARE @userId BIGINT = (
        SELECT   u.id
        FROM     users u WITH(NOLOCK)
        WHERE    u.role_id != @Admin
        ORDER BY u.id ASC
        OFFSET @counter ROWS
        FETCH NEXT 1 ROWS ONLY
    )
    IF NOT EXISTS (SELECT * FROM enrollees e WITH(NOLOCK) WHERE e.user_id = @userId)
    BEGIN
        DECLARE @countryId INT = CEILING (4 * RAND())
        DECLARE @cityNum   INT = CEILING (4 * RAND())
        DECLARE @countryName NVARCHAR(64) = (
            SELECT name
            FROM @Countries
            WHERE id = @countryId
        )
        DECLARE @cityName NVARCHAR(64) = (
            SELECT name
            FROM @Cities
            WHERE countryId = @countryId
            AND num = @cityNum
        )
        INSERT INTO enrollees (country, city, school_score, user_id)
        VALUES
        (
            @countryName,
            @cityName,
            CEILING(100 * RAND()),
            @userId
        )
    END
    SET @counter = @counter + 1
END
GO
-- #module: init-enrollees <<< END

-- #module: init-enrollees_has_subjects >>> START
-- #dependencies: [enrollees, subjects, enrollees_has_subjects, init-enrollees, init-subjects]

TRUNCATE TABLE enrollees_has_subjects
GO

DECLARE @subjectsRequired INT = 3
DECLARE @totalFaculties INT = (SELECT COUNT(*) FROM faculties WITH(NOLOCK))
DECLARE @totalEnrollees INT = (SELECT COUNT(*) FROM enrollees WITH(NOLOCK))
DECLARE @counter INT = 0
WHILE (@counter < @totalEnrollees)
BEGIN
    DECLARE @enrolleeId BIGINT = (
        SELECT e.id
        FROM enrollees e WITH(NOLOCK)
        ORDER BY e.id ASC
        OFFSET @counter ROWS
        FETCH NEXT 1 ROWS ONLY
    )
    IF ((SELECT COUNT(*) FROM enrollees_has_subjects es WHERE es.enrollee_id = @enrolleeId) < @subjectsRequired)
    BEGIN
        PRINT N'Generating subjects for enrollee with ID = ' + CAST(@enrolleeId AS NVARCHAR(30))
        SET TRANSACTION ISOLATION LEVEL READ COMMITTED
        BEGIN TRANSACTION
            DELETE FROM enrollees_has_subjects WHERE enrollee_id = @enrolleeId
            DECLARE @facultyId BIGINT = (
                SELECT f.id
                FROM faculties f
                ORDER BY f.id ASC
                OFFSET CAST(FLOOR(@totalFaculties * RAND()) AS INT) ROWS
                FETCH NEXT 1 ROWS ONLY
            )
            DECLARE @subjectsCount INT = 0
            WHILE (@subjectsCount < @subjectsRequired)
            BEGIN
                DECLARE @subjectId BIGINT = (
                    SELECT fhs.subject_id
                    FROM faculties_has_subjects fhs
                    WHERE fhs.faculty_id = @facultyId
                    ORDER BY fhs.faculty_id ASC
                    OFFSET @subjectsCount ROWS
                    FETCH NEXT 1 ROWS ONLY
                )
                INSERT INTO enrollees_has_subjects (enrollee_id, subject_id, score)
                VALUES
                (
                    @enrolleeId,
                    @subjectId,
                    CEILING(100 * RAND())
                )
                SET @subjectsCount = @subjectsCount + 1
            END
        COMMIT TRANSACTION
        PRINT N'Success' + (CHAR(13) + CHAR(10)) -- line-break CR + LF
    END
    SET @counter = @counter + 1
END
GO
-- #module: init-enrollees_has_subjects <<< END

-- #module: init-faculties_has_enrollees >>> START
-- #dependencies: [enrollees, faculties, enrollees_has_subjects, faculties_has_enrollees, faculties_has_subjects, init-enrollees, init-faculties, init-enrollees_has_subjects, init-faculties_has_subjects]

TRUNCATE TABLE faculties_has_enrollees
GO

DECLARE @totalEnrollees INT = (SELECT COUNT(*) FROM enrollees WITH(NOLOCK))
DECLARE @subjectsMin    INT = 3
DECLARE @counter        INT = 0
WHILE (@counter < @totalEnrollees)
BEGIN
    DECLARE @enrolleeId BIGINT = (
        SELECT e.id
        FROM enrollees e WITH(NOLOCK)
        ORDER BY e.id ASC
        OFFSET @counter ROWS
        FETCH NEXT 1 ROWS ONLY
    )
    IF NOT EXISTS (SELECT * FROM faculties_has_enrollees WITH(NOLOCK) WHERE enrollee_id = @enrolleeId)
    BEGIN
        PRINT N'Looking for appropriate faculty for enrollee with ID = ' + CAST(@enrolleeId AS NVARCHAR(30))
        DECLARE @availableFaculties TABLE(id BIGINT)
        INSERT INTO @availableFaculties (id)
        SELECT f.id
        FROM faculties f WITH(NOLOCK)
        JOIN faculties_has_subjects fs WITH(NOLOCK) ON fs.faculty_id = f.id
        WHERE fs.subject_id IN (
            SELECT es.subject_id
            FROM enrollees_has_subjects es WITH(NOLOCK)
            WHERE es.enrollee_id = @enrolleeId
        )
        GROUP BY f.id
        HAVING COUNT(f.id) >= @subjectsMin
        DECLARE @facultyId BIGINT = (
            SELECT id FROM @availableFaculties
            ORDER BY id
            OFFSET CAST(FLOOR((SELECT COUNT(*) FROM @availableFaculties) * RAND()) AS INT) ROWS
            FETCH NEXT 1 ROWS ONLY
        )
        IF (@facultyId IS NOT NULL)
        BEGIN
            INSERT INTO faculties_has_enrollees (faculty_id, enrollee_id, filing_date)
            VALUES (@facultyId, @enrolleeId, GETDATE())
        END
    END
    SET @counter = @counter + 1
END
GO
-- #module: init-faculties_has_enrollees <<< END

