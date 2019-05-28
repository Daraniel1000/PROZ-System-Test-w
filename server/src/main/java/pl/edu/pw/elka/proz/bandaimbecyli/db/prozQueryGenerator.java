package pl.edu.pw.elka.proz.bandaimbecyli.db;

class prozQueryGenerator {
    static String CheckUserQuery()
    {
        return "select user_id, login, first_name, last_name, admin from users where login = ? and password = ?";
    }

    static String TestsForUserQuery(int uID)
    {
        return "select test_id, title, start_date, finish_date, type, isTestComplete(" + uID + ",test_id) as isComplete from test \n" +
                "where test_id in \n" +
                "    (select test_id from users_tests \n" +
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

    static String InsertQuestionQuery()
    {
        return "insert into Question (TEXT, TYPE) values (?, ?)";
    }

    static String insertAnswerQuery()
    {
        return "insert into Answers (TEXT, QUESTION_ID, CORRECT) values (?, ?, ?)";
    }

    static String InsertTestQuery()
    {
        return "insert into Test (TITLE, START_DATE, FINISH_DATE, TYPE) values (?, ?, ?, ?)";
    }

    static String insertTestQuestionsQuery()
    {
        return "insert into Test_Questions (QUESTION_ID, TEST_ID) values (?, ?)";
    }

    static String InsertResultsAnswersQuery()
    {
        return "insert into RESULTS_ANSWERS values (?, ?)";
    }

    static String ResultsForUserTestQuery(int tID, int uID)
    {
        return "select * from RESULTS where TEST_ID = " + tID + " AND USER_ID = " + uID;
    }

    static String AnswerIDsForResultsQuery(int rID)
    {
        return "select ANSWER_ID from RESULTS_ANSWERS where RESULTS_ID = " + rID;
    }

    static String GetQuestionQuery(int questionId) {
        return "select * from question where question_id = " + questionId;
    }
}
