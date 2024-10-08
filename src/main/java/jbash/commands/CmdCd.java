package jbash.commands;

import jbash.environment.JProcess;
import jbash.filesystem.File;
import jbash.filesystem.FileSystemAPI;

import java.util.List;

class CmdCd extends Command {
    CmdCd(String name, JProcess parent) {
        super(name, parent);
    }

    @Override
    public String getHelp() {
        return "Usage: cd [directory]";
    }

    @Override
    public int execute(List<String> argv) {
        if (argv.size() > 1) { err("too many arguments"); return -1; }
        if (argv.isEmpty()) {
            FileSystemAPI.getInstance().moveCurrentDirectory("");
            parent.env.set("PWD", FileSystemAPI.getInstance().getCurrentDirectory().getPath());
            return 0;
        }
        if (FileSystemAPI.getInstance().moveCurrentDirectory(argv.getFirst())) {
            parent.env.set("PWD", FileSystemAPI.getInstance().getCurrentDirectory().getPath());
            return 0;
        }

        // FIXME: I'm not sure what other error messages there are but surely there's more than this
        err("No such file or directory");
        return -1;
    }
}
