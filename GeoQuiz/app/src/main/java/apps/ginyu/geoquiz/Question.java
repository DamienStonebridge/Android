package apps.ginyu.geoquiz;

import android.os.Parcel;
import android.os.Parcelable;

public class Question implements Parcelable {
    private int mTextResId;
    private boolean mAnswerTrue;
    private boolean mAnswered;

    public Question(int mTextResId, boolean mAnswerTrue) {
        this.mTextResId = mTextResId;
        this.mAnswerTrue = mAnswerTrue;
        this.mAnswered = false;
    }

    protected Question(Parcel in) {
        mTextResId = in.readInt();
        mAnswerTrue = in.readByte() != 0;
        mAnswered = in.readByte() != 0;
    }

    public static final Creator<Question> CREATOR = new Creator<Question>() {
        @Override
        public Question createFromParcel(Parcel in) {
            return new Question(in);
        }

        @Override
        public Question[] newArray(int size) {
            return new Question[size];
        }
    };

    public int getTextResId() {
        return mTextResId;
    }

    public void setTextResId(int textResId) {
        mTextResId = textResId;
    }

    public boolean isAnswerTrue() {
        return mAnswerTrue;
    }

    public void setAnswerTrue(boolean answerTrue) {
        mAnswerTrue = answerTrue;
    }

    public boolean isAnswered() {
        return mAnswered;
    }

    public void setAnswered(boolean answered) {
        mAnswered = answered;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mTextResId);
        dest.writeByte((byte) (mAnswerTrue ? 1 : 0));
        dest.writeByte((byte) (mAnswered ? 1 : 0));
    }
}
