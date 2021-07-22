package it.niedermann.android.markdown.markwon.textwatcher;

import android.os.Looper;
import android.view.KeyEvent;
import android.widget.EditText;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.test.core.app.ApplicationProvider;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import it.niedermann.android.markdown.markwon.MarkwonMarkdownEditor;
import it.niedermann.android.markdown.model.EListType;

import static org.robolectric.Shadows.shadowOf;

@RunWith(RobolectricTestRunner.class)
public class AutoContinuationTextWatcherTest extends TestCase {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private EditText editText;

    @Before
    public void reset() {
        this.editText = new MarkwonMarkdownEditor(ApplicationProvider.getApplicationContext());
    }

    @Test
    public void shouldContinueLists() {
        for (EListType listType : EListType.values()) {
            this.editText.setText(listType.listSymbolWithTrailingSpace + "Test");
            pressEnter(6);
            assertText(listType.listSymbolWithTrailingSpace + "Test\n" + listType.listSymbolWithTrailingSpace, 9);
        }
        for (EListType listType : EListType.values()) {
            this.editText.setText(listType.checkboxUncheckedWithTrailingSpace + "Test");
            pressEnter(10);
            assertText(listType.checkboxUncheckedWithTrailingSpace + "Test\n" + listType.checkboxUncheckedWithTrailingSpace, 17);
        }
        for (EListType listType : EListType.values()) {
            this.editText.setText(listType.checkboxChecked + " Test");
            pressEnter(10);
            assertText(listType.checkboxChecked + " Test\n" + listType.checkboxUncheckedWithTrailingSpace, 17);
        }
    }

    @Test
    public void shouldContinueOrderedLists() {
        this.editText.setText("1. Test");
        pressEnter(7);
        assertText("1. Test\n2. ", 11);

        this.editText.setText("11. Test");
        pressEnter(8);
        assertText("11. Test\n12. ", 13);
    }

    private void pressEnter(int atPosition) {
        this.editText.setSelection(atPosition);
        this.editText.dispatchKeyEvent(new KeyEvent(0, 0, KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_ENTER, 0));
    }

    private void assertText(String expected, int cursorPosition) {
        assertEquals(expected, this.editText.getText().toString());
        assertEquals(cursorPosition, this.editText.getSelectionStart());
        assertEquals(cursorPosition, this.editText.getSelectionEnd());
    }
}
