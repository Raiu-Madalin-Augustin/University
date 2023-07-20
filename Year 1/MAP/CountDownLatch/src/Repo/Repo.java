package Repo;

import Model.Exceptions.RepositoryException;
import Model.PrgState;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;


public class Repo implements  IRepo{

    List<PrgState> myPrgStates;
    PrgState program;
    String logFilePath;


    public Repo(PrgState initial_state,String logfile){
        this.myPrgStates= new ArrayList<PrgState>();
        this.logFilePath=logfile;
        program=initial_state;
        myPrgStates.add(program);
    }


    @Override
    public PrgState getCrtPrg() {

        return myPrgStates.get(myPrgStates.size()-1);
    }

    @Override
    public void setCrtProgram(PrgState program){myPrgStates.set(myPrgStates.size()-1,program);}

    @Override
    public List<PrgState> getPrgStateList(){return myPrgStates;}

    @Override
    public void addPrg(PrgState newPrg){ myPrgStates.add(newPrg);}

    @Override
    public void setProgramStateList(List<PrgState> prg){myPrgStates=prg;}

    @Override
    public int size(){return myPrgStates.size();}

    @Override
    public void logPrgStateExec(PrgState state) throws Exception {
        PrintWriter writer;
        try {
            writer = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, true)));
        } catch (IOException e) {
            throw new Exception("ERROR: file exception, creating a file writer on the given file path failed\n");
        }
        writer.write(state.toString());
        writer.flush();
        writer.close();
    }
}
