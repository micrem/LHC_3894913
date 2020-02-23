package Infrastructure.LHC;

public class EventRunExperimentPartial {
    private ExperimentScope scope;
    private InitialEnergy initialEnergy;

    public EventRunExperimentPartial(ExperimentScope scope, InitialEnergy initialEnergy) {
        setScope(scope);
        setInitialEnergy(initialEnergy);
    }

    public ExperimentScope getScope() {
        return scope;
    }

    public void setScope(ExperimentScope scope) {
        this.scope = scope;
    }

    public InitialEnergy getInitialEnergy() {
        return initialEnergy;
    }

    public void setInitialEnergy(InitialEnergy initialEnergy) {
        this.initialEnergy = initialEnergy;
    }
}
