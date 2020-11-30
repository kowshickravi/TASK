CREATE OR REPLACE PROCEDURE dbo."UP_insight_SessionEndpointGetByUserId"
(
    IN       pi_UserId       INTEGER,
    IN       pi_SessionId    UUID,
    INOUT    po_ReturnValue  INTEGER, 
    INOUT    rc_ResultSet    REFCURSOR
)
    LANGUAGE 'plpgsql'
AS
$BODY$
-- ========================================================================================================
-- Description : 
--             :
-- Author      : 
-- --------------------------------------------------------------------------------------------------------
-- [Change History]
-- --------------------------------------------------------------------------------------------------------
-- Date (yyyy-mm-dd)  Changed By             Build               Etrack/Jira     Change "Description"
--
-- 2020-06-18         Jayaprakash[k1]        20_02_RobsonDB                      Migrated from SQL Server
--
-- ------------------------------------------------------------------------------------------------------------
-- [COPYIGHT NOTICE]
-- ------------------------------------------------------------------------------------------------------------
-- Copyright © 2020 Broadcom. All rights reserved.
-- The term “Broadcom” refers to Broadcom Inc. and/or its subsidiaries.
-- 
-- This software and all information contained therein is confidential and proprietary and shall not be
-- duplicated, used, disclosed or disseminated in any way except as authorized by the applicable license agreement,
-- without the express written permission of Broadcom. All authorized reproductions must be marked with this language.
-- 
-- EXCEPT AS SET FORTH IN THE APPLICABLE LICENSE AGREEMENT, TO THE EXTENT PERMITTED BY APPLICABLE LAW OR
-- AS AGREED BY BROADCOM IN ITS APPLICABLE LICENSE AGREEMENT, BROADCOM PROVIDES THIS DOCUMENTATION “AS IS”
-- WITHOUT WARRANTY OF ANY KIND, INCLUDING WITHOUT LIMITATION, ANY IMPLIED WARRANTIES OF MERCHANTABILITY,
-- FITNESS FOR A PARTICULAR PURPOSE, OR NONINFRINGEMENT. IN NO EVENT WILL BROADCOM BE LIABLE TO THE END USER OR
-- ANY THIRD PARTY FOR ANY LOSS OR DAMAGE, DIRECT OR INDIRECT, FROM THE USE OF THIS DOCUMENTATION,
-- INCLUDING WITHOUT LIMITATION, LOST PROFITS, LOST INVESTMENT, BUSINESS INTERRUPTION, GOODWILL, OR LOST DATA,
-- EVEN IF BROADCOM IS EXPRESSLY ADVISED IN ADVANCE OF THE POSSIBILITY OF SUCH LOSS OR DAMAGE
-- ======================================================================================================== 
DECLARE 
BEGIN
    SELECT 
        SE."SessionEndpointId",
        SE."SessionId".
        SE."EndpointId",
        SE."Failed",
        SE."DateStarted",
        SE."DateCompleted",
        SE."CallingBackToken",
        SE."MatchingMessagesCount",
        SE."DateCreated",
        SE."DateAmended",
        SE."WhoAmended_nt_username",
        SE."WhoAmended_hostname"
    FROM dbo."SessionEndpoint" AS SE
    INNER JOIN dbo."Session" AS S ON S."SessionId" = SE."SessionId"
    WHERE
        SE."SessionId" = pi_SessionId
        AND S."UserId" = pi_UserId
        AND SE."CallbackToken" IS NOT NULL
        AND SE."DateCompleted" IS NULL;

            po_ReturnValue = 0;
END;
$BODY$;


