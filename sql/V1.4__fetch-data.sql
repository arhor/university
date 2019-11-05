USE [university]
GO

-- Fetch all users >>> START
SELECT [user].[id]
     , [user].[email]
     , [user].[password]
     , [user].[first_name]
     , [user].[last_name]
     , [user].[role_id]
     , [user].[lang_id]

FROM [users] [user] WITH(NOLOCK)
-- Fetch all users <<< END

-- Fetch users page >>> START
SELECT [user].[id]
     , [user].[email]
     , [user].[password]
     , [user].[first_name]
     , [user].[last_name]
     , [user].[role_id]
     , [user].[lang_id]

FROM [users] [user] WITH(NOLOCK)

ORDER BY [user].[id] ASC

OFFSET 0 ROWS
FETCH NEXT 10 ROWS ONLY
-- Fetch users page <<< END

-- Fetch users joining roles & langs >>> START
SELECT [user].[id]
     , [user].[email]
     , [user].[password]
     , [user].[first_name]
     , [user].[last_name]
     , [role].[title] AS 'role'
     , [lang].[label] AS 'lang'

FROM [users] [user] WITH(NOLOCK)
JOIN [roles] [role] WITH(NOLOCK) ON [role].[id] = [user].[role_id]
JOIN [langs] [lang] WITH(NOLOCK) ON [lang].[id] = [user].[lang_id]
-- Fetch users joining roles & langs <<< END
