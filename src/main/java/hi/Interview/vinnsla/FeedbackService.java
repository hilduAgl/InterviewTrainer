package hi.Interview.vinnsla;

import javafx.concurrent.Task;


/******************************************************************************
 *  Nafn    : Hildur Agla
 *  T-póstur: hao37@hi.is
 *
 *  Lýsing  : Klasi sem gefur feedback á hve svar við viðtalsspurningu er gott.
 *
 *
 *****************************************************************************/


public class FeedbackService {

    public static Task<String> feedbackTask(String spurning, String userAnswer, String job, String company) {
        return new Task<>() {
            @Override
            protected String call() throws Exception {
                return OpenAIAssistant.getAIResponse(spurning, userAnswer, job, company);
            }
        };
    }
}

