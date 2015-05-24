package com.mr.poppa.kingquiz;

import android.test.AndroidTestCase;

/**
 * Created by Benjamin on 4/15/2015.
 */
public class KingActivityTest extends AndroidTestCase {


    int kingId = 0;
    int questionId = 0;
    KingActivity kingActivity;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        kingActivity = new KingActivity();
    }

    public void testCheckAnswer() {
        boolean answer = kingActivity.checkAnswer(kingId, questionId);
        assertTrue("Boolean was false expected true", answer);
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }
}
