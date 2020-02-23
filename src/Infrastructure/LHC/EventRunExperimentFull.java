package Infrastructure.LHC;

public class EventRunExperimentFull {
    private InitialEnergy initialEnergy;

    public EventRunExperimentFull(InitialEnergy initialEnergy) {
        setInitialEnergy(initialEnergy);
    }

    public InitialEnergy getInitialEnergy() {
        return initialEnergy;
    }

    public void setInitialEnergy(InitialEnergy initialEnergy) {
        this.initialEnergy = initialEnergy;
    }

    public ExperimentScope getScope() {
        return ExperimentScope.ESFull;
    }
}
