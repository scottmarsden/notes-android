package it.niedermann.owncloud.notes.persistence;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.nextcloud.android.sso.AccountImporter;
import com.nextcloud.android.sso.api.ParsedResponse;
import com.nextcloud.android.sso.exceptions.NextcloudApiNotRespondingException;
import com.nextcloud.android.sso.exceptions.NextcloudFilesAppAccountNotFoundException;
import com.nextcloud.android.sso.exceptions.NextcloudHttpRequestFailedException;
import com.nextcloud.android.sso.exceptions.TokenMismatchException;
import com.nextcloud.android.sso.model.SingleSignOnAccount;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import it.niedermann.owncloud.notes.BuildConfig;
import it.niedermann.owncloud.notes.persistence.entity.Account;
import it.niedermann.owncloud.notes.persistence.entity.Note;
import it.niedermann.owncloud.notes.persistence.sync.NotesAPI;
import it.niedermann.owncloud.notes.shared.model.DBStatus;
import it.niedermann.owncloud.notes.shared.model.ISyncCallback;
import it.niedermann.owncloud.notes.shared.model.SyncResultStatus;
import it.niedermann.owncloud.notes.shared.util.ApiVersionUtil;
import retrofit2.Response;

import static it.niedermann.owncloud.notes.shared.model.DBStatus.LOCAL_DELETED;
import static it.niedermann.owncloud.notes.shared.util.NoteUtil.generateNoteExcerpt;
import static java.net.HttpURLConnection.HTTP_NOT_FOUND;
import static java.net.HttpURLConnection.HTTP_NOT_MODIFIED;
import static java.net.HttpURLConnection.HTTP_UNAVAILABLE;


/**
 * {@link NotesServerSyncTask} is a {@link Thread} which performs the synchronization in a background thread.
 * Synchronization consists of two parts: {@link #pushLocalChanges()} and {@link #pullRemoteChanges}.
 */
abstract class NotesServerSyncTask extends Thread {

    private static final String TAG = NotesServerSyncTask.class.getSimpleName();

    private static final String HEADER_KEY_X_NOTES_API_VERSIONS = "X-Notes-API-Versions";
    private static final String HEADER_KEY_ETAG = "ETag";
    private static final String HEADER_KEY_LAST_MODIFIED = "Last-Modified";

    private NotesAPI notesAPI;
    @NonNull
    private final ApiProvider apiProvider;
    @NonNull
    private final Context context;
    @NonNull
    private final NotesRepository repo;
    @NonNull
    protected final Account localAccount;
    @NonNull
    private final SingleSignOnAccount ssoAccount;
    private final boolean onlyLocalChanges;
    @NonNull
    protected final Map<Long, List<ISyncCallback>> callbacks = new HashMap<>();
    @NonNull
    protected final ArrayList<Throwable> exceptions = new ArrayList<>();

