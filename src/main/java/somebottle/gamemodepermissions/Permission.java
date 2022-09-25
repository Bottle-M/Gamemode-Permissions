package somebottle.gamemodepermissions;

import org.bukkit.entity.Player;

public final class Permission {
    /**
     * 检查用户是否拥有权限节点
     * 比如nodeStr="cmd.say"，就会检查:
     * minecraft.command.cmd,
     * ^minecraft.command.cmd, (排除权限)
     * minecraft.command.cmd.*,
     * ^minecraft.command.cmd.*, (排除权限)
     * minecraft.command.cmd.say,
     * ^minecraft.command.cmd.say, (排除权限)
     * minecraft.command.cmd.say.*
     * ^minecraft.command.cmd.say.* (排除权限)
     *
     * @param player  Player对象(发送命令的玩家)
     * @param nodeStr 待检查权限节点
     * @return 返回布尔值，代表是否成功
     */
    boolean test(Player player, String nodeStr) {
        // 先将节点字符串分割成节点数组
        String[] nodeArr = nodeStr.split("\\.");
        // 节点测试匹配字符串
        String testNodes = "minecraft.command";
        // 排除节点测试匹配字符串
        String testNegNodes = "^" + testNodes;
        // 是否匹配到了权限
        boolean matched = false;
        // 是否测试到了最后一个节点
        boolean testingLastNode;
        // 遍历数组
        for (int i = 0, len = nodeArr.length; i < len; i++) {
            String node = nodeArr[i];
            testingLastNode = (i == len - 1);
            testNodes = testNodes + "." + node;
            testNegNodes = testNegNodes + "." + node;
            if (testingLastNode && player.hasPermission(testNegNodes)) {
                // 如果当前检查的节点被排除了(negative)
                return false; // 玩家没有权限
            } else if (player.hasPermission(testNegNodes + ".*")) {
                // 如果当前检查的节点加上通配符被排除了(negative)
                return false; // 玩家没有权限
            } else if (!matched) {
                // 没有匹配的时候才进行正向(positive)测试，已经匹配了就只进行逆向(negative)匹配
                if (testingLastNode && player.hasPermission(testNodes)) {
                    // 如果当前检查的节点能匹配到权限，且已经检查到了最后一个节点，说明用户拥有该权限
                    matched = true;
                } else if (player.hasPermission(testNodes + ".*")) {
                    // 如果当前检查的节点加上通配符能匹配到权限，说明用户拥有该节点下的所有权限
                    matched = true;
                }
            }
        }
        return matched;
    }
}
