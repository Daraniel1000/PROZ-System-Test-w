package pl.edu.pw.elka.proz.bandaimbecyli.db;

class prozQueryGenerator {
    static String CheckUserQuery()
    {
        return "select user_id from users where login = ? and password = ?";
    }

    static String TestsForUserQuery(int uID)
    {
        return "select * from test\n" +
                "where test_id in\n" +
                "    (select test_id from users_tests\n" +
                "    where users_tests.user_id = " + uID + ")";
    }

    static String GetTestQuery(int tID)
    {
        return "select * from test\n" +
                "where test_id = " + tID;
    }

    static String AnswersForQuestionQuery(int qID) {
        return "select * from answers\n" +
                "where question_id = " + qID;
    }

    static String QuestionsForTestQuery(int tID)
    {
        return "select * from question\n" +
                "where question_id in\n" +
                "    (select question_id from test_questions\n" +
                "    where test_id = " + tID +")";
    }

    static String InsertResultsQuery()
    {
        return "insert into Results (TEST_ID, USER_ID, SENT_DATE, POINTS) values (?, ?, ?, ?)";
    }

    static String InsertResultsAnswersQuery()
    {
        return "insert into RESULTS_ANSWERS values (?, ?)";
    }

    static String IsTestFinishedQuery(int tID, int uID)
    {
        return "select * from RESULTS where TEST_ID = " + tID + " AND USER_ID = " + uID +";";
    }

}
