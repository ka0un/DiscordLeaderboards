package org.kasun.discordleaderboards.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainConfigNewKeysList {
    public List<String> newKeys;

    public MainConfigNewKeysList() {
        newKeys = new ArrayList<>(Arrays.asList(
                "storage-method",
                "address",
                "database",
                "username",
                "password",
                "scheduledelaymins",
                "leaderboards",
                "webhook-url",
                "webhook-avatar-url",
                "webhook-user-name",
                "embed-title",
                "embed-url",
                "embed-colour",
                "embed-footer",
                "embed-image",
                "embed-thumbnail",
                "pluginversion",
                "firsttime",
                "discordsrvslashcommand"
        ));
    }

    public List<String> getNewKeys() {
        return newKeys;
    }
}
