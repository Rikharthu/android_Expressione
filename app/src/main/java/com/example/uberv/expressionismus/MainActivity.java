package com.example.uberv.expressionismus;

import android.graphics.Color;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    public static final String LOG_TAG=MainActivity.class.getSimpleName();

    private TextView outputTv;
    private EditText originalTextEt;
    private EditText regexEt;
    private ImageButton saveRegexBtn;
    private String regex="";
    private String originalText="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        originalTextEt= (EditText) findViewById(R.id.original_text_tv);
        regexEt= (EditText) findViewById(R.id.regex_tv);
        outputTv= (TextView) findViewById(R.id.output_tv);
        saveRegexBtn= (ImageButton) findViewById(R.id.save_regex_btn);

        originalTextEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // not implemented
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // not implemented
            }

            @Override
            public void afterTextChanged(Editable s) {
                originalText=s.toString().trim();
                updateDisplay();
            }
        });
        regexEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // not implemented
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // not implemented
            }

            @Override
            public void afterTextChanged(Editable s) {
                regex=s.toString().trim();
                updateDisplay();
            }
        });
        saveRegexBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!regex.isEmpty()){
                    SaveRegexDialogFragment dialog = SaveRegexDialogFragment.newInstance(regex);
                    dialog.show(getSupportFragmentManager(),"SaveRegexDialogFragment");
                }else{
                    regexEt.setError("Cannot save empty regex!");
                    regexEt.requestFocus();
                }
            }
        });
    }

    // TODO refactor
    private void updateDisplay() {
        try {
            String htmlString=originalText;
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(originalText);
            // Check all occurrencies
            int c=0;
            String group;
            final SpannableStringBuilder sb = new SpannableStringBuilder(originalText);
            int colorDark=Color.rgb(0,132,195);
            int colorLight=Color.rgb(0,192,255);
            int color;
            int prevEndIndex=0;
            boolean nextDark=true;
            while (matcher.find()) {
                c++;
                group=matcher.group();
                Log.d(LOG_TAG, matcher.start()+" - "+matcher.end()+"\t"+group);
                if(prevEndIndex!=0 && (prevEndIndex)==matcher.start()){
                    nextDark=!nextDark;
                }
                // Span to set text color to some RGB value
                final ForegroundColorSpan fcs = new ForegroundColorSpan(nextDark?colorDark:colorLight);
                // Span to make text bold
                final StyleSpan bss = new StyleSpan(android.graphics.Typeface.BOLD);
                // Set the text color for first 4 characters
                sb.setSpan(fcs, matcher.start(), matcher.end(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                sb.setSpan(new UnderlineSpan(), matcher.start(), matcher.end(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                // make them also bold
//                sb.setSpan(bss, 0, 4, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
                prevEndIndex=matcher.end();
            }
//            outputTv.setText(Html.fromHtml(htmlString));
            outputTv.setText(sb);
        }catch(Exception e){
            // not implemented. just to avoid crashing due to incorrect regex syntax
        }
    }

    // put the marble in the bag
    public static String insert(String bag, String marble, int index) {
        String bagBegin = bag.substring(0,index);
        String bagEnd = bag.substring(index);
        return bagBegin + marble + bagEnd;
    }

}
