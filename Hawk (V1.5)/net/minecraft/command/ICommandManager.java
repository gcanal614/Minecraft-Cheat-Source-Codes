package net.minecraft.command;

import java.util.List;
import java.util.Map;
import net.minecraft.util.BlockPos;

public interface ICommandManager {
   Map getCommands();

   int executeCommand(ICommandSender var1, String var2);

   List getPossibleCommands(ICommandSender var1);

   List getTabCompletionOptions(ICommandSender var1, String var2, BlockPos var3);
}
