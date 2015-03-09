package hci.divinesymphony.net.flashtrainer;

import hci.divinesymphony.net.flashtrainer.backend.SampleProblemSet;
import hci.divinesymphony.net.flashtrainer.beans.ProblemSet;
import hci.divinesymphony.net.flashtrainer.beans.DisplayItem;
import hci.divinesymphony.net.flashtrainer.util.SystemUiHider;

import android.annotation.TargetApi;
import android.app.Activity;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.view.View.OnClickListener;

import java.util.List;
import java.io.IOException;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 *
 * @see SystemUiHider
 */
public class Quiz extends Activity {
    /**
     * Whether or not the system UI should be auto-hidden after
     * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
     */
    private static final boolean AUTO_HIDE = true;

    /**
     * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
     * user interaction before hiding the system UI.
     */
    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

    /**
     * If set, will toggle the system UI visibility upon interaction. Otherwise,
     * will show the system UI visibility upon interaction.
     */
    private static final boolean TOGGLE_ON_CLICK = true;

    /**
     * The flags to pass to {@link SystemUiHider#getInstance}.
     */
    private static final int HIDER_FLAGS = SystemUiHider.FLAG_HIDE_NAVIGATION;

    /**
     * The instance of the {@link SystemUiHider} for this activity.
     */
    private SystemUiHider mSystemUiHider;

