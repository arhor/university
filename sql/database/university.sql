-- #main

-- #create-database: university >>> START
USE [master]
GO

IF (DB_ID('university') IS NULL)
BEGIN
    CREATE DATABASE [university]
    ON [PRIMARY]
    (
        NAME = university_db,
        FILENAME = 'D:\\database\\university_db.mdf',
        SIZE = 128MB,
        MAXSIZE = UNLIMITED,
        FILEGROWTH = 15%
    )
    LOG ON
    (
        NAME = university_db,
        FILENAME = 'D:\\database\\university_db.ldf',
        SIZE = 128MB,
        MAXSIZE = UNLIMITED,
        FILEGROWTH = 15%
    )
END
-- #create-database: university <<< END

-- #create-user >>> START
USE [university]
GO

IF (DATABASE_PRINCIPAL_ID('UniversitySA') IS NULL)
BEGIN
    CREATE LOGIN UniversitySA
    WITH PASSWORD = 'dragonlance'
    GO

    CREATE USER UniversitySA
    FOR LOGIN UniversitySA
    GO
END
-- #create-user <<< END