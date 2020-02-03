package Infrastructure.LHC;

public class EventRunExperimentFull {
    public InitialEnergy getInitialEnergy() {
        return initialEnergy;
    }

    public void setInitialEnergy(InitialEnergy initialEnergy) {
        this.initialEnergy = initialEnergy;
    }

    private InitialEnergy initialEnergy;
    public EventRunExperimentFull(InitialEnergy initialEnergy) {
        setInitialEnergy(initialEnergy);
    }

    public ExperimentScope getScope() {
        return ExperimentScope.ESFull;
    }
}
