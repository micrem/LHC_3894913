package Infrastructure.Security.IDCard;

public class IDCardv2 extends IDCard implements IROIDCardMultichip, IIDCardMultichip {
    protected Chip chipFingerprint;

    public IDCardv2() {
        super();
        version = IDCardVersion.MultiChip;
        chipFingerprint = new Chip();
    }

    @Override
    public void writeFingerprintData(String fingerprint) {
        chipFingerprint.setData(fingerprint);
    }

    @Override
    public String getFingerprint(ICardReader iCardReader) {
        return chipFingerprint.getData();
    }

    @Override
    public IROIDCardMultichip getMultichipReadAccess(ICardReader cardReader) {
        return this;
    }

    @Override
    public IIDCardMultichip getMultichipWriteAccess(ICardWriter cardWriter) {
        return this;
    }
}
