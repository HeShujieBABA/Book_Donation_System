package com.example.book_donation_system.activity;

import android.support.annotation.NonNull;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.book_donation_system.BuildConfig;
import com.example.book_donation_system.R;

import me.drakeet.multitype.Items;
import me.drakeet.support.about.AbsAboutActivity;
import me.drakeet.support.about.Card;
import me.drakeet.support.about.Category;
import me.drakeet.support.about.Contributor;
import me.drakeet.support.about.Line;

/**
 * Created by 何书杰 on 2017/10/17.
 */

public class AboutActivity extends AbsAboutActivity {
    @Override
    protected void onCreateHeader(@NonNull ImageView icon, @NonNull TextView slogan, @NonNull TextView version) {
        icon.setImageResource(R.drawable.user_icon);
        slogan.setText("About Page By He Shujie");
        version.setText("Version" +" " + BuildConfig.VERSION_NAME);
    }
    @Override
    protected void onItemsCreated(@NonNull Items items) {
        items.add(new Category("介绍与帮助"));
        items.add(new Card(getString(R.string.card_content)));
        items.add(new Line());
        items.add(new Category("开发人员"));
        items.add(new Contributor(R.drawable.user_icon, "何书杰", "Developer & designer", "http://weibo.com/u/3263426514"));
        items.add(new Contributor(R.drawable.user_icon, "陈湘", "Developer"));
        items.add(new Contributor(R.drawable.user_icon, "政委", "Developer"));
        items.add(new Contributor(R.drawable.user_icon, "小磊", "Developer"));
        items.add(new Line());
    }
}
