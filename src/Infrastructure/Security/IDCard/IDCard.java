package Infrastructure.Security.IDCard;

import HumanResources.Person;
import Infrastructure.Security.Permission;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

public class IDCard implements IIDCard, IROIDCard, IIDCardPWedit, IIDCardLockAccess {
    static int idCounter = 0;
    private final int id;
    protected IDCardVersion version = IDCardVersion.SingleChip;
    private Person person;
    private Chip chipPassword;
    private LocalDate validFrom;
    private LocalDate validTo;
    private int[][] irisStructure = new int[10][10];
    private ArrayList<Permission> permissionList = new ArrayList<>();
    private boolean isLocked;


    public IDCard() {
        id = idCounter++;
        chipPassword = new Chip();
    }

    @Override
    public Person getPerson() {
        return person;
    }

    @Override
    public void setPerson(Person person) {
        this.person = person;
    }

    @Override
    public LocalDate getValidFrom() {
        return validFrom;
    }

    @Override
    public void setValidFrom(LocalDate validFrom) {
        this.validFrom = validFrom;
    }

    @Override
    public LocalDate getValidTo() {
        return validTo;
    }

    @Override
    public void setValidTo(LocalDate validTo) {
        this.validTo = validTo;
    }

    @Override
    public int[][] getIrisStructure() {
        return irisStructure;
    }

    @Override
    public void setIrisStructure(int[][] irisStructureInput) {
        this.irisStructure = Arrays.stream(irisStructureInput).map(int[]::clone).toArray(int[][]::new);
    }

    @Override
    public boolean isLocked() {
        return isLocked;
    }

    @Override
    public void setLocked(boolean locked) {
        isLocked = locked;
    }

    @Override
    public void lock() {
        setLocked(true);
    }

    @Override
    public void setPermission(Permission permission, boolean allow) {
        if (permissionList.contains(permission)) {
            if (!allow) permissionList.remove(permission);
        } else {
            if (allow) permissionList.add(permission);
        }
    }

    @Override
    public IIDCard grantWriteAccess(ICardWriter cardWriter) {
        //verify ICardWriter compatibility
        if (cardWriter == null) return null;
        return this;
    }

    @Override
    public IIDCardPWedit grantPasswordChangeAccess(ICardReader cardReader) {
        if (cardReader == null) return null;
        return this;
    }

    @Override
    public IIDCardLockAccess grantLockAccess(ICardReader cardReader) {
        if (cardReader == null) return null;
        return this;
    }

    @Override
    public String getPassword() {
        return chipPassword.getData();
    }

    @Override
    public void setPassword(String password) {
        this.chipPassword.setData(password);
    }

    @Override
    public boolean hasPermission(Permission permission) {
        return permissionList.contains(permission);
    }

    @Override
    public void setNewPassword(String newPassword) {
        setPassword(newPassword);
    }

    @Override
    public IDCardVersion getVersion() {
        return version;
    }

    @Override
    public IROIDCardMultichip getMultichipReadAccess(ICardReader cardReader) {
        if (cardReader == null) return null;
        return null;
    }

    @Override
    public IIDCardMultichip getMultichipWriteAccess(ICardWriter cardWriter) {
        if (cardWriter == null) return null;
        return null;
    }

    @Override
    public Integer getID() {
        return id;
    }
}
