package database_classes;

public class prozQueryGenerator {
    public static String TestsForUserQuery(int uID)
    {
        return "select * from test\n" +
                "where test_id in\n" +
                "    (select test_id from users_tests\n" +
                "    where users_tests.user_id = " + uID + ");";
    }

    public static String GetTestQuery(int tID)
    {
        return "select * from test\n" +
                "where test_id = " + tID + ";";
    }

    public static String AnswersForQuestionQuery(int qID) {
        return "select * from answers\n" +
                "where question_id = " + qID + ";";
    }

    public static String QuestionsForTestQuery(int tID)
    {
        return "select * from question\n" +
                "where question_id in\n" +
                "    (select question_id from test_questions\n" +
                "    where test_id = " + tID +");";
    }

}
