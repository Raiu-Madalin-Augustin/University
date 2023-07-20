package Repo;

import Model.Exceptions.RepositoryException;
import Model.PrgState;

import java.util.List;

public interface IRepo {
    void addPrg(PrgState newPrg);
    PrgState getCrtPrg();
    void setCrtProgram(PrgState program);
    List<PrgState> getPrgStateList();
    void setProgramStateList(List<PrgState> program_state_list);
    int size();
    void logPrgStateExec(PrgState state) throws Exception;

}
