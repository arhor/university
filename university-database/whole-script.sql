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

IF (OBJECT_ID('langs') IS NULL)
BEGIN
CREATE TABLE langs
    (
        id       BIGINT     NOT NULL IDENTITY(1,1),
        label    CHAR(2)    NOT NULL,
        CONSTRAINT PK_langs PRIMARY KEY CLUSTERED (id ASC),
        CONSTRAINT CHK_langs_label
        CHECK (label LIKE '[A-Z][A-Z]')
    )
END
GO

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
END
GO

IF (OBJECT_ID('users') IS NULL)
BEGIN
    CREATE TABLE users
    (
        id            BIGINT           NOT NULL IDENTITY(1,1),
        email         NVARCHAR(255)    NOT NULL,
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
    CREATE UNIQUE NONCLUSTERED INDEX IDX_users_email ON users (email ASC)
END
GO

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
END
GO

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
        modified_date DATETIME      NOT NULL
        CONSTRAINT PK_users_audit_modification PRIMARY KEY CLUSTERED (id ASC)
    )
END
GO

IF (OBJECT_ID('enrollees') IS NULL)
BEGIN
    CREATE TABLE enrollees
    (
        id              BIGINT          NOT NULL IDENTITY(1,1),
        country         NVARCHAR(64)    NOT NULL,
        city            NVARCHAR(64)    NOT NULL,
        school_score    SMALLINT        NOT NULL,
        user_id         BIGINT          NOT NULL UNIQUE,
        CONSTRAINT PK_enrollees PRIMARY KEY CLUSTERED (id ASC),
        CONSTRAINT CHK_enrollees_school_score
        CHECK (school_score >= 0 AND school_score <= 100),
        CONSTRAINT FK_enrollees_user_id FOREIGN KEY (user_id)
        REFERENCES users (id)
            ON DELETE CASCADE
            ON UPDATE CASCADE
    )
    CREATE NONCLUSTERED INDEX IDX_enrollees_country ON enrollees (country ASC)
    CREATE NONCLUSTERED INDEX IDX_enrollees_city ON enrollees (city ASC)
END
GO

IF (OBJECT_ID('users_audit') IS NULL)
BEGIN
    CREATE TABLE users_audit
    (
        id               BIGINT           NOT NULL IDENTITY(1, 1),
        user_id          BIGINT           NOT NULL,
        email            NVARCHAR(255)    NOT NULL,
        password         NVARCHAR(512)    NOT NULL,
        first_name       NVARCHAR(50)     NOT NULL,
        last_name        NVARCHAR(50)     NOT NULL,
        role_id          BIGINT           NOT NULL,
        modified_by      VARCHAR(128)     NOT NULL,
        modified_date    DATETIME         NOT NULL,
        operation        VARCHAR(20)      NOT NULL,
        CONSTRAINT PK_users_audit PRIMARY KEY CLUSTERED (id ASC),
        CONSTRAINT CHK_users_audit_operation
        CHECK (operation in ('CREATED', 'DELETED'))
    )
END
GO

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
    IF EXISTS (SELECT 1 FROM deleted)
    BEGIN
        INSERT INTO users_audit
        (
            user_id,
            email,
            password,
            first_name,
            last_name,
            role_id,
             modified_by,
            modified_date,
            operation
        )
        SELECT d.id
             , d.email
             , d.password
             , d.first_name
             , d.last_name
             , d.role_id
             , @login_name
             , GETDATE()
             , 'DELETED'
        FROM deleted d
    END
    ELSE
    BEGIN
        INSERT INTO users_audit
        (
            user_id,
            email,
            password,
            first_name,
            last_name,
            role_id,
            modified_by,
            modified_date,
            operation
        )
        SELECT i.id
             , i.email
             , i.password
             , i.first_name
             , i.last_name
             , i.role_id
             , @login_name
             , GETDATE()
             , 'CREATED'
          FROM inserted i
    END
END
GO

IF (OBJECT_ID('enrollees_has_subjects') IS NULL)
BEGIN
    CREATE TABLE enrollees_has_subjects
    (
        subject_id     BIGINT      NOT NULL,
        enrollee_id    BIGINT      NOT NULL,
        score          SMALLINT    NOT NULL,
        CONSTRAINT PK_enrollees_has_subjects PRIMARY KEY CLUSTERED (subject_id, enrollee_id),
        CONSTRAINT CHK_enrollees_has_subjects_score
        CHECK (score >= 0 AND score <= 100),
        CONSTRAINT FK_enrollees_has_subjects_subject_id FOREIGN KEY (subject_id)
        REFERENCES subjects (id)
            ON DELETE CASCADE
            ON UPDATE CASCADE,
        CONSTRAINT FK_enrollees_has_subjects_enrollee_id FOREIGN KEY (enrollee_id)
        REFERENCES enrollees (id)
            ON DELETE CASCADE
            ON UPDATE CASCADE
    )
END
GO

IF (OBJECT_ID('faculties_has_enrollees') IS NULL)
BEGIN
    CREATE TABLE faculties_has_enrollees
    (
        faculty_id     BIGINT      NOT NULL,
        enrollee_id    BIGINT      NOT NULL,
        filing_date    DATETIME    NOT NULL,
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
END
GO

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
        INSERT INTO users_audit_modification
        (
            user_id,
            field_name,
            old_value,
            new_value,
            modified_by,
            modified_date
        )
        VALUES
        (
            @id,
            @field,
            @old_val,
            @new_val,
            @login_name,
           GETDATE()
        )
    END
END
GO

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

