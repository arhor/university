-- #main

-- #create-database: university >>> START
USE [master]
GO

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
-- #create-database: university <<< END