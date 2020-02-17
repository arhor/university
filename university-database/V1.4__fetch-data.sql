USE university
GO

-- Fetch all users >>> START
SELECT u.id
     , u.email
     , u.password
     , u.first_name
     , u.last_name
     , u.role_id
     , u.lang_id
FROM users u WITH(NOLOCK)
-- Fetch all users <<< END

-- Fetch users page >>> START
SELECT u.id
     , u.email
     , u.password
     , u.first_name
     , u.last_name
     , u.role_id
     , u.lang_id
FROM users u WITH(NOLOCK)
ORDER BY u.id ASC
OFFSET 0 ROWS
FETCH NEXT 10 ROWS ONLY
-- Fetch users page <<< END

-- Fetch users joining roles & langs >>> START
SELECT u.id
     , u.email
     , u.password
     , u.first_name
     , u.last_name
     , r.title AS 'role'
     , l.label AS 'lang'
FROM users u WITH(NOLOCK)
JOIN roles r WITH(NOLOCK) ON r.id = u.role_id
JOIN langs l WITH(NOLOCK) ON l.id = u.lang_id
-- Fetch users joining roles & langs <<< END
