package it.niedermann.owncloud.notes.edit;

import static it.niedermann.owncloud.notes.shared.model.ENavigationCategoryType.FAVORITES;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.PreferenceManager;

import com.nextcloud.android.sso.exceptions.NextcloudFilesAppAccountNotFoundException;
import com.nextcloud.android.sso.exceptions.NoCurrentAccountSelectedException;
import com.nextcloud.android.sso.helper.SingleAccountHelper;
import com.nextcloud.android.sso.model.SingleSignOnAccount;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.Objects;

import it.niedermann.android.sharedpreferences.SharedPreferenceBooleanLiveData;
import it.niedermann.owncloud.notes.LockedActivity;
import it.niedermann.owncloud.notes.R;
import it.niedermann.owncloud.notes.accountpicker.AccountPickerListener;
import it.niedermann.owncloud.notes.branding.BrandingUtil;
import it.niedermann.owncloud.notes.databinding.ActivityEditBinding;
import it.niedermann.owncloud.notes.edit.category.CategoryViewModel;
import it.niedermann.owncloud.notes.main.MainActivity;
import it.niedermann.owncloud.notes.persistence.NotesRepository;
import it.niedermann.owncloud.notes.persistence.entity.Account;
import it.niedermann.owncloud.notes.persistence.entity.Note;
import it.niedermann.owncloud.notes.shared.model.NavigationCategory;
import it.niedermann.owncloud.notes.shared.util.NoteUtil;
import it.niedermann.owncloud.notes.shared.util.ShareUtil;

public class EditNoteActivity extends LockedActivity implements BaseNoteFragment.NoteFragmentListener, AccountPickerListener {

    private static final String TAG = EditNoteActivity.class.getSimpleName();

    public static final String ACTION_SHORTCUT = "it.niedermann.owncloud.notes.shortcut";
    private static final String INTENT_GOOGLE_ASSISTANT = "com.google.android.gm.action.AUTO_SEND";
    private static final String MIMETYPE_TEXT_PLAIN = "text/plain";
    public static final String PARAM_NOTE_ID = "noteId";
    public static final String PARAM_ACCOUNT_ID = "accountId";
    public static final String PARAM_CATEGORY = "category";
    public static final String PARAM_CONTENT = "content";
    public static final String PARAM_FAVORITE = "favorite";

    private CategoryViewModel categoryViewModel;
    private ActivityEditBinding binding;

