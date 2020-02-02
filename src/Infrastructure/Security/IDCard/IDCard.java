package Infrastructure.Security.IDCard;

import HumanResources.Person;
import Infrastructure.Security.Permission;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

public class IDCard implements IIDCard, IROIDCard {
    static int idCounter=0;

    private Person person;
    private Chip chip;

    private final int id;
    private LocalDate validFrom;
    private LocalDate validTo;
    private int[][] irisStructure = new int[10][10];
    private ArrayList<Permission> permissionList = new ArrayList<>();
    private boolean isLocked;



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
    public void setPassword(String password) {
        this.chip.setPassword(password);
    }

    @Override
    public void setPermission(Permission permission, boolean allow) {
        if (permissionList.contains(permission)){
            if (!allow) permissionList.remove(permission);
        } else {
            if (allow) permissionList.add(permission);
        }
    }

    @Override
    public IIDCard grantWriteAccess(ICardWriter cardWriter) {
        //verify ICardWriter compatibility
        return this;
    }

    @Override
    public String getPassword() {
        return chip.getPassword();
    }

    @Override
    public boolean hasPermission(Permission permission) {
        return permissionList.contains(permission);
    }

    public IDCard() {
        id = idCounter++;
        chip = new Chip();
    }
}
