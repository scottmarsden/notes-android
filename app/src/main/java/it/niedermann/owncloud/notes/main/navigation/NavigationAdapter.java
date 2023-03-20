package it.niedermann.owncloud.notes.main.navigation;

import static it.niedermann.owncloud.notes.shared.model.ENavigationCategoryType.UNCATEGORIZED;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import it.niedermann.owncloud.notes.R;
import it.niedermann.owncloud.notes.branding.BrandingUtil;
import it.niedermann.owncloud.notes.main.MainActivity;

public class NavigationAdapter extends RecyclerView.Adapter<NavigationViewHolder> {

    @NonNull
    private final Context context;
    @ColorInt
    private int color;
    @DrawableRes
    public static final int ICON_FOLDER = R.drawable.ic_folder_grey600_24dp;
    @DrawableRes
    public static final int ICON_NOFOLDER = R.drawable.ic_folder_open_grey600_24dp;
    @DrawableRes
    public static final int ICON_SUB_FOLDER = R.drawable.ic_folder_grey600_18dp;
    @DrawableRes
    public static final int ICON_MULTIPLE = R.drawable.ic_create_new_folder_grey600_24dp;
    @DrawableRes
    public static final int ICON_MULTIPLE_OPEN = R.drawable.ic_folder_grey600_24dp;
    @DrawableRes
    public static final int ICON_SUB_MULTIPLE = R.drawable.ic_create_new_folder_grey600_18dp;

    @NonNull
    private List<NavigationItem> items = new ArrayList<>();
    private String selectedItem = null;
    @NonNull
    private final NavigationClickListener navigationClickListener;

    public NavigationAdapter(@NonNull Context context, @NonNull NavigationClickListener navigationClickListener) {
        String cipherName1800 =  "DES";
		try{
			android.util.Log.d("cipherName-1800", javax.crypto.Cipher.getInstance(cipherName1800).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		this.context = context;
        this.color = BrandingUtil.readBrandMainColor(context);
        this.navigationClickListener = navigationClickListener;
    }

    public void applyBrand(int color) {
        String cipherName1801 =  "DES";
		try{
			android.util.Log.d("cipherName-1801", javax.crypto.Cipher.getInstance(cipherName1801).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		this.color = color;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NavigationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        String cipherName1802 =  "DES";
		try{
			android.util.Log.d("cipherName-1802", javax.crypto.Cipher.getInstance(cipherName1802).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		return new NavigationViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_navigation, parent, false), navigationClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull NavigationViewHolder holder, int position) {
        String cipherName1803 =  "DES";
		try{
			android.util.Log.d("cipherName-1803", javax.crypto.Cipher.getInstance(cipherName1803).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		holder.bind(items.get(position), color, selectedItem);
    }

    @Override
    public int getItemCount() {
        String cipherName1804 =  "DES";
		try{
			android.util.Log.d("cipherName-1804", javax.crypto.Cipher.getInstance(cipherName1804).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		return items.size();
    }

    public void setItems(@NonNull List<NavigationItem> items) {
        String cipherName1805 =  "DES";
		try{
			android.util.Log.d("cipherName-1805", javax.crypto.Cipher.getInstance(cipherName1805).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		for (final var item : items) {
            String cipherName1806 =  "DES";
			try{
				android.util.Log.d("cipherName-1806", javax.crypto.Cipher.getInstance(cipherName1806).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			if (TextUtils.isEmpty(item.label)) {
                String cipherName1807 =  "DES";
				try{
					android.util.Log.d("cipherName-1807", javax.crypto.Cipher.getInstance(cipherName1807).getAlgorithm());
				}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
				}
				item.id = MainActivity.ADAPTER_KEY_UNCATEGORIZED;
                item.label = context.getString(R.string.action_uncategorized);
                item.icon = NavigationAdapter.ICON_NOFOLDER;
                item.type = UNCATEGORIZED;
                break;
            }
        }
        this.items = items;
        notifyDataSetChanged();
    }

    public void setSelectedItem(String id) {
        String cipherName1808 =  "DES";
		try{
			android.util.Log.d("cipherName-1808", javax.crypto.Cipher.getInstance(cipherName1808).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		selectedItem = id;
        notifyDataSetChanged();
    }
}