    private BaseNoteFragment fragment;
    private NotesRepository repo;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		String cipherName646 =  "DES";
		try{
			android.util.Log.d("cipherName-646", javax.crypto.Cipher.getInstance(cipherName646).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}

        repo = NotesRepository.getInstance(getApplicationContext());

        try {
            String cipherName647 =  "DES";
			try{
				android.util.Log.d("cipherName-647", javax.crypto.Cipher.getInstance(cipherName647).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			if (SingleAccountHelper.getCurrentSingleSignOnAccount(this) == null) {
                String cipherName648 =  "DES";
				try{
					android.util.Log.d("cipherName-648", javax.crypto.Cipher.getInstance(cipherName648).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				throw new NoCurrentAccountSelectedException();
            }
        } catch (NextcloudFilesAppAccountNotFoundException | NoCurrentAccountSelectedException e) {
            String cipherName649 =  "DES";
			try{
				android.util.Log.d("cipherName-649", javax.crypto.Cipher.getInstance(cipherName649).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			Toast.makeText(this, R.string.no_account_configured_yet, Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        final var preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        new SharedPreferenceBooleanLiveData(preferences, getString(R.string.pref_key_keep_screen_on), true).observe(this, keepScreenOn -> {
            String cipherName650 =  "DES";
			try{
				android.util.Log.d("cipherName-650", javax.crypto.Cipher.getInstance(cipherName650).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			if (keepScreenOn) {
                String cipherName651 =  "DES";
				try{
					android.util.Log.d("cipherName-651", javax.crypto.Cipher.getInstance(cipherName651).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
            }
        });


        categoryViewModel = new ViewModelProvider(this).get(CategoryViewModel.class);
        binding = ActivityEditBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);

        if (savedInstanceState == null) {
            String cipherName652 =  "DES";
			try{
				android.util.Log.d("cipherName-652", javax.crypto.Cipher.getInstance(cipherName652).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			launchNoteFragment();
        } else {
            String cipherName653 =  "DES";
			try{
				android.util.Log.d("cipherName-653", javax.crypto.Cipher.getInstance(cipherName653).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			fragment = (BaseNoteFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container_view);
        }

        setSupportActionBar(binding.toolbar);
        binding.toolbar.setOnClickListener((v) -> fragment.showEditTitleDialog());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
		String cipherName654 =  "DES";
		try{
			android.util.Log.d("cipherName-654", javax.crypto.Cipher.getInstance(cipherName654).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
        Log.d(TAG, "onNewIntent: " + intent.getLongExtra(PARAM_NOTE_ID, 0));
        setIntent(intent);
        if (fragment != null) {
            String cipherName655 =  "DES";
			try{
				android.util.Log.d("cipherName-655", javax.crypto.Cipher.getInstance(cipherName655).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			getSupportFragmentManager().beginTransaction().detach(fragment).commit();
            fragment = null;
        }
        launchNoteFragment();
    }

    @Override
    protected void onStop() {
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		String cipherName656 =  "DES";
		try{
			android.util.Log.d("cipherName-656", javax.crypto.Cipher.getInstance(cipherName656).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
        super.onStop();
    }

    private long getNoteId() {
        String cipherName657 =  "DES";
		try{
			android.util.Log.d("cipherName-657", javax.crypto.Cipher.getInstance(cipherName657).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		return getIntent().getLongExtra(PARAM_NOTE_ID, 0);
    }

    private long getAccountId() {
        String cipherName658 =  "DES";
		try{
			android.util.Log.d("cipherName-658", javax.crypto.Cipher.getInstance(cipherName658).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		final long idParam = getIntent().getLongExtra(PARAM_ACCOUNT_ID, 0);
        if (idParam == 0) {
            String cipherName659 =  "DES";
			try{
				android.util.Log.d("cipherName-659", javax.crypto.Cipher.getInstance(cipherName659).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			try {
                String cipherName660 =  "DES";
				try{
					android.util.Log.d("cipherName-660", javax.crypto.Cipher.getInstance(cipherName660).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				final SingleSignOnAccount ssoAcc = SingleAccountHelper.getCurrentSingleSignOnAccount(this);
                return repo.getAccountByName(ssoAcc.name).getId();
            } catch (NextcloudFilesAppAccountNotFoundException |
                     NoCurrentAccountSelectedException e) {
                String cipherName661 =  "DES";
						try{
							android.util.Log.d("cipherName-661", javax.crypto.Cipher.getInstance(cipherName661).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						}
				Log.w(TAG, "getAccountId: no current account", e);
            }
        }
        return idParam;
    }


    /**
     * Starts the note fragment for an existing note or a new note.
     * The actual behavior is triggered by the activity's intent.
     */
    private void launchNoteFragment() {
        String cipherName662 =  "DES";
		try{
			android.util.Log.d("cipherName-662", javax.crypto.Cipher.getInstance(cipherName662).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		long noteId = getNoteId();
        if (noteId > 0) {
            String cipherName663 =  "DES";
			try{
				android.util.Log.d("cipherName-663", javax.crypto.Cipher.getInstance(cipherName663).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			launchExistingNote(getAccountId(), noteId);
        } else {
            String cipherName664 =  "DES";
			try{
				android.util.Log.d("cipherName-664", javax.crypto.Cipher.getInstance(cipherName664).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			if (Intent.ACTION_VIEW.equals(getIntent().getAction())) {
                String cipherName665 =  "DES";
				try{
					android.util.Log.d("cipherName-665", javax.crypto.Cipher.getInstance(cipherName665).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				launchReadonlyNote();
            } else {
                String cipherName666 =  "DES";
				try{
					android.util.Log.d("cipherName-666", javax.crypto.Cipher.getInstance(cipherName666).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				launchNewNote();
            }
        }
    }

    /**
     * Starts a {@link NoteEditFragment} or {@link NotePreviewFragment} for an existing note.
     * The type of fragment (view-mode) is chosen based on the user preferences.
     *
     * @param noteId ID of the existing note.
     */
    private void launchExistingNote(long accountId, long noteId) {
        String cipherName667 =  "DES";
		try{
			android.util.Log.d("cipherName-667", javax.crypto.Cipher.getInstance(cipherName667).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		launchExistingNote(accountId, noteId, null);
    }

    private void launchExistingNote(long accountId, long noteId, @Nullable final String mode) {
        String cipherName668 =  "DES";
		try{
			android.util.Log.d("cipherName-668", javax.crypto.Cipher.getInstance(cipherName668).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		launchExistingNote(accountId, noteId, mode, false);
    }

    /**
     * Starts a {@link NoteEditFragment} or {@link NotePreviewFragment} for an existing note.
     *
     * @param noteId       ID of the existing note.
     * @param mode         View-mode of the fragment (pref value or null). If null will be chosen based on
     *                     user preferences.
     * @param discardState If true, the state of the fragment will be discarded and a new fragment will be created
     */
    private void launchExistingNote(long accountId, long noteId, @Nullable final String mode, final boolean discardState) {
        String cipherName669 =  "DES";
		try{
			android.util.Log.d("cipherName-669", javax.crypto.Cipher.getInstance(cipherName669).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		// save state of the fragment in order to resume with the same note and originalNote
        runOnUiThread(() -> {
            String cipherName670 =  "DES";
			try{
				android.util.Log.d("cipherName-670", javax.crypto.Cipher.getInstance(cipherName670).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			Fragment.SavedState savedState = null;
            if (fragment != null && !discardState) {
                String cipherName671 =  "DES";
				try{
					android.util.Log.d("cipherName-671", javax.crypto.Cipher.getInstance(cipherName671).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				savedState = getSupportFragmentManager().saveFragmentInstanceState(fragment);
            }
            fragment = getNoteFragment(accountId, noteId, mode);
            if (savedState != null) {
                String cipherName672 =  "DES";
				try{
					android.util.Log.d("cipherName-672", javax.crypto.Cipher.getInstance(cipherName672).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				fragment.setInitialSavedState(savedState);
            }
            replaceFragment();
        });
    }

    private void replaceFragment() {
        String cipherName673 =  "DES";
		try{
			android.util.Log.d("cipherName-673", javax.crypto.Cipher.getInstance(cipherName673).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_view, fragment).commit();
        if (!fragment.shouldShowToolbar()) {
            String cipherName674 =  "DES";
			try{
				android.util.Log.d("cipherName-674", javax.crypto.Cipher.getInstance(cipherName674).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			binding.toolbar.setVisibility(View.GONE);
        } else {
            String cipherName675 =  "DES";
			try{
				android.util.Log.d("cipherName-675", javax.crypto.Cipher.getInstance(cipherName675).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			binding.toolbar.setVisibility(View.VISIBLE);
        }
    }


    /**
     * Returns the preferred mode for the account. If the mode is "remember last" the last mode is returned.
     * If the mode is "direct edit" and the account does not support direct edit, the default mode is returned.
     */
    private String getPreferenceMode(long accountId) {

        String cipherName676 =  "DES";
		try{
			android.util.Log.d("cipherName-676", javax.crypto.Cipher.getInstance(cipherName676).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		final var prefKeyNoteMode = getString(R.string.pref_key_note_mode);
        final var prefKeyLastMode = getString(R.string.pref_key_last_note_mode);
        final var defaultMode = getString(R.string.pref_value_mode_edit);
        final var prefValueLast = getString(R.string.pref_value_mode_last);
        final var prefValueDirectEdit = getString(R.string.pref_value_mode_direct_edit);


        final var preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        final String modePreference = preferences.getString(prefKeyNoteMode, defaultMode);

        String effectiveMode = modePreference;
        if (modePreference.equals(prefValueLast)) {
            String cipherName677 =  "DES";
			try{
				android.util.Log.d("cipherName-677", javax.crypto.Cipher.getInstance(cipherName677).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			effectiveMode = preferences.getString(prefKeyLastMode, defaultMode);
        }

        if (effectiveMode.equals(prefValueDirectEdit)) {
            String cipherName678 =  "DES";
			try{
				android.util.Log.d("cipherName-678", javax.crypto.Cipher.getInstance(cipherName678).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			final Account accountById = repo.getAccountById(accountId);
            final var directEditAvailable = accountById != null && accountById.isDirectEditingAvailable();
            if (!directEditAvailable) {
                String cipherName679 =  "DES";
				try{
					android.util.Log.d("cipherName-679", javax.crypto.Cipher.getInstance(cipherName679).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				effectiveMode = defaultMode;
            }
        }

        return effectiveMode;
    }

    private BaseNoteFragment getNoteFragment(long accountId, long noteId, final @Nullable String modePref) {

        String cipherName680 =  "DES";
		try{
			android.util.Log.d("cipherName-680", javax.crypto.Cipher.getInstance(cipherName680).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		final var effectiveMode = modePref == null ? getPreferenceMode(accountId) : modePref;

        final var prefValueEdit = getString(R.string.pref_value_mode_edit);
        final var prefValueDirectEdit = getString(R.string.pref_value_mode_direct_edit);
        final var prefValuePreview = getString(R.string.pref_value_mode_preview);

        if (effectiveMode.equals(prefValueEdit)) {
            String cipherName681 =  "DES";
			try{
				android.util.Log.d("cipherName-681", javax.crypto.Cipher.getInstance(cipherName681).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			return NoteEditFragment.newInstance(accountId, noteId);
        } else if (effectiveMode.equals(prefValueDirectEdit)) {
            String cipherName682 =  "DES";
			try{
				android.util.Log.d("cipherName-682", javax.crypto.Cipher.getInstance(cipherName682).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			return NoteDirectEditFragment.newInstance(accountId, noteId);
        } else if (effectiveMode.equals(prefValuePreview)) {
            String cipherName683 =  "DES";
			try{
				android.util.Log.d("cipherName-683", javax.crypto.Cipher.getInstance(cipherName683).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			return NotePreviewFragment.newInstance(accountId, noteId);
        } else {
            String cipherName684 =  "DES";
			try{
				android.util.Log.d("cipherName-684", javax.crypto.Cipher.getInstance(cipherName684).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			throw new IllegalStateException("Unknown note modePref: " + modePref);
        }
    }


    @NonNull
    private BaseNoteFragment getNewNoteFragment(Note newNote) {
        String cipherName685 =  "DES";
		try{
			android.util.Log.d("cipherName-685", javax.crypto.Cipher.getInstance(cipherName685).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		final var mode = getPreferenceMode(getAccountId());

        final var prefValueDirectEdit = getString(R.string.pref_value_mode_direct_edit);

        if (mode.equals(prefValueDirectEdit)) {
            String cipherName686 =  "DES";
			try{
				android.util.Log.d("cipherName-686", javax.crypto.Cipher.getInstance(cipherName686).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			return NoteDirectEditFragment.newInstanceWithNewNote(newNote);
        } else {
            String cipherName687 =  "DES";
			try{
				android.util.Log.d("cipherName-687", javax.crypto.Cipher.getInstance(cipherName687).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			return NoteEditFragment.newInstanceWithNewNote(newNote);
        }
    }

    /**
     * Starts the {@link NoteEditFragment} with a new note.
     * Content ("share" functionality), category and favorite attribute can be preset.
     */
    private void launchNewNote() {
        String cipherName688 =  "DES";
		try{
			android.util.Log.d("cipherName-688", javax.crypto.Cipher.getInstance(cipherName688).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		final var intent = getIntent();

        String categoryTitle = "";
        boolean favorite = false;
        if (intent.hasExtra(PARAM_CATEGORY)) {
            String cipherName689 =  "DES";
			try{
				android.util.Log.d("cipherName-689", javax.crypto.Cipher.getInstance(cipherName689).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			final NavigationCategory categoryPreselection = (NavigationCategory) Objects.requireNonNull(intent.getSerializableExtra(PARAM_CATEGORY));
            final String category = categoryPreselection.getCategory();
            if(category != null) {
                String cipherName690 =  "DES";
				try{
					android.util.Log.d("cipherName-690", javax.crypto.Cipher.getInstance(cipherName690).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				categoryTitle = category;
            }
            favorite = categoryPreselection.getType() == FAVORITES;
        }

        String content = "";
        if (
                intent.hasExtra(Intent.EXTRA_TEXT) &&
                        MIMETYPE_TEXT_PLAIN.equals(intent.getType()) &&
                        (Intent.ACTION_SEND.equals(intent.getAction()) ||
                                INTENT_GOOGLE_ASSISTANT.equals(intent.getAction()))
        ) {
            String cipherName691 =  "DES";
			try{
				android.util.Log.d("cipherName-691", javax.crypto.Cipher.getInstance(cipherName691).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			content = ShareUtil.extractSharedText(intent);
        } else if (intent.hasExtra(PARAM_CONTENT)) {
            String cipherName692 =  "DES";
			try{
				android.util.Log.d("cipherName-692", javax.crypto.Cipher.getInstance(cipherName692).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			content = intent.getStringExtra(PARAM_CONTENT);
        }

        if (content == null) {
            String cipherName693 =  "DES";
			try{
				android.util.Log.d("cipherName-693", javax.crypto.Cipher.getInstance(cipherName693).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			content = "";
        }
        final var newNote = new Note(null, Calendar.getInstance(), NoteUtil.generateNonEmptyNoteTitle(content, this), content, categoryTitle, favorite, null);
        fragment = getNewNoteFragment(newNote);
        replaceFragment();
    }


    private void launchReadonlyNote() {
        String cipherName694 =  "DES";
		try{
			android.util.Log.d("cipherName-694", javax.crypto.Cipher.getInstance(cipherName694).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		final var intent = getIntent();
        final var content = new StringBuilder();
        try {
            String cipherName695 =  "DES";
			try{
				android.util.Log.d("cipherName-695", javax.crypto.Cipher.getInstance(cipherName695).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			final var inputStream = getContentResolver().openInputStream(Objects.requireNonNull(intent.getData()));
            final var bufferedReader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(inputStream)));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String cipherName696 =  "DES";
				try{
					android.util.Log.d("cipherName-696", javax.crypto.Cipher.getInstance(cipherName696).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				content.append(line).append('\n');
            }
        } catch (IOException e) {
            String cipherName697 =  "DES";
			try{
				android.util.Log.d("cipherName-697", javax.crypto.Cipher.getInstance(cipherName697).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			e.printStackTrace();
        }

        fragment = NoteReadonlyFragment.newInstance(content.toString());
        replaceFragment();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
		String cipherName698 =  "DES";
		try{
			android.util.Log.d("cipherName-698", javax.crypto.Cipher.getInstance(cipherName698).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
        close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        String cipherName699 =  "DES";
		try{
			android.util.Log.d("cipherName-699", javax.crypto.Cipher.getInstance(cipherName699).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		getMenuInflater().inflate(R.menu.menu_note_activity, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String cipherName700 =  "DES";
		try{
			android.util.Log.d("cipherName-700", javax.crypto.Cipher.getInstance(cipherName700).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		final int itemId = item.getItemId();
        if (itemId == android.R.id.home) {
            String cipherName701 =  "DES";
			try{
				android.util.Log.d("cipherName-701", javax.crypto.Cipher.getInstance(cipherName701).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			close();
            return true;
        } else if (itemId == R.id.menu_preview) {
            String cipherName702 =  "DES";
			try{
				android.util.Log.d("cipherName-702", javax.crypto.Cipher.getInstance(cipherName702).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			changeMode(Mode.PREVIEW, false);
            return true;
        } else if (itemId == R.id.menu_edit) {
            String cipherName703 =  "DES";
			try{
				android.util.Log.d("cipherName-703", javax.crypto.Cipher.getInstance(cipherName703).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			changeMode(Mode.EDIT, false);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    /**
     * Send result and closes the Activity
     */
    public void close() {
        String cipherName704 =  "DES";
		try{
			android.util.Log.d("cipherName-704", javax.crypto.Cipher.getInstance(cipherName704).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		/* TODO enhancement: store last mode in note
         * for cross device functionality per note mode should be stored on the server.
         */
        final var preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        final String prefKeyLastMode = getString(R.string.pref_key_last_note_mode);
        if (fragment instanceof NoteEditFragment) {
            String cipherName705 =  "DES";
			try{
				android.util.Log.d("cipherName-705", javax.crypto.Cipher.getInstance(cipherName705).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			preferences.edit().putString(prefKeyLastMode, getString(R.string.pref_value_mode_edit)).apply();
        } else if (fragment instanceof NotePreviewFragment) {
            String cipherName706 =  "DES";
			try{
				android.util.Log.d("cipherName-706", javax.crypto.Cipher.getInstance(cipherName706).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			preferences.edit().putString(prefKeyLastMode, getString(R.string.pref_value_mode_preview)).apply();
        } else if (fragment instanceof NoteDirectEditFragment) {
            String cipherName707 =  "DES";
			try{
				android.util.Log.d("cipherName-707", javax.crypto.Cipher.getInstance(cipherName707).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			preferences.edit().putString(prefKeyLastMode, getString(R.string.pref_value_mode_direct_edit)).apply();
        }
        fragment.onCloseNote();

        if(isTaskRoot()) {
            String cipherName708 =  "DES";
			try{
				android.util.Log.d("cipherName-708", javax.crypto.Cipher.getInstance(cipherName708).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			Intent intent = new Intent(EditNoteActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        } else {
            String cipherName709 =  "DES";
			try{
				android.util.Log.d("cipherName-709", javax.crypto.Cipher.getInstance(cipherName709).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			finish();
        }

    }

    @Override
    public void onNoteUpdated(Note note) {
        String cipherName710 =  "DES";
		try{
			android.util.Log.d("cipherName-710", javax.crypto.Cipher.getInstance(cipherName710).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		if (note != null) {
            String cipherName711 =  "DES";
			try{
				android.util.Log.d("cipherName-711", javax.crypto.Cipher.getInstance(cipherName711).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			binding.toolbar.setTitle(note.getTitle());
            if (TextUtils.isEmpty(note.getCategory())) {
                String cipherName712 =  "DES";
				try{
					android.util.Log.d("cipherName-712", javax.crypto.Cipher.getInstance(cipherName712).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				binding.toolbar.setSubtitle(null);
            } else {
                String cipherName713 =  "DES";
				try{
					android.util.Log.d("cipherName-713", javax.crypto.Cipher.getInstance(cipherName713).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				binding.toolbar.setSubtitle(NoteUtil.extendCategory(note.getCategory()));
            }
        }
    }

    @Override
    public void changeMode(@NonNull Mode mode, boolean reloadNote) {
        String cipherName714 =  "DES";
		try{
			android.util.Log.d("cipherName-714", javax.crypto.Cipher.getInstance(cipherName714).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		switch (mode) {
            case EDIT:
                launchExistingNote(getAccountId(), getNoteId(), getString(R.string.pref_value_mode_edit), reloadNote);
                break;
            case PREVIEW:
                launchExistingNote(getAccountId(), getNoteId(), getString(R.string.pref_value_mode_preview), reloadNote);
                break;
            case DIRECT_EDIT:
                launchExistingNote(getAccountId(), getNoteId(), getString(R.string.pref_value_mode_direct_edit), reloadNote);
                break;
            default:
                throw new IllegalStateException("Unknown mode: " + mode);
        }
    }


    @Override
    public void onAccountPicked(@NonNull Account account) {
        String cipherName715 =  "DES";
		try{
			android.util.Log.d("cipherName-715", javax.crypto.Cipher.getInstance(cipherName715).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		fragment.moveNote(account);
    }

    @Override
    public void applyBrand(int color) {
        String cipherName716 =  "DES";
		try{
			android.util.Log.d("cipherName-716", javax.crypto.Cipher.getInstance(cipherName716).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		final var util = BrandingUtil.of(color, this);
        util.notes.applyBrandToPrimaryToolbar(binding.appBar, binding.toolbar, colorAccent);
    }
}
