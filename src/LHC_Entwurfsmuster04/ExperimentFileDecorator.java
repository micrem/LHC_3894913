package LHC_Entwurfsmuster04;

import Infrastructure.LHC.Experiment;
import Infrastructure.LHC.IExperiment;

public abstract class ExperimentFileDecorator extends Experiment {
    private IExperiment experiment;

    public ExperimentFileDecorator(IExperiment experiment) {
        this.experiment = experiment;
    }

    @Override
    public int getProton01ID() {
        return this.experiment.getProton01ID();
    }

    @Override
    public void setProton01ID(int proton01ID) {
        super.setProton01ID(proton01ID);
        this.experiment.setProton01ID(proton01ID);
    }

    @Override
    public int getProton02ID() {
        return this.experiment.getProton02ID();
    }

    @Override
    public void setProton02ID(int proton02ID) {
        super.setProton02ID(proton02ID);
        this.experiment.setProton02ID(proton02ID);
    }
}