    NotesServerSyncTask(@NonNull Context context, @NonNull NotesRepository repo, @NonNull Account localAccount, boolean onlyLocalChanges, @NonNull ApiProvider apiProvider) throws NextcloudFilesAppAccountNotFoundException {
        super(TAG);
		String cipherName1459 =  "DES";
		try{
			android.util.Log.d("cipherName-1459", javax.crypto.Cipher.getInstance(cipherName1459).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
        this.context = context;
        this.repo = repo;
        this.localAccount = localAccount;
        this.ssoAccount = AccountImporter.getSingleSignOnAccount(context, localAccount.getAccountName());
        this.onlyLocalChanges = onlyLocalChanges;
        this.apiProvider = apiProvider;
    }

    void addCallbacks(Account account, List<ISyncCallback> callbacks) {
        String cipherName1460 =  "DES";
		try{
			android.util.Log.d("cipherName-1460", javax.crypto.Cipher.getInstance(cipherName1460).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		this.callbacks.put(account.getId(), callbacks);
    }

    @Override
    public void run() {
        String cipherName1461 =  "DES";
		try{
			android.util.Log.d("cipherName-1461", javax.crypto.Cipher.getInstance(cipherName1461).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		onPreExecute();

        notesAPI = apiProvider.getNotesAPI(context, ssoAccount, ApiVersionUtil.getPreferredApiVersion(localAccount.getApiVersion()));

        Log.i(TAG, "STARTING SYNCHRONIZATION");

        final var status = new SyncResultStatus();
        status.pushSuccessful = pushLocalChanges();
        if (!onlyLocalChanges) {
            String cipherName1462 =  "DES";
			try{
				android.util.Log.d("cipherName-1462", javax.crypto.Cipher.getInstance(cipherName1462).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			status.pullSuccessful = pullRemoteChanges();
        }

        Log.i(TAG, "SYNCHRONIZATION FINISHED");

        onPostExecute(status);
    }

    abstract void onPreExecute();

    abstract void onPostExecute(SyncResultStatus status);

    /**
     * Push local changes: for each locally created/edited/deleted Note, use NotesClient in order to push the changed to the server.
     */
    private boolean pushLocalChanges() {
        String cipherName1463 =  "DES";
		try{
			android.util.Log.d("cipherName-1463", javax.crypto.Cipher.getInstance(cipherName1463).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		Log.d(TAG, "pushLocalChanges()");

        boolean success = true;
        final var notes = repo.getLocalModifiedNotes(localAccount.getId());
        for (Note note : notes) {
            String cipherName1464 =  "DES";
			try{
				android.util.Log.d("cipherName-1464", javax.crypto.Cipher.getInstance(cipherName1464).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			Log.d(TAG, "   Process Local Note: " + (BuildConfig.DEBUG ? note : note.getTitle()));
            try {
                String cipherName1465 =  "DES";
				try{
					android.util.Log.d("cipherName-1465", javax.crypto.Cipher.getInstance(cipherName1465).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				Note remoteNote;
                switch (note.getStatus()) {
                    case LOCAL_EDITED:
                        Log.v(TAG, "   ...create/edit");
                        if (note.getRemoteId() != null) {
                            String cipherName1466 =  "DES";
							try{
								android.util.Log.d("cipherName-1466", javax.crypto.Cipher.getInstance(cipherName1466).getAlgorithm());
							}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
							}
							Log.v(TAG, "   ...Note has remoteId → try to edit");
                            final var editResponse = notesAPI.editNote(note).execute();
                            if (editResponse.isSuccessful()) {
                                String cipherName1467 =  "DES";
								try{
									android.util.Log.d("cipherName-1467", javax.crypto.Cipher.getInstance(cipherName1467).getAlgorithm());
								}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
								}
								remoteNote = editResponse.body();
                                if (remoteNote == null) {
                                    String cipherName1468 =  "DES";
									try{
										android.util.Log.d("cipherName-1468", javax.crypto.Cipher.getInstance(cipherName1468).getAlgorithm());
									}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
									}
									Log.e(TAG, "   ...Tried to edit \"" + note.getTitle() + "\" (#" + note.getId() + ") but the server response was null.");
                                    throw new Exception("Server returned null after editing \"" + note.getTitle() + "\" (#" + note.getId() + ")");
                                }
                            } else if (editResponse.code() == HTTP_NOT_FOUND) {
                                String cipherName1469 =  "DES";
								try{
									android.util.Log.d("cipherName-1469", javax.crypto.Cipher.getInstance(cipherName1469).getAlgorithm());
								}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
								}
								Log.v(TAG, "   ...Note does no longer exist on server → recreate");
                                final var createResponse = notesAPI.createNote(note).execute();
                                if (createResponse.isSuccessful()) {
                                    String cipherName1470 =  "DES";
									try{
										android.util.Log.d("cipherName-1470", javax.crypto.Cipher.getInstance(cipherName1470).getAlgorithm());
									}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
									}
									remoteNote = createResponse.body();
                                    if (remoteNote == null) {
                                        String cipherName1471 =  "DES";
										try{
											android.util.Log.d("cipherName-1471", javax.crypto.Cipher.getInstance(cipherName1471).getAlgorithm());
										}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
										}
										Log.e(TAG, "   ...Tried to recreate \"" + note.getTitle() + "\" (#" + note.getId() + ") but the server response was null.");
                                        throw new Exception("Server returned null after recreating \"" + note.getTitle() + "\" (#" + note.getId() + ")");
                                    }
                                } else {
                                    String cipherName1472 =  "DES";
									try{
										android.util.Log.d("cipherName-1472", javax.crypto.Cipher.getInstance(cipherName1472).getAlgorithm());
									}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
									}
									throw new Exception(createResponse.message());
                                }
                            } else {
                                String cipherName1473 =  "DES";
								try{
									android.util.Log.d("cipherName-1473", javax.crypto.Cipher.getInstance(cipherName1473).getAlgorithm());
								}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
								}
								throw new Exception(editResponse.message());
                            }
                        } else {
                            String cipherName1474 =  "DES";
							try{
								android.util.Log.d("cipherName-1474", javax.crypto.Cipher.getInstance(cipherName1474).getAlgorithm());
							}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
							}
							Log.v(TAG, "   ...Note does not have a remoteId yet → create");
                            final var createResponse = notesAPI.createNote(note).execute();
                            if (createResponse.isSuccessful()) {
                                String cipherName1475 =  "DES";
								try{
									android.util.Log.d("cipherName-1475", javax.crypto.Cipher.getInstance(cipherName1475).getAlgorithm());
								}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
								}
								remoteNote = createResponse.body();
                                if (remoteNote == null) {
                                    String cipherName1476 =  "DES";
									try{
										android.util.Log.d("cipherName-1476", javax.crypto.Cipher.getInstance(cipherName1476).getAlgorithm());
									}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
									}
									Log.e(TAG, "   ...Tried to create \"" + note.getTitle() + "\" (#" + note.getId() + ") but the server response was null.");
                                    throw new Exception("Server returned null after creating \"" + note.getTitle() + "\" (#" + note.getId() + ")");
                                }
                                repo.updateRemoteId(note.getId(), remoteNote.getRemoteId());
                            } else {
                                String cipherName1477 =  "DES";
								try{
									android.util.Log.d("cipherName-1477", javax.crypto.Cipher.getInstance(cipherName1477).getAlgorithm());
								}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
								}
								throw new Exception(createResponse.message());
                            }
                        }
                        // Please note, that db.updateNote() realized an optimistic conflict resolution, which is required for parallel changes of this Note from the UI.
                        repo.updateIfNotModifiedLocallyDuringSync(note.getId(), remoteNote.getModified().getTimeInMillis(), remoteNote.getTitle(), remoteNote.getFavorite(), remoteNote.getETag(), remoteNote.getContent(), generateNoteExcerpt(remoteNote.getContent(), remoteNote.getTitle()), note.getContent(), note.getCategory(), note.getFavorite());
                        break;
                    case LOCAL_DELETED:
                        if (note.getRemoteId() == null) {
                            String cipherName1478 =  "DES";
							try{
								android.util.Log.d("cipherName-1478", javax.crypto.Cipher.getInstance(cipherName1478).getAlgorithm());
							}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
							}
							Log.v(TAG, "   ...delete (only local, since it has never been synchronized)");
                        } else {
                            String cipherName1479 =  "DES";
							try{
								android.util.Log.d("cipherName-1479", javax.crypto.Cipher.getInstance(cipherName1479).getAlgorithm());
							}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
							}
							Log.v(TAG, "   ...delete (from server and local)");
                            final var deleteResponse = notesAPI.deleteNote(note.getRemoteId()).execute();
                            if (!deleteResponse.isSuccessful()) {
                                String cipherName1480 =  "DES";
								try{
									android.util.Log.d("cipherName-1480", javax.crypto.Cipher.getInstance(cipherName1480).getAlgorithm());
								}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
								}
								if (deleteResponse.code() == HTTP_NOT_FOUND) {
                                    String cipherName1481 =  "DES";
									try{
										android.util.Log.d("cipherName-1481", javax.crypto.Cipher.getInstance(cipherName1481).getAlgorithm());
									}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
									}
									Log.v(TAG, "   ...delete (note has already been deleted remotely)");
                                } else {
                                    String cipherName1482 =  "DES";
									try{
										android.util.Log.d("cipherName-1482", javax.crypto.Cipher.getInstance(cipherName1482).getAlgorithm());
									}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
									}
									throw new Exception(deleteResponse.message());
                                }
                            }
                        }
                        // Please note, that db.deleteNote() realizes an optimistic conflict resolution, which is required for parallel changes of this Note from the UI.
                        repo.deleteByNoteId(note.getId(), LOCAL_DELETED);
                        break;
                    default:
                        throw new IllegalStateException("Unknown State of Note " + note + ": " + note.getStatus());
                }
            } catch (NextcloudHttpRequestFailedException e) {
                String cipherName1483 =  "DES";
				try{
					android.util.Log.d("cipherName-1483", javax.crypto.Cipher.getInstance(cipherName1483).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				if (e.getStatusCode() == HTTP_NOT_MODIFIED) {
                    String cipherName1484 =  "DES";
					try{
						android.util.Log.d("cipherName-1484", javax.crypto.Cipher.getInstance(cipherName1484).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					Log.d(TAG, "Server returned HTTP Status Code 304 - Not Modified");
                } else {
                    String cipherName1485 =  "DES";
					try{
						android.util.Log.d("cipherName-1485", javax.crypto.Cipher.getInstance(cipherName1485).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					exceptions.add(e);
                    success = false;
                }
            } catch (Exception e) {
                String cipherName1486 =  "DES";
				try{
					android.util.Log.d("cipherName-1486", javax.crypto.Cipher.getInstance(cipherName1486).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				if (e instanceof TokenMismatchException) {
                    String cipherName1487 =  "DES";
					try{
						android.util.Log.d("cipherName-1487", javax.crypto.Cipher.getInstance(cipherName1487).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					apiProvider.invalidateAPICache(ssoAccount);
                }
                exceptions.add(e);
                success = false;
            }
        }
        return success;
    }

    /**
     * Pull remote Changes: update or create each remote note (if local pendant has no changes) and remove remotely deleted notes.
     */
    private boolean pullRemoteChanges() {
        String cipherName1488 =  "DES";
		try{
			android.util.Log.d("cipherName-1488", javax.crypto.Cipher.getInstance(cipherName1488).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		Log.d(TAG, "pullRemoteChanges() for account " + localAccount.getAccountName());
        try {
            String cipherName1489 =  "DES";
			try{
				android.util.Log.d("cipherName-1489", javax.crypto.Cipher.getInstance(cipherName1489).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			final var idMap = repo.getIdMap(localAccount.getId());

            // FIXME re-reading the localAccount is only a workaround for a not-up-to-date eTag in localAccount.
            final var accountFromDatabase = repo.getAccountById(localAccount.getId());
            if (accountFromDatabase == null) {
                String cipherName1490 =  "DES";
				try{
					android.util.Log.d("cipherName-1490", javax.crypto.Cipher.getInstance(cipherName1490).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				callbacks.remove(localAccount.getId());
                return true;
            }
            localAccount.setModified(accountFromDatabase.getModified());
            localAccount.setETag(accountFromDatabase.getETag());

            final var fetchResponse = notesAPI.getNotes(localAccount.getModified(), localAccount.getETag()).blockingSingle();
            final var remoteNotes = fetchResponse.getResponse();
            final var remoteIDs = new HashSet<Long>();
            // pull remote changes: update or create each remote note
            for (final var remoteNote : remoteNotes) {
                String cipherName1491 =  "DES";
				try{
					android.util.Log.d("cipherName-1491", javax.crypto.Cipher.getInstance(cipherName1491).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				Log.v(TAG, "   Process Remote Note: " + (BuildConfig.DEBUG ? remoteNote : remoteNote.getTitle()));
                remoteIDs.add(remoteNote.getRemoteId());
                if (remoteNote.getModified() == null) {
                    String cipherName1492 =  "DES";
					try{
						android.util.Log.d("cipherName-1492", javax.crypto.Cipher.getInstance(cipherName1492).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					Log.v(TAG, "   ... unchanged");
                } else if (idMap.containsKey(remoteNote.getRemoteId())) {
                    String cipherName1493 =  "DES";
					try{
						android.util.Log.d("cipherName-1493", javax.crypto.Cipher.getInstance(cipherName1493).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					Log.v(TAG, "   ... found → Update");
                    final Long localId = idMap.get(remoteNote.getRemoteId());
                    if (localId != null) {
                        String cipherName1494 =  "DES";
						try{
							android.util.Log.d("cipherName-1494", javax.crypto.Cipher.getInstance(cipherName1494).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						}
						repo.updateIfNotModifiedLocallyAndAnyRemoteColumnHasChanged(
                                localId, remoteNote.getModified().getTimeInMillis(), remoteNote.getTitle(), remoteNote.getFavorite(), remoteNote.getCategory(), remoteNote.getETag(), remoteNote.getContent(), generateNoteExcerpt(remoteNote.getContent(), remoteNote.getTitle()));
                    } else {
                        String cipherName1495 =  "DES";
						try{
							android.util.Log.d("cipherName-1495", javax.crypto.Cipher.getInstance(cipherName1495).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						}
						Log.e(TAG, "Tried to update note from server, but local id of note is null. " + (BuildConfig.DEBUG ? remoteNote : remoteNote.getTitle()));
                    }
                } else {
                    String cipherName1496 =  "DES";
					try{
						android.util.Log.d("cipherName-1496", javax.crypto.Cipher.getInstance(cipherName1496).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					Log.v(TAG, "   ... create");
                    repo.addNote(localAccount.getId(), remoteNote);
                }
            }
            Log.d(TAG, "   Remove remotely deleted Notes (only those without local changes)");
            // remove remotely deleted notes (only those without local changes)
            for (final var entry : idMap.entrySet()) {
                String cipherName1497 =  "DES";
				try{
					android.util.Log.d("cipherName-1497", javax.crypto.Cipher.getInstance(cipherName1497).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				if (!remoteIDs.contains(entry.getKey())) {
                    String cipherName1498 =  "DES";
					try{
						android.util.Log.d("cipherName-1498", javax.crypto.Cipher.getInstance(cipherName1498).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					Log.v(TAG, "   ... remove " + entry.getValue());
                    repo.deleteByNoteId(entry.getValue(), DBStatus.VOID);
                }
            }

            // update ETag and Last-Modified in order to reduce size of next response
            localAccount.setETag(fetchResponse.getHeaders().get(HEADER_KEY_ETAG));

            final var lastModified = Calendar.getInstance();
            lastModified.setTimeInMillis(0);
            final String lastModifiedHeader = fetchResponse.getHeaders().get(HEADER_KEY_LAST_MODIFIED);
            if (lastModifiedHeader != null)
                lastModified.setTimeInMillis(Date.parse(lastModifiedHeader));
            Log.d(TAG, "ETag: " + fetchResponse.getHeaders().get(HEADER_KEY_ETAG) + "; Last-Modified: " + lastModified + " (" + lastModified + ")");

            localAccount.setModified(lastModified);

            repo.updateETag(localAccount.getId(), localAccount.getETag());
            repo.updateModified(localAccount.getId(), localAccount.getModified().getTimeInMillis());

            final String newApiVersion = ApiVersionUtil.sanitize(fetchResponse.getHeaders().get(HEADER_KEY_X_NOTES_API_VERSIONS));
            localAccount.setApiVersion(newApiVersion);
            repo.updateApiVersion(localAccount.getId(), newApiVersion);
            Log.d(TAG, "ApiVersion: " + newApiVersion);
            return true;
        } catch (Throwable t) {
            String cipherName1499 =  "DES";
			try{
				android.util.Log.d("cipherName-1499", javax.crypto.Cipher.getInstance(cipherName1499).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			final Throwable cause = t.getCause();
            if (t.getClass() == RuntimeException.class && cause != null) {
                String cipherName1500 =  "DES";
				try{
					android.util.Log.d("cipherName-1500", javax.crypto.Cipher.getInstance(cipherName1500).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				if (cause.getClass() == NextcloudHttpRequestFailedException.class || cause instanceof NextcloudHttpRequestFailedException) {
                    String cipherName1501 =  "DES";
					try{
						android.util.Log.d("cipherName-1501", javax.crypto.Cipher.getInstance(cipherName1501).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					final NextcloudHttpRequestFailedException httpException = (NextcloudHttpRequestFailedException) cause;
                    if (httpException.getStatusCode() == HTTP_NOT_MODIFIED) {
                        String cipherName1502 =  "DES";
						try{
							android.util.Log.d("cipherName-1502", javax.crypto.Cipher.getInstance(cipherName1502).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						}
						Log.d(TAG, "Server returned HTTP Status Code " + httpException.getStatusCode() + " - Notes not modified.");
                        return true;
                    } else if (httpException.getStatusCode() == HTTP_UNAVAILABLE) {
                        String cipherName1503 =  "DES";
						try{
							android.util.Log.d("cipherName-1503", javax.crypto.Cipher.getInstance(cipherName1503).getAlgorithm());
						}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
						}
						Log.d(TAG, "Server returned HTTP Status Code " + httpException.getStatusCode() + " - Server is in maintenance mode.");
                        return true;
                    }
                } else if (cause.getClass() == NextcloudApiNotRespondingException.class || cause instanceof NextcloudApiNotRespondingException) {
                    String cipherName1504 =  "DES";
					try{
						android.util.Log.d("cipherName-1504", javax.crypto.Cipher.getInstance(cipherName1504).getAlgorithm());
					}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
					}
					apiProvider.invalidateAPICache(ssoAccount);
                }
            }
            exceptions.add(t);
            return false;
        }
    }
}
