package Controller;

import Model.Exceptions.*;
import Model.PrgState;
import Model.adt.Heap;
import Model.value.IValue;
import Model.value.RefValue;
import Repo.IRepo;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Controller {
    private IRepo repository;
    private String execution_logs;
    private ExecutorService executor;


    public Controller(IRepo repository)
    {
        this.repository=repository;
        this.execution_logs="";

    }
    public String getExecution_logs() {
            return repository.getCrtPrg().toString2();
        }

    public void addProgram(PrgState program)
    {
        repository.addPrg( program);
        this.execution_logs="";

    }
    public IRepo getRepository(){return repository;}
    public String logs(){return execution_logs;}

    private void resetLogs(){execution_logs="";

    }
    List<PrgState> removeCompletedProgram(List<PrgState> inProgramList){
        return inProgramList.stream()
                .filter(PrgState::isNotCompleted)
                .collect(Collectors.toList());
    }

    private List<Integer> getAddressesFromSymbolsTable(Collection<IValue> symTableValues) {
        return symTableValues.stream()
                .filter(v -> v instanceof RefValue)
                .map(v -> { RefValue v_ref = (RefValue) v;
                    return v_ref.getAddress();})
                .collect(Collectors.toList());
    }

    private List<Integer> getAddressesReferencedByHeapVariables(Collection<IValue> heapTableValues) {
        return heapTableValues.stream()
                .filter(v -> v instanceof RefValue)
                .map(v -> ((RefValue) v).getAddress())
                .collect(Collectors.toList());
    }
    private Map<Integer, IValue> unsafeGarbageCollector(List<Integer> symbols_table_addresses,Map<Integer,IValue>heap)
    {
        return heap.entrySet().stream().
                filter(dict_elem->symbols_table_addresses.contains(dict_elem.getKey())).
                collect(Collectors.toMap(Map.Entry::getKey,Map.Entry::getValue));

    }
    private Map<Integer, IValue> safeGarbageCollector(List<Integer> symbols_table_addresses,List<Integer>heapAddr, Map<Integer, IValue> heap) {
        return heap.entrySet().stream()
                .filter(e->symbols_table_addresses.contains(e.getKey())|| heapAddr.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey,Map.Entry::getValue));
    }

    public void oneStepForAllPrograms(List<PrgState> prgList)throws ControllerException{
        prgList.forEach(prg -> {
            try {
                repository.logPrgStateExec(prg);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        });
        List<Callable<PrgState>> callList = prgList.stream()
                .map((PrgState prg) -> (Callable<PrgState>)(prg::oneStepExecution))
                .collect(Collectors.toList());
        List<PrgState> newPrgList;
        try {
            newPrgList = executor.invokeAll(callList).stream()
                    .map(future -> {
                        try {
                            return future.get();
                        } catch (InterruptedException | ExecutionException e) {
                            Throwable exception = e.getCause();
                            if (exception instanceof MyException) {
                                System.out.println(exception.getMessage());
                            }
                        }
                        return null;
                    })
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
            prgList.addAll(newPrgList);
            prgList.forEach(prg -> {
                try {
                    repository.logPrgStateExec(prg);
                } catch ( Exception e) {
                    System.out.println(e.getMessage());
                }
            });
        } catch (InterruptedException e) {
            System.out.println("ERROR: the execution was interrupted");
        }
        repository.setProgramStateList(prgList);
    }

    public void executeOneStep()
    {
        executor = Executors.newFixedThreadPool(8);
        removeCompletedProgram(repository.getPrgStateList());
        List<PrgState> programStates = repository.getPrgStateList();
        if(programStates.size() > 0)
        {
            try {
                oneStepForAllPrograms(repository.getPrgStateList());
            } catch (ControllerException e) {
                System.out.println();
            }
            programStates.forEach(e -> {
                try {
                    repository.logPrgStateExec(e);
                } catch (Exception e1) {
                    System.out.println();
                }
            });
            removeCompletedProgram(repository.getPrgStateList());
            executor.shutdownNow();
        }
    }
    public void allStepsExecution(boolean displayFlag) throws ControllerException{
        int step = 0;
        executor = Executors.newFixedThreadPool(2);
        List<PrgState> prgList = removeCompletedPrograms(repository.getPrgStateList());
        while(prgList.size() > 0) {
            List<Integer> symTableAddr = repository.getPrgStateList().stream()
                    .map(p -> getAddressesFromSymbolsTable(p.getSymTable().getContent().values()))
                    .reduce(new ArrayList<>(), (p1, p2) -> Stream.concat(p1.stream(), p2.stream())
                            .distinct().collect(Collectors.toList()));
            Heap heap = repository.getPrgStateList().get(0).getHeap();
            List<Integer> heapAddr = getAddressesReferencedByHeapVariables(heap.getContent().values());
            Map<Integer, IValue> newHeap = safeGarbageCollector(symTableAddr, heapAddr, heap.getContent());
//            heap.setContent(newHeap);
            oneStepForAllPrograms(prgList);
            if(displayFlag) {
                displayCrtState(step);
                step = step + 1;
            }
            prgList = removeCompletedPrograms(repository.getPrgStateList());
        }
        executor.shutdownNow();
        repository.setProgramStateList(prgList);
    }

    List<PrgState> removeCompletedPrograms(List<PrgState> input) {
        return input.stream().filter(PrgState::isNotCompleted)
                .collect(Collectors.toList());
    }


    public void displayCrtState(int step) {
        System.out.print("-----------------------------------------------Step ");
        System.out.print(step);
        System.out.println("---------------------------------------------------------------------------------------------------------------------");
        repository.getPrgStateList().forEach(System.out::println);
    }

        public String getProgramName(){return "Name";}

}
