# ShopAds
In any economy, advertising is a wonderful tool to get things moving, now you can get it in your servers economy, without all the spammy messing!

Shop Ads lets your players pay in-game to have the server broadcast an ad for their shop or business. Optionally you can then have others warp to that shop, check it out and be popped back where they were before once they have had a good look.

Players pay depending on how many hours they want their ad to run for and Shop Ads handles all warps and costs.

---
## Contents
 - [Configuration](#Configuration)
 - [Permissions](#Permissions)
 - [Authors](#Authors)
 - [License](#License)
---

## Configuration

| Option             | Default | Type                 | Description                                                                        |
|--------------------|---------|----------------------|------------------------------------------------------------------------------------|
| shopsPerPlayer     | 1       | Int                  | Maximum number of ads allowed to each player.                                      |
| adCost             | 20      | Double/Currency      | Cost per hour of advertising.                                                      |
| maxAdRunTime       | 24      | Int/Hours            | Longest time an ad can run for.                                                    |
| announceRadius     | 0       | Int/Blocks           | Distance in blocks that an advertisement will be heard (0 for unlimited)           |
| announceInterval   | 240     | Int/Seconds          | Time in seconds between ad announcements.                                          |
| sendToAll          | true    | Bool (true/false)    | Whether to send to all players, disregarding their choice.                         |
| randomOrder        | false   | Bool (true/false)    | Should the ads be in a random order.                                               |
| adsOverWorlds      | true    | Bool (true/false)    | Whether ads are broadcasted over to other worlds.                                  |
| enableTp           | true    | Bool (true/false)    | Allows or denies the tp of players to ad locations.                                |
| tpTimeout          | 60      | Int/Seconds          | Time in seconds until player will be returned to previous location (0 to disable). |
| tpCost             | 0       | Double/Currency      | Price to charge to teleport (0 for free).                                          |
| transWorldAddition | 0       | Double/Currency      | Additional fee to tp to a shop in a different world (0 to disable).                |
| tpCostDestination  | shop    | String (shop/server) | Who receives the money from tp's.                                                  |
| setLocationCost    | 0       | Double/Currency      | Cost to change the location of a shop.                                             |
| setWorldCost       | 0       | Double/Currency      | Cost to add a world to advertise a shop in.                                        |
| setShopColorCost   | 0       | Double/Currency      | Cost to change the display color of the shop label.                                |
| setAdColorCost     | 0       | Double/Currency      | Cost to change the color of the ad.                                                |
| setNameCost        | 0       | Double/Currency      | Cost to change a shops name.                                                       |
| setAdCost          | 0       | Double/Currency      | Cost to change an ad message of a shop.                                            |
| labelColor         | Gold    | String/Color         | Color of both the label of ShopAds messages and shop messages.                     |
| messageColor       | Gray    | String/Color         | Color of both the ShopAds messages and shop messages.                              |
| defaultShopColor   | Gold    | String/Color         | Default shop label color.                                                          |
| defaultAdColor     | Gray    | String/Color         | Default ad message color.                                                          |

<br />
<table class="tg">
<thead>
  <tr>
    <th class="tg-73oq">Color Name</th>
    <th class="tg-73oq">Minecraft Color Code</th>
  </tr>
</thead>
<tbody>
  <tr>
    <td class="tg-73oq">Black</td>
    <td class="tg-73oq">§0</td>
  </tr>
  <tr>
    <td class="tg-73oq">Dark Blue</td>
    <td class="tg-73oq">§1</td>
  </tr>
  <tr>
    <td class="tg-73oq">Dark Green</td>
    <td class="tg-73oq">§2</td>
  </tr>
  <tr>
    <td class="tg-73oq">Teal</td>
    <td class="tg-73oq">§3</td>
  </tr>
  <tr>
    <td class="tg-73oq">Dark Red</td>
    <td class="tg-73oq">§4</td>
  </tr>
  <tr>
    <td class="tg-73oq">Purple</td>
    <td class="tg-73oq">§5</td>
  </tr>
  <tr>
    <td class="tg-73oq">Gold</td>
    <td class="tg-73oq">§6</td>
  </tr>
  <tr>
    <td class="tg-73oq">Gray</td>
    <td class="tg-73oq">§7</td>
  </tr>
  <tr>
    <td class="tg-73oq">Dark Gray</td>
    <td class="tg-73oq">§8</td>
  </tr>
  <tr>
    <td class="tg-73oq">Blue</td>
    <td class="tg-73oq">§9</td>
  </tr>
  <tr>
    <td class="tg-73oq">Bright Green</td>
    <td class="tg-73oq">§a</td>
  </tr>
  <tr>
    <td class="tg-73oq">Aqua</td>
    <td class="tg-73oq">§b</td>
  </tr>
  <tr>
    <td class="tg-73oq">Red</td>
    <td class="tg-73oq">§c</td>
  </tr>
  <tr>
    <td class="tg-73oq">Light Purple</td>
    <td class="tg-73oq">§d</td>
  </tr>
  <tr>
    <td class="tg-73oq">Yellow</td>
    <td class="tg-73oq">§e</td>
  </tr>
  <tr>
    <td class="tg-73oq">White</td>
    <td class="tg-73oq">§f</td>
  </tr>
</tbody>
</table>

---

## Commands

| Usage                                      | Description                      | Permission                                  |
|--------------------------------------------|----------------------------------|---------------------------------------------|
| /ads create\|c <shopname> <hrs> <message>  | Creates a new ad                 | shopads.create                              |
| /ads set <shopname> loc                    | Modify the location of an ad     | shopads.set.self \| shopads.set.other       |
| /ads set <shopname> <name\|ad> <value>     | Modify the name/message of an ad | shopads.set.self \| shopads.set.other       |
| /ads delete\|del <shopname>                | Delete an ad/shop                | shopads.delete.self \| shopads.delete.other |
| /ads stats\|stat [player]                  | View stats for a player's ads    | shopads.stats.self \| shopads.stats.other   |
| /ads rates                                 | List current hourly ad cost      | shopads.commands.ads                        |
| /ads list                                  | List all ads                     | shopads.commands.ads                        |
| /ads config                                | List config options              | shopads.config                              |
| /ads config <key> <value>                  | Set a config option              | shopads.config                              |
| /ads reload\|rel                           | Reload the plugin                | shopads.reload                              |
| /ads disable                               | Disable the plugin               | shopads.disable                             |
| /shops                                     | List all shops                   | shopads.commands.shops                      |
| /shops <shop>                              | Teleport to a shop               | shopads.commands.shops                      |

---

## Permissions

<table class="tg">
<thead>
  <tr>
    <th class="tg-0pky">Permission</th>
    <th class="tg-0pky">Description</th>
    <th class="tg-0pky">Children</th>
    <th class="tg-0pky">Default</th>
  </tr>
</thead>
<tbody>
  <tr>
    <td class="tg-0pky">shopads.*</td>
    <td class="tg-0pky">All permissions</td>
    <td class="tg-0pky">shopads.user<br>shopads.admin</td>
    <td class="tg-0pky"></td>
  </tr>
  <tr>
    <td class="tg-0pky">shopads.user</td>
    <td class="tg-0pky">User permissions</td>
    <td class="tg-0pky">shopads.commands.ads<br>shopads.commands.shops<br>shopads.create<br>shopads.delete.self<br>shopads.stats.self<br>shopads.set.self</td>
    <td class="tg-0pky">true</td>
  </tr>
  <tr>
    <td class="tg-0pky">shopads.admin</td>
    <td class="tg-0pky">Admin permissions</td>
    <td class="tg-0pky">shopads.time.unlimited<br>shopads.count.unlimited<br>shopads.delete.other<br>shopads.stats.other<br>shopads.set.other<br>shopads.reload<br>shopads.config<br>shopads.disable</td>
    <td class="tg-0pky">op</td>
  </tr>
  <tr>
    <td class="tg-0pky">shopads.commands.ads</td>
    <td class="tg-0pky">Allows use of /ads</td>
    <td class="tg-0pky"></td>
    <td class="tg-0pky"></td>
  </tr>
  <tr>
    <td class="tg-0pky">shopads.commands.shops</td>
    <td class="tg-0pky">Allows use of /shops</td>
    <td class="tg-0pky"></td>
    <td class="tg-0pky"></td>
  </tr>
  <tr>
    <td class="tg-0pky">shopads.create</td>
    <td class="tg-0pky">Creation of shop ads</td>
    <td class="tg-0pky"></td>
    <td class="tg-0pky"></td>
  </tr>
  <tr>
    <td class="tg-0pky">shopads.delete.self</td>
    <td class="tg-0pky">Deletion of YOUR ads</td>
    <td class="tg-0pky"></td>
    <td class="tg-0pky"></td>
  </tr>
  <tr>
    <td class="tg-0pky">shopads.delete.other</td>
    <td class="tg-0pky">Deletion of OTHERS ads</td>
    <td class="tg-0pky"></td>
    <td class="tg-0pky"></td>
  </tr>
  <tr>
    <td class="tg-0pky">shopads.stats.self</td>
    <td class="tg-0pky">Viewing YOUR stats</td>
    <td class="tg-0pky"></td>
    <td class="tg-0pky"></td>
  </tr>
  <tr>
    <td class="tg-0pky">shopads.stats.other</td>
    <td class="tg-0pky">Viewing OTHERS stats</td>
    <td class="tg-0pky"></td>
    <td class="tg-0pky"></td>
  </tr>
  <tr>
    <td class="tg-0pky">shopads.set.self</td>
    <td class="tg-0pky">Modifying YOUR ads</td>
    <td class="tg-0pky"></td>
    <td class="tg-0pky"></td>
  </tr>
  <tr>
    <td class="tg-0pky">shopads.set.other</td>
    <td class="tg-0pky">Modifying OTHERS ads</td>
    <td class="tg-0pky"></td>
    <td class="tg-0pky"></td>
  </tr>
  <tr>
    <td class="tg-0pky">shopads.time.unlimited</td>
    <td class="tg-0pky">Unlimited run time on YOUR ads</td>
    <td class="tg-0pky"></td>
    <td class="tg-0pky"></td>
  </tr>
  <tr>
    <td class="tg-0pky">shopads.count.unlimited</td>
    <td class="tg-0pky">Unlimited ads</td>
    <td class="tg-0pky"></td>
    <td class="tg-0pky"></td>
  </tr>
  <tr>
    <td class="tg-0pky">shopads.reload</td>
    <td class="tg-0pky">Reloads this plugin</td>
    <td class="tg-0pky"></td>
    <td class="tg-0pky"></td>
  </tr>
  <tr>
    <td class="tg-0pky">shopads.config</td>
    <td class="tg-0pky">Configures this plugin</td>
    <td class="tg-0pky"></td>
    <td class="tg-0pky"></td>
  </tr>
  <tr>
    <td class="tg-0pky">shopads.disable</td>
    <td class="tg-0pky">Disables this plugin</td>
    <td class="tg-0pky"></td>
    <td class="tg-0pky"></td>
  </tr>
</tbody>
</table>

---

## Authors

<table>
    <tr>
        <td align="center"><a href="https://github.com/nofishleft"><img src="https://avatars.githubusercontent.com/u/38971061" width="100px;" alt=""/><br /><sub><b>Rishaan Gupta</b></sub></a><br /><sub><b>Maintainer</b></sub></td>
        <td align="center"><a href="https://github.com/hpiz"><img src="https://avatars0.githubusercontent.com/u/780390" width="100px;" alt=""/><br /><sub><b>Hpiz</b></sub></a><br /><sub><b>Former Author</b></sub></td>
    </tr>
</table>

---

## License

TO DO

---

[nofishleft-github]: https://github.com/nofishleft
[hpiz-github]: https://github.com/hpiz