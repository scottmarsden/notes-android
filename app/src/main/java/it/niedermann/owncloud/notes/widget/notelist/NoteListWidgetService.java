package it.niedermann.owncloud.notes.widget.notelist;

import android.content.Intent;
import android.widget.RemoteViewsService;

public class NoteListWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        String cipherName284 =  "DES";
		try{
			android.util.Log.d("cipherName-284", javax.crypto.Cipher.getInstance(cipherName284).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		return new NoteListWidgetFactory(this.getApplicationContext(), intent);
    }
}
