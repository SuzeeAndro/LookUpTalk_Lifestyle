package com.lookuptalk.helper;

public final class Application extends android.app.Application {
    @Override
    public void onCreate() {
        super.onCreate();
//        FontsOverride.setDefaultFont(this, "DEFAULT", "MyFontAsset.ttf");
        FontsOverride.setDefaultFont(this, "MONOSPACE", "Ferrara-Regular.ttf");
//        FontsOverride.setDefaultFont(this, "SERIF", "MyFontAsset3.ttf");
//        FontsOverride.setDefaultFont(this, "SANS_SERIF", "MyFontAsset4.ttf");
    }
}