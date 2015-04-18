package tp3.plugin.commands;

import tp3.plugin.models.Receiver;

public interface Command {
	CommandResponse execute(Receiver receiver);
}
