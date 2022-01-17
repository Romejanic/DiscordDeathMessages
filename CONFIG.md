# DiscordDeathMessages Configuration File
The config file is located in the `plugins/DiscordDeathMessages/config.json` file in your server directory.

## Options
As of version 1.1.1, the available configuration options are:

|Option|Type|Description|
|------|----|-----------|
|webhookURL|String|The URL of the webhook on Discord which messages are sent to. You shouldn't need to touch this, you should instead use `/ddmset` in game or in the server console.|
|deathMottos|List of strings|A list of motto messages which are randomly selected from when a player dies unless that player changes their motto with `/ddmmotto`. You can add or remove mottos in this config file.|
|blockedWords|List of strings|A list of banned words from player mottos. This is a basic find and replace system so it may not cover all bases but anything you want to be blocked can be placed here. [See the example on the plugin page](https://www.spigotmc.org/resources/discorddeathmessages.93644/).|
|preventCaching|Boolean (true/false)|Whether to prevent Discord from caching player head and skin images. Making this true will make skin changes more responsive, but it may cause images to take longer to load.|
|showTeam|Boolean (true/false)|Whether to display the player's team in the death messages. This will use the display name defined in Minecraft without any colour codes. [Learn more about teams here](https://minecraft.fandom.com/wiki/Scoreboard#Teams).|
|teamLabel|String|If `showTeam` is true, this is the label above the team which will be displayed. Change this if you want the teams to be called something other than teams (e.g. 'Faction').|
|showPetDeaths|Boolean (true/false)|Whether or not to send death messages for tamed pets in game.|
|showUpdatesInGame|Boolean (true/false)|Whether or not to show update notifications in game to players with the `ddm.updates` permission. If this is set to false, the plugin will however still check for updates and will still send notifications to the console. It will also still notify players if an urgent update is required.|

## Default Config
This the the default configuration file as of version 1.1.1 in case you need a reference or the default values.

```json
{
  "webhookURL": null,
  "deathMottos": [
    "Ouch!",
    "Rest In Peace",
    "Yikes",
    "You Died",
    "I Am Dead",
    "Destroyed!",
    "Rekt!",
    "GG",
    "You're Fired!",
    "Git Gud",
    "Killed!",
    "Slaughtered!",
    "Owned!",
    "Goodbye!"
  ],
  "blockedWords": [],
  "preventCaching": true,
  "showTeam": false,
  "teamLabel": "Team",
  "showPetDeaths": true,
  "showUpdatesInGame": true
}
```