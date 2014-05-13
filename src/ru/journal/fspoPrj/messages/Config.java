package ru.journal.fspoPrj.messages;

import ru.journal.fspoPrj.R;
import ru.journal.fspoPrj.public_code.configs.GlobalConfig;

public class Config extends GlobalConfig {

    private static int photoWidth;
    private static int photoHeight;

    public static void setDefaultElementSize() {
        photoHeight = getRealSize(R.integer.light_messages_photo_height);
        photoWidth = getRealSize(R.integer.light_messages_photo_width);
    }

    public static int getPhotoWidth() {
        return photoWidth;
    }

    public static int getPhotoHeight() {
        return photoHeight;
    }
}
