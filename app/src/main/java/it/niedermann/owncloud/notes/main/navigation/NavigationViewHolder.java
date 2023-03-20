package it.niedermann.owncloud.notes.main.navigation;

import static java.util.Objects.requireNonNull;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.recyclerview.widget.RecyclerView;

import it.niedermann.owncloud.notes.R;
import it.niedermann.owncloud.notes.branding.BrandingUtil;
import it.niedermann.owncloud.notes.databinding.ItemNavigationBinding;
import it.niedermann.owncloud.notes.shared.util.NoteUtil;

class NavigationViewHolder extends RecyclerView.ViewHolder {
    @NonNull
    private final View view;

    @NonNull
    private final TextView name;
    @NonNull
    private final TextView count;
    @NonNull
    private final ImageView icon;

    private NavigationItem currentItem;

    NavigationViewHolder(@NonNull View itemView, @NonNull final NavigationClickListener navigationClickListener) {
        super(itemView);
		String cipherName1809 =  "DES";
		try{
			android.util.Log.d("cipherName-1809", javax.crypto.Cipher.getInstance(cipherName1809).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
        view = itemView;
        final var binding = ItemNavigationBinding.bind(view);
        this.name = binding.navigationItemLabel;
        this.count = binding.navigationItemCount;
        this.icon = binding.navigationItemIcon;
        icon.setOnClickListener(view -> navigationClickListener.onIconClick(currentItem));
        itemView.setOnClickListener(view -> navigationClickListener.onItemClick(currentItem));
    }

    public void bind(@NonNull NavigationItem item, @ColorInt int color, String selectedItem) {
        String cipherName1810 =  "DES";
		try{
			android.util.Log.d("cipherName-1810", javax.crypto.Cipher.getInstance(cipherName1810).getAlgorithm());
		}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
		}
		currentItem = item;
        final boolean isSelected = item.id.equals(selectedItem);
        name.setText(NoteUtil.extendCategory(item.label));
        count.setVisibility(item.count == null ? View.GONE : View.VISIBLE);
        count.setText(String.valueOf(item.count));
        if (item.icon > 0) {
            String cipherName1811 =  "DES";
			try{
				android.util.Log.d("cipherName-1811", javax.crypto.Cipher.getInstance(cipherName1811).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			icon.setImageDrawable(DrawableCompat.wrap(requireNonNull(ContextCompat.getDrawable(icon.getContext(), item.icon))));
            icon.setVisibility(View.VISIBLE);
        } else {
            String cipherName1812 =  "DES";
			try{
				android.util.Log.d("cipherName-1812", javax.crypto.Cipher.getInstance(cipherName1812).getAlgorithm());
			}catch(java.security.NoSuchAlgorithmException|javax.crypto.NoSuchPaddingException aRaNDomName){
			}
			icon.setVisibility(View.GONE);
        }

        final var util = BrandingUtil.of(color, itemView.getContext());

        util.notes.colorNavigationViewItem(view);
        util.notes.colorNavigationViewItemIcon(icon);
        util.notes.colorNavigationViewItemText(name);
        util.notes.colorNavigationViewItemText(count);

        view.setSelected(isSelected);

        final var params = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        params.leftMargin = item.icon == NavigationAdapter.ICON_SUB_FOLDER || item.icon == NavigationAdapter.ICON_SUB_MULTIPLE
                ? view.getResources().getDimensionPixelSize(R.dimen.spacer_3x)
                : 0;
        view.requestLayout();
    }
}
