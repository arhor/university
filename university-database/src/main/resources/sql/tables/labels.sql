-- #dependencies: [langs]

-- #create-table: labels >>> START
USE [university];

IF (OBJECT_ID('labels') IS NULL)
BEGIN
    CREATE TABLE [labels]
    (
        [lang_id]       [BIGINT]           NOT NULL,
        [label]         [NVARCHAR](64)     NOT NULL,
        [value]         [NVARCHAR](500)    NOT NULL,

        CONSTRAINT [PK_labels] PRIMARY KEY CLUSTERED ([lang_id], [label]),

        CONSTRAINT [FK_faculty_titles_lang_id] FOREIGN KEY ([lang_id])
        REFERENCES [langs] (id)
            ON DELETE CASCADE
            ON UPDATE CASCADE
    )
END
-- #create-table: labels <<< END