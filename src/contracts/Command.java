package contracts;

public interface Command {
    void runCommand(String[] option) throws Exception;
}
