package apps.ginyu.geoquiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;

import javax.xml.parsers.SAXParser;

public class QuizActivity extends AppCompatActivity {

   private Button mTrueButton;
   private Button mFalseButton;
   private Button mNextButton;
   private Button mCheatButton;
   private TextView mQuestionTextView;
   private Toast mToast;

   private static final String TAG = "QuizActivity";

   private static final String KEY_INDEX = "index";
   private static final String IS_CHEATER = "cheater";
   private static final String QUESTIONS = "questions";
   private static final String ANSWERED_QUESTIONS = "ans";

   private static final int REQUEST_CODE_CHEAT = 0;

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
   private Boolean mIsCheater = false;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_quiz);

      mTrueButton = (Button) findViewById(R.id.true_button);
      mFalseButton = (Button) findViewById(R.id.false_button);
      mNextButton = (Button) findViewById(R.id.next_button);
      mCheatButton = (Button) findViewById(R.id.cheat_button);

      if (savedInstanceState != null) {
         Parcelable[] parcelables = savedInstanceState.getParcelableArray(QUESTIONS);
         if (parcelables != null) {
            mQuestions = new Question[parcelables.length];
            System.arraycopy(parcelables, 0, mQuestions, 0, parcelables.length);
         }
         mCurrentIndex = savedInstanceState.getInt(KEY_INDEX);
         mIsCheater = savedInstanceState.getBoolean(IS_CHEATER);
         mAnsweredQuestions = savedInstanceState.getInt(ANSWERED_QUESTIONS);
      }

      if (mIsCheater || mQuestions[mCurrentIndex].isAnswered()) DisableButtons();

      mQuestionTextView = (TextView) findViewById(R.id.question_textview);
      int question = mQuestions[mCurrentIndex].getTextResId();
      mQuestionTextView.setText(question);

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
            mIsCheater = false;
            mCurrentIndex = (mCurrentIndex + 1) % mQuestions.length;
            if (mQuestions[mCurrentIndex].isAnswered()) {
               mTrueButton.setEnabled(false);
               mFalseButton.setEnabled(false);
            } else {
               mTrueButton.setEnabled(true);
               mFalseButton.setEnabled(true);
            }
            UpdateQuestion();
            if (mToast != null) mToast.cancel();
         }
      });

      mCheatButton.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            Intent cheatIntent = CheatActivity.newIntent(QuizActivity.this, mQuestions[mCurrentIndex].isAnswerTrue());
            QuizActivity.this.startActivityForResult(cheatIntent, REQUEST_CODE_CHEAT);
         }
      });
   }

   @Override
   protected void onActivityResult(int requestCode, int resultCode, Intent data) {
      if (resultCode != RESULT_OK) return;
      if (requestCode == REQUEST_CODE_CHEAT) {
         if (data == null) return;
         mIsCheater = CheatActivity.wasAnswerShown(data);
         if (mIsCheater) {
            DisableButtons();
            mQuestions[mCurrentIndex].setAnswered(true);
            mAnsweredQuestions++;
         }
      }
   }

   void DisableButtons() {
      mTrueButton.setEnabled(false);
      mFalseButton.setEnabled(false);
   }

   @Override
   public void onSaveInstanceState(Bundle savedInstanceState) {
      savedInstanceState.putInt(KEY_INDEX, mCurrentIndex);
      savedInstanceState.putBoolean(IS_CHEATER, mIsCheater);
      savedInstanceState.putParcelableArray(QUESTIONS, mQuestions);
      savedInstanceState.putInt(ANSWERED_QUESTIONS, mAnsweredQuestions);
      super.onSaveInstanceState(savedInstanceState);
   }

   private void UpdateQuestion() {
      int question = mQuestions[mCurrentIndex].getTextResId();
      mQuestionTextView.setText(question);
   }

   private void CheckAnswer(boolean result) {
      mAnsweredQuestions++;
      int messageResId;
      if (mQuestions[mCurrentIndex].isAnswerTrue() == result) {
         messageResId = R.string.correct_toast;
         mScore += 1;
      } else
         messageResId = R.string.false_toast;
      mQuestions[mCurrentIndex].setAnswered(true);
      mTrueButton.setEnabled(false);
      mFalseButton.setEnabled(false);
      mToast = Toast.makeText(this, messageResId, Toast.LENGTH_SHORT);
      mToast.show();
      if (mQuestions.length == mAnsweredQuestions) {
         CharSequence cq = getString(R.string.result) + " " + (mScore * 100 / mQuestions.length);
         mToast.cancel();
         Toast.makeText(this, cq, Toast.LENGTH_LONG).show();
      }
   }
}
