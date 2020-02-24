<?xml version="1.0" encoding="UTF-8"?>
<module xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xsi:schemaLocation="http://www.arhor.by/sql-module ./../sql-module.xsd"
        xmlns="http://www.arhor.by/sql-module"
        name="init-faculties_has_enrollees">

    <dependencies>
        <dependency name="faculties"/>
        <dependency name="enrollees"/>
        <dependency name="faculties_has_enrollees"/>
    </dependencies>

    <queries context="university">
        <insert>
            <![CDATA[
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
                );

            	WITH vEnrolleeSubjects (id)
            	AS
            	(
            		SELECT es.subject_id
            		FROM enrollees_has_subjects es WITH(NOLOCK)
            		WHERE es.enrollee_id = @enrolleeId
            	)



            	SET @counter = @counter + 1
            END
            ]]>
        </insert>
    </queries>

</module>