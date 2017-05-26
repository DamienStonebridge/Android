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

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_quiz);

      Log.d(TAG, "onCreate(Bundle) called");

      if(savedInstanceState != null) mCurrentIndex = savedInstanceState.getInt(KEY_INDEX);

      mQuestionTextView = (TextView) findViewById(R.id.question_textview);
      int question = mQuestions[mCurrentIndex].getTextResId();
      mQuestionTextView.setText(question);

      mTrueButton = (Button) findViewById(R.id.true_button);
      mFalseButton = (Button) findViewById(R.id.false_button);
      mNextButton = (Button) findViewById(R.id.next_button);

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
            UpdateQuestion();
         }
      });
   }

   @Override
   protected void onStart() {
      super.onStart();
      Log.d(TAG, "onStart() called");
   }

   @Override
   protected void onResume() {
      super.onResume();
      Log.d(TAG, "onResume() called");
   }

   @Override
   protected void onPause() {
      super.onPause();
      Log.d(TAG, "onPause() called");
   }

   @Override
   protected void onStop() {
      super.onStop();
      Log.d(TAG, "onStop() called");
   }

   @Override
   protected void onDestroy () {
      super.onDestroy();
      Log.d(TAG, "onDestroy() called");
   }

   @Override
   public void onSaveInstanceState(Bundle savedInstanceState) {
      super.onSaveInstanceState(savedInstanceState);
      Log.i(TAG, "onSaveInstanceState");
      savedInstanceState.putInt(KEY_INDEX, mCurrentIndex);
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
