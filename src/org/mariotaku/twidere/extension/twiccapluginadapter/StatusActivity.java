package org.mariotaku.twidere.extension.twiccapluginadapter;

import org.mariotaku.twidere.Twidere;
import org.mariotaku.twidere.model.ParcelableLocation;
import org.mariotaku.twidere.model.ParcelableStatus;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class StatusActivity extends Activity {
	@Override
	public final void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		final ParcelableStatus status = Twidere.getStatusFromIntent(getIntent());
		if (status == null) {
			finish();
			return;
		}
		final Intent intent = new Intent("jp.r246.twicca.ACTION_SHOW_TWEET");
		final ParcelableLocation location = status.location;
		intent.addCategory(Intent.CATEGORY_DEFAULT);
		intent.putExtra(Intent.EXTRA_TEXT, status.text_plain);
		intent.putExtra("id", String.valueOf(status.status_id));
		if (location != null) {
			intent.putExtra("latitude", String.valueOf(location.latitude));
			intent.putExtra("longitude", String.valueOf(location.longitude));
		}
		intent.putExtra("created_at", String.valueOf(status.status_timestamp));
		intent.putExtra("source", String.valueOf(status.source));
		if (status.in_reply_to_status_id > 0) {
			intent.putExtra("in_reply_to_status_id", String.valueOf(status.in_reply_to_status_id));
		}
		intent.putExtra("user_screen_name", status.screen_name);
		intent.putExtra("user_name", status.name);
		intent.putExtra("user_id", status.user_id);
		final String profile_image_url_string = status.profile_image_url_string;
		intent.putExtra("user_profile_image_url", Utils.getOriginalTwitterProfileImage(profile_image_url_string));
		intent.putExtra("user_profile_image_url_mini", Utils.getMiniTwitterProfileImage(profile_image_url_string));
		intent.putExtra("user_profile_image_url_normal", profile_image_url_string);
		intent.putExtra("user_profile_image_url_bigger", Utils.getBiggerTwitterProfileImage(profile_image_url_string));
		startActivity(Intent.createChooser(intent, getString(R.string.app_name)));
		finish();
	}
}
