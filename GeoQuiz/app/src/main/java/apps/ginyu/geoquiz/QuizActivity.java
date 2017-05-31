package apps.ginyu.geoquiz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {

   private Button mTrueButton;
   private Button mFalseButton;
   private Button mNextButton;
   private TextView mQuestionTextView;
   private Toast mToast;

   private static final String TAG = "QuizActivity";

   private static final String KEY_INDEX = "index";

   private Question[] mQuestions = new Question[]{
      new Question(R.string.question1, false),
      new Question(R.string.question2, false),
      new Question(R.string.question3, true),
      new Question(R.string.question4, true),
      new Question(R.string.question5, false),
      new Question(R.string.question6, true),
      new Question(R.string.question7, false),
      new Question(R.string.question8, false),
      new Question(R.string.question9, true),
      new Question(R.string.question10, true),
      new Question(R.string.question11, false),
      new Question(R.string.question12, false),
   };

   private int mCurrentIndex = 0;
   private int mAnsweredQuestions = 0;
   private int mScore = 0;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_quiz);

      if(savedInstanceState != null) mCurrentIndex = savedInstanceState.getInt(KEY_INDEX);

      mQuestionTextView = (TextView) findViewById(R.id.question_textview);
      int question = mQuestions[mCurrentIndex].getTextResId();
      mQuestionTextView.setText(question);

      mTrueButton = (Button) findViewById(R.id.true_button);
      mFalseButton = (Button) findViewById(R.id.false_button);
      mNextButton = (Button) findViewById(R.id.next_button);

      if(mQuestions[mCurrentIndex].isAnswered()) {
         mTrueButton.setEnabled(false);
      }
      else
         mTrueButton.setEnabled(true);

      mTrueButton.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            CheckAnswer(true);
         }
      });

      mFalseButton.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            CheckAnswer(false);
         }
      });

      mNextButton.setOnClickListener(new View.OnClickListener() {

         @Override
         public void onClick(View v) {
            mCurrentIndex = (mCurrentIndex + 1) % mQuestions.length;
            if (mQuestions[mCurrentIndex].isAnswered()) {
               mTrueButton.setEnabled(false);
               mFalseButton.setEnabled(false);
            }
            else {
               mTrueButton.setEnabled(true);
               mFalseButton.setEnabled(true);
            }
            UpdateQuestion();
            if(mToast != null) mToast.cancel();
         }
      });
   }

   @Override
   public void onSaveInstanceState(Bundle savedInstanceState) {
      super.onSaveInstanceState(savedInstanceState);
      savedInstanceState.putInt(KEY_INDEX, mCurrentIndex);
   }

   private void UpdateQuestion () {
      int question = mQuestions[mCurrentIndex].getTextResId();
      mQuestionTextView.setText(question);
   }

   private void CheckAnswer (boolean result) {
      mAnsweredQuestions++;
      int messageResId;
      if(mQuestions[mCurrentIndex].isAnswerTrue() == result) {
         messageResId = R.string.correct_toast;
         mScore += 1;
      }
      else
         messageResId = R.string.false_toast;
      mQuestions[mCurrentIndex].setAnswered(true);
      mTrueButton.setEnabled(false);
      mFalseButton.setEnabled(false);
      mToast = Toast.makeText(this, messageResId, Toast.LENGTH_SHORT);
      mToast.show();
      CharSequence cq = getString(R.string.result) + " " + (mScore * 100 / mQuestions.length);
      if(mQuestions.length == mAnsweredQuestions) {
         mToast.cancel();
         Toast.makeText(this, cq, Toast.LENGTH_LONG).show();
      }
   }
}
