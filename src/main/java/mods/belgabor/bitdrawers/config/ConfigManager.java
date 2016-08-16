package mods.belgabor.bitdrawers.config;

import net.minecraftforge.common.config.Configuration;

import java.io.File;

/**
 * Created by Belgabor on 19.07.2016.
 */
public class ConfigManager {
    private static final String LANG_PREFIX = "bitDrawers.config.";
    
    public Configuration config;
    
    public boolean debugTrace = false;
    public int bitdrawerStorage = 16;
    public boolean allowBagMultiInsertion = true;
    public boolean allowChiseledBlockMultiInsertion = false;
    public boolean chatty = true;
    
    public ConfigManager(File config) {
        this.config = new Configuration(config);
        sync();
    }
    
    public void sync() {
        debugTrace = config.get(Configuration.CATEGORY_GENERAL, "enableDebugLogging", debugTrace,
                "Writes additional log messages while using the mod.  Mainly for debug purposes.  Should be kept disabled unless instructed otherwise.")
                .setLanguageKey(LANG_PREFIX + "prop.enableDebugLogging").getBoolean();
        
        bitdrawerStorage = config.get(Configuration.CATEGORY_GENERAL, "bitdrawerBaseStorage", bitdrawerStorage,
                "Base storage of a bit drawer (stacks).").setRequiresWorldRestart(true)
                .setLanguageKey(LANG_PREFIX + "prop.bitdrawerBaseStorage").getInt();

        allowBagMultiInsertion = config.get(Configuration.CATEGORY_GENERAL, "allowBagMultiInsertion", allowBagMultiInsertion,
                "If set the contents of all bags in a players inventory will try to be inserted on a double right-click on the bit drawer controller.")
                .setLanguageKey(LANG_PREFIX + "prop.allowBagMultiInsertion").getBoolean();

        allowChiseledBlockMultiInsertion = config.get(Configuration.CATEGORY_GENERAL, "allowChiseledBlockMultiInsertion", allowChiseledBlockMultiInsertion,
                "If set all Chisels & Bits blocks in a players inventory will try to be inserted on a double right-click on the bit drawer controller.")
                .setLanguageKey(LANG_PREFIX + "prop.allowChiseledBlockMultiInsertion").getBoolean();

        chatty = config.get(Configuration.CATEGORY_GENERAL, "chatty", chatty,
                "If set the player will be informed in chat if something didn't work (if possible).")
                .setLanguageKey(LANG_PREFIX + "prop.chatty").getBoolean();

        if (config.hasChanged())
            config.save();
    }
}
