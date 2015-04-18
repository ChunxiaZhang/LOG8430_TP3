package tp3.plugin.commands;

import tp3.plugin.models.Receiver;

public class Invoker {
	public Invoker() {
	}

	public CommandResponse execute(Command cmd,Receiver receiver) {
		return cmd.execute(receiver);        
	}
}
