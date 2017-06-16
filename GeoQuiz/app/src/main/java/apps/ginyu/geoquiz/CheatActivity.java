package apps.ginyu.geoquiz;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import javax.xml.transform.TransformerFactoryConfigurationError;

public class CheatActivity extends AppCompatActivity {

   private TextView mTextView;
   private Button mButton;
   private Boolean mAnswer = false;
   private Boolean alreadyDisplayed = false;

   private static final String ANSWERED = "ANSWERED";

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_cheat);

      mTextView = (TextView) findViewById(R.id.answer_textview);
      mButton = (Button) findViewById(R.id.show_button);

      mAnswer = getIntent().getBooleanExtra("Answer", false);

      if(savedInstanceState != null) alreadyDisplayed = savedInstanceState.getBoolean(ANSWERED, false);
      if(alreadyDisplayed) DisplayAnswer();

      mButton.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            if(!alreadyDisplayed) {
               DisplayAnswer();
               alreadyDisplayed = true;
            }
         }
      });
   }

   void DisplayAnswer () {
      if(mTextView == null) mTextView = (TextView) findViewById(R.id.answer_textview);
      if(mAnswer) mTextView.setText(R.string.true_button);
      else  mTextView.setText((R.string.false_button));
      setAnswerShown();
   }

   @Override
   protected void onSaveInstanceState(Bundle outState) {
      outState.putBoolean(ANSWERED, alreadyDisplayed);
      super.onSaveInstanceState(outState);
   }

   public static Intent newIntent(Context packageContext, Boolean bool) {
      Intent intent = new Intent(packageContext, CheatActivity.class);
      intent.putExtra("Answer", bool);
      return intent;
   }

   public static Boolean wasAnswerShown (Intent result) {
      return result.getBooleanExtra("IsCheated", false);
   }

   private void setAnswerShown () {
      Intent intent = new Intent();
      intent.putExtra("IsCheated", true);
      setResult(RESULT_OK, intent);
   }
}
