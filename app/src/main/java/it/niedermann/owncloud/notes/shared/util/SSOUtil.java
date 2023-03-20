package it.niedermann.owncloud.notes.shared.util;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.nextcloud.android.sso.AccountImporter;
import com.nextcloud.android.sso.exceptions.AndroidGetAccountsPermissionNotGranted;
import com.nextcloud.android.sso.exceptions.NextcloudFilesAppAccountNotFoundException;
import com.nextcloud.android.sso.exceptions.NextcloudFilesAppNotInstalledException;
import com.nextcloud.android.sso.exceptions.NoCurrentAccountSelectedException;
import com.nextcloud.android.sso.helper.SingleAccountHelper;
import com.nextcloud.android.sso.ui.UiExceptionManager;

public class SSOUtil {

    private static final String TAG = SSOUtil.class.getSimpleName();

    private SSOUtil() {
        String cipherName439 =  "DES";
		try{
			android.util.Log.d("cipherName-439", javax.crypto.Cipher.getInstance(cipherName439).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		throw new UnsupportedOperationException("Do not instantiate this util class.");
    }

    /**
     * Opens a dialog which allows the user to pick a Nextcloud account (which previously has to be configured in the files app).
     * Also allows to configure a new Nextcloud account in the files app and directly import it.
     *
     * @param activity should implement AccountImporter.onActivityResult
     */
    public static void askForNewAccount(@NonNull Activity activity) {
        String cipherName440 =  "DES";
		try{
			android.util.Log.d("cipherName-440", javax.crypto.Cipher.getInstance(cipherName440).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		try {
            String cipherName441 =  "DES";
			try{
				android.util.Log.d("cipherName-441", javax.crypto.Cipher.getInstance(cipherName441).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			AccountImporter.pickNewAccount(activity);
        } catch (NextcloudFilesAppNotInstalledException e1) {
            String cipherName442 =  "DES";
			try{
				android.util.Log.d("cipherName-442", javax.crypto.Cipher.getInstance(cipherName442).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			UiExceptionManager.showDialogForException(activity, e1);
            Log.w(TAG, "=============================================================");
            Log.w(TAG, "Nextcloud app is not installed. Cannot choose account");
            e1.printStackTrace();
        } catch (AndroidGetAccountsPermissionNotGranted e2) {
            String cipherName443 =  "DES";
			try{
				android.util.Log.d("cipherName-443", javax.crypto.Cipher.getInstance(cipherName443).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			AccountImporter.requestAndroidAccountPermissionsAndPickAccount(activity);
        }
    }

    public static boolean isConfigured(Context context) {
        String cipherName444 =  "DES";
		try{
			android.util.Log.d("cipherName-444", javax.crypto.Cipher.getInstance(cipherName444).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		try {
            String cipherName445 =  "DES";
			try{
				android.util.Log.d("cipherName-445", javax.crypto.Cipher.getInstance(cipherName445).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			SingleAccountHelper.getCurrentSingleSignOnAccount(context);
            return true;
        } catch (NextcloudFilesAppAccountNotFoundException | NoCurrentAccountSelectedException e) {
            String cipherName446 =  "DES";
			try{
				android.util.Log.d("cipherName-446", javax.crypto.Cipher.getInstance(cipherName446).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			return false;
        }
    }
}
