package duke.commands;

import duke.DukeException;
import duke.Storage;
import duke.Task;
import duke.TaskList;
import duke.Ui;

public class DeleteCommand extends Command {

    public DeleteCommand(String description) {
        super(description);
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws DukeException {
        try {
            int index = Integer.parseInt(description.substring(7)) - 1;
            Task task = tasks.get(index);
            tasks.delete(index);
            ui.printDeleteTask(task, tasks.size());
        } catch (IndexOutOfBoundsException e) {
            throw new DukeException("OOPS!!! You cannot delete a non-existent task.");
        }
    }
}
