package apps.ginyu.geoquiz;

import android.media.Image;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import java.lang.Math;

import static java.lang.Math.floorMod;

public class QuizActivity extends AppCompatActivity {

   private Button mTrueButton;
   private Button mFalseButton;
   private ImageButton mPrevButton;
   private ImageButton mNextButton;
   private TextView mQuestionTextView;

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

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_quiz);

      mQuestionTextView = (TextView) findViewById(R.id.question_textview);
      int question = mQuestions[mCurrentIndex].getTextResId();
      mQuestionTextView.setText(question);

      mTrueButton = (Button) findViewById(R.id.true_button);
      mFalseButton = (Button) findViewById(R.id.false_button);
      mPrevButton = (ImageButton) findViewById(R.id.prev_button);
      mNextButton = (ImageButton) findViewById(R.id.next_button);

      mQuestionTextView.setOnClickListener(new View.OnClickListener() {

         @Override
         public void onClick(View v) {
            mCurrentIndex = (mCurrentIndex + 1) % mQuestions.length;
            UpdateQuestion();
         }
      });

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

      mPrevButton.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            mCurrentIndex = ((mCurrentIndex - 1) % mQuestions.length + mQuestions.length) % mQuestions.length;
            UpdateQuestion();
         }
      });

      mNextButton.setOnClickListener(new View.OnClickListener() {

         @Override
         public void onClick(View v) {
            mCurrentIndex = (mCurrentIndex + 1) % mQuestions.length;
            UpdateQuestion();
         }
      });
   }

   private void UpdateQuestion () {
      int question = mQuestions[mCurrentIndex].getTextResId();
      mQuestionTextView.setText(question);
   }

   private void CheckAnswer (boolean result) {
      int messageResId =  (mQuestions[mCurrentIndex].isAnswerTrue() == result) ? R.string.correct_toast : R.string.false_toast;
      Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();
   }
}