    private ProblemSet probSet;
    private Button btn_problem;
    private Button btn_0;
    private Button btn_1;
    private Button btn_2;
    private Button btn_3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_quiz);

        //locate the button controls and save for later
        this.btn_problem = ((Button)findViewById(R.id.btn_problem));
        this.btn_0 = ((Button)findViewById(R.id.btn_0));
        this.btn_1 = ((Button)findViewById(R.id.btn_1));
        this.btn_2 = ((Button)findViewById(R.id.btn_2));
        this.btn_3 = ((Button)findViewById(R.id.btn_3));

        //create click listeners
        this.btn_problem.setOnClickListener(oclProblem);
        this.btn_0.setOnClickListener(oclResponse_0);
        this.btn_1.setOnClickListener(oclResponse_1);
        this.btn_2.setOnClickListener(oclResponse_2);
        this.btn_3.setOnClickListener(oclResponse_3);

        final View controlsView = findViewById(R.id.fullscreen_content_controls);
        final View contentView = findViewById(R.id.fullscreen_content);

        // Set up an instance of SystemUiHider to control the system UI for
        // this activity.
        mSystemUiHider = SystemUiHider.getInstance(this, contentView, HIDER_FLAGS);
        mSystemUiHider.setup();
        mSystemUiHider
                .setOnVisibilityChangeListener(new SystemUiHider.OnVisibilityChangeListener() {
                    // Cached values.
                    int mControlsHeight;
                    int mShortAnimTime;

                    @Override
                    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
                    public void onVisibilityChange(boolean visible) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
                            // If the ViewPropertyAnimator API is available
                            // (Honeycomb MR2 and later), use it to animate the
                            // in-layout UI controls at the bottom of the
                            // screen.
                            if (mControlsHeight == 0) {
                                mControlsHeight = controlsView.getHeight();
                            }
                            if (mShortAnimTime == 0) {
                                mShortAnimTime = getResources().getInteger(
                                        android.R.integer.config_shortAnimTime);
                            }
                            controlsView.animate()
                                    .translationY(visible ? 0 : mControlsHeight)
                                    .setDuration(mShortAnimTime);
                        } else {
                            // If the ViewPropertyAnimator APIs aren't
                            // available, simply show or hide the in-layout UI
                            // controls.
                            controlsView.setVisibility(visible ? View.VISIBLE : View.GONE);
                        }

                        if (visible && AUTO_HIDE) {
                            // Schedule a hide().
                            delayedHide(AUTO_HIDE_DELAY_MILLIS);
                        }
                    }
                });

        // Set up the user interaction to manually show or hide the system UI.
        contentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TOGGLE_ON_CLICK) {
                    mSystemUiHider.toggle();
                } else {
                    mSystemUiHider.show();
                }
            }
        });

        // Upon interacting with UI controls, delay any scheduled hide()
        // operations to prevent the jarring behavior of controls going away
        // while interacting with the UI.
        findViewById(R.id.dummy_button).setOnTouchListener(mDelayHideTouchListener);


        //Initialize with a real problem set
        this.populate_problem();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
        delayedHide(100);
    }


    /**
     * Touch listener to use for in-layout UI controls to delay hiding the
     * system UI. This is to prevent the jarring behavior of controls going away
     * while interacting with activity UI.
     */
    View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (AUTO_HIDE) {
                delayedHide(AUTO_HIDE_DELAY_MILLIS);
            }
            return false;
        }
    };

    Handler mHideHandler = new Handler();
    Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            mSystemUiHider.hide();
        }
    };

    /**
     * Schedules a call to hide() in [delay] milliseconds, canceling any
     * previously scheduled calls.
     */
    private void delayedHide(int delayMillis) {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, delayMillis);
    }

    private void populate_problem() {
        //TODO replace this line with a call providing a randomly selected problem set
        this.probSet = SampleProblemSet.getSample();

        DisplayItem problem = probSet.getProblem();
        List<DisplayItem> responses = probSet.getResponses();

        //fill in problem info from the provided problem set
        //TODO add image support
        this.btn_problem.setText(problem.getText());

        //fill in responses
        this.btn_0.setText(responses.get(0).getText());
        this.btn_1.setText(responses.get(1).getText());
        this.btn_2.setText(responses.get(2).getText());
        this.btn_3.setText(responses.get(3).getText());

    }

    /**
     * OnClick handler for problem
     */
    OnClickListener oclProblem = new OnClickListener() {
        @Override
        public void onClick(View v) {
            //TODO this could be used to play a sound or animation on a click
        }
    };

    /**
     * OnClick handler for response selections
     */
    OnClickListener oclResponse_0 = new OnClickListener() {
        @Override
        public void onClick(View v) {
            process_response(0);
        }
    };
    OnClickListener oclResponse_1 = new OnClickListener() {
        @Override
        public void onClick(View v) {
            process_response(1);
        }
    };
    OnClickListener oclResponse_2 = new OnClickListener() {
        @Override
        public void onClick(View v) {
            process_response(2);
        }
    };
    OnClickListener oclResponse_3 = new OnClickListener() {
        @Override
        public void onClick(View v) {
            process_response(3);
        }
    };

    void process_response(int N) {
        //TODO process the click, compare result, and either delay for lockout, or present reward
        String selectedId = this.probSet.getResponses().get(N).getId();

        boolean correct = hci.divinesymphony.net.flashtrainer.backend.AnswerChecker.isCorrect(this.probSet, selectedId);

        if (correct) {
            this.reward();
        } else {
            this.punish();
        }

//TODO this old code just shows the user selection for debugging, can be deleted
//        String test_string = Integer.toString(N);
//        this.btn_problem.setText(test_string);

    }

    void reward() {
        //TODO issue a reward experience
        String url = "http://download.wavetlan.com/SVV/Media/HTTP/H264/Talkinghead_Media/H264_test1_Talkinghead_mp4_480x360.mp4"; // your URL here
        MediaPlayer mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            mediaPlayer.setDataSource(url);
            mediaPlayer.prepare(); // might take long! (for buffering, etc)
        } catch (IOException e) {
            //intentionally do nothing, as we can't recover from a missing video other than immediately returning to the quiz
        }

        mediaPlayer.start();
        mediaPlayer.release();
        mediaPlayer = null;
    }

    void punish() {
        //TODO punish the user with a delay and or lockout - this code may not work, needs testing
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            //intentionally do nothing here.  If there is an interruption, the
            //punishment is just less severe and no real harm done
        }
    }

}
