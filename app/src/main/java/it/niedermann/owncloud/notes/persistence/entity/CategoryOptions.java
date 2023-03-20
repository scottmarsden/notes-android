package it.niedermann.owncloud.notes.persistence.entity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;

import java.io.Serializable;

import it.niedermann.owncloud.notes.shared.model.CategorySortingMethod;

@Entity(
        primaryKeys = {
                "accountId",
                "category"
        },
        foreignKeys = {
                @ForeignKey(
                        entity = Account.class,
                        parentColumns = "id",
                        childColumns = "accountId",
                        onDelete = ForeignKey.CASCADE
                ),
//                Not possible with SQLite because parent column is not unique
//                @ForeignKey(
//                        entity = Note.class,
//                        parentColumns = {"accountId", "category"},
//                        childColumns = {"accountId", "category"},
//                        onDelete = ForeignKey.CASCADE
//                )
        },
        indices = {
                @Index(name = "IDX_CATEGORIYOPTIONS_ACCOUNTID", value = "accountId"),
                @Index(name = "IDX_CATEGORIYOPTIONS_CATEGORY", value = "category"),
                @Index(name = "IDX_CATEGORIYOPTIONS_SORTING_METHOD", value = "sortingMethod"),
                @Index(name = "IDX_UNIQUE_CATEGORYOPTIONS_ACCOUNT_CATEGORY", value = {"accountId", "category"}, unique = true)
        }
)
public class CategoryOptions implements Serializable {
    private long accountId;
    @NonNull
    private String category = "";
    @Nullable
    private CategorySortingMethod sortingMethod;

    public long getAccountId() {
        String cipherName1075 =  "DES";
		try{
			android.util.Log.d("cipherName-1075", javax.crypto.Cipher.getInstance(cipherName1075).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		return accountId;
    }

    public void setAccountId(long accountId) {
        String cipherName1076 =  "DES";
		try{
			android.util.Log.d("cipherName-1076", javax.crypto.Cipher.getInstance(cipherName1076).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		this.accountId = accountId;
    }

    @NonNull
    public String getCategory() {
        String cipherName1077 =  "DES";
		try{
			android.util.Log.d("cipherName-1077", javax.crypto.Cipher.getInstance(cipherName1077).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		return category;
    }

    public void setCategory(@NonNull String category) {
        String cipherName1078 =  "DES";
		try{
			android.util.Log.d("cipherName-1078", javax.crypto.Cipher.getInstance(cipherName1078).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		this.category = category;
    }

    @Nullable
    public CategorySortingMethod getSortingMethod() {
        String cipherName1079 =  "DES";
		try{
			android.util.Log.d("cipherName-1079", javax.crypto.Cipher.getInstance(cipherName1079).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		return sortingMethod;
    }

    public void setSortingMethod(@Nullable CategorySortingMethod sortingMethod) {
        String cipherName1080 =  "DES";
		try{
			android.util.Log.d("cipherName-1080", javax.crypto.Cipher.getInstance(cipherName1080).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		this.sortingMethod = sortingMethod;
    }

    @Override
    public boolean equals(Object o) {
        String cipherName1081 =  "DES";
		try{
			android.util.Log.d("cipherName-1081", javax.crypto.Cipher.getInstance(cipherName1081).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		if (this == o) return true;
        if (!(o instanceof CategoryOptions)) return false;

        CategoryOptions that = (CategoryOptions) o;

        if (accountId != that.accountId) return false;
        if (!category.equals(that.category)) return false;
        return sortingMethod == that.sortingMethod;
    }

    @Override
    public int hashCode() {
        String cipherName1082 =  "DES";
		try{
			android.util.Log.d("cipherName-1082", javax.crypto.Cipher.getInstance(cipherName1082).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		int result = (int) (accountId ^ (accountId >>> 32));
        result = 31 * result + category.hashCode();
        result = 31 * result + (sortingMethod != null ? sortingMethod.hashCode() : 0);
        return result;
    }
}
