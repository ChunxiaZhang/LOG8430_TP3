package testcommand;

import tp3.plugin.commands.Command;
import tp3.plugin.commands.CommandResponse;
import tp3.plugin.models.Receiver;

public class testCommand implements Command {

	@Override
	public CommandResponse execute(Receiver receiver) {
		System.out.println("Executing command!");
		return null;
	}

}
